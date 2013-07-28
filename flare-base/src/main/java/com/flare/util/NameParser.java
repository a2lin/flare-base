package com.flare.util;

public class NameParser {
	
	String name;
	
	public NameParser(String s)
	{
		name = s;
	}
	
	public String[] getNames()
	{
		//lastname, firstname
		String[] split_names = name.split(", ");
		
		return split_names;
	}
	
}
