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
		String fId = "";
		SolrInputDocument offering = new SolrInputDocument();
		
		for(int i = 0; i < eventList.size(); i++)
		{
			SolrInputDocument sid = new SolrInputDocument();
//			sid.addField("id",eventList.get(i).get(0)+i);
			String quarter = eventList.get(i).get(0).substring(0, 2);
			String year = eventList.get(i).get(0).substring(2, 4);
			String cid = eventList.get(i).get(1) + "."+eventList.get(i).get(2)+"."+quarter+"."+year;
			String id = eventList.get(i).get(1) + "."+eventList.get(i).get(2)+"."+quarter+"."+year+"."
					+eventList.get(i).get(4) +"."+ eventList.get(i).get(5);
			sid.addField("id", id);
			sid.addField("cid", cid);
			
			if(fId.equals(""))
			{
				fId = cid;
				offering.addField("type", "offering");
				offering.addField("cid",fId);
				offering.addField("id", "offering."+fId);
				offering.addField("subjcode", eventList.get(i).get(1));
				offering.addField("courseno", eventList.get(i).get(2));
			}
			else if(!fId.equals(cid))
			{
				try {
					sserv.add(offering);
				} catch (SolrServerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				offering = new SolrInputDocument();
				fId = cid;
				offering.addField("type", "offering");
				offering.addField("id",fId);
				offering.addField("subjcode", eventList.get(i).get(1));
				offering.addField("courseno", eventList.get(i).get(2));
			}
			sid.addField("type", "component");
			sid.addField("quarter", quarter);
			sid.addField("year",year);
			sid.addField("subjcode", eventList.get(i).get(1));
			sid.addField("courseno", eventList.get(i).get(2));
			sid.addField("sectid", eventList.get(i).get(3) );
			
			String doctype = eventList.get(i).get(4);
			sid.addField("doctype", doctype );
			
			if(doctype.equals("LE"))
			{
				offering.addField("lecture", id);
			}
			else if(doctype.equals("DI"))
			{
				offering.addField("discussion", id);
			}
			else if(doctype.equals("SE"))
			{
				offering.addField("seminar", id);
			}
			else if(doctype.equals("TU"))
			{
				offering.addField("tutorial", id);
			}
			else if(doctype.equals("LA"))
			{
				offering.addField("lab", id);
			}
			else
			{
				offering.addField("other", id);
			}
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
					sid.addField("professor", name);
				}
				else
				{
					NameParser np = new NameParser(name);
					String[] names = np.getNames();
					sid.addField("fn_professor", names[1]);
					sid.addField("ln_professor", names[0]);
					sid.addField("professor", names[1] + " " + names[0]);
				}
			}
			try {
				sserv.add(sid);
				if(i == eventList.size()-1)
				{
					sserv.add(offering);
				}
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
