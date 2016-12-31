package lruCache;


import java.util.*;
import java.io.IOException;
import java.util.Scanner;

public class LRUCache {

 
	public static void main(String[] args){
		Scanner in = new Scanner(System.in);
        int numSets, setAssoc;
        int ip_cnt=0;
        int ip_cnt1=0;
        do
        {
        	if(ip_cnt == 0){
        	ip_cnt = 1;
            System.out.print("Enter the cache size(1/32/64/128/256/512/1024/2048/4096): ");
            numSets = in.nextInt();
        	}
        	else{
        		System.out.println("Incorrect Input! Please Enter Cache size in the following powers of 2");
        		System.out.print("Enter the cache size (1/32/64/128/256/512/1024/2048/4096): ");
                numSets = in.nextInt();
        		
        	}
        }
        while(numSets != 1 && numSets != 32 && numSets != 64 && numSets != 128 && numSets != 256 && numSets != 512 && numSets != 1024 && numSets!=2048 && numSets!=4096);
        do
        {
        	if(ip_cnt1 == 0){
        		
        	ip_cnt1=1;
            System.out.print("Enter set associativity (1/2/4/8): ");
            setAssoc = in.nextInt();
        	}
        	else{
        		System.out.println("Incorrect Input! Please enter set associativity in the following powers of 2");	
        		 System.out.print("Enter set associativity (1/2/4/8): ");
                 setAssoc = in.nextInt();
        	}
        }
        while(setAssoc != 1 && setAssoc != 2 && setAssoc != 4 && setAssoc !=8);
        Cache cache = new Cache(numSets, setAssoc);
        System.out.println("Cache created!");

        //Getting file to read from
        System.out.print("Enter the filename to check: ");
        String datFile = in.next();
        System.out.print(datFile);
        //Filling cache from file
        
        try{
        	cache.fillFromFile(datFile);
        }
        catch(Exception ex)
        {
        	System.out.print(ex);
        }
        cache.printStats();

        }
        
	}
	
	

