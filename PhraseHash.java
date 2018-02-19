package sentinal;

import java.util.LinkedList;

public class PhraseHash implements PhraseHashInterface {

    // -----------------------------------------------------------
    // Fields
    // -----------------------------------------------------------
    
    private final static int BUCKET_COUNT = 1000;
    private int size, longest;
    private LinkedList<String>[] buckets;
    
    
    // -----------------------------------------------------------
    // Constructor
    // -----------------------------------------------------------
    
    @SuppressWarnings("unchecked") // Don't worry about this >_>
    PhraseHash () {
        buckets = new LinkedList[BUCKET_COUNT];
        for (int i = 0; i < BUCKET_COUNT; i++) {
        	buckets[i] = null;
        }
        longest = 0;
    }
    
    // -----------------------------------------------------------
    // Public Methods
    // -----------------------------------------------------------
    
    public int size () {
        return size;
    }
    
    public boolean isEmpty () {
        return size == 0;
    }
    
    public void put (String s) {
        int index = hash(s);
        int length = wordLength(s);
        LinkedList<String> current = buckets[index];
        if (current == null) {
            LinkedList<String> toAdd = new LinkedList<String>();
            toAdd.add(s);
            buckets[index] = toAdd;
            size++;
            if (length > longest) {
            	longest = length;
            }
        } else {
        	for (String toFind : current) {
        		if (toFind.equals(s)) {
        			return;
        		}
        	}
        	current.add(s);
        	buckets[index] = current;
        	size++;
        	if (length > longest) {
            	longest = length;
            }
        }
    }
    
    public String get (String s) {
        if (s == null) {
        	return null;
        }
        int index = hash(s);
        LinkedList<String> current = buckets[index];
        if (current == null) {
        	return null;
        }
        for (String toFind: current) {
        	if(toFind.equals(s)) { 
        	    return toFind;
        	}
        }
        return null;
    }
    
    public int longestLength () {
        return longest;
    }
    
    // -----------------------------------------------------------
    // Helper Methods
    // -----------------------------------------------------------
    
    private int hash (String s) {
        int hash = 0;
        for (int i = 0; i < s.length(); i++) {
        	hash = (23 * hash + s.charAt(i)) % 	BUCKET_COUNT;
        }
        return hash;
    }
    
    private int wordLength (String s) {
    	String[] words = s.split(" ");
    	return words.length;
    }
    
}
