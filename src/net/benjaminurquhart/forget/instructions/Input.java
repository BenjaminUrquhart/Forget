package net.benjaminurquhart.forget.instructions;

import net.benjaminurquhart.forget.memory.Cache;
import net.benjaminurquhart.forget.memory.Pointer;
import net.benjaminurquhart.forget.memory.PointerStack;
import net.benjaminurquhart.forget.memory.RAM;
import net.benjaminurquhart.forget.memory.ShortTerm;

public class Input extends Instruction {

	private Pointer pointer;
	
	public Input(Pointer pointer) {
		this.pointer = pointer;
	}
	
	@Override
	public void execute() {
		try {
			if(pointer == null) pointer = RAM.malloc();
			RAM.writeMemory(pointer, new ShortTerm(System.in.read()));
			
			Cache.CURRENT_PTR = pointer;
			PointerStack.STACK.push(pointer);
		}
		catch(Exception e) {
			System.err.println("Warning, failed to read from standard input");
			e.printStackTrace();
		}
	}

}
