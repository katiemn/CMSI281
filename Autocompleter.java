package autocompleter;

import java.util.ArrayList;

public class Autocompleter implements AutocompleterInterface {

    // -----------------------------------------------------------
    // Fields
    // -----------------------------------------------------------
    TTNode root;
    
    
    // -----------------------------------------------------------
    // Constructor
    // -----------------------------------------------------------
    Autocompleter () {
        root = null;
    }
    
    
    // -----------------------------------------------------------
    // Methods
    // -----------------------------------------------------------
    
    public boolean isEmpty () {
    	return root == null;
    }
    
    public void addTerm (String toAdd) {
        root = addTerm(toAdd,root,0);
    }
    
    public boolean hasTerm (String query) {
    	String wordToFind = normalizeTerm(query);
    	int startInd = 0;
    	TTNode current = root;
    	while (current != null) {
    		int compare = compareChars(wordToFind.charAt(startInd),current.letter);
    		if (compare < 0) {
    			current = current.left;
    		} else if (compare > 0) {
    			current = current.right;
    		} else {
    			startInd++;
    			if (startInd == wordToFind.length()) {
    				return current.wordEnd;
    			}
    			current = current.mid;
    		}
    	}
    	return false;
    }
    
    public String getSuggestedTerm (String query) {
    	String wordToFind = normalizeTerm(query);
    	int startInd = 0;
    	TTNode current = root;
    	while (current != null) {
    		int compare = compareChars(wordToFind.charAt(startInd),current.letter);
    		if (compare < 0) {
    			current = current.left;
    		} else if (compare > 0) {
    			current = current.right;
    		} else {
    			startInd++;
    			if (startInd == wordToFind.length()) {
    				if (current.wordEnd == true) {
    					return wordToFind;
    				} else {
    					words = new ArrayList<String>();
    					inorder (current.mid,wordToFind);
    					return words.get(0);
    				}
    			}
    			current = current.mid;
    		}
    	}
    	return null;
    }
    
    public ArrayList<String> getSortedTerms () {
    	words = new ArrayList<String>();
        inorder(root,"");
        return words;
    }
    
    
    // -----------------------------------------------------------
    // Helper Methods
    // -----------------------------------------------------------
    
    private String normalizeTerm (String s) {
        // Edge case handling: empty Strings illegal
        if (s == null || s.equals("")) {
            throw new IllegalArgumentException();
        }
        return s.trim().toLowerCase();
    }
    
    /*
     * Returns:
     *   int less than 0 if c1 is alphabetically less than c2
     *   0 if c1 is equal to c2
     *   int greater than 0 if c1 is alphabetically greater than c2
     */
    private int compareChars (char c1, char c2) {
        return Character.toLowerCase(c1) - Character.toLowerCase(c2);
    }
    
    // [!] Add your own helper methods here!
    
    private ArrayList<String> words;
   
    private TTNode addTerm (String s, TTNode n, int startInd) {
        String wordToAdd = normalizeTerm(s);
        if (n == null) {
        	n = new TTNode (wordToAdd.charAt(startInd),false);
        }
        int compare = compareChars(wordToAdd.charAt(startInd),n.letter);
    	if (compare < 0) {
    	    n.left = addTerm(wordToAdd, n.left, startInd);
    	} else if (compare > 0) {
    		n.right = addTerm(wordToAdd, n.right,startInd);
    	} else {
    		startInd++;
    		if (startInd < wordToAdd.length()) {
    			n.mid = addTerm(wordToAdd, n.mid, startInd);
    		} else {
    			n.wordEnd = true;
    		}
    	}
    	return n;
    }
    
    private void inorder (TTNode n, String s) {
    	if (n != null) {
    	    inorder (n.left, s);
    	    s = s + n.letter;
    	    if (n.wordEnd == true) {
    		    words.add(s);
    	    }
    	    inorder(n.mid,s);
    	    s = s.substring(0,s.length() - 1);
    	    inorder(n.right,s);
    	}
    }
     
    // -----------------------------------------------------------
    // TTNode Internal Storage
    // -----------------------------------------------------------
    
    /*
     * Internal storage of autocompleter search terms
     * as represented using a Ternary Tree with TTNodes
     */
    private class TTNode {
        
        boolean wordEnd;
        char letter;
        TTNode left, mid, right;
        
        TTNode (char c, boolean w) {
            letter  = c;
            wordEnd = w;
            left    = null;
            mid     = null;
            right   = null;
        }
        
    }
}
