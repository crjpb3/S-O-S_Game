package Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import Product.SOSCell;

class SOSCellTest {
  @Test
  void testIsEmpty(){
    SOSCell Cell1 = new SOSCell();
    assertTrue(Cell1.isEmpty());

    Cell1.setContent("S");
    assertFalse(Cell1.isEmpty());

    Cell1.setContent("O");
    assertFalse(Cell1.isEmpty());
  }

  @Test
  void testGetContent(){
    SOSCell Cell1 = new SOSCell();
    
    Cell1.setContent("S");
    assertEquals("S", Cell1.getContent());

    Cell1.setContent("O");
    assertEquals("O", Cell1.getContent());
  }
}