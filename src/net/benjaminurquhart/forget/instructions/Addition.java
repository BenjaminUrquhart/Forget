package net.benjaminurquhart.forget.instructions;

import net.benjaminurquhart.forget.memory.Pointer;

public class Addition extends MathExpr {
	
	public Addition(Pointer p1, Pointer p2) {
		super(p1,p2);
	}
	
	@Override
	public void execute() {
		this.result = p1.read()+p2.read();
	}

}
