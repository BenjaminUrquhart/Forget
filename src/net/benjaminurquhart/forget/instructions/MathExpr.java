package net.benjaminurquhart.forget.instructions;

import net.benjaminurquhart.forget.memory.Cache;
import net.benjaminurquhart.forget.memory.Pointer;

public abstract class MathExpr extends Instruction {

	protected int result;
	protected Pointer p1,p2;
	
	public MathExpr(Pointer p1, Pointer p2) {
		this.p1 = p1;
		this.p2 = p2;
	}
	
	public int evaluate() {
		if(p1 == null) {
			p1 = Cache.CURRENT_PTR;
		}
		if(p2 == null) {
			p2 = Cache.CURRENT_PTR;
		}
		this.execute();
		return result;
	}
}
