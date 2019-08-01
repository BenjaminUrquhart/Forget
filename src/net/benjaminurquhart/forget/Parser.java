package net.benjaminurquhart.forget;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import net.benjaminurquhart.forget.instructions.*;
import net.benjaminurquhart.forget.memory.Cache;
import net.benjaminurquhart.forget.memory.Pointer;
import net.benjaminurquhart.forget.memory.RAM;

public class Parser {

	
	// Don't touch it
	public static List<Instruction> parse(String program) {
		List<Instruction> out = new ArrayList<>();
		String[] lines = program.replaceAll("(\\s){2,}|\n", "$1").split(";");
		String[] args;
		String line;
		
		Stack<Integer> jmpStack = new Stack<>();
		
		for(int i = 0; i < lines.length; i++) {
			line = lines[i].trim();
			if(line.isEmpty()) continue;
			args = line.split(" ");
			if(line.matches("const 0x(?:[0-9A-Fa-f]+) -?\\d+")) {
				RAM.writeLongTerm(Pointer.get(Integer.parseInt(args[1].substring(2),16)), Integer.parseInt(args[2]));
				out.add(null);
			}
			else {
				RAM.finished();
			}
			if(line.matches("push -?\\d+")) {
				out.add(new PushMem(Integer.parseInt(args[1])));
			}
			else if(line.equals("pop")) {
				out.add(new PopMem());
			}
			else if(line.matches("read 0x(?:[0-9A-Fa-f]+)")) {
				out.add(new ReadMem(Pointer.get(Integer.parseInt(args[1].substring(2),16))));
			}
			else if(line.matches("write 0x(?:[0-9A-Fa-f]+)")) {
				out.add(new WriteMem(Pointer.get(Integer.parseInt(args[1].substring(2),16)), Integer.parseInt(args[2])));
			}
			else if(line.matches("cleanse(?: 0x(?:[0-9A-Fa-f]+))?")) {
				out.add(new SanitizePointer(args.length > 1 ? Pointer.get(Integer.parseInt(args[1].substring(2),16)) : Cache.CURRENT_PTR));
			}
			else if(line.matches("while 0x(?:[0-9A-Fa-f]+)")) {
				out.add(new While(Pointer.get(Integer.parseInt(args[1].substring(2),16))));
				jmpStack.push(i);
			}
			else if(line.equals("end")) {
				out.add(new Jump(jmpStack.pop()));
			}
			else if(line.equals("dump")) {
				out.add(new PrintStack());
			}
			else if(line.equals("out")) {
				out.add(new PrintTop());
			}
		}
		return out;
	}
}
