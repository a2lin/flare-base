package com.flare.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileHtmlReader {
	private FileReader file;
	private BufferedReader buf;
	
	public FileHtmlReader(String uri)
	{
		try {
			file = new FileReader(uri);
		} catch (FileNotFoundException e) {
			System.err.println("Error with FileReader creation. Invalid URI");
			e.printStackTrace();
		}
	}
	public String readHtml()
	{
		
		buf = new BufferedReader(file);
		
		String line = "";
		StringBuilder stB = new StringBuilder();
		try {
			while((line = buf.readLine()) != null)
			{
				stB.append(line);
			}
		} catch (IOException e) {
			System.err.println("Error reading lines. Check BufferedReader.");
			e.printStackTrace();
		}
		return stB.toString();
	}
}
