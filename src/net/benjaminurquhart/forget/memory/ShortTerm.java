package net.benjaminurquhart.forget.memory;

import java.util.concurrent.ThreadLocalRandom;

public class ShortTerm extends Memory {
	
	private int timeUntilDestruction, timeElapsed;
	
	public ShortTerm() {
		this(0);
	}
	public ShortTerm(int value) {
		super(value);
		this.timeUntilDestruction = ThreadLocalRandom.current().nextInt(1000);
		this.timeElapsed = 0;
	}
	@Override
	public int read() {
		this.timeUntilDestruction = ThreadLocalRandom.current().nextInt(1000);
		this.timeElapsed = 0;
		return super.read();
	}
	@Override
	public void write(int value) {
		this.value = value;
	}
	@Override
	public boolean shouldCleanUp() {
		return timeElapsed>=timeUntilDestruction;
	}
	@Override
	public void tick() {
		timeElapsed++;
	}
}
