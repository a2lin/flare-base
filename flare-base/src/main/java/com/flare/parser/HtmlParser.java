package com.flare.parser;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.flare.util.FileHtmlReader;


public class HtmlParser 
{
	private Document doc;
    
	public HtmlParser(String html)
	{
		doc = Jsoup.parse(html);
	}
	
	public ArrayList<String> getSections()
	{
		Elements link = doc.select("font[size=+2]");
		for(int i = 0; i < link.size(); i++)
		{
			
			//GETS THE SUBJECT CODE (i.e. WCWP or AIP or whatever)
			String s = link.get(i).nextElementSibling().nextElementSibling().nextElementSibling().toString();
			s = s.replaceAll(".*\\(", "");
			s=s.replaceAll("\\).*","");

			
			System.out.println(">>>>>>");
			Elements tableloc = link.get(i).parent().select("[class=TITLETXT]");
			Pattern p = Pattern.compile("<tr class=\"blacktxt\">");
			Matcher m;
			for(int j = 0; j < tableloc.size(); j++)
			{
				System.out.println("^^^^^^^^");
				Element tr = tableloc.get(j).parent().parent().parent().parent().parent().nextElementSibling();
				String str = tr.toString();
				
				while(p.matcher(str).find())
				{
					System.out.println("==");
					System.out.println(tr.toString());
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
			System.out.println("<<<<<");
		}
		return null;
	}
	
	public static void main( String[] args )
    {
		//String html = "<html><head></head><body><td class='sectxt'>17039</td><td class='sectxt'>17038</td></body></html>";
        String uri = "/Users/alexanderlin/Desktop/CSched.html";
		FileHtmlReader fhr = new FileHtmlReader(uri);
		String html = fhr.readHtml();
		HtmlParser hp = new HtmlParser(html);
        hp.getSections();
    }
}
