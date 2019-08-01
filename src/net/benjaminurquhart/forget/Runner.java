package net.benjaminurquhart.forget;

import java.util.ArrayList;
import java.util.List;

import net.benjaminurquhart.forget.instructions.*;
import net.benjaminurquhart.forget.memory.PointerStack;
import net.benjaminurquhart.forget.memory.RAM;

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
		for(int i = 0; i < instructions.length; i++) {
			try {
				if(instructions[i] == null) continue;
				
				// While loops
				if(instructions[i] instanceof Condition && !((Condition)instructions[i]).evaluate()) {
					depth = 1;
					start = i++;
					while(depth > 0) {
						if(instructions[i] instanceof Jump) depth--;
						if(instructions[i] instanceof While) depth++;
						i++;
					}
				}
				// Other instructions
				else {
					instructions[i].execute();
				}
				if(i >= instructions.length) break;
				
				// "end" statement
				if(instructions[i] instanceof Jump) {
					i = ((Jump)instructions[i]).getJump();
				}
				RAM.tick();
			}
			catch(IndexOutOfBoundsException e) {
				System.err.println("Ran out of instructions while finding closing \"end\" instruction");
				System.out.println("Offending loop:");
				System.err.println(instructions[start]);
			}
			catch(Exception e) {
				System.err.println("Exception at instruction "+i+":");
				System.err.println(instructions[i]);
				e.printStackTrace();
				break;
			}
		}
		
		// Print the stack and memory
		System.out.println("Stack: ");
		System.out.println(PointerStack.STACK);
		System.out.println("\nValues:");
		List<String> values = new ArrayList<>();
		while(!PointerStack.STACK.isEmpty()) {
			values.add(Integer.toHexString(RAM.forceRead(PointerStack.STACK.pop()).read()));
		}
		System.out.println(values);
		values.stream().map(c -> (char)Integer.parseInt(c,16)+"").forEach(System.out::print);
		System.out.println();
		System.out.println("\nMemory:");
		System.out.println(RAM.getRAMAsString());
	}
}
