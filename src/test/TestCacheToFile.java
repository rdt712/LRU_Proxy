package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import main.*;

public class TestCacheToFile 
{	
	CacheToFile ctf;
	private String directory;
	private String url;
	private StringBuffer strBuffer;
	private BufferedReader reader;
	private String str;
	private File file;
	
	@Before
	public void setup() 
	{
		directory = "./test-data/";
		url = "fileName";
		str = "www.google.com";
		strBuffer = new StringBuffer(str);
		ctf = new CacheToFile(directory);
		
		try
		{
			reader = new BufferedReader(new FileReader("./data/input.txt"));
			file = new File("./test-data/fileName");
			if (!file.exists())
			{
				file.createNewFile();
			}
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
			if (file.exists())
			{
				file.delete();
			}
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Test
	public void writeTest() 
	{
		ctf.write(url, strBuffer);
		
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
	public void readTest() 
	{
		
		ctf.read(url);
		
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
		System.out.println("remove test");
		//File file = new File("./test-data/fileName");
		assertFalse(file.exists());
	} 
}
