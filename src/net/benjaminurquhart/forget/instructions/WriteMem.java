package net.benjaminurquhart.forget.instructions;

import net.benjaminurquhart.forget.memory.Cache;
import net.benjaminurquhart.forget.memory.Pointer;
import net.benjaminurquhart.forget.memory.RAM;

public class WriteMem extends Instruction {

	private Pointer pointer;
	private int value;
	
	public WriteMem(Pointer pointer, int value) {
		this.pointer = pointer;
		this.value = value;
	}
	
	@Override
	public void execute() {
		RAM.writeMemory(pointer, value);
		Cache.CURRENT_PTR = pointer;
		Cache.CURRENT = RAM.readMemory(pointer);
	}

}
