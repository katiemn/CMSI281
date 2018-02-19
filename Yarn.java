package yarn;

public class Yarn implements YarnInterface {

    // -----------------------------------------------------------
    // Fields
    // -----------------------------------------------------------
    private Strand[] items;
    private int size;
    private int uniqueSize;
    private final int MAX_SIZE = 100;
    
    
    // -----------------------------------------------------------
    // Constructors
    // -----------------------------------------------------------
    Yarn () {
        items = new Strand[MAX_SIZE];
        size = 0;
        uniqueSize = 0;
    }
    
    Yarn (Yarn other) {
        items = new Strand[MAX_SIZE];
        size = other.size;
        uniqueSize = other.uniqueSize;
        for (int i = 0; i < other.uniqueSize; i++) {
        	this.items[i] = new Strand(other.items[i].text, other.items[i].count);
        }
    }
    
    // -----------------------------------------------------------
    // Methods
    // -----------------------------------------------------------
    public boolean isEmpty () {
        if (size == 0) {
            return true; 
        } else {
            return false;
        }
    }
    
    public int getSize () {
        return size;
    }
    
    public int getUniqueSize () {
        return uniqueSize;
    }
    
    public boolean insert (String toAdd) {
    	checkCapacity();
    	if (full == true) {
    		return false;
    	} else {
    		append(toAdd);
    		return true;
    	}
        
    }
    
    public int remove (String toRemove) {
        checkInstance(toRemove);
        if (result == true) {
            if (items[placeholder].count == 1) {
       	        this.removeAll(toRemove);
       	    } else {
       		    items[placeholder].count --;
       		    size --;
       	    }
         } else {
    	     return 0;
         }
        return items[placeholder].count;  
    }
        
    public void removeAll (String toNuke) {
        checkInstance(toNuke);
        int decrease = items[placeholder].count;
        if (result == true) {
        	items[placeholder].text = items[uniqueSize-1].text;
        	items[placeholder].count = 0;
        	uniqueSize --; 
        	size = size - decrease;
        }
    }
    
    public int count (String toCount) {
    	int counter = 0;
        checkInstance(toCount);
        if (result == true) {
        	counter = items[placeholder].count;
        } 
        return counter;
    }
    
    public boolean contains (String toCheck) {
        checkInstance(toCheck);
        if(result == true) {
        	return true;
        } else {
        	return false;
        }
    }
    
    public String getNth (int n) {
    	String print = "";
    	if (n < 0 || n >= size) {
    		return "Not a valid index";
    	} else {
    		int c = 0;
    	    for (int i = 0; i < uniqueSize; i++) {
    	    	if (n < items[i].count + c) {
    	    		print = items[i].text;
    	    		break;
    	    	} else {
    	    		c += items[i].count;
    	    	}
    	    }
    	}
    	return print;
    }
    
    public String getMostCommon () {
    	int current;
        int mostCommon = 0;
        int place = 0;
        if (size == 0) {
        	return null;
        } else {
            for (int i = 0; i < uniqueSize; i++) {
                current = items[i].count;
                if (current > mostCommon) {
            	    mostCommon = current;
            	    place = i;
                }
            }
        }
        return items[place].text;
    }
    
    public void swap (Yarn other) {
    	Yarn temp = new Yarn(other);
    	other.items = items;
    	items = temp.items;
    	int sizeTemp = this.size;
    	this.size = other.size;
    	other.size = sizeTemp;
    	int uniqueTemp = this.uniqueSize;
    	this.uniqueSize = other.uniqueSize;
    	other.uniqueSize = uniqueTemp;
        
    }
    
    public String toString () {
    	String print = "";
        for (int i = 0; i < uniqueSize; i++) {
        	if (i == uniqueSize - 1) {
        		print += "\""+items[i].text+"\""+":"+" "+String.valueOf(items[i].count);
        	} else {
        		print += "\""+items[i].text+"\""+":"+" "+String.valueOf(items[i].count)+","+" ";
        	}
        }
        return "{"+" "+print+" "+"}";
    }
    
    

    
    // -----------------------------------------------------------
    // Static methods
    // -----------------------------------------------------------
    
    public static Yarn knit (Yarn y1, Yarn y2) {
        Yarn n = new Yarn(y1);
        for (int i = 0; i < y2.uniqueSize; i++) {
            n.append(y2.items[i].text);
            if (y2.items[i].count > 1) {
            	n.items[n.placeholder].count = n.items[n.placeholder].count + (y2.items[i].count - 1);
            	n.size = n.size + y2.items[i].count - 1;
            }
        }
        return n;   
    }
    
    public static Yarn tear (Yarn y1, Yarn y2) {
        Yarn n = new Yarn(y1);
        for (int i = 0; i < y1.uniqueSize; i++) {
        	if (y2.contains(y1.items[i].text) == true) {
        	    n.remove(y1.items[i].text);
        	} else {
        		i = i;
        	}
        }
        return n;   
    }
    
    public static boolean sameYarn (Yarn y1, Yarn y2) {
        if (y1.uniqueSize != y2.uniqueSize) {
        	return false;
        } else {
        	for (int i = 0; i < y2.uniqueSize; i++) {
        		y1.checkInstance(y2.items[i].text);
        		if (y1.result == false) {
        			return false;
        		} else {
        			if (y1.items[y1.placeholder].count != y2.items[i].count) {
        				return false;
        			}
        		}
        	}
        	return true;
        }
    }
    
    
    // -----------------------------------------------------------
    // Private helper methods
    // -----------------------------------------------------------
    // Add your own here!
    
    private boolean result;
    private boolean full;
    private int placeholder;
   
    
    private void append (String toAdd) {
        checkInstance(toAdd);
        if (result == true) {
        	int oldCount = items[placeholder].count;
        	items[placeholder].count = oldCount + 1;
        	size++;
        } else {
        	items[uniqueSize] = new Strand (toAdd, 1);
            size++;
            uniqueSize++;
        }
    }
    
    private void checkCapacity () {
        if (uniqueSize == MAX_SIZE) {
            full = true;
        } else {
            full = false;
        }
    }
    
    private void checkInstance (String toCheck) {
        for (int i = 0; i < uniqueSize; i++) {
            if (!items[i].text.equals(toCheck)) {
                result = false;
            } else {
            	result = true;
                placeholder = i;
                break;
            }
        }
    }
   
    
}

class Strand {
    String text;
    int count;
    
    Strand (String s, int c) {
        text = s;
        count = c;
    }
}
