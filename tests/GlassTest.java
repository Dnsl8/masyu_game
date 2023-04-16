import org.junit.Before;
import org.junit.Test;

import javax.swing.*;

import java.awt.*;

import static org.junit.Assert.*;

public class GlassTest {
    JButton[][] bb = new JButton[6][6];
    int cellaSz = 66;
    int cellaM = 66;
    Glass g = new Glass(bb,cellaSz,cellaM);

    @Test
    public void szomszedTest1() {
        boolean b = g.szomszed(new Point(0,0), new Point(0,1));
        boolean c = g.szomszed(new Point(0,0), new Point(0,2));
        assertFalse(c);
        assertTrue(b);
    }
}