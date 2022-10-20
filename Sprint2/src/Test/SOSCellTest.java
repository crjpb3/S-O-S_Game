package Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import Product.SOSCell;

class SOSCellTest {
  @Test
  void testIsEmpty(){
    SOSCell Cell1 = new SOSCell();
    assertEquals(true, Cell1.isEmpty());

    Cell1.setContent(CellContent.S_CHAR);
    assertEquals(false, Cell1.isEmpty());
  }

  @Test
  void testGetContent(){
    SOSCell Cell1 = new SOSCell();
    
    Cell1.setContent(CellContent.X_CHAR);
    assertEquals(CellContent.X_CHAR, Cell1.getContent());

    Cell1.setContent(CellContent.O_CHAR);
    assertEquals(CellContent.O_CHAR, Cell1.getContent());

  }
}