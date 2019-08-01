package net.benjaminurquhart.forget.instructions;

import net.benjaminurquhart.forget.memory.Cache;
import net.benjaminurquhart.forget.memory.Pointer;
import net.benjaminurquhart.forget.memory.RAM;

public class PrintTop extends Instruction {

	@Override
	public void execute() {
		Pointer pointer = Cache.CURRENT_PTR;
		
		if(pointer == null) {
			pointer = RAM.malloc();
		}
		
		System.out.print((char)pointer.read());
	}
}
