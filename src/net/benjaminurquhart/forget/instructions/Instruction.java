package net.benjaminurquhart.forget.instructions;

import java.lang.reflect.Field;

public abstract class Instruction {

	public abstract void execute();
	
	// Don't judge
	@Override
	public String toString() {
		StringBuilder out = new StringBuilder(this.getClass().getSimpleName()+"[");
		try {
			for(Field field : this.getClass().getDeclaredFields()) {
				field.setAccessible(true);
				out.append(field.getName()+"="+field.get(this));
				out.append(", ");
			}
		}
		catch(Exception e) {}
		String s = out.toString();
		if(s.endsWith(", ")) {
			s = s.substring(0, s.length() - 2);
		}
		return s+"]";
	}
}
