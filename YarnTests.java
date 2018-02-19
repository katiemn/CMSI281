package yarn;

import static org.junit.Assert.*;
import org.junit.Test;

public class YarnTests {

    @Test
    public void testYarn() {
        Yarn ball = new Yarn();
        
        Yarn happy = new Yarn();
    }

    @Test
    public void testIsEmpty() {
        Yarn ball = new Yarn();
        assertTrue(ball.isEmpty());
        ball.insert("not_empty");
        assertFalse(ball.isEmpty());
        
        Yarn happy = new Yarn();
        happy.insert("puppy");
        assertFalse(happy.isEmpty());
        happy.remove("puppy");
        assertTrue(happy.isEmpty());
    }

    @Test
    public void testGetSize() {
        Yarn ball = new Yarn();
        ball.insert("dup");
        ball.insert("dup");
        assertEquals(2, ball.getSize());
        ball.insert("unique");
        assertEquals(3, ball.getSize());
        
        Yarn happy = new Yarn();
        assertEquals(0, happy.getSize());
        happy.insert("pencil");
        happy.insert("pencil");
        assertEquals(2, happy.getSize());
        happy.remove("pencil");
        assertEquals(1, happy.getSize());
    }

    @Test
    public void testGetUniqueSize() {
        Yarn ball = new Yarn();
        ball.insert("dup");
        ball.insert("dup");
        assertEquals(1, ball.getUniqueSize());
        ball.insert("unique");
        assertEquals(2, ball.getUniqueSize());
        
        Yarn happy = new Yarn();
        happy.insert("wow");
        happy.insert("yeah");
        happy.insert("wow");
        assertEquals(2, happy.getUniqueSize());
        happy.remove("yeah");
        assertEquals(1, happy.getUniqueSize());
    }

    @Test
    public void testInsert() {
        Yarn ball = new Yarn();
        ball.insert("dup");
        ball.insert("dup");
        ball.insert("unique");
        assertTrue(ball.contains("dup"));
        assertTrue(ball.contains("unique"));
        
        Yarn happy = new Yarn();
        happy.insert("candy");
        assertTrue(happy.contains("candy"));
        assertFalse(happy.contains("cookie"));
    }

    @Test
    public void testRemove() {
        Yarn ball = new Yarn();
        ball.insert("dup");
        ball.insert("dup");
        assertEquals(2, ball.getSize());
        assertEquals(1, ball.getUniqueSize());
        ball.remove("dup");
        assertEquals(1, ball.getSize());
        assertEquals(1, ball.getUniqueSize());
        
        Yarn happy = new Yarn();
        happy.insert("phone");
        happy.insert("water");
        happy.insert("phone");
        assertEquals(1, happy.remove("phone"));
        assertEquals(0, happy.remove("joy"));
    }

    @Test
    public void testRemoveAll() {
        Yarn ball = new Yarn();
        ball.insert("dup");
        ball.insert("dup");
        ball.insert("unique");
        assertEquals(3, ball.getSize());
        assertEquals(2, ball.getUniqueSize());
        ball.removeAll("dup");
        assertEquals(1, ball.getSize());
        assertEquals(1, ball.getUniqueSize());
        
        Yarn happy = new Yarn();
        happy.insert("dog");
        happy.insert("dog");
        happy.insert("cat");
        happy.insert("fish");
        happy.removeAll("dog");
        assertEquals(2, happy.getUniqueSize());
        happy.removeAll("lady");
        assertEquals(2, happy.getUniqueSize());
    }

    @Test
    public void testCount() {
        Yarn ball = new Yarn();
        ball.insert("dup");
        ball.insert("dup");
        ball.insert("unique");
        assertEquals(2, ball.count("dup"));
        assertEquals(1, ball.count("unique"));
        assertEquals(0, ball.count("forneymon"));
        
        Yarn happy = new Yarn();
        happy.insert("a");
        happy.insert("a");
        happy.insert("b");
        assertEquals(2, happy.count("a"));
        happy.remove ("a");
        assertEquals(1, happy.count("a"));
    }

