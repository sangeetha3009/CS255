import java.util.*;
import java.io.IOException;
import java.util.Scanner;

public class LRUCache {

 
	public static void main(String[] args){
		Scanner in = new Scanner(System.in);
        int numSets, setAssoc;

        do
        {
            System.out.print("Enter number of cache sets (1/32/64/128/256/512): ");
            numSets = in.nextInt();
        }
        while(numSets != 1 && numSets != 32 && numSets != 64 && numSets != 128 && numSets != 256 && numSets != 512);

        do
        {
            System.out.print("Enter set associativity (1/2/4): ");
            setAssoc = in.nextInt();
        }
        while(setAssoc != 1 && setAssoc != 2 && setAssoc != 4);
        Cache cache = new Cache(numSets, setAssoc);
        System.out.println("Cache created!");

        //Getting file to read from
        System.out.print("Enter the filename to check: ");
        String datFile = in.next();
        System.out.print(datFile);
        //Filling cache from file
        in.close(); //End of keyboard input
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
