package linked_yarn;

import java.util.NoSuchElementException;

public class LinkedYarn implements LinkedYarnInterface {

    // -----------------------------------------------------------
    // Fields
    // -----------------------------------------------------------
    private Node head;
    private int size, uniqueSize, modCount;
    
    // -----------------------------------------------------------
    // Constructors
    // -----------------------------------------------------------
    LinkedYarn () {
        head = null;
        size = 0;
        uniqueSize = 0;
        modCount = 0;
    }
    
    LinkedYarn (LinkedYarn other) {
    	for (Node c = other.head; c != null; c = c.next) {
    		this.append(c.text, c.count);
    	}
    }
    
    // -----------------------------------------------------------
    // Methods
    // -----------------------------------------------------------
    public boolean isEmpty () {
        return size == 0;
    }
    
    public int getSize () {
        return size;
    }
    
    public int getUniqueSize () {
        return uniqueSize;
    }
    
    public void insert (String toAdd) {
        append(toAdd, 1);
    }
    
    public int remove (String toRemove) {
        return removeInstances(toRemove, 1);
    }
    
    public void removeAll (String toNuke) {
    	Node located = locate(toNuke);
    	if (found == true) {
            removeInstances(toNuke, located.count);
    	}
    }
    
    public int count (String toCount) {
        Node located = locate(toCount);
        if (found == false) {
        	return 0;
        }
        return located.count;
    }
    
    public boolean contains (String toCheck) {
        locate(toCheck);
        if (found == true) {
        	return true;
        } else {
        	return false;
        }
    }
    
    public String getMostCommon () {
        String mostCommon = null;
        int highestCount = -1;
        for (Node c = head; c != null; c = c.next) {
        	Node currentNode = c;
        	if (currentNode.count > highestCount) {
        		highestCount = currentNode.count;
        		mostCommon = currentNode.text;
        	}
        }
        return mostCommon; 
    }
    
    public void swap (LinkedYarn other) {
        Node tempHead = head;
        int tempSize = size;
        int tempUniqueSize = uniqueSize;
        int tempModCount = modCount;
        
        head = other.head;
        size = other.size;
        uniqueSize = other.uniqueSize;
        modCount = other.modCount + 1;
        
        other.head = tempHead;
        other.size = tempSize;
        other.uniqueSize = tempUniqueSize;
        other.modCount = tempModCount + 1;
    }
    
    public LinkedYarn.Iterator getIterator () {
        if (this.isEmpty() == true) {
        	throw new IllegalStateException();
        } else {
            LinkedYarn.Iterator it = new Iterator(this);
            return it;
        }
    }
    
    // -----------------------------------------------------------
    // Static methods
    // -----------------------------------------------------------
    
    public static LinkedYarn knit (LinkedYarn y1, LinkedYarn y2) {
        LinkedYarn result = new LinkedYarn(y1);
        for (Node c = y2.head; c != null; c = c.next) {
        	result.append(c.text, c.count);
        }
        return result;
    }
    
    public static LinkedYarn tear (LinkedYarn y1, LinkedYarn y2) {
        LinkedYarn result = new LinkedYarn(y1);
        for (Node c = y2.head; c != null; c = c.next) {
        	result.removeInstances(c.text, c.count);
        }
        return result;
    }
    
    public static boolean sameYarn (LinkedYarn y1, LinkedYarn y2) {
        return tear(y1, y2).isEmpty() && tear(y2, y1).isEmpty();
    }
    
    // -----------------------------------------------------------
    // Private helper methods
    // -----------------------------------------------------------
    
    // You should add some methods here!
    private boolean found;
    
    private Node locate (String toFind) {
    	Node curr = head;
    	int keep = uniqueSize;
    	while (keep > 0) {
    	    if (curr.text.equals(toFind)) {
    	    	found = true;
    		    break;
    	    } else {
    	    	if (curr.next != null) {
    		        curr = curr.next;
    	    	} 
    	    	keep--;
    	    }
    	}
    	if (keep == 0) {
    		found = false;
    	}
    	return curr;
    }
    
    private void append (String text, int count) {
    	Node located = locate(text);
    	if (found == false) {
    		if (head == null) {
    			head = new Node (text,count);
    			head.next = null;
    			head.prev = null;
    		} else {
    		    Node currentTail = this.getTail();
    		    currentTail.next = new Node (text,count);
    		    currentTail.next.next = null;
    		    currentTail.next.prev = currentTail;
    		}
        	uniqueSize++;
    	} else {
    	  located.count += count; 
    	}
    	size += count;
    	modCount++;
    }
    
