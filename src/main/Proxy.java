package main;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.*;
import java.util.Scanner;

/**
 * Proxy
 * @author Ken Cooney
 * @date 06/11/2011
 *
 * This is the class that can be executed.
 */
public class Proxy 
{	
	private CacheLog cacheLog;
	private CacheList cacheList;
	private MiniHttp miniHttp;
	private CacheToFile cacheToFile;
	
	private String directory;
	private boolean windows;
	private int req_num = 0;
	
	public Proxy(String inDirectory, int maxCacheSize)
	{

		if (maxCacheSize<1)
		{
			maxCacheSize=1;
		}
		
		// Determine which way slashes go for directories.
		String os = System.getProperty("os.name").toLowerCase();
		windows=(os.indexOf( "win" ) >= 0); 

		this.directory=inDirectory;
		if (windows && ! directory.endsWith("\\"))
		{
			directory=directory+"\\";
		}
		if (! windows && ! directory.endsWith("/"))
		{
			directory=directory+"/";
		}
		
		if (isValidDirectory())
		{
			try
			{
			
				cacheLog = new CacheLog(directory);
				cacheList = new CacheList(directory, maxCacheSize);
				cacheToFile = new CacheToFile(directory);
				miniHttp = new MiniHttp();
			
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			if (!isValidDirectory())
			{
				System.out.println("ERROR: "+directory+" is not a valid directory.");
			}
			else
			{
				System.out.println("ERROR: input.txt file not found in specified directory.");
			}
		}
	}
	
	public void httpGet(int port) throws IOException{
		//Enter an infinite loop to listen for connections
		ServerSocket serverSocket = new ServerSocket(port);
		while(true)
		{
			try{ //Open a server socket to listen on
				Socket client = serverSocket.accept(); //Accept client connection
				req_num ++;
		        final BufferedReader from_client = new BufferedReader(new InputStreamReader(client.getInputStream()));
		        final OutputStreamWriter to_client= new OutputStreamWriter(client.getOutputStream());
				String url = cacheLog.requestLog(from_client, req_num);
				if(url != null && !url.startsWith("ERROR")){
					HTTPHandler thread = this.new HTTPHandler(req_num, url, to_client);
					thread.start();
				}
				else
					req_num--;
				Thread.sleep(300);
				
			}
			catch (Exception e) {
				System.out.println("Exception caught while trying to listen on port "+port+" or while listening for a connection");
				System.out.println(e.getMessage());
			}			
		}
	}
	
	private class HTTPHandler extends Thread
	{
		
		int req_num;
		String url;
		OutputStreamWriter to_client;
		
		HTTPHandler(int req_num, String url, OutputStreamWriter to_client)
		{
			this.req_num = req_num;
			this.url = url;
			this.to_client = to_client;
		}
		
		public void run()
		{
			boolean hit=cacheToFile.isCached(url);
			if (hit)
			{
				cacheLog.logHit(url);
			}
			else
			{
				cacheLog.logMiss(url);
			}
			String removedURL=cacheList.addNewObject(url, hit);
			if (removedURL.trim().length()>0)
			{
				//webCache.removeCache(removedURL);
				// physically removed the cached file
				cacheToFile.remove(removedURL);
			}

			// Step 4: If hit, send data to output
			//         If miss, pull data and save it
			String log_file = directory+url.replaceAll("/", ".").replaceAll("\\?", ".");
			if (hit && miniHttp.validate(url, log_file))
			{
				// display cached file to System.out
				cacheToFile.read(url, to_client, req_num);
			}
			else
			{
				StringBuffer data=miniHttp.fetch(url);
				if(data.length() != 0) //If data is empty, drop this request
				{
					cacheToFile.write(url, data, to_client);
					cacheToFile.read(url, to_client, req_num);
				}
			}			
		}		
	}
	
	private boolean isValidDirectory()
	{
		boolean returnValue=false;
		File file = new File(directory);
		if (file.exists() && file.isDirectory())
		{
			returnValue=true;
		}
		return returnValue;
	}
	
	public static void main(String args[])
	{
		if (args.length==2)
		{
			Scanner scanner = new Scanner(System.in);
			try
			{
				String directory=args[0];
				String temp=args[1];
				int maxCacheSize=Integer.parseInt(temp);
				Proxy proxy=new Proxy(directory, maxCacheSize);
				System.out.println("Enter a valid port number to listen on: ");
				int port = scanner.nextInt();
				proxy.httpGet(port);
			}
			catch (Exception e)
			{
				System.out.println("ERROR: could not process parameters");
			}
		}
		else
		{
			System.out.println("Pass in the following parameters:");
			System.out.println("directory where input.txt resides");
			System.out.println("maximum number of cached web pages (integer, minimum is 1)");
		}
	}
}