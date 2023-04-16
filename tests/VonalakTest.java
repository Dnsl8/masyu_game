import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class VonalakTest {
    Vonalak v;

    @Before
    public void setUp() {
        v = new Vonalak(66,66);
    }

    @Test
    public void addLineTest() {
        v.addLine(new Point(0,0), new Point(1,0));
        v.addLine(new Point(0,0), new Point(0,1));
        v.addLine(new Point(0,5), new Point(0,4));
        v.addLine(new Point(1,5), new Point(0,5));
        v.addLine(new Point(2,2), new Point(3,3));

        assertTrue(v.vizszintes.containsKey(new Point(0,0)));
        assertTrue(v.fuggoleges.containsKey(new Point(0,0)));
        assertTrue(v.fuggoleges.containsKey(new Point(0,4)));
        assertTrue(v.vizszintes.containsKey(new Point(0,5)));
        assertFalse(v.fuggoleges.containsKey(new Point(2,2)));
        assertFalse(v.fuggoleges.containsKey(new Point(3,3)));
        assertFalse(v.vizszintes.containsKey(new Point(2,2)));
        assertFalse(v.vizszintes.containsKey(new Point(3,3)));

    }

    @Test
    public void deleteLine() {
        v.addLine(new Point(0,0),new Point(1,0));
        v.deleteLine(new Point(0,0),new Point(1,0));
        assertFalse(v.vizszintes.containsKey(new Point(0,0)));
    }

    @Test
    public void vanVizszintes() {
        v.addLine(new Point(0,0),new Point(1,0));
        assertTrue(v.vanVizszintes(new Point(0,0)));
        assertFalse(v.vanVizszintes(new Point(0,1)));
    }

    @Test
    public void vanFuggoleges() {
        v.addLine(new Point(0,0),new Point(0,1));
        assertTrue(v.vanFuggoleges(new Point(0,0)));
        assertFalse(v.vanFuggoleges(new Point(0,1)));
    }

    @Test
    public void checkX() {
        v.addLine(new Point(0,0),new Point(1,0));
        assertFalse(v.checkX(new Point(1,0),new Point(2,0)));
        assertTrue(v.checkX(new Point(0,0),new Point(0,1)));

    }

    @Test
    public void checkO() {
        v.addLine(new Point(0,0),new Point(1,0));
        assertFalse(v.checkO(new Point(1,0),new Point(1,1)));
        assertTrue(v.checkO(new Point(1,0),new Point(2,0)));
    }

    @Test
    public void mindenFok2vagy0() {
        v.addLine(new Point(0,0),new Point(1,0));
        v.addLine(new Point(1,0),new Point(1,1));
        v.addLine(new Point(1,1),new Point(0,1));
        v.addLine(new Point(0,0),new Point(0,1));
        assertTrue(v.mindenFok2vagy0());
        v.deleteLine(new Point(0,0),new Point(1,0));
        assertFalse(v.mindenFok2vagy0());
    }

    @Test
    public void kor() {
        v.addLine(new Point(0,0),new Point(1,0));
        v.addLine(new Point(1,0),new Point(1,1));
        v.addLine(new Point(1,1),new Point(0,1));
        v.addLine(new Point(0,0),new Point(0,1));

        v.addLine(new Point(2,0),new Point(3,0));
        v.addLine(new Point(3,0),new Point(3,1));
        v.addLine(new Point(3,1),new Point(2,1));
        v.addLine(new Point(2,0),new Point(2,1));

        assertFalse(v.kor());

        v.deleteLine(new Point(2,0),new Point(3,0));
        v.deleteLine(new Point(3,0),new Point(3,1));
        v.deleteLine(new Point(3,1),new Point(2,1));
        v.deleteLine(new Point(2,0),new Point(2,1));

        assertTrue(v.kor());

    }
}