    @Test
    public void testContains() {
        Yarn ball = new Yarn();
        ball.insert("dup");
        ball.insert("dup");
        ball.insert("unique");
        assertTrue(ball.contains("dup"));
        assertTrue(ball.contains("unique"));
        assertFalse(ball.contains("forneymon"));
        
        Yarn happy = new Yarn();
        happy.insert("beach");
        happy.insert("skate");
        assertTrue(happy.contains("beach"));
        assertTrue(happy.contains("skate"));
        assertFalse(happy.contains("air"));
    }
    
    @Test
    public void testCopy() {
        Yarn ball = new Yarn();
        ball.insert("dup");
        ball.insert("dup");
        ball.insert("unique");
        Yarn dolly = new Yarn(ball);
        assertEquals(2, dolly.count("dup"));
        assertEquals(1, dolly.count("unique"));
        dolly.insert("cool");
        assertFalse(ball.contains("cool"));
        
        //this unit test pointed out that I was only returning false in sameYarn if the two yarns were not the same uniqueSize
        Yarn happy = new Yarn();
        happy.insert("cat");
        happy.insert("bat");
        happy.insert("cat");
        happy.insert("hat");
        happy.insert("cat");
        happy.insert("bat");
        Yarn sad = new Yarn(happy);
        assertEquals(6, sad.getSize());
        assertTrue(Yarn.sameYarn(happy, sad));
        happy.remove("cat");
        assertFalse(Yarn.sameYarn(happy, sad));
    }

    @Test
    public void testGetNth() {
        Yarn ball = new Yarn();
        ball.insert("dup");
        ball.insert("dup");
        ball.insert("unique");
        ball.insert("cool");
        Yarn comparison = new Yarn(ball);
        for (int i = 0; i < ball.getSize(); i++) {
            String gotten = ball.getNth(i);
            assertTrue(comparison.contains(gotten));
            comparison.remove(gotten);
        }
        
        Yarn happy = new Yarn();
        happy.insert("pumpkin");
        happy.insert("pumpkin");
        happy.insert("ocean");
        happy.insert("ocean");
        happy.insert("beach");
        happy.insert("sun");
        assertTrue(happy.getNth(0).equals("pumpkin"));
        assertTrue(happy.getNth(2).equals("ocean"));
        happy.remove("pumpkin");
        assertTrue(happy.getNth(4).equals("sun"));
        
    }

    @Test
    public void testGetMostCommon() {
        Yarn ball = new Yarn();
        ball.insert("dup");
        ball.insert("dup");
        ball.insert("unique");
        ball.insert("cool");
        assertEquals("dup", ball.getMostCommon());
        ball.insert("cool");
        String mc = ball.getMostCommon();
        assertTrue(mc.equals("dup") || mc.equals("cool"));
        
        Yarn happy = new Yarn();
        happy.insert("friend");
        happy.insert("friend");
        happy.insert("friend");
        happy.insert("family");
        happy.insert("group");
        happy.insert("group");
        happy.insert("family");
        happy.insert("family");
        String mostCommon = happy.getMostCommon();
        assertTrue(mostCommon.equals("friend") || mostCommon.equals("family"));
        happy.remove("family");
        assertEquals("friend", happy.getMostCommon());
    }

    @Test
    public void testSwap() {
        Yarn y1 = new Yarn();
        y1.insert("dup");
        y1.insert("dup");
        y1.insert("unique");
        Yarn y2 = new Yarn();
        y2.insert("yo");
        y2.insert("sup");
        y1.swap(y2);
        assertTrue(y1.contains("yo"));
        assertTrue(y1.contains("sup"));
        assertTrue(y2.contains("dup"));
        assertTrue(y2.contains("unique"));
        assertFalse(y1.contains("dup"));
        
        //this test failed until I created temp variables for size and uniqueSize
        Yarn happy = new Yarn();
        happy.insert("leaf");
        happy.insert("green");
        happy.insert("tree");
        Yarn sad = new Yarn();
        sad.insert("joy");
        sad.insert("joy");
        happy.swap(sad);
        assertEquals(3, sad.getSize());
        assertEquals(1, happy.getUniqueSize());  
    }
    
