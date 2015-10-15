package test;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import main.*;

@RunWith(value=Parameterized.class)
public class TestCacheRequest {
	private String expected;
	private String value;
	
	@Parameters
	public static Collection<Object[]> getTestParameters() {
		return Arrays.asList(new Object[][] {
				{"", ""}
		});
	}
	
	public TestCacheRequest(String expected, String value) {
		this.expected = expected;
		this.value = value;
	}

	@Test
	public void testCacheRequest() {
		CacheRequest cacheRequest = new CacheRequest("/");
		
	}
	
	@Test
	public void testRead() {
		CacheRequest cacheRequest = new CacheRequest("/");
		System.out.println("expected " + expected);
		assertEquals(expected, cacheRequest.read());
	}
}
