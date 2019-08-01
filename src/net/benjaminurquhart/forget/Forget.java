package net.benjaminurquhart.forget;

import java.io.File;
import java.nio.file.Files;
import java.util.stream.Collectors;

public class Forget {

	public static void main(String[] args) {
		String program = "";
		
		if(args.length > 0) {
			try {
				File file = new File(args[0]);
				if(!file.exists()) {
					System.out.println("Failed to find file "+args[0]);
					return;
				}
				program = Files.lines(file.toPath()).collect(Collectors.joining());
			}
			catch(Exception e) {
				System.err.println("Error reading file:");
				e.printStackTrace();
				return;
			}
		}
		else {
			int ptr = 0;
			for(char c : "\n!dlroW ,olleH".toCharArray()) {
				program+="const 0x"+Integer.toHexString(ptr++)+" "+(int)c+";";
			}
			for(int i = 0; i < ptr; i++) {
				program+="cleanse 0x"+Integer.toHexString(ptr-i-1)+";pop;out;";
			}
		}
		System.out.println("Program:\n"+program+"\n\n");
		
		Runner runner = new Runner(program);
		runner.run();
	}
}
