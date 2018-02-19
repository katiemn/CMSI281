package sentinal;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Sentinal implements SentinalInterface {

    // -----------------------------------------------------------
    // Fields
    // -----------------------------------------------------------
    
    private PhraseHash posHash, negHash;

    // -----------------------------------------------------------
    // Constructor
    // -----------------------------------------------------------
    
    Sentinal (String posFile, String negFile) throws FileNotFoundException {
    	posHash = new PhraseHash();
    	negHash = new PhraseHash();
        loadSentimentFile(posFile, true);
        loadSentimentFile(negFile,false);
    }
    
    // -----------------------------------------------------------
    // Methods
    // -----------------------------------------------------------
    
    public void loadSentiment (String phrase, boolean positive) {
        if (positive == true) {
        	posHash.put(phrase);
        } else {
        	negHash.put(phrase);
        }
    }
    
    public void loadSentimentFile (String filename, boolean positive) throws FileNotFoundException {
        if (positive == true) {
        	File posFile = new File(filename);
        	Scanner sc = new Scanner(posFile);
        	while (sc.hasNextLine()) {
        		String s = sc.nextLine();
        		loadSentiment(s, true);
        	}
        	sc.close();
        } else {
            File negFile = new File(filename);
            Scanner sc = new Scanner(negFile);
            while (sc.hasNextLine()) {
                String s = sc.nextLine();
            	loadSentiment(s, false);
            }
            sc.close();
        }
    }
    
    public String sentinalyze (String filename) throws FileNotFoundException {
    	int posCount = 0;
    	int negCount = 0;
    	int posLongestLength = posHash.longestLength();
    	int negLongestLength = negHash.longestLength();
    	File file = new File(filename);
        Scanner sc = new Scanner(file);
        while (sc.hasNextLine()) {
            String s = sc.nextLine();
        	String[] sent = s.split(" ");
        	posCount += phraseScanPos(posLongestLength, sent);
        	negCount += phraseScanNeg(negLongestLength, sent);
        }
        sc.close();
        if (posCount > negCount) {
        	return "positive";
        } else if (negCount > posCount) {
        	return "negative";
        } else {
        	return "neutral";
        }
    }
    
    // -----------------------------------------------------------
    // Helper Methods
    // -----------------------------------------------------------
    
    // TODO: Add your helper methods here!
    
    private int phraseScanPos (int longestLength, String[] sent) {
    	int n = longestLength;
    	int posCount = 0;
    	while (n >= 1) {
    		int begin = 0;
    		int end = n;
    		while (end <= sent.length) {
    			String[] part = new String[n];
    			part = Arrays.copyOfRange(sent,begin,end);
    			String toFind = stringFromPart(part);
    			String contains = posHash.get(toFind);
    			if (contains != null) {
    				posCount++;
    			} 
    			begin++;
    			end++;
    		}
    		n--;
    	}
    	return posCount;
    }
    
    private int phraseScanNeg (int longestLength, String[] sent) {
    	int n = longestLength;
    	int negCount = 0;
    	while (n >= 1) {
    		int begin = 0;
    		int end = n;
    		while (end <= sent.length) {
    			String[] part = new String[n];
    			part = Arrays.copyOfRange(sent,begin,end);
    			String toFind = stringFromPart(part);
    			String contains = negHash.get(toFind);
    			if (contains != null) {
    				negCount ++;
    			}
    			begin++;
    			end++;
    		}
    		n--;
    	}
    	return negCount;
    }
    
    private String stringFromPart (String[] s) {
    	String total = new String ("");
    	for (int i = 0; i < s.length; i++) {
    		if (i == s.length - 1) {
    			total += s[i];
    			return total;
    		}
    		total += s[i] + " ";
    	}
    	return total;
    }
    
}