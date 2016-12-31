package lruCache;

import static org.junit.Assert.*;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class LRUCache_TestRunner {
	   public static void main(String[] args) {
		   Result result = JUnitCore.runClasses(LRUCache_Test.class);
		      for (Failure failure : result.getFailures()) {
		         System.out.println("Failure :"+failure.toString());
		      }
		      System.out.println("Success :"+result.wasSuccessful());
		   }
}
