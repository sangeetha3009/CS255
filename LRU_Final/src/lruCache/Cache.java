package lruCache;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.*;

public class Cache 
{
    public Set[] sets;
    private int setAssoc, hitCount, missCount, totalCount;
    private double hitRate, missRate;
    private int putCount,getCount;
    public int index=0;
    public Cache(int passedNumSets, int passedSetAssoc)
    {
    	index=passedNumSets / passedSetAssoc;
        this.sets = new Set[index];
        for(int i = 0; i < this.sets.length; i++)
        {
        	
            this.sets[i] = new Set(passedSetAssoc);

        }
        this.setAssoc = passedSetAssoc;
        this.hitCount = 0; this.missCount = 0; this.totalCount = 0;
        this.hitRate = 0.0; this.missRate = 0.0;
        this.putCount=0;this.getCount=0;
        System.out.println("-----------");
        System.out.println("Sets");
        System.out.println(this.sets);
        System.out.println("-----------");
    }

    /**
     * Takes a .dat file name, reads memory addresses from it, and simulates filling the cache
     * as it reads each address
     */
    public void fillFromFile(String fileName) throws IOException {
       
    	FileReader f1=new FileReader(fileName);
    	BufferedReader br=new BufferedReader(f1);
    	String l1;
        while((l1 = br.readLine())!= null)
        {
            totalCount++;
            long addressToRead1=0;
            String data="";
            String addressToRead = l1; //Getting next byte address
            System.out.println("Address to read"+addressToRead);
            String[] a = addressToRead.split("\\s+");
            System.out.println("Op :"+a[1]);
            if(a[1].equals("R")){
            	getCount++;
            	System.out.println("get "+getCount);
            }
            if(a[1].equals("W"))
            	putCount++;
            
            try
            {
            	String[] a1 = a[0].split("\\:");
            	addressToRead1=Long.decode(a1[0]);
            	data=a[2];
            }
            catch(Exception e)
            {
            	
            	System.out.println("Except"+e);
            }
           
            addressToRead1 /= 4; //Converting to a word address
            long blockAddress = addressToRead1 / 4;
            long location = (blockAddress % sets.length); //Location = (MemoryAddress % CacheSize)
            System.out.println(blockAddress + ": set " + location);
            Set setToPlaceAddress = sets[(int)location];
            System.out.println("Set to place add setLength"+sets[(int)location].setLength);
            System.out.println("Set to place add blocks"+sets[(int)location].blocks);

            boolean isHit = setToPlaceAddress.checkQueue(blockAddress,data);
            System.out.println("Hit or Miss "+isHit);
            System.out.println(totalCount + "  AT  " + location + " : " + sets[(int)location].blocks);
            if(isHit && a[1].equals("R")) {
            	
                hitCount++;
                
            }
            if(!isHit && a[1].equals("R"))
            {
                missCount++;
            }
            
        }
        br.close();
        hitRate = hitCount / (double)totalCount * 100;
        missRate = missCount / (double)totalCount * 100;
    }

    public int getSetAssoc() {
        return setAssoc;
    }

    public void printStats() {
        System.out.println("Cache Stats!\n-----------------");
        System.out.println(this);
        System.out.println("Hit Count: " + hitCount);
        System.out.println("Miss Count: " + missCount);
        System.out.println("Hit Rate: " + hitRate);
        System.out.println("Miss Rate: " + missRate);
        System.out.println("Memory Reads: "+getCount);
        System.out.println("Memory Writes: "+putCount);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Cache Sets: " + sets.length + "\n");
        sb.append("Set Associativity: " + setAssoc + "\n");
        sb.append("Block Size: 4");

        return sb.toString();
    }
}