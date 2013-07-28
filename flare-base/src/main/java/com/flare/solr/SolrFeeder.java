package com.flare.solr;

import java.util.ArrayList;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.ConcurrentUpdateSolrServer;
import org.apache.solr.common.SolrInputDocument;


public class SolrFeeder {
	
	private SolrServer sserv;
	private ArrayList<ArrayList<String>> eventList;
	public SolrFeeder(String uri, ArrayList<ArrayList<String>> arr)
	{
		sserv = new ConcurrentUpdateSolrServer(uri,100,4);
	}
	public void addDocsToServer()
	{
		int id = 0;
		for(int i = 0; i < eventList.size(); i++)
		{
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id",eventList.get(i).get(0) + id);
			String quarter = eventList.get(i).get(0).substring(0, 2);
			String year = eventList.get(i).get(0).substring(2, 4);
			sid.addField("quarter", quarter);
			sid.addField("year",year);
			sid.addField("subjcode", eventList.get(i).get(1));
			sid.addField("sectid", eventList.get(i).get(2) );
			sid.addField("doctype", eventList.get(i).get(3) );
			sid.addField("sectcode", eventList.get(i).get(4));
			sid.addField("days", eventList.get(i).get(5));
			
			String splitter = eventList.get(i).get(6);
			String[] split = splitter.split(" - ");
			

		}
	}
}
