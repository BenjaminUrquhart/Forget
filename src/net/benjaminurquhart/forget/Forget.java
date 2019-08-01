package net.benjaminurquhart.forget;

public class Forget {

	public static void main(String[] args) {
		String program = "";
		
		int ptr = 0;
		for(char c : "\n!dlroW ,olleH".toCharArray()) {
			program+="const 0x"+Integer.toHexString(ptr++)+" "+(int)c+";";
		}
		for(int i = 0; i < ptr; i++) {
			program+="cleanse 0x"+Integer.toHexString(ptr-i-1)+";pop;out;";
		}
		System.out.println("Program:\n"+program+"\n\n");
		
		Runner runner = new Runner(program);
		runner.run();
	}
}
