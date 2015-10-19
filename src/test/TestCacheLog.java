package test;

import org.junit.*;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import main.*;
public class TestCacheLog {
	
	CacheLog cacheLog;
	BufferedReader reader;
	BufferedWriter writer;
	SimpleDateFormat format;
	String URL;
	
	
	@Before
	public void setup() {
		cacheLog = new CacheLog("test-data\\");
		URL = "www.google.com";
		format = new SimpleDateFormat("EEE MMMM dd HH:mm:ss yyyy");
		try{
			writer = new BufferedWriter(new FileWriter("test-data\\output.log", false));
			reader = new BufferedReader(new FileReader("test-data\\output.log"));
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	@After
	public void teardown(){
		try {
			reader.close();
			writer.write("");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testLogRemoval(){
		cacheLog.logRemoval(URL);
		Calendar cal = Calendar.getInstance();
		String dateString=format.format(cal.getTime());
		try {
			String line = reader.readLine();
			String expected = dateString+" "+URL+" the cached page is evicted";
			assertTrue(expected.equals(line));

		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testLogHit(){
		cacheLog.logHit(URL);
		Calendar cal = Calendar.getInstance();
		String dateString=format.format(cal.getTime());
		try {
			String line = reader.readLine();
			String expected = dateString+" "+URL+" cache hit";
			assertTrue(expected.equals(line));

		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testLogMiss(){
		cacheLog.logMiss(URL);
		Calendar cal = Calendar.getInstance();
		String dateString=format.format(cal.getTime());
		try {
			String line = reader.readLine();
			String expected = dateString+" "+URL+" cache miss";
			assertTrue(expected.equals(line));

		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	

}
