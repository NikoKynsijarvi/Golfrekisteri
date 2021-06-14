package golfrekisteri.test;
// Generated by ComTest BEGIN
import java.util.*;
import golfrekisteri.*;
import static org.junit.Assert.*;
import org.junit.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2021.04.13 16:21:00 // Generated by ComTest
 *
 */
@SuppressWarnings({ "all" })
public class TilastotTest {



  // Generated by ComTest BEGIN
  /** testAnnaTilastot72 */
  @Test
  public void testAnnaTilastot72() {    // Tilastot: 72
    Tilastot t = new Tilastot(); 
    Tilasto ti = new Tilasto(1); t.lisaa(ti); 
    Tilasto ti2 = new Tilasto(2); t.lisaa(ti2); 
    Tilasto ti3 = new Tilasto(1); t.lisaa(ti3); 
    List<Tilasto> loytyneet; 
    loytyneet = t.annaTilastot(1); 
    assertEquals("From: Tilastot line: 80", 2, loytyneet.size()); 
    assertEquals("From: Tilastot line: 81", true, loytyneet.get(0) == ti); 
    assertEquals("From: Tilastot line: 82", true, loytyneet.get(1) == ti3); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testIterator172 */
  @Test
  public void testIterator172() {    // Tilastot: 172
    Tilastot t = new Tilastot(); 
    Tilasto ti = new Tilasto(1); t.lisaa(ti); 
    Tilasto ti2 = new Tilasto(2); t.lisaa(ti2); 
    Tilasto ti3 = new Tilasto(1); t.lisaa(ti3); 
    Iterator<Tilasto> i2=t.iterator(); 
    assertEquals("From: Tilastot line: 181", ti, i2.next()); 
    assertEquals("From: Tilastot line: 182", ti2, i2.next()); 
    assertEquals("From: Tilastot line: 183", ti3, i2.next()); 
  } // Generated by ComTest END
}