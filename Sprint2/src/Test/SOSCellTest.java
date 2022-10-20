package Test;

import static org.junit.jupiter.api.Assertions.*;

import Product.SOSCell.CellContent;
import org.junit.jupiter.api.Test;
import Product.SOSCell;

class SOSCellTest {
  @Test
  void testIsEmpty(){
    SOSCell Cell1 = new SOSCell();
    assertTrue(Cell1.isEmpty());

    Cell1.setContent(SOSCell.CellContent.S_CHAR);
    assertFalse(Cell1.isEmpty());

    Cell1.setContent(SOSCell.CellContent.O_CHAR);
    assertFalse(Cell1.isEmpty());
  }

  @Test
  void testGetContent(){
    SOSCell Cell1 = new SOSCell();
    
    Cell1.setContent(CellContent.S_CHAR);
    assertEquals(CellContent.S_CHAR, Cell1.getContent());

    Cell1.setContent(SOSCell.CellContent.O_CHAR);
    assertEquals(SOSCell.CellContent.O_CHAR, Cell1.getContent());
  }
}