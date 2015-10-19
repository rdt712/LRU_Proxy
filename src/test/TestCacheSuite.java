package test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(value = org.junit.runners.Suite.class)
@SuiteClasses(value = {TestCacheLog.class, TestCacheRequest.class,
						TestCacheToFile.class, TestCacheList.class})
public class TestCacheSuite {
	public static void main(String[] args) {
		org.junit.runner.JUnitCore.runClasses();
	}
}
