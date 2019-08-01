package net.benjaminurquhart.forget.memory;

// Represents a value stored in memory
public abstract class Memory {
	
	protected int value;
	
	public Memory() {
		this.value = 0;
	}
	public Memory(int value) {
		this.value = value;
	}
	public int read() {
		return value;
	}
	public void write(int value) {
		throw new UnsupportedOperationException();
	}
	public abstract void tick();
	public abstract boolean shouldCleanUp();
	
	@Override
	public int hashCode() {
		return value;
	}
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName()+"[value=0x"+Integer.toHexString(value)+"]";
	}
}
