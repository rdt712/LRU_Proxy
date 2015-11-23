package main;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.HttpResponse;
import org.apache.http.impl.client.DefaultHttpClient;

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
					//System.out.println(response.toString());
					InputStream is = response.getEntity().getContent();
					in = new BufferedReader(new InputStreamReader(is));

					String line = "";
					strBuffer.append(response+"\n");
					while ((line = in.readLine()) != null) 
					{
						strBuffer.append(line + "\n");
					}
					in.close();
			}
			catch (Exception e)
			{
				System.out.println("MiniHttp: Bad GET");
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
				if(log_resp != null){
					int log_hr = Integer.parseInt(log_resp.substring(40, 42));
					int resp_hr = Integer.parseInt(response.toString().substring(40, 42));
					int log_min = Integer.parseInt(log_resp.substring(43, 45));
					int resp_min = Integer.parseInt(response.toString().substring(43, 45));
					if(log_hr == resp_hr && resp_min - log_min < 30)
						return true;
					else if(resp_hr-1 == log_hr && resp_min-log_min+60 < 30)
						return true;
					else
						return false;
				}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		}
		return false;
	}
	
}
