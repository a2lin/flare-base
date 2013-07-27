package com.flare.parser;

import java.util.ArrayList;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.CssSelectorNodeFilter;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.flare.util.FileHtmlReader;


public class HtmlParser 
{
	private Parser parser;
    
	public HtmlParser(String html)
	{
		parser = Parser.createParser(html,"UTF-8");
	}
	
	public ArrayList<String> getSections()
	{
		
		NodeFilter nF = new CssSelectorNodeFilter("[class~='sectxt']");
		NodeList nL;
		try {
			nL = parser.extractAllNodesThatMatch(nF);
			System.out.println(nL.toHtml());
			
			for(int i = 0; i < nL.size(); i++)
			{
				Node n = nL.elementAt(i);
				System.out.println(n.getFirstChild().getText());
			}
		} catch (ParserException e) {
			e.printStackTrace();
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
