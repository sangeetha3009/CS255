package lruCache;

import static org.junit.Assert.*;

import org.junit.Test;

public class LRUCache_Test{
	Set s=new Set();
	
	@Test
	public void test() {
		int numSets=32;
		int setAssoc=2;
		Cache cache = new Cache(numSets, setAssoc);
		String datFile="pin.txt";
		try{
        	cache.fillFromFile(datFile);
        }
        catch(Exception ex)
        {
        	System.out.print(ex);
        }
		int len=cache.index;
		int val=0;

		try
		{
		assertEquals(1,cache.sets[0].getLRU(228607));
		}
		catch(AssertionError e)
		{
			System.out.println("from 1"+e);
		}
		
		}
	
	@Test
	public void test2() {
		int numSets=64;
		int setAssoc=2;
		Cache cache = new Cache(numSets, setAssoc);
		String datFile="pin.txt";
		try{
        	cache.fillFromFile(datFile);
        }
        catch(Exception ex)
        {
        	System.out.print(ex);
        }
		int len=cache.index;
		int val=0;
		try
		{
		assertEquals(0,cache.sets[0].getLRU(228607));

		}
		catch(AssertionError e)
		{
			System.out.println("from 2"+e);
		}
		try
		{
		
		assertEquals(1,cache.sets[0].getLRU(228607));
		}
		catch(AssertionError e)
		{
			System.out.println("from 2"+e);
		}
		
		}
	}