    private int removeInstances (String text, int count) {
        Node located = locate(text);
        if (found == false) {
        	return 0;
        } 
        int newCount = located.count - count;
        if (newCount <= 0) {
        	if (located.prev == null) {
        		if (uniqueSize == 1) {
        			head = null;
        		} else {
        		    head = located.next;
        	        head.prev = null;
        		}
        	} else if (located.next == null) {
        	    Node newEnd = located.prev;
        	    newEnd.next = null;
        	} else {
        	    Node left = located.prev;
        	    Node right = located.next;
        	    left.next = right;
        	    right.prev = left;
        	}
        	size -= located.count;
        	uniqueSize--;
        	modCount++;
        	return 0;
        } else {
        	located.count = newCount;
        	size -= count;
        	modCount++;
        	return newCount;
        }
    }
    
    private Node getTail() {
    	Node tail = head;
        for (Node c = head; c != null; c = c.next) {
    	    tail = c;
    	}
        return tail;
    }
    
    // -----------------------------------------------------------
    // Inner Classes
    // -----------------------------------------------------------
    
    public class Iterator implements LinkedYarnIteratorInterface {
        private LinkedYarn owner;
        private Node current;
        private int itModCount;
        private int nextCount;
        private int prevCount;
        private int index;
        
        
        Iterator (LinkedYarn y) {
            current = head;
        	itModCount = modCount;
        	nextCount = 1;
        	prevCount = 1;
        	index = 1;
        }
        
        public boolean hasNext () {
        	if (!isValid()) {
        		return false;
        	}
        	if (current != null && index < size) {
        		return true;
        	} else {
        		return false;
        	}
        }
        
        public boolean hasPrev () {
        	if (!isValid()) {
        		return false;
        	}
        	if (current != null && index > 1) {
        		return true;
        	} else {
        		return false;
        	}
        }
        
        public boolean isValid () {
            return itModCount == modCount;
        }
        
        public String getString () {
            if (!isValid()) {
            	return null;
            } else {
            	if (current != null) {
            		return current.text;
            	} else {
            		return null;
            	}
            }
        }

        public void next () {
        	if (!hasNext()) {
        		throw new NoSuchElementException();
        	} 
        	if (!isValid()) {
        		throw new IllegalStateException();
        	}
        	if (nextCount < current.count) {
        		nextCount++;
        		index++;
        	} else {
        		current = current.next;
        		nextCount = 1;
        		index++;
        	}
        }
        
        public void prev () {
            if (!hasPrev()) {
            	throw new NoSuchElementException();
            }
            if (!isValid()) {
            	throw new IllegalStateException();
            }
            if (prevCount < current.count) {
            	prevCount++;
            	index--;
            } else {
            	current = current.prev;
            	prevCount = 1;
            	index--;
            }
        }
        
        public void replaceAll (String toReplaceWith) {
        	if (!isValid()) {
        		throw new IllegalStateException();
        	} else {
        	    current.text = toReplaceWith;
        	    itModCount++;
        	    modCount++;
            }
        }
    }
    
    class Node {
        Node next, prev;
        String text;
        int count;
        
        Node (String t, int c) {
            text = t;
            count = c;
        }
    }
}

/** 
In general, I found that the linked list implementation of yarns was 
easier to program. However, since we first completed this problem as 
a sequential list it may have felt easier because I had seen it once 
before. However, in regards to specific operations, removing strings 
from a linked list felt simpler. It was much easier to simply repair 
references in a linked list when removing a node, rather than having 
to fill in missing strands with other data to serve as a placeholder. 
On the other hand, locating strings was simpler in a sequential list 
than in a linked one. This was due to the fact that random access in 
sequential lists made it much easier to look through all of the data,
but an entire separate iterator class had to be created to allow users
to look through the data in linked lists. Similarly, the private 
method that I created in each of these implementations to find a 
particular string so that it could be removed or added to was easier 
in the sequential list implementation due to random access as well. 

Beyond the simplicity of programming one implementation over the 
other, there many actual scenarios where using a specific 
implementation would be more practical. For example, if one were to 
constantly be having to reference all of the data, then using a 
sequential list would be more effective, especially if the strands
being looked at were not right next to each other. In this case, 
utilizing a sequential list would be better due to the random access 
that the sequential implementation provides. This makes it simple to 
see any element of the list where using a linked list would require 
iterating through each string in the other that they were added using 
the next and previous methods of the iterator class. In another 
scenario, if there was a lot of data that had to be added then the 
linked list would be more practical. This is because the sequential 
list can only accommodate 100 strands where there is not a limit on 
the number of nodes that can be stored in a linked list. 
*/