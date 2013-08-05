package com.flare.parser;

import com.flare.util.FileHtmlReader;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }

    public static void main(String[] args)
    {
    	String html_uri = "/Users/alexanderlin/Desktop/CSched.html";
		FileHtmlReader fhr = new FileHtmlReader(html_uri);
		String html = fhr.readHtml();
		HtmlParser hp = new HtmlParser(html);
    	hp.getSections();
    }
}