    @Test
    public void testToString() {
        Yarn y1 = new Yarn();
        assertTrue(y1.toString().equals("{  }"));
        y1.insert("unique");
        assertTrue(y1.toString().equals("{ \"unique\": 1 }"));
        y1.insert("dup");
        y1.insert("dup");
        assertTrue(y1.toString().equals("{ \"unique\": 1, \"dup\": 2 }") ||
                   y1.toString().equals("{ \"dup\": 2, \"unique\": 1 }"));
        
        Yarn happy = new Yarn();
        happy.insert("hotdog");
        assertTrue(happy.toString().equals("{ \"hotdog\": 1 }"));
        happy.insert("hotdog");
        assertTrue(happy.toString().equals("{ \"hotdog\": 2 }"));
    }

    @Test
    public void testKnit() {
        Yarn y1 = new Yarn();
        y1.insert("dup");
        y1.insert("dup");
        y1.insert("unique");
        Yarn y2 = new Yarn();
        y2.insert("dup");
        y2.insert("cool");
        Yarn y3 = Yarn.knit(y1, y2);
        assertEquals(3, y3.count("dup"));
        assertEquals(1, y3.count("unique"));
        assertEquals(1, y3.count("cool"));
        y3.insert("test");
        assertFalse(y1.contains("test"));
        assertFalse(y2.contains("test"));
        
        //this test showed that I was not updating size accordingly in knit
        Yarn happy = new Yarn();
        happy.insert("onion");
        happy.insert("banana");
        Yarn sad = new Yarn();
        sad.insert("ice");
        sad.insert("ice");
        sad.insert("cream");
        sad.insert("cream");
        Yarn crazy = Yarn.knit(happy, sad);
        assertEquals(6, crazy.getSize());
        assertEquals(4, crazy.getUniqueSize());
    }

    @Test
    public void testTear() {
        Yarn y1 = new Yarn();
        y1.insert("dup");
        y1.insert("dup");
        y1.insert("unique");
        Yarn y2 = new Yarn();
        y2.insert("dup");
        y2.insert("cool");
        Yarn y3 = Yarn.tear(y1, y2);
        assertEquals(1, y3.count("dup"));
        assertEquals(1, y3.count("unique"));
        assertFalse(y3.contains("cool"));
        y3.insert("test");
        assertFalse(y1.contains("test"));
        assertFalse(y2.contains("test"));
        
        Yarn happy = new Yarn();
        happy.insert("onion");
        happy.insert("banana");
        Yarn sad = new Yarn();
        sad.insert("ice");
        sad.insert("ice");
        sad.insert("cream");
        sad.insert("cream");
        sad.insert("banana");
        Yarn crazy = Yarn.tear(happy, sad);
        assertEquals(1, crazy.getSize());
        assertEquals(1, crazy.getUniqueSize());
    }

    @Test
    public void testSameYarn() {
        Yarn y1 = new Yarn();
        y1.insert("dup");
        y1.insert("dup");
        y1.insert("unique");
        Yarn y2 = new Yarn();
        y2.insert("unique");
        y2.insert("dup");
        y2.insert("dup");
        assertTrue(Yarn.sameYarn(y1, y2));
        assertTrue(Yarn.sameYarn(y2, y1));
        y2.insert("test");
        assertFalse(Yarn.sameYarn(y1, y2));
        
        Yarn happy = new Yarn();
        happy.insert("apple");
        happy.insert("orange");
        Yarn sad = new Yarn();
        sad.insert("orange");
        sad.insert("apple");
        sad.insert("yellow");
        assertFalse(Yarn.sameYarn(happy, sad));
        sad.remove("yellow");
        assertTrue(Yarn.sameYarn(sad, happy));
    }

}
