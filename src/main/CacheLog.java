package main;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * CacheLog
 * @author Ken Cooney
 * @date 06/10/2011
 * 
 * Writes to the output.log what is going on with the cache.
 * 
 * 
 * TESTED via CacheLogTestSuite.  All tests pass.
 */
public class CacheLog 
{

	private BufferedWriter out;
	private BufferedReader in;
	private String filename;
	private SimpleDateFormat format;
	private String req_log;
	
	private String returnString;
	
	public CacheLog(String directory)
	{
		req_log = directory + "/server_req.log";
		filename=directory+"/output.log";
		returnString="\n";
		format =
            new SimpleDateFormat("EEE MMMM dd HH:mm:ss yyyy");

	}
	
	public void openLogForAppend()
	{
		try
		{
			out = new BufferedWriter(new FileWriter(filename, true));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	public void logRemoval(String URL)
	{
		openLogForAppend();
		Calendar cal = Calendar.getInstance();
		String dateString=format.format(cal.getTime());
		try
		{
			out.write(dateString+" "+URL+" the cached page is evicted"+returnString);
			out.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void logHit(String URL)
	{
		openLogForAppend();
		Calendar cal = Calendar.getInstance();
		String dateString=format.format(cal.getTime());
		try
		{
			out.write(dateString+" "+URL+" cache hit"+returnString);
			out.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void logMiss(String URL)
	{
		openLogForAppend();
		Calendar cal = Calendar.getInstance();
		String dateString=format.format(cal.getTime());
		try
		{
			out.write(dateString+" "+URL+" cache miss"+returnString);
			out.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void requestLog(Socket client, int req_num)
	{
		try {
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			out = new BufferedWriter(new FileWriter(req_log, true));
			out.write(req_num);
			String input = in.readLine();
			if (input != null)
				out.write(input);
			Calendar cal = Calendar.getInstance();
			String dateString=format.format(cal.getTime());
			out.write(dateString + "\n");
			in.close();
			out.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
