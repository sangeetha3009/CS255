import java.util.*;
import java.io.IOException;

public class LRU <K,V> extends LinkedHashMap<K,V>{
	private static final long serialVerionUID=-1L;
	private int size;
	
	private LRU(int size){
		super(size,0.75f,true);
		this.size=size;
	}
	
	public static <K,V> LRU <K,V> newInstance(int size){
		return new LRU<K,V> (size);
	}
	
	public void setMaxSize(int size){
		this.size=size;
	}
	
	@Override
	protected boolean removeEldestEntry(Map.Entry<K,V> eldest){

		return size() > size;
	}
	
	
}