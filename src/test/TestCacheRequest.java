package test;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.*;

public class TestCacheRequest {
	
	private CacheRequest cacheRequest;
	private String directory;
	private BufferedReader reader;
	
	@Before
	public void setup() {
		directory = "./data/";
		cacheRequest = new CacheRequest(directory);
		try
		{
			reader = new BufferedReader(new FileReader(directory + "input.txt"));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@After
	public void teardown() {
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
	public void testRead() {
		try {
			String line = cacheRequest.read();
			String expected = reader.readLine();
			assertTrue(expected.equals(line));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		} 
	}
}
