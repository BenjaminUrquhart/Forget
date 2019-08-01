package net.benjaminurquhart.forget.memory;

// I was going somewhere with this
public class LongTerm extends Memory {
	
	public LongTerm() {
		super(0);
	}
	public LongTerm(int value) {
		super(value);
	}

	@Override
	public void tick() {}
	
	@Override
	public boolean shouldCleanUp() {
		return false;
	}
}
