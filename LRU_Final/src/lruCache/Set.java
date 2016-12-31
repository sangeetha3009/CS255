package lruCache;

public class Set {

    public int setLength=0; //Set associativity
    LRU<Long, String> blocks;
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
    public boolean checkQueue(long blockAddress,String data) {
    	System.out.println("bk add "+blockAddress);
        if(blocks.containsKey(blockAddress)){ 
            updateQueue(blockAddress,data);
            return true; //It's a hit
        }

        insertWithLRU(blockAddress,data); //Insert address with LRU algorithm
        return false; //It's a miss
    }

    public int getLRU(long blockAddress){
    	if(blocks.containsKey(blockAddress)){
    		blocks.values();
    		System.out.println(blockAddress);
    		return 1;
    	}
    	else 
    		return 0;
    		
    
    }
    /**
     * Method to move address to the back of the queue
     */
    private void updateQueue(long mostRecent,String data) {

    	blocks.remove(mostRecent);
    	insertWithLRU(mostRecent,data);
    	return;
    }

    /**
     * Algorithm to remove the least recently used address and add a new one
     */
    private void insertWithLRU(long address,String data) {

        blocks.put(address,data); //Add new one to the back
        return;
    }

}