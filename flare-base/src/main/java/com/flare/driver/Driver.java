package com.flare.driver;

import com.flare.parser.HtmlParser;
import com.flare.solr.SolrFeeder;
import com.flare.util.FileHtmlReader;

public class Driver {
	private static final String solr_base = "http://ec2-54-213-31-81.us-west-2.compute.amazonaws.com:8983/solr";
	public static void main( String[] args )
    {
		//String html = "<html><head></head><body><td class='sectxt'>17039</td><td class='sectxt'>17038</td></body></html>";
        String html_uri = "/Users/alexanderlin/Desktop/CSched.html";
		FileHtmlReader fhr = new FileHtmlReader(html_uri);
		String html = fhr.readHtml();
		HtmlParser hp = new HtmlParser(html);
        SolrFeeder sfeed = new SolrFeeder(solr_base,hp.getSections());
        sfeed.addDocsToServer();
    }
}
