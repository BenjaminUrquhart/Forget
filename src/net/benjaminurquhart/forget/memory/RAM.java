package net.benjaminurquhart.forget.memory;

import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

import net.benjaminurquhart.forget.exceptions.Segfault;

// Represents the pool of memory the program uses
// If more than one program is run, they share
// memory because global state
public class RAM {

	private static Map<Pointer, Memory> memoryMap = Collections.synchronizedMap(new HashMap<>());
	private static boolean initialized = false;
	
	// Reads the memory at the given address. If the memory isn't allocated,
	// it either returns garbage or segfaults.
	public static Memory readMemory(Pointer pointer) {
		if(!pointer.isDisinfected()) {
			throw new Segfault(pointer);
		}
		if(!memoryMap.containsKey(pointer)) {
			if(ThreadLocalRandom.current().nextBoolean()) {
				writeMemory(pointer, new ShortTerm(ThreadLocalRandom.current().nextInt()));
			}
			else {
				throw new Segfault(pointer);
			}
		}
		return memoryMap.get(pointer);
	}
	
	// Used for printing the stack after execution
	public static Memory forceRead(Pointer pointer) {
		return memoryMap.get(pointer);
	}
	// Writes to the given memory address. If the pointer happens to point
	// to immutable (long-term) memory, it segfaults.
	public static void writeMemory(Pointer pointer, int value) {
		if(memoryMap.containsKey(pointer)) {
			memoryMap.get(pointer).write(value);
		}
		else {
			writeMemory(pointer, new ShortTerm(value));
		}
	}
	
	public static void writeMemory(Pointer pointer, Memory memory) {
		PointerStack.STACK.push(pointer);
		memoryMap.put(pointer, memory);
	}
	
	// Writes immutable (long-term) memory. Throws an exception if the program
	// attempts to write to it after initialization.
	public static void writeLongTerm(Pointer pointer, int value) {
		if(initialized) throw new IllegalStateException("Cannot write to long-term memory after initialization");
		writeMemory(pointer, new LongTerm(value));
	}
	
	public static Pointer malloc() {
		Pointer out = new Pointer();
		while(memoryMap.containsKey(out)) {
			out = new Pointer();
		}
		memoryMap.put(out, new ShortTerm());
		Pointer.put(out);
		return out;
	}
	// Handles the "forgetting" of memory values
	public static void tick() {
		Set<Pointer> toFree = new HashSet<>();
		for(Map.Entry<Pointer, Memory> entry : memoryMap.entrySet()) {
			entry.getValue().tick();
			if(entry.getValue().shouldCleanUp()) {
				toFree.add(entry.getKey());
			}
		}
		toFree.forEach(memoryMap::remove);
	}
	
	// Disables the writing of long-term memory
	public static void finished() {
		initialized = true;
	}
	
	public static String getRAMAsString() {
		StringBuilder out = new StringBuilder();
		for(Map.Entry<Pointer, Memory> entry : memoryMap.entrySet()) {
			out.append(entry.getKey()+" -> "+entry.getValue());
			out.append("\n");
		}
		return out.toString().trim();
	}
}
