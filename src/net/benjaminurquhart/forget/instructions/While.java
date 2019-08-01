package net.benjaminurquhart.forget.instructions;

import net.benjaminurquhart.forget.memory.Pointer;
import net.benjaminurquhart.forget.memory.RAM;

public class While extends Condition {
	
	private Pointer pointer;
	
	public While(Pointer pointer) {
		this.pointer = pointer;
	}

	@Override
	public void execute() {
		this.value = RAM.readMemory(pointer).read() > 0;
	}
	
	@Override
	public String toString() {
		return "While[cond: "+pointer+" > 0]";
	}
}
