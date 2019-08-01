package net.benjaminurquhart.forget.instructions;

import net.benjaminurquhart.forget.memory.Cache;
import net.benjaminurquhart.forget.memory.Pointer;
import net.benjaminurquhart.forget.memory.PointerStack;
import net.benjaminurquhart.forget.memory.RAM;

public class PushMem extends Instruction {
	
	private int value;
	
	public PushMem(int value) {
		this.value = value;
	}
	
	@Override
	public void execute() {
		Pointer pointer = RAM.malloc();
		RAM.writeMemory(pointer, value);
		Cache.CURRENT_PTR = pointer;
		PointerStack.STACK.push(pointer);
	}

}
