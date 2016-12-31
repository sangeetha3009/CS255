import java.util.*;

public class Set {

    public int setLength=0; //Set associativity
    LRU<Integer, Integer> blocks;
    /**
     * Constructor
     */

    public Set()
    {
    	this.setLength = 0;
    	blocks=LRU.newInstance(setLength);
        blocks.setMaxSize(setLength);
    }
    public Set(int setLength)
    {
        this.setLength = setLength;
        blocks=LRU.newInstance(setLength);
        blocks.setMaxSize(setLength);
        
    }
    
    /**
     * Check if the block is already there and placing it if it is not
     */
    public boolean checkQueue(int blockAddress) {
        if(blocks.containsValue(blockAddress)){ 

            updateQueue(blockAddress);
            //Move it to the back (most recently used)
 
            return true; //It's a hit
        }
        insertWithLRU(blockAddress); //Insert address with LRU algorithm

        return false; //It's a miss
    }

    public int getLRU(int blockAddress){
    	if(blocks.containsValue(blockAddress))
    		return 1;
    	else 
    		return 0;
    		
    
    }
    /**
     * Method to move address to the back of the queue
     */
    private void updateQueue(int mostRecent) {
    	blocks.remove(mostRecent);
    	insertWithLRU(mostRecent);
    }

    /**
     * Algorithm to remove the least recently used address and add a new one
     */
    private void insertWithLRU(int address) {

        blocks.put(address,address); //Add new one to the back
    }

}