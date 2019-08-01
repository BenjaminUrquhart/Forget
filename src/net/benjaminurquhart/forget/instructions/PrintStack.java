package net.benjaminurquhart.forget.instructions;

import net.benjaminurquhart.forget.memory.Cache;
import net.benjaminurquhart.forget.memory.Pointer;
import net.benjaminurquhart.forget.memory.PointerStack;

public class PrintStack extends Instruction {

	@Override
	public void execute() {
		Pointer pointer = Cache.CURRENT_PTR;
		if(pointer != null) System.out.print((char)pointer.read());
		
		while(!PointerStack.STACK.isEmpty()) {
			pointer = PointerStack.STACK.pop();
			System.out.print((char)pointer.read());
		}
	}

}
