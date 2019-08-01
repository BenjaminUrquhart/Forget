package net.benjaminurquhart.forget.exceptions;

import net.benjaminurquhart.forget.memory.Pointer;

public class Segfault extends RuntimeException {

	private static final long serialVersionUID = -7186298346164673819L;
	
	private Pointer pointer;
	
	public Segfault(Pointer pointer) {
		super("Attempted to deference invalid/poisoned pointer ("+pointer+")");
		this.pointer = pointer;
	}
	public Pointer getPointer() {
		return pointer;
	}
}
