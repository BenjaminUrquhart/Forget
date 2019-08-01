package net.benjaminurquhart.forget.instructions;

import net.benjaminurquhart.forget.memory.Cache;
import net.benjaminurquhart.forget.memory.Pointer;

public class SanitizePointer extends Instruction {

	private Pointer pointer;
	
	public SanitizePointer(Pointer pointer) {
		this.pointer = pointer;
	}
	
	@Override
	public void execute() {
		if(pointer == null) pointer = Cache.CURRENT_PTR;
		pointer.disinfect();
	}

}
