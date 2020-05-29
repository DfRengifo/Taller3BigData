package bam;

import java.util.HashMap;
import java.util.List;

import com.textrazor.AnalysisException;
import com.textrazor.NetworkException;
import com.textrazor.TextRazor;
import com.textrazor.annotations.AnalyzedText;
import com.textrazor.annotations.Entity;

public class Enricher extends Thread
{
	private static final String KEY = "9ae86e8f450079384e145555b3cc485a4629a3d3eb96277b2418b765";
	
	private String text; 
	
	public Enricher (String text)
	{
		this.text = text;
	}
	
	public void run ()
	{				
		TextRazor client = new TextRazor(KEY);
		
		client.addExtractor("words");
		client.addExtractor("entities");

		try 
		{
			AnalyzedText response = client.analyze(text);
			HashMap<String, String> map = new HashMap<String, String>(); 
			for (Entity entity : response.getResponse().getEntities()) 
			{
			    if (!map.containsKey(entity.getEntityId()))
			    {
			    	String resp = entity.getWikiLink();
			    	
			    	List<String> lol = entity.getDBPediaTypes();
			    	if(lol != null)
			    	{
			    		resp += ";"+entity.getDBPediaTypes().toString();
			    	}
			    	
			    	List<String> lol2 = entity.getFreebaseTypes();
			    	if(lol2 != null)
			    	{
			    		resp += ";"+entity.getFreebaseTypes().toString();
			    	}			    	
			    	
			    	if(entity.getType() != null)
			    	{
				    	resp += ";"+entity.getType();
			    	}

			    	map.put(entity.getEntityId(), entity.getWikiLink());
			    }
			}
		} 
		catch (NetworkException e) 
		{
			e.printStackTrace();
		} 
		catch (AnalysisException e) 
		{
			e.printStackTrace();
		}		
	}
	
}
