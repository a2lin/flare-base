package com.flare.solr;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.ConcurrentUpdateSolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;

import com.flare.util.DateParser;
import com.flare.util.NameParser;


public class SolrFeeder {
	
	private SolrServer sserv;
	private ArrayList<ArrayList<String>> eventList;
	public SolrFeeder(String uri, ArrayList<ArrayList<String>> arr)
	{
		sserv = new HttpSolrServer(uri);
		eventList = arr;
	}
	public void addDocsToServer()
	{
		for(int i = 0; i < eventList.size(); i++)
		{
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id",eventList.get(i).get(0)+i);
			String quarter = eventList.get(i).get(0).substring(0, 2);
			String year = eventList.get(i).get(0).substring(2, 4);
			sid.addField("quarter", quarter);
			sid.addField("year",year);
			sid.addField("subjcode", eventList.get(i).get(1));
			sid.addField("courseno", eventList.get(i).get(2));
			sid.addField("sectid", eventList.get(i).get(3) );
			sid.addField("doctype", eventList.get(i).get(4) );
			sid.addField("sectcode", eventList.get(i).get(5));
			sid.addField("days", eventList.get(i).get(6));
			
			DateParser dpx = new DateParser(eventList.get(i).get(7));
			int[] ctime = dpx.getDateInt();

			sid.addField("classhr_start", ctime[0]);
			sid.addField("classhr_end", ctime[1]);
			
			sid.addField("location", eventList.get(i).get(8));
			sid.addField("room", eventList.get(i).get(9));
			
			String name = eventList.get(i).get(10);
			if(!name.equals("0")){
				if(name.equals("Staff"))
				{
					sid.addField("fn_professor", name);
					sid.addField("ln_professor", name);
				}
				else
				{
					NameParser np = new NameParser(name);
					String[] names = np.getNames();
					sid.addField("fn_professor", names[1]);
					sid.addField("ln_professor", names[0]);
				}
			}
			try {
				sserv.add(sid);
			} catch (SolrServerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			sserv.commit();
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
