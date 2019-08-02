package net.benjaminurquhart.forget.instructions;

public abstract class Condition extends Instruction {
	
	protected volatile boolean value;

	public boolean evaluate() {
		this.value = false;
		this.execute();
		return value;
	}

}
