package linked_yarn;

import static org.junit.Assert.*;

import org.junit.*;
import org.junit.rules.Timeout;

import yarn.Yarn;

public class LinkedYarnTests {
    
    // =================================================
    // Test Configuration
    // =================================================
    
    // Global timeout to prevent infinite loops from
    // crashing the test suite
    @Rule
    public Timeout globalTimeout = Timeout.seconds(2);
    
    // Used as the basic empty LinkedYarn to test; the @Before
    // method is run before every @Test
    LinkedYarn ball;
    @Before
    public void init () {
        ball = new LinkedYarn();
    }
    
    
    // =================================================
    // Unit Tests
    // =================================================
    
    // Initialization Tests
    // -------------------------------------------------
    @Test
    public void testInit() {
        assertTrue(ball.isEmpty());
        assertEquals(0, ball.getSize());
        
        assertEquals(0,ball.getUniqueSize());
    }

    // Basic Tests
    // -------------------------------------------------
    @Test
    public void testIsEmpty() {
        assertTrue(ball.isEmpty());
        ball.insert("not_empty");
        assertFalse(ball.isEmpty());
    }
    
    @Test
    public void testIsEmpty_t2() {
        ball.insert("a");
        ball.insert("b");
        ball.insert("a");
        ball.removeAll("a");
        assertFalse(ball.isEmpty());
        ball.removeAll("b");
        assertTrue(ball.isEmpty());
    }

    @Test
    public void testGetSize() {
        ball.insert("dup");
        ball.insert("dup");
        assertEquals(2, ball.getSize());
        ball.insert("unique");
        assertEquals(3, ball.getSize());
    }

    @Test
    public void testGetUniqueSize() {
        ball.insert("dup");
        ball.insert("dup");
        assertEquals(1, ball.getUniqueSize());
        ball.insert("unique");
        assertEquals(2, ball.getUniqueSize());
    }
    
    @Test
    public void testGetUniqueSize_t3() {
        ball.insert("u1");
        ball.insert("u2");
        ball.remove("u1");
        assertEquals(ball.getUniqueSize(), 1);
        ball.remove("u2");
        assertEquals(ball.getUniqueSize(), 0);
    }

    // LinkedYarn Manipulation Tests
    // -------------------------------------------------
    @Test
    public void testInsert() {
        ball.insert("dup");
        ball.insert("dup");
        ball.insert("unique");
        assertTrue(ball.contains("dup"));
        assertTrue(ball.contains("unique"));
    }

    @Test
    public void testRemove() {
        ball.insert("dup");
        ball.insert("dup");
        assertEquals(2, ball.getSize());
        assertEquals(1, ball.getUniqueSize());
        int dups = ball.remove("dup");
        assertEquals(1, ball.getSize());
        assertEquals(1, ball.getUniqueSize());
        assertEquals(1, dups);
    }
    
    @Test
    public void testRemove_t1() {
        ball.insert("uni1");
        int dups = ball.remove("uni1");
        assertEquals(ball.getSize(), 0);
        assertEquals(dups, 0);
        assertFalse(ball.contains("uni1"));
    }

    @Test
    public void testRemoveAll() {
        ball.insert("dup");
        ball.insert("dup");
        ball.insert("unique");
        assertEquals(3, ball.getSize());
        assertEquals(2, ball.getUniqueSize());
        ball.removeAll("dup");
        assertEquals(1, ball.getSize());
        assertEquals(1, ball.getUniqueSize());
    }
    
    @Test
    public void testRemoveAll_t1() {
        ball.removeAll("uni1");
        ball.insert("uni1");
        ball.removeAll("uni1");
        assertEquals(ball.getSize(), 0);
        assertFalse(ball.contains("uni1"));
    }
    
