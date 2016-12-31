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
    public Cache(int passedNumSets, int passedSetAssoc)
    {
    	int index=passedNumSets / passedSetAssoc;
        this.sets = new Set[index];
        for(int i = 0; i < this.sets.length; i++)
        {
        	
            this.sets[i] = new Set(passedSetAssoc);

        }
        this.setAssoc = passedSetAssoc;
        this.hitCount = 0; this.missCount = 0; this.totalCount = 0;
        this.hitRate = 0.0; this.missRate = 0.0;
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
            int addressToRead1=0;
            String addressToRead = l1; //Getting next byte address
            System.out.println("Address to read"+addressToRead);
            String[] a = addressToRead.split("\\s+");
            System.out.println("String is"+a[0]);
            try
            {
            	addressToRead1=Integer.decode(a[0]);
            }
            catch(Exception e)
            {
            	
            	System.out.println("Except"+e);
            }
           
            addressToRead1 /= 4; //Converting to a word address
            int blockAddress = addressToRead1 / 4;
            int location = (blockAddress % sets.length); //Location = (MemoryAddress % CacheSize)
            System.out.println(blockAddress + ": set " + location);
            Set setToPlaceAddress = sets[location];
            
            System.out.println("Set to place add setLength"+sets[location].setLength);
            System.out.println("Set to place add blocks"+sets[location].blocks);
            boolean isHit = setToPlaceAddress.checkQueue(blockAddress);
            System.out.println(totalCount + "  AT  " + location + " : " + sets[location].blocks);
            if(isHit) {
                hitCount++;
            }
            else {
                missCount++;
            }
            System.out.println(isHit);
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
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Cache Sets: " + sets.length + "\n");
        sb.append("Set Associativity: " + setAssoc + "\n");
        sb.append("Block Size: 4");

        return sb.toString();
    }
}