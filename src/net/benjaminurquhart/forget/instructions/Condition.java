package net.benjaminurquhart.forget.instructions;

public abstract class Condition extends Instruction {
	
	protected boolean value;
	
	public boolean evaluate() {
		this.execute();
		return value;
	}

}
