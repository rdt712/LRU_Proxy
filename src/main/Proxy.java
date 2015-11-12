package main;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
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
	private CacheRequest cacheRequest;
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
		
		if (isValidDirectory() && isInputFilePresent())
		{
			try
			{
			
				cacheLog = new CacheLog(directory);
				cacheRequest= new CacheRequest(directory);
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
			if (! isValidDirectory())
			{
				System.out.println("ERROR: "+directory+" is not a valid directory.");
			}
			else
			{
				System.out.println("ERROR: input.txt file not found in specified directory.");
			}
		}
	}
	
	public void httpGet(int port){
		//Enter an infinite loop to listen for connections
		for(;;)
		{
			try{ //Open a server socket to listen on
				ServerSocket serverSocket = new ServerSocket(port);
				Socket client = serverSocket.accept(); //Accept client connection
				//
				serverSocket.close();  //Close server socket
				req_num ++;
				cacheLog.requestLog(client, req_num);
				//BufferedReader socketReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
	            //System.out.println(socketReader.readLine());
			}
			catch (IOException e) {
				System.out.println("Exception caught while trying to listen on port "+port+" or while listening for a connection");
				System.out.println(e.getMessage());
			}
			
			//Send a log request with this number: req_num ++;
		
			//newThread thread = server.new newThread(clientSocket, reader);
			//thread.start();
			
		}
	}
	
	public void run()
	{
		// A normal proxy will remain running
		// and waiting for requests
		// However we are simulated a proxy that
		// reads a set number of requests and the
		// stops.  So, we'll loop through the
		// file and stop when we reach the end.
		String url="";
		do
		{
			// Step 1: read request from file.
			url=cacheRequest.read();
			
			// If we have one, proceed.
			if (url.trim().length()>0)
			{
				// Step 2: Check to see if URL is cached
				//         Log this in the cache log.
				boolean hit=cacheToFile.isCached(url);
				if (hit)
				{
					cacheLog.logHit(url);
				}
				else
				{
					cacheLog.logMiss(url);
				}

				// Step 3: Based on hit/miss, add to LRU
				// cache list.  This logs a message if 
				// an old cached object is deleted 				
				String removedURL=cacheList.addNewObject(url, hit);
				if (removedURL.trim().length()>0)
				{
					//webCache.removeCache(removedURL);
					// physically removed the cached file
					cacheToFile.remove(removedURL);
				}

				// Step 4: If hit, send data to output
				//         If miss, pull data and save it
				if (hit)
				{
					// display cached file to System.out
					cacheToFile.read(url);
				}
				else
				{
					StringBuffer data=miniHttp.fetch(url);
					cacheToFile.write(url, data);
				}
				
				// wait a second before next read
				// I just use this as a timer mechanism
				try
				{
					Thread.sleep(1000);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		} while (url.trim().length()>0);

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
	
	private boolean isInputFilePresent()
	{
		boolean returnValue=false;
		File inFile = new File(directory+"input.txt");
		if (inFile.exists())
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