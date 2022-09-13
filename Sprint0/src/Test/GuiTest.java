package Test;

import Product.Gui;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GuiTest {

    @Test
    void add() {
        Gui g = new Gui();
        assertEquals(4,g.add(2,2));
        assertEquals(-10,g.add(-20,10));
    }
}