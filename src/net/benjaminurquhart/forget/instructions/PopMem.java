package net.benjaminurquhart.forget.instructions;

import net.benjaminurquhart.forget.memory.Cache;
import net.benjaminurquhart.forget.memory.PointerStack;
import net.benjaminurquhart.forget.memory.RAM;

public class PopMem extends Instruction {

	@Override
	public void execute() {
		Cache.CURRENT_PTR = PointerStack.STACK.pop();
		Cache.CURRENT = RAM.readMemory(Cache.CURRENT_PTR);
	}

}
