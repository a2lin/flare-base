package com.flare.parser;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.flare.model.AbstractClass;
import com.flare.model.Lecture;
import com.flare.util.FileHtmlReader;


public class HtmlParser 
{
	private Document doc;
	private static final String quarter = "FA13";
	private ArrayList<ArrayList<String>> aList = new ArrayList<ArrayList<String>>();
    
	public HtmlParser(String html)
	{
		String htFixed = html.replaceAll("<center>", "");
		htFixed = htFixed.replaceAll("</center>", "");
		htFixed = htFixed.replaceAll("<font face=\"arial\" size=\"-1\">.*?</font>", "");
		doc = Jsoup.parse(htFixed);
	}
	
	public ArrayList<ArrayList<String>> getSections()
	{
		Elements link = doc.select("font[size=+2]");
		
		for(int i = 0; i < link.size(); i++)
		{
			//LOVE THIS TAG (hasAttr)
			if(link.get(i).hasAttr("face")){
				continue;
			}
			
			String sID;
			//GETS THE SUBJECT CODE (i.e. WCWP or AIP or whatever)
			//TODO -> Fix this logic, i don't think we need this check
//			if(link.get(i) != null)
//			{
				sID = link.get(i).toString();
				sID = sID.replaceAll(".*\\(", "");
				sID=sID.replaceAll("\\).*","");
//				System.out.println("reg "+sID);
//			}
//			else
//			{
//				sID = link.get(i).toString();
//				sID = sID.replaceAll(".*\\(", "");
//				sID=sID.replaceAll("\\).*","");
//				System.out.println(sID);
//			}

			
//			System.out.println(">>>>>>");
//				System.out.println("139");
				Element tableParent = link.get(i).nextElementSibling();
//				System.out.println(link.get(i).nextElementSibling().nextElementSibling().nextElementSibling());
				while(tableParent != null && !tableParent.tag().toString().trim().equals("table"))
				{
//					System.out.print("*");
					tableParent = tableParent.nextElementSibling();
				}
				if(tableParent == null)
				{
					continue;
				}
//				System.out.println(link.get(i).nextElementSibling().nextElementSibling().nextElementSibling().nextElementSibling());
//				System.out.println("======");
//				System.out.println(tableParent);
//				if(link.get(i).nextElementSibling().nextElementSibling().nextElementSibling().nextElementSibling() == null)
//				{
//					
//				}
//			Elements tableloc = link.get(i).parent().select("[class=TITLETXT]");
			Elements tableloc = tableParent.select("[class=TITLETXT]");
			Pattern p = Pattern.compile("<tr class=\"blacktxt\">");
			
			for(int j = 0; j < tableloc.size(); j++)
			{
//				System.out.println("^^^^^^^^");
				Element tr = tableloc.get(j).parent().parent().parent().parent().parent().nextElementSibling();
				String str = tr.toString();
				
//				String lecID = str;
//				String[] lecture = lecID.split("\n");
//				Lecture l = new Lecture();
//				for(int k = 0; k < lecture.length; k++)
//				{
////					System.out.println(lecture[k]);
//				}
				String coursenum = tableloc.get(j).parent().parent().parent().parent().previousSibling().toString();
				coursenum = coursenum.replaceAll("<td.*?>", "");
				coursenum = coursenum.replaceAll("</td>", "");
				coursenum = coursenum.trim();
				while(p.matcher(str).find())
				{
//					System.out.println("==");
//					System.out.println(tr.toString());
					String toSplit = tr.toString();
					toSplit = toSplit.replace("<tr class=\"blacktxt\">","");
					toSplit = toSplit.replace("</tr>","");
					String[] arr = toSplit.trim().split("\n");
//					System.out.println("*");
					ArrayList<String> classEvent = new ArrayList<String>();
					for(int y = 0; y < arr.length; y++)
					{
//						System.out.println("-");
						arr[y] = arr[y].replaceAll("<td.*?>","");
						arr[y] = arr[y].replaceAll("</td>","");
//						System.out.println(arr[y]);		
					}
					classEvent.add(quarter);
					classEvent.add(sID);
					classEvent.add(coursenum);

					for(int z = 3; z < arr.length; z++ )
					{
						if(arr[z].trim().equals("&nbsp;"))
						{
							classEvent.add("0");
						}
						else
						{
							classEvent.add(""+arr[z].trim());
						}
					}
					if(!classEvent.get(6).equals("<span class=\"redtxt\">Cancelled</span>") && !classEvent.get(6).equals("<span class=\"greentxt\">TBA</span>"))
					aList.add(classEvent);

//					for(String s:classEvent)
//					{
//						System.out.println(s);
//					}
//					System.out.println("======");
					
					
					
					tr = tr.nextElementSibling();
					if(tr == null)
					{
						break;
					}
					else
					{
						str = tr.toString();
					}
				}
			}
//			System.out.println(link.get(i).parent().select("[align=right]"));
//			System.out.println(">>>>");
//			Elements tr = link.get(i).parent().select("[class=blacktxt]");
//			for(int j = 0; j < tr.size(); j++)
//			{
//				System.out.println(tr.get(j).select("[class=sectxt]"));
//			}
//			System.out.println(link.get(i).parent().select("[class=blacktxt]"));
//			System.out.println("<<<<<");
		}
//		
		for(ArrayList<String> s : aList)
		{
			for(String s2 : s)
			{
				System.out.println(s2);
			}
			System.out.println("======");
		}
		System.out.println(aList.size());
		return aList;
	}
}
