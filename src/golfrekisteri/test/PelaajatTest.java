package golfrekisteri.test;
// Generated by ComTest BEGIN
import static org.junit.Assert.*;
import org.junit.*;
import golfrekisteri.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2021.04.13 16:18:18 // Generated by ComTest
 *
 */
@SuppressWarnings("all")
public class PelaajatTest {



  // Generated by ComTest BEGIN
  /** 
   * testLisaa46 
   * @throws SailoException when error
   */
  @Test
  public void testLisaa46() throws SailoException {    // Pelaajat: 46
    Pelaajat pelaajat = new Pelaajat(); 
    Pelaaja a = new Pelaaja(); Pelaaja a2 = new Pelaaja(); 
    assertEquals("From: Pelaajat line: 50", 0, pelaajat.getLkm()); 
    pelaajat.lisaa(a); assertEquals("From: Pelaajat line: 51", 1, pelaajat.getLkm()); 
    pelaajat.lisaa(a2); assertEquals("From: Pelaajat line: 52", 2, pelaajat.getLkm()); 
    pelaajat.lisaa(a); assertEquals("From: Pelaajat line: 53", 3, pelaajat.getLkm()); 
    assertEquals("From: Pelaajat line: 54", a, pelaajat.anna(0)); 
    assertEquals("From: Pelaajat line: 55", a2, pelaajat.anna(1)); 
    try {
    assertEquals("From: Pelaajat line: 56", a, pelaajat.anna(6)); 
    fail("Pelaajat: 56 Did not throw IndexOutOfBoundsException");
    } catch(IndexOutOfBoundsException _e_){ _e_.getMessage(); }
    pelaajat.lisaa(a); 
    pelaajat.lisaa(a); 
    pelaajat.lisaa(a); 
    pelaajat.lisaa(a); 
    pelaajat.lisaa(a); 
    pelaajat.lisaa(a); 
    pelaajat.lisaa(a); 
  } // Generated by ComTest END
}