package com.flare.util;

public class DateParser {
	
	private String date;
	
	public DateParser(String date)
	{
		this.date = date;
	}
	
	public int[] getDateInt()
	{
		String[] split = date.split(" - ");
		int[] result = new int[split.length];
    	for(int i = 0; i < split.length; i++)
    	{
    		String[] split2 = split[i].split(":");
    		String part1 = split2[0];
    		String part2 = split2[1].substring(0,split2[1].length()-1);
    		int time = Integer.parseInt(part1+part2);
    		String hourmark = split2[1].substring(split2[1].length()-1,split2[1].length());
    		if(hourmark.equals("p") && time != 1200)
    		{
    			time += 1200;
    		}
    		if(hourmark.equals("a") && time==1200)
    		{
    			time -= 1200;
    		}
    		result[i] = time;
    	}

    	return result;
	}
}
