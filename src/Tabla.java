import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.*;
import java.util.Random;


public class Tabla extends JPanel implements Serializable {
    protected char [][] cellak = new char[6][6];
    protected int[] z = {0,7,14,21,28,35,42,49,56,63};
    private final int width;
    private final int height;
    private final JButton [][] buttons;
    private final Glass glass;

    public Tabla( final Glass pp, final JButton [][] bb, int w, int h){
        buttons = bb;
        glass = pp;
        width = w;
        height = h;

        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton btn = (JButton) e.getSource();
                Point point = btn.getLocation();
                point.x /= btn.getWidth();
                point.y /= btn.getHeight();
                glass.buttonClicked(point);

                if(glass.vonalak.mindenXOErintve() && glass.vonalak.mindenFok2vagy0() && glass.vonalak.kor()){
                    glass.setVisible(false);
                    removeAll();
                    JLabel l = new JLabel("   Congratulations");
                    l.setFont(new Font("Serif", Font.PLAIN, 40));
                    BorderLayout b = new BorderLayout();
                    setLayout(b);
                    add(l, b.CENTER);

                }
            }
        };

        setLayout(new GridLayout(6,6));
        setSize(width, height);
        for(int i=0;i<6;i++) {
            for (int j = 0; j < 6; j++) {
                buttons[i][j] = new JButton();
                add(buttons[i][j]);
                buttons[i][j].addActionListener(al);
            }
        }
        ujPalya(getRandom(z));
    }

    protected int getRandom(int[] array) {
        int rnd = new Random().nextInt(array.length);
        return array[rnd];
    }

    private void fekete(int j, int i){
        try {
            Image img = ImageIO.read(getClass().getResource("fekete.png")).getScaledInstance(58, 58, Image.SCALE_DEFAULT);
            buttons[i][j].setIcon(new ImageIcon(img));
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    private void feher(int j, int i){
        try {
            Image img = ImageIO.read(getClass().getResource("feher.png")).getScaledInstance(66, 66, Image.SCALE_DEFAULT);
            buttons[i][j].setIcon(new ImageIcon(img));
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    protected void saveTabla(String filename) {
        try {
            FileWriter fw = new FileWriter(filename);
            PrintWriter pw = new PrintWriter(fw);
            for(int x = 0;x <6;x++){
                for(int y = 0;y<6;y++){
                    pw.write(cellak[x][y]);
                }
                pw.write('\n');
            }

            pw.close();

        } catch (IOException ex) {
            System.out.println("IOException is caught");
        }
    }

    protected void load(String filename) {
        try {
            FileReader fr = new FileReader("filename");
            BufferedReader br = new BufferedReader(fr);

            for(int x = 0; x < 6;x++) {
                String line = br.readLine();
                if (line == null) break;
                for(int y = 0; y < 6; y++){
                    cellak[x][y] = line.charAt(y);
                    if(line.charAt(y) == 'X'){
                        fekete(y,x);
                    }
                    if(line.charAt(y) == 'O'){
                        feher(y,x);
                    }
                }
                x++;
            }
            br.close();
        }
        catch(IOException e){
        }
    }

    public void ujPalya( int z){
        try {
            FileReader fr = new FileReader("masyu_tablak");
            BufferedReader br = new BufferedReader(fr);

            int x = 0;
            while(x < z){
                String line = br.readLine();
                if (line == null) break;
                x++;
            }

            x = 0;
            while (x<6) {
                String line = br.readLine();
                if (line == null) break;
                for(int y = 0; y < 6; y++){
                    cellak[x][y] = line.charAt(y);
                    if(line.charAt(y) == 'X'){
                        fekete(y,x);
                    }
                    if(line.charAt(y) == 'O'){
                        feher(y,x);
                    }
                }
                x++;
            }
            br.close();
        }
        catch(IOException e){
        }
    }
}

