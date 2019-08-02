package net.benjaminurquhart.forget.instructions;

public class HasInput extends Condition {

	@Override
	public void execute() {
		try {
			this.value = System.in.available() > 0;
		}
		catch(Exception e) {
			System.err.println("Warning: failed to determine if stdin has available characters");
			this.value = false;
		}
	}

}
