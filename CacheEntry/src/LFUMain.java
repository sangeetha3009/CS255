
public class LFUMain {
	
	public static void main(String[] args){
		LFUCache f=new LFUCache(2);
		f.addCacheEntry(1, "A");
		f.addCacheEntry(2, "B");
		f.addCacheEntry(3, "D");
		boolean t=f.isFull();
		System.out.println(t);
		String l=f.getCacheEntry(2);
		System.out.println(l);
	}

}
