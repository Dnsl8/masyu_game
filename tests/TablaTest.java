import org.junit.Test;

import javax.swing.*;

import static org.junit.Assert.*;

public class TablaTest {

    @Test
    public void getRandomTest() {
        JButton[][] j = new JButton[6][6];
        Glass g = new Glass(j,66,66);
        int w = 400;
        int h = 400;
        Tabla t = new Tabla(g,j,w,h);
        int[] a = {1,4,7,9,14};
        int i = t.getRandom(a);
        assertTrue(i==1 || i==4 ||i==7 || i==9 || i==14);

    }

}