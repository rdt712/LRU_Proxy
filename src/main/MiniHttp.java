package main;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.HttpResponse;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 * MiniHttp
 * @author Ken Cooney
 * @date 06/11/2011
 *
 * This fetches the HTML page as defined URL
 * and returns it as a StringBuffer.
 * 
 * This can be used to save the output to a file (cache).
 * 
 */
public class MiniHttp 
{
	HttpClient httpClient;
	BufferedReader in;
    
	public MiniHttp()
	{
		in = null;
		httpClient = new DefaultHttpClient();
	}
	
	public StringBuffer fetch(String URL)
	{
		StringBuffer strBuffer = new StringBuffer("");
	
		if (URL != null && URL.trim().length()>0)
		{
			// If URL doesn't start with http://, add it
			if (! URL.startsWith("http://") &&
				! URL.startsWith("https://"))
			{
				URL="http://"+URL;
			}
			
			try
			{
					HttpGet httpGet = new HttpGet(URL);
					HttpResponse response = httpClient.execute(httpGet);
					InputStream iStream = response.getEntity().getContent();
					in = new BufferedReader(new InputStreamReader(iStream));
					String line = "";
					strBuffer.append(response+"\n");
					while ((line = in.readLine()) != null)
					{
						strBuffer.append(line + "\n");
					}
					in.close();
					EntityUtils.consume(response.getEntity());
			}
			catch (Exception e)
			{
				//e.printStackTrace();
				System.out.println("MiniHttp: Bad GET Request: dropping");
			}
			finally
			{
				if (in != null)
				{
					try
					{
						in.close();
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
			}
		}
		return strBuffer;
	}
	
	public boolean validate(String URL, String log_file){
		
		if (URL != null && URL.trim().length()>0)
		{
			// If URL doesn't start with http://, add it
			if (! URL.startsWith("http://") &&
				! URL.startsWith("https://"))
			{
				URL="http://"+URL;
			}
		
		try
		{
				HttpGet httpGet = new HttpGet(URL);
				HttpResponse response = httpClient.execute(httpGet);
				BufferedReader in = new BufferedReader(new FileReader(log_file));
				String log_resp = in.readLine();
				in.close();
				//EntityUtils.consume(response.getEntity());
				Pattern pattern = Pattern.compile("(?:[01]\\d|2[0123]):(?:[012345]\\d):(?:[012345]\\d)");
				Matcher matcher = null;
				if(log_resp != null){
					matcher = pattern.matcher(log_resp);}
				if(matcher.find()){
					int log_hr = Integer.parseInt(matcher.group(0).substring(0, 2));
					int log_min = Integer.parseInt(matcher.group(0).substring(3, 5));
					matcher = pattern.matcher(response.toString());
					EntityUtils.consume(response.getEntity()); //response is not used after this, so consume it now
					if(matcher.find()){
						int resp_hr = Integer.parseInt(matcher.group(0).substring(0, 2));
						int resp_min = Integer.parseInt(matcher.group(0).substring(3, 5));
						if(log_hr == resp_hr && resp_min - log_min < 30)
							return true;
						else if(resp_hr-1 == log_hr && resp_min-log_min+60 < 30)
							return true;
						else
							return false;
					}
				}
				EntityUtils.consume(response.getEntity()); //Consume response before continuing
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		}
		return false;
	}
	
}
