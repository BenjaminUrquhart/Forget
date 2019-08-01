package net.benjaminurquhart.forget.instructions;

public class Jump extends Instruction {

	private int toJumpTo;
	
	public Jump(int jump) {
		this.toJumpTo = jump;
	}
	
	public int getJump() {
		return toJumpTo;
	}
	@Override
	public void execute() {}

}
