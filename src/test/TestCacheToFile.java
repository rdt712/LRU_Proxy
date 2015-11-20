package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;

import main.*;

public class TestCacheToFile 
{	
	private CacheToFile ctf;
	private String directory;
	private String url;
	private StringBuffer strBuffer;
	private BufferedReader reader;
	private String str;
	private File file;
	private OutputStreamWriter to_client;
	
	@Before
	public void setup() 
	{
		directory = "./test-data/";
		url = "fileName";
		str = "www.google.com";
		strBuffer = new StringBuffer(str);
		ctf = new CacheToFile(directory);
		to_client = new OutputStreamWriter(System.out);
		
		try
		{
			reader = new BufferedReader(new FileReader("./data/input.txt"));
			file = new File("./test-data/fileName");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@After
	public void tearDown()
	{
		try
		{
			reader.close();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Test
	public void writeTest() 
	{
		ctf.write(url, strBuffer, to_client);
		
		try
		{
			String line = reader.readLine();
			String expected = strBuffer.toString();
			assertTrue(expected.equals(line));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		} 
	} 
	
	@Test
	public void isCachedTest() 
	{
		if(ctf.isCached(url))
			assertTrue(true);
		else
			assertFalse(false);
	} 
	
	@Test
	public void removeTest() 
	{
		ctf.remove(url);
		assertFalse(file.exists());
	} 
}