    @Test
    public void testRemoveAll_t3() {
        ball.insert("dup1");
        ball.insert("dup1");
        ball.insert("dup2");
        ball.insert("dup2");
        ball.insert("uni1");
        ball.removeAll("dup1");
        assertEquals(ball.getSize(), 3);
        assertEquals(ball.getUniqueSize(), 2);
        assertFalse(ball.contains("dup1"));
        assertTrue(ball.contains("uni1"));
        ball.removeAll("dup2");
        assertEquals(ball.getSize(), 1);
        assertEquals(ball.getUniqueSize(), 1);
        assertFalse(ball.contains("dup2"));
        ball.removeAll("uni1");
        assertEquals(ball.getSize(), 0);
        assertEquals(ball.getUniqueSize(), 0);
    }

    @Test
    public void testCount() {
        ball.insert("dup");
        ball.insert("dup");
        ball.insert("unique");
        assertEquals(2, ball.count("dup"));
        assertEquals(1, ball.count("unique"));
        assertEquals(0, ball.count("forneymon"));
    }
    
    @Test
    public void testCount_t2() {
        ball.insert("dup");
        ball.insert("dup2");
        ball.insert("dup");
        ball.insert("dup2");
        assertEquals(ball.count("dup"), 2);
        assertEquals(ball.count("dup2"), 2);
        ball.removeAll("dup");
        assertEquals(ball.count("dup"), 0);
        ball.remove("dup2");
    }


    @Test
    public void testContains() {
        ball.insert("dup");
        ball.insert("dup");
        ball.insert("unique");
        assertTrue(ball.contains("dup"));
        assertTrue(ball.contains("unique"));
        assertFalse(ball.contains("forneymon"));
    }
    // This is tested pretty much everywhere so...


    @Test
    public void testGetMostCommon() {
        ball.insert("dup");
        ball.insert("dup");
        ball.insert("unique");
        ball.insert("cool");
        assertEquals("dup", ball.getMostCommon());
        ball.insert("cool");
        String mc = ball.getMostCommon();
        assertTrue(mc.equals("dup") || mc.equals("cool"));
    }

    // Iterator Tests
    // -------------------------------------------------
    @Test
    public void testIteratorBasics() {
        ball.insert("a");
        ball.insert("a");
        ball.insert("a");
        ball.insert("b");
        LinkedYarn.Iterator it = ball.getIterator();

        // Test next()
        LinkedYarn dolly = new LinkedYarn(ball);
        while (true) {
            String gotten = it.getString();
            assertTrue(dolly.contains(gotten));
            dolly.remove(gotten);
            if (it.hasNext()) {it.next();} else {break;}
        }
        assertTrue(dolly.isEmpty());
        assertFalse(it.hasNext());
        
        // Test prev()
        dolly = new LinkedYarn(ball);
        while (true) {
            String gotten = it.getString();
            assertTrue(dolly.contains(gotten));
            dolly.remove(gotten);
            if (it.hasPrev()) {it.prev();} else {break;}
        }
        assertTrue(dolly.isEmpty());
        assertFalse(it.hasPrev());
        
        int countOfReplaced = ball.count(it.getString());
        it.replaceAll("replaced!");
        assertEquals(countOfReplaced, ball.count("replaced!"));
        assertTrue(it.isValid());
        
        ball.insert("c");
        assertFalse(it.isValid());
    }
    
    // Inter-LinkedYarn Tests
    // -------------------------------------------------
    @Test
    public void testCopy() {
        ball.insert("dup");
        ball.insert("dup");
        ball.insert("unique");
        LinkedYarn dolly = new LinkedYarn(ball);
        assertEquals(2, dolly.count("dup"));
        assertEquals(1, dolly.count("unique"));
        dolly.insert("cool");
        assertFalse(ball.contains("cool"));
    }
    
    @Test
    public void testCopy_t3() {
        ball.insert("a");
        ball.insert("b");
        ball.insert("c");
        ball.insert("b");
        ball.insert("c");
        LinkedYarn dolly = new LinkedYarn(ball);
        dolly.insert("c");
        dolly.removeAll("a");
        assertTrue(ball.contains("a"));
        assertEquals(2, dolly.count("b"));
        assertEquals(3, dolly.count("c"));
        assertEquals(2, ball.count("c"));
    }


