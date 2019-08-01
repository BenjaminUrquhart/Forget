package net.benjaminurquhart.forget.instructions;

import net.benjaminurquhart.forget.memory.Pointer;

public class Subtraction extends MathExpr {

	public Subtraction(Pointer p1, Pointer p2) {
		super(p1, p2);
	}

	@Override
	public void execute() {
		//System.err.println(p1.read()+" - "+p2.read());
		this.result = p1.read()-p2.read();
	}

}
