import java.awt.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import static java.lang.Math.abs;

public class Glass extends JPanel{
    protected Vonalak vonalak;
    private final JButton [][] buttons;
    private Point active;

    public Glass(final JButton [][] bb, int cellaSz, int cellaM) {
        setOpaque(false);
        buttons = bb;
        vonalak = new Vonalak(cellaSz, cellaM);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int i = 0; i < 6; ++i) {
            for (int j = 0; j < 5; ++j) {
                Vonal line = vonalak.fuggoleges.get(new Point(i,j));
                if (line != null){
                    JButton b1 = buttons[j][i];
                    JButton b2 = buttons[j + 1][i];

                    Point startPoint = b1.getLocation();
                    Point endPoint = b2.getLocation();
                    startPoint.x += (b1.getWidth() / 2);
                    startPoint.y += (b2.getHeight()/ 2 +20);
                    endPoint.x += (b1.getWidth() / 2);
                    endPoint.y += (b2.getHeight()/ 2 +20);
                    g.setColor(line.color);
                    g.drawLine(startPoint.x,startPoint.y, endPoint.x, endPoint.y);
                }
            }
        }
        for (int i = 0; i < 5; ++i) {
            for (int j = 0; j < 6; ++j) {
                Vonal line = vonalak.vizszintes.get(new Point(i,j));
                if (line != null) {
                    g.setColor(line.color);
                    JButton b1 = buttons[j][i];
                    JButton b2 = buttons[j][i + 1];

                    Point startPoint = b1.getLocation();
                    Point endPoint = b2.getLocation();
                    startPoint.x += (b1.getWidth() / 2);
                    startPoint.y += (b2.getHeight()/ 2 +20);
                    endPoint.x += (b1.getWidth() / 2);
                    endPoint.y += (b2.getHeight()/ 2 +20);
                    g.drawLine(startPoint.x,startPoint.y, endPoint.x, endPoint.y);
                }
            }
        }
    }

    protected boolean szomszed(Point startPoint, Point endPoint){
        return (abs(startPoint.x - endPoint.x) == 1 && startPoint.y ==  endPoint.y) ||
                (abs(startPoint.y - endPoint.y) == 1 && startPoint.x ==  endPoint.x);
    }

    protected void buttonClicked(Point point) {
        if (active == null) {
            active = point;
            buttons[point.y][point.x].setBorder(new LineBorder(Color.BLACK));
        } else {
            buttons[active.y][active.x].setBorder(new LineBorder(Color.GRAY));
            if(szomszed(active, point)){
                vonalak.flipLine(active,  point);
            }
            active = null;
        }

        revalidate();
        repaint();
    }
}