    @Test
    public void testSwap() {
        LinkedYarn y1 = new LinkedYarn();
        y1.insert("dup");
        y1.insert("dup");
        y1.insert("unique");
        LinkedYarn y2 = new LinkedYarn();
        y2.insert("yo");
        y2.insert("sup");
        y1.swap(y2);
        assertTrue(y1.contains("yo"));
        assertTrue(y1.contains("sup"));
        assertTrue(y2.contains("dup"));
        assertTrue(y2.contains("unique"));
        assertFalse(y1.contains("dup"));
    }

    // Static Method Tests
    // -------------------------------------------------
    @Test
    public void testKnit() {
        LinkedYarn y1 = new LinkedYarn();
        y1.insert("dup");
        y1.insert("dup");
        y1.insert("unique");
        LinkedYarn y2 = new LinkedYarn();
        y2.insert("dup");
        y2.insert("cool");
        LinkedYarn y3 = LinkedYarn.knit(y1, y2);
        assertEquals(3, y3.count("dup"));
        assertEquals(1, y3.count("unique"));
        assertEquals(1, y3.count("cool"));
        y3.insert("test");
        assertFalse(y1.contains("test"));
        assertFalse(y2.contains("test"));
    }

    @Test
    public void testTear() {
        LinkedYarn y1 = new LinkedYarn();
        y1.insert("dup");
        y1.insert("dup");
        y1.insert("unique");
        LinkedYarn y2 = new LinkedYarn();
        y2.insert("dup");
        y2.insert("cool");
        LinkedYarn y3 = LinkedYarn.tear(y1, y2);
        assertEquals(1, y3.count("dup"));
        assertEquals(1, y3.count("unique"));
        assertFalse(y3.contains("cool"));
        y3.insert("test");
        assertFalse(y1.contains("test"));
        assertFalse(y2.contains("test"));
    }
    
    @Test
    public void testTear_t2() {
        ball.insert("a");
        ball.insert("b");
        ball.insert("a");
        ball.insert("c");
        ball.insert("c");
        LinkedYarn y1 = new LinkedYarn();
        y1.insert("a");
        y1.insert("a");
        y1.insert("b");
        y1.insert("c");
        LinkedYarn y2 = LinkedYarn.tear(ball, y1);
        assertEquals(y2.getSize(), 1);
        assertFalse(y2.contains("a"));
        assertFalse(y2.contains("b"));
      
    }
    
    @Test
    public void testTear_t3() {
        ball.insert("a");
        ball.insert("b");
        ball.insert("b");
        LinkedYarn y2 = LinkedYarn.tear(ball, ball);
        assertEquals(y2.getSize(), 0);
        assertFalse(y2.contains("a"));
    }


    @Test
    public void testSameYarn() {
        LinkedYarn y1 = new LinkedYarn();
        y1.insert("dup");
        y1.insert("dup");
        y1.insert("unique");
        LinkedYarn y2 = new LinkedYarn();
        y2.insert("unique");
        y2.insert("dup");
        y2.insert("dup");
        assertTrue(LinkedYarn.sameYarn(y1, y2));
        assertTrue(LinkedYarn.sameYarn(y2, y1));
        y2.insert("test");
        assertFalse(LinkedYarn.sameYarn(y1, y2));
    }
    
    @Test
    public void testSameYarn_t3() {
        ball.insert("a");
        ball.insert("b");
        ball.insert("b");
        LinkedYarn y1 = new LinkedYarn();
        y1.insert("b");
        y1.insert("a");
        assertFalse(LinkedYarn.sameYarn(ball, y1));
        ball.removeAll("b");
        y1.removeAll("b");
        assertTrue(LinkedYarn.sameYarn(ball, y1));
    }

}

