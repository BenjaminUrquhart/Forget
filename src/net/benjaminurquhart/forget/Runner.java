package net.benjaminurquhart.forget;

import java.util.ArrayList;
import java.util.List;

import net.benjaminurquhart.forget.instructions.*;
import net.benjaminurquhart.forget.memory.Cache;
import net.benjaminurquhart.forget.memory.Pointer;
import net.benjaminurquhart.forget.memory.PointerStack;
import net.benjaminurquhart.forget.memory.RAM;
import net.benjaminurquhart.forget.memory.ShortTerm;

public class Runner {

	private List<Instruction> instructions;
	
	public Runner(List<Instruction> instructions) {
		this.instructions = instructions;
	}
	public Runner(String program) {
		this.instructions = Parser.parse(program);
	}
	
	// Runs the given program
	public void run() {
		Instruction[] instructions = this.instructions.toArray(new Instruction[0]);
		
		int start = 0;
		int depth = 0;
		int res;
		
		Pointer tmp;
		Condition condition;
		
		for(int i = 0; i < instructions.length; i++) {
			try {
				if(instructions[i] == null) continue;
				
				//System.err.println(instructions[i]);
				// While loops
				if(instructions[i] instanceof Condition) {
					condition = (Condition)instructions[i];
					if(!condition.evaluate()) {
						depth = 1;
						start = i++;
						while(depth > 0) {
							if(instructions[i] instanceof Jump) depth--;
							if(instructions[i] instanceof Condition) depth++;
							i++;
						}
					}
				}
				else if(instructions[i] instanceof MathExpr) {
					res = ((MathExpr)instructions[i]).evaluate();
					tmp = RAM.malloc();
					RAM.writeMemory(tmp, new ShortTerm(res));
					Cache.CURRENT_PTR = tmp;
					PointerStack.STACK.push(tmp);
				}
				// Other instructions
				else {
					instructions[i].execute();
				}
				if(i >= instructions.length) break;
				
				// "end" statement
				if(instructions[i] instanceof Jump) {
					i = ((Jump)instructions[i]).getJump();
					i--;
				}
				RAM.tick();
			}
			catch(IndexOutOfBoundsException e) {
				System.err.println("Ran out of instructions while finding closing \"end\" instruction");
				System.out.println("Offending loop:");
				System.err.println(instructions[start]);
			}
			catch(Exception e) {
				System.err.println("\nException at instruction "+i+":");
				System.err.println(instructions[i]);
				e.printStackTrace();
				break;
			}
		}
		if(Forget.debug) {
			// Print the stack and memory
			System.out.println("\nStack: ");
			System.out.println(PointerStack.STACK);
			System.out.println("\nValues:");
			List<String> values = new ArrayList<>();
			while(!PointerStack.STACK.isEmpty()) {
			values.add("0x"+Integer.toHexString(RAM.forceRead(PointerStack.STACK.pop()).read()));
			}
			System.out.println(values);
			values.stream()
			  	  .map(c -> Character.toString((char)Integer.parseInt(c.substring(2),16)))
			  	  .map(s -> s.equals("\n") ? "\\n" : s)
			  	  .forEach(System.out::print);
			System.out.println();
			System.out.println("\nMemory:");
			System.out.println(RAM.getRAMAsString());
		}
	}
}
