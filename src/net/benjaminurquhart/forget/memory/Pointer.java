package net.benjaminurquhart.forget.memory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import net.benjaminurquhart.forget.exceptions.Segfault;


// Represents a pointer. If this was C, these would be real pointers
// I could also use Unsafe, but that requires effort
public class Pointer {

	private int value;
	private boolean disinfected;
	
	private static final Map<Integer, Pointer> CACHE = new HashMap<>();
	
	public static Pointer get(int address) {
		return CACHE.computeIfAbsent(address, addr -> new Pointer(addr));
	}
	protected static void put(Pointer pointer) {
		CACHE.put(pointer.getValue(), pointer);
	}
	
	
	protected Pointer() {
		this(ThreadLocalRandom.current().nextInt());
	}
	protected Pointer(int value) {
		this.value = value;
		this.disinfected = false;
	}
	
	public void disinfect() {
		this.disinfected = true;
	}
	public boolean isDisinfected() {
		return disinfected;
	}
	public int read() {
		if(!disinfected) {
			throw new Segfault(this);
		}
		return RAM.readMemory(this).read();
	}
	public int getValue() {
		return value;
	}
	
	@Override
	public int hashCode() {
		return value;
	}
	@Override
	public String toString() {
		return "Pointer[addr=0x"+Integer.toHexString(value)+", poison="+!disinfected+"]";
	}
}
