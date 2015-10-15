package TestCacheList;

import org.junit.*;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestCacheList {
	public CacheList testList;
	public String directory = "C:/Users/coxa11/Desktop";
	public int maxSize;
	
	public String expectedString;
	public int expectedInt;
	public String givenURL;
	
	
	// Before each test, reset maxSize to 2 and create a new CacheList to test with. Always use "www.google.com" to use during the test call. 
	// Any one or more of these may be overridden in the test case to fit that test's needs.  
	@Before
	public void before(){
		maxSize = 2;
		testList = new CacheList(directory,maxSize);
		expectedString = "";
		expectedInt = 0;
		givenURL= "www.google.com";
	}
	
	// This will test addNewObject for basic addition, no hit. Output should be "".
	@Test
	public void testAddNewObjectA(){
		assertEquals(expectedString,testList.addNewObject(givenURL, false));
	}
	
	// This will test addNewObject for addition to a list that is at the maxSize, no hit. Output should be "www.facebook.com";
	@Test
	public void testAddNewObjectB(){
		maxSize = 1;
		testList = new CacheList(directory,maxSize);
		expectedString = "www.facebook.com";
		testList.addNewObject("www.facebook.com", false);
		assertEquals(expectedString,testList.addNewObject(givenURL, false));
	}
	
	// This will test addNewObject for addition to a list with a hit, but not at the maxSize. Output should be "";
	@Test
	public void testAddNewObjectC(){
		testList.addNewObject(givenURL, false);
		assertEquals(expectedString,testList.addNewObject(givenURL,true));
	}
	
	//This will test addNewObject for addition to a list with a hit, and exceeding the maxSize. Should be "". 
	//A call with a "hit" will never exceed the maxSize cause duplicate is deleted first. 
	@Test
	public void testAddNewObjectD(){
		testList.addNewObject("www.facebook.com", false);
		testList.addNewObject("www.yahoo.com",false);
		testList.addNewObject(givenURL,false);
		assertEquals(expectedString,testList.addNewObject(givenURL,true));
	}
	
	//This will see what addNewObject will do if something is passed that is a hit, but hit is marked false. 
	//Should knock off last entry as the method sits. In this test, should be www.facebook.com
	@Test
	public void testAddNewObjectE(){
		expectedString = "www.facebook.com";
		testList.addNewObject("www.facebook.com",false);
		testList.addNewObject(givenURL, false);
		assertEquals(expectedString,testList.addNewObject(givenURL, false));
	}
		
	//This will see what addNewObject will do if something is passed that is not a hit but is marked as true.
	//Should operate as normal because linkedList.remove won't remove anything. Expected "".
	@Test
	public void testAddNewObjectF(){
		assertEquals(expectedString,testList.addNewObject(givenURL,true));
	}
	
	//This will test getCacheSize for an empty list.
	@Test
	public void testGetCacheSizeA(){
		assertEquals(expectedInt,testList.getCacheSize());
	}
	
	//This will test getCacheSize for a list with only one entry. Should be 0.
	@Test
	public void testGetCacheSizeB(){
		expectedInt = 1;
		testList.addNewObject(givenURL, false);
		assertEquals(expectedInt,testList.getCacheSize());
	}
	
	//This will test getCacheSize for a list with multiple entries that would have pushed it past the maxSize.
	@Test
	public void testGetCacheSizeC(){
		expectedInt = 2;
		testList.addNewObject(givenURL, false);
		testList.addNewObject("www.facebook.com", false);
		testList.addNewObject("www.yahoo.com", false);
		assertEquals(expectedInt,testList.getCacheSize());
	}
	
	//This will test getChacheSize for a list that had a hit. Should be 1 in this case.
	@Test
	public void testGetCacheSizeD(){
		expectedInt = 1;
		testList.addNewObject(givenURL, false);
		testList.addNewObject(givenURL, true);
		assertEquals(expectedInt,testList.getCacheSize());
	}
	
	//This will test getHead on an empty list. Should be "".
	@Test
	public void testGetHeadA(){
		assertEquals(expectedString,testList.getHead());
	}
	
	//This will test getHead on a populated list. Should be what was added last. In this case, expectedString (www.google.com).
	@Test
	public void testGetHeadB(){
		expectedString = "www.google.com";
		testList.addNewObject("www.facebook.com", false);
		testList.addNewObject("www.yahoo.com", false);
		testList.addNewObject("www.facebook.com", true);
		testList.addNewObject(givenURL, false);
		assertEquals(expectedString,testList.getHead());
	}
	
	//This will test get at any negative index should throw exception.
	@Test(expected=IndexOutOfBoundsException.class)
	public void testGetA(){
		testList.get(-1);
	}
	
	//This will test get on a populated list with an index in the proper range. Trying for index 1, which will be "www.facebook.com" in this case.
	@Test
	public void testGetB(){
		expectedString = "www.facebook.com";
		testList.addNewObject("www.facebook.com", false);
		testList.addNewObject("www.yahoo.com", false);
		testList.addNewObject("www.facebook.com", true);
		testList.addNewObject(givenURL, false);
		assertEquals(expectedString,testList.get(1));
	}
	
	//This will test get on a populated list with an index equal to the size. Expecting "" to return.
	@Test
	public void testGetC(){
		testList.addNewObject("www.facebook.com", false);
		testList.addNewObject("www.yahoo.com", false);
		testList.addNewObject("www.facebook.com", true);
		testList.addNewObject(givenURL, false);
		assertEquals(expectedString,testList.get(2));
	}
	
	//This will test get on a populated list with an index greater to the size. Expecting "" to return.
	@Test
	public void testGetD(){
		testList.addNewObject("www.facebook.com", false);
		testList.addNewObject("www.yahoo.com", false);
		testList.addNewObject("www.facebook.com", true);
		testList.addNewObject(givenURL, false);
		assertEquals(expectedString,testList.get(3));
	}

}
/*
 * Possible found bugs: Log not returning to new line after entry, readability problem.
 * 					    Possibility hit not marked correctly, causing two instances of the same site to be in the list.
 * 						Possibility of get throwing exception if passed negative index, may need to fix logic.
 */
