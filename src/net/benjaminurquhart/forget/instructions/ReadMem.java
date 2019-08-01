package net.benjaminurquhart.forget.instructions;

import net.benjaminurquhart.forget.memory.Cache;
import net.benjaminurquhart.forget.memory.Pointer;
import net.benjaminurquhart.forget.memory.RAM;

public class ReadMem extends Instruction {

	private Pointer pointer;
	
	public ReadMem(Pointer pointer) {
		this.pointer = pointer;
	}
	
	@Override
	public void execute() {
		Cache.CURRENT = RAM.readMemory(pointer);
		Cache.CURRENT_PTR = pointer;
	}

}
