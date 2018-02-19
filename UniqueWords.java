import java.util.Scanner;

public class UniqueWords {
	
	public static void main(String[] args) {
		
		System.out.println("Please enter a sentence");
		Scanner sc = new Scanner(System.in);
		String sent = sc.nextLine();
		String[] words = sent.split(" ");
		
	    int count = 0;
	    String[] unique = new String[words.length];
        for (int i = 0; i < words.length; i++) {
    	   for (int j = 0; j < words.length; j++) {
    		   if (i != j) {
    			   if (words[i].equals(words[j])) {
    				   count++; 
    			   } 
    		   }	
    	   }
    	   if (count > 0) {
    		   unique[i] = null;
    		   count = 0;
    	   } else {
    		   unique[i] = words[i];
    		   count = 0;
    	   }
       }
       
       int wordCount = 0;
       for (int x =  0; x < unique.length; x++) {
    	   if (unique[x] != null) {
    		   wordCount++;
    	   } else {
    		   wordCount = wordCount;
    	   }
       }
       System.out.println(wordCount);
	}
}