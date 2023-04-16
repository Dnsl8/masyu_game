import java.awt.*;
import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.swing.*;
import static java.lang.Math.max;
import static java.lang.Math.min;

public class Vonalak extends JComponent implements Serializable{

    protected Map<Point, Vonal> vizszintes;
    protected Map<Point, Vonal> fuggoleges;
    private char [][] cellak ;
    private final int cellaSzelesseg;
    private final int cellaMagassag;

    public Vonalak(int cellaSz, int cellaM){
        cellaSzelesseg = cellaSz;
        cellaMagassag = cellaM ;
        vizszintes = new HashMap<>();
        fuggoleges = new HashMap<>();
    }

    protected void addLine(Point point1, Point point2) {
        addLine(point1, point2, Color.black);
    }

    protected void addLine(Point point1, Point point2, Color color) {
        if (point1.x == point2.x) {
            if (point1.y == point2.y + 1) {
                fuggoleges.put(point2, new Vonal(color));
            }
            else if (point2.y == point1.y + 1) {
                fuggoleges.put(point1, new Vonal(color));
            }
            else {
                throw new RuntimeException(
                        "Nem tortenhet meg: " + point1.x + " " + point1.y + " " + point2.x + " " + point2.y);
            }
        }
        else if (point1.y == point2.y) {
            if (point1.x == point2.x + 1) {
                vizszintes.put(point2, new Vonal(color));
            }
            else if (point2.x == point1.x + 1) {
                vizszintes.put(point1, new Vonal(color));
            }
            else {
                throw new RuntimeException(
                        "Nem tortenhet meg: " + point1.x + " " + point1.y + " " + point2.x + " " + point2.y);
            }
        }
        repaint();
    }

    protected boolean deleteLine(Point point1, Point point2) {
        Vonal line = null;
        if (point1.x == point2.x) {
            if (point1.y == point2.y + 1) {
                line = fuggoleges.remove(point2);
            }
            else if (point2.y == point1.y + 1) {
                line = fuggoleges.remove(point1);
            }
        }
        else if (point1.y == point2.y) {
            if (point1.x == point2.x + 1) {
                line = vizszintes.remove(point2);
            }
            else if (point2.x == point1.x + 1) {
                line = vizszintes.remove(point1);
            }
        }
        return line != null;
    }

    protected boolean vanVizszintes(Point p) {
        return (p.x >= 0) && (p.x < 5) && (p.y >= 0) && (p.y < 6) && vizszintes.get(p) != null;
    }

    protected boolean vanFuggoleges(Point p) {
        return (p.x >= 0) && (p.x < 6) && (p.y >= 0) && (p.y < 5) && fuggoleges.get(p) != null;
    }

    protected boolean checkX(Point point1, Point point2) {
        if ((point2.x == point1.x + 1 && point2.y == point1.y) &&
                (vanVizszintes(new Point(point1.x - 1, point1.y)))){
            return false;
        }
        if ((point1.x  == point2.x +1 && point2.y == point1.y) &&
                (vanVizszintes(new Point(point1.x, point1.y)))) {
            return false;
        }
        if ((point2.x == point1.x  && point2.y == point1.y + 1) &&
                (vanFuggoleges(new Point(point1.x, point1.y - 1)))) {
            return false;
        }
        if ((point2.x == point1.x  && point1.y == point2.y +1) &&
                (vanFuggoleges(new Point(point1.x, point1.y)))) {
            return false;
        }
        return true;
    }

    protected boolean checkXMellett(Point point1, Point point2){
        if ((point1.x  == point2.x +1 && point2.y == point1.y )&&
                ((vanFuggoleges(new Point(point1.x-1,point1.y-1)))
                        || (vanFuggoleges(new Point(point2.x,point2.y))))){
            return false;
        }
        if ((point2.x == point1.x + 1 && point2.y == point1.y) &&
                ((vanFuggoleges(new Point(point1.x+1,point1.y-1)))
                        || (vanFuggoleges(new Point(point2.x,point2.y))))){
            return false;
        }
        if ((point2.x == point1.x  && point2.y == point1.y + 1) &&
                ((vanVizszintes(new Point(point1.x-1,point1.y+1)))
                        || (vanVizszintes(new Point(point2.x,point2.y))))){
            return false;
        }
        if ((point2.x == point1.x  && point1.y == point2.y +1) &&
                ( (vanVizszintes(new Point(point1.x-1,point1.y-1)))
                        || (vanVizszintes(new Point(point2.x,point2.y))))){
            return false;
        }
        return true;
    }

    protected boolean cellabanX(int i, int j){
        return (i >= 0) && (i < 6) && (j >= 0) && (j < 6) && (cellak[i][j] == 'X');
    }

    protected boolean checkXKanyar(Point p1, Point p2){
        if((p1.x == p2.x && (p2.y == p1.y+1 || p1.y == p2.y+1)) &&
                ((cellabanX(p1.y, p1.x-1) && vanVizszintes(new Point(p1.x-1,p1.y))) ||
                (cellabanX(p1.y, p1.x+1) && vanVizszintes(new Point(p1.x,p1.y))) ||
                (cellabanX(p2.y, p2.x-1) && vanVizszintes(new Point(p2.x-1,p2.y))) ||
                (cellabanX(p2.y, p2.x+1) && vanVizszintes(new Point(p2.x,p2.y))))){
            return false;
        }
        if(((p2.x == p1.x+1 || p1.x == p2.x+1) && p2.y == p1.y) &&
                ((cellabanX(p1.y-1, p1.x) && vanFuggoleges(new Point(p1.x,p1.y-1))) ||
                (cellabanX(p1.y+1, p1.x) && vanFuggoleges(new Point(p1.x,p1.y))) ||
                (cellabanX(p2.y-1, p2.x) && vanFuggoleges(new Point(p2.x,p2.y-1))) ||
                (cellabanX(p2.y+1, p2.x) && vanFuggoleges(new Point(p2.x,p2.y))))){
            return false;
        }
        return true;
    }

    protected boolean checkO(Point point1, Point point2) {
        if ((point2.x == point1.x + 1 && point2.y == point1.y) &&
                ( (vanFuggoleges(new Point(point1.x,point1.y-1)))
                        || (vanFuggoleges(new Point(point1.x,point1.y))))){
            return false;
        }
        if ((point1.x  == point2.x +1 && point2.y == point1.y) &&
                ( (vanFuggoleges(new Point(point1.x,point1.y-1)))
                        || (vanFuggoleges(new Point(point1.x,point1.y))))){
            return false;
        }
        if ((point2.x == point1.x  && point2.y == point1.y + 1) &&
                ( (vanVizszintes(new Point(point1.x-1,point1.y)))
                        || (vanVizszintes(new Point(point1.x,point1.y))))){
            return false;
        }
        if ((point2.x == point1.x  && point1.y == point2.y + 1) &&
                ( (vanVizszintes(new Point(point1.x-1,point1.y)))
                        || (vanVizszintes(new Point(point1.x,point1.y))))){
            return false;
        }
        return true;
    }

    protected boolean cellabanO(int i, int j) {
        return (i >= 0) && (i < 6) && (j >= 0) && (j < 6) && (cellak[i][j] == 'O');
    }

    protected boolean checkOMellet(Point p1, Point p2){
        if (p1.y == p2.y) {
            int elejex = min(p1.x, p2.x);
            int vegex = max(p1.x, p2.x);
            if (elejex + 1 == vegex) {
                for (int kezdet = elejex - 3; kezdet <= elejex; ++kezdet) {
                    boolean negyHosszu = cellabanO(p1.y, kezdet + 2);
                    for (int szakaszKezdet = kezdet; szakaszKezdet < kezdet + 4; ++szakaszKezdet) {
                        if (szakaszKezdet != elejex && !vanVizszintes(new Point(szakaszKezdet, p1.y))) {
                            negyHosszu = false;
                        }
                    }
                    if (negyHosszu) {
                        return false;
                    }
                }
            }
        }
        if (p1.x == p2.x) {
            int elejey = min(p1.y, p2.y);
            int vegey = max(p1.y, p2.y);
            if (elejey + 1 == vegey) {
                for (int kezdet = elejey - 3; kezdet <= elejey; ++kezdet) {
                    boolean negyHosszu = cellabanO(kezdet + 2, p1.x);
                    for (int szakaszKezdet = kezdet; szakaszKezdet < kezdet + 4; ++szakaszKezdet) {
                        if (szakaszKezdet != elejey && !vanFuggoleges(new Point(p1.x, szakaszKezdet))) {
                            negyHosszu = false;
                        }
                    }
                    if (negyHosszu) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    protected boolean cellabanPont(int i, int j){
        return (i >= 0) && (i < 6) && (j >= 0) && (j < 6) && (cellak[i][j] == '.');
    }

    protected boolean mindenXOErintve(){
        for(int i=0;i <6;i++){
            for(int j = 0;j<6;j++){
                if(!(cellabanPont(j,i) || vanVizszintes(new Point(i,j))
                        || vanVizszintes(new Point(i-1,j)) || vanFuggoleges(new Point(i,j)) || vanFuggoleges(new Point(i,j-1)))){
                   return false;
                }
            }
        }
        return true;
    }

    protected boolean mindenFok2vagy0(){
        for(int i=0;i <6;i++){
            for(int j = 0;j<6;j++){
                int fok = (vanVizszintes(new Point(i,j)) ? 1 : 0)
                        + (vanVizszintes(new Point(i-1,j)) ? 1 : 0)
                        + (vanFuggoleges(new Point(i,j)) ? 1 : 0)
                        + (vanFuggoleges(new Point(i,j-1))  ? 1 : 0);
                if(fok != 0 && fok != 2){
                    return  false;
                }
            }
        }
        return true;
    }

    protected boolean kor(){
        if (fuggoleges.size() == 0) {
            return false;
        }
        Set<Point> elerheto = new HashSet<Point>();
        Set<Point> atvizsgalt = new HashSet<Point>();

        elerheto.add(fuggoleges.entrySet().iterator().next().getKey());

        while(!elerheto.isEmpty()){
            Point mostVizsgal = elerheto.iterator().next();

            Point ujPont = new Point(mostVizsgal.x,mostVizsgal.y+1);
            if(vanFuggoleges(new Point(mostVizsgal.x, mostVizsgal.y)) &&
                    !atvizsgalt.contains(ujPont)){
                elerheto.add(ujPont);
            }

            ujPont = new Point(mostVizsgal.x,mostVizsgal.y-1);
            if(vanFuggoleges(new Point(mostVizsgal.x, mostVizsgal.y - 1)) &&
                    !atvizsgalt.contains(ujPont)){
                elerheto.add(ujPont);
            }

            ujPont = new Point(mostVizsgal.x+1,mostVizsgal.y);
            if(vanVizszintes(new Point(mostVizsgal.x, mostVizsgal.y)) &&
                    !atvizsgalt.contains(ujPont)){
                elerheto.add(ujPont);
            }

            ujPont = new Point(mostVizsgal.x-1,mostVizsgal.y);
            if(vanVizszintes(new Point(mostVizsgal.x-1, mostVizsgal.y)) &&
                    !atvizsgalt.contains(ujPont)){
                elerheto.add(ujPont);
            }

            elerheto.remove(mostVizsgal);
            atvizsgalt.add(mostVizsgal);
        }

        if(atvizsgalt.size() == fuggoleges.size() + vizszintes.size()){
            return true;
        }
        return false;
    }


    protected void ujCellak(char[][] ujCellak) {
        vizszintes.clear();
        fuggoleges.clear();
        cellak = ujCellak;
        repaint();
    }

    protected void flipLine(Point point1,  Point point2) {
        if ((!deleteLine(point1, point2)) &&
                        (cellak[point1.y][point1.x] != 'X' || (
                                checkX(point1, point2) && checkXMellett(point1,point2)
                        )) &&
                        checkXKanyar(point1,point2) &&
                        (cellak[point1.y][point1.x] != 'O' || checkO(point1, point2)) &&
                        checkOMellet(point1,point2) &&
                        (cellak[point2.y][point2.x] != 'X' || (
                                checkX(point2, point1) && checkXMellett(point2,point1)
                        )) &&
                        checkXKanyar(point2,point1) &&
                        (cellak[point2.y][point2.x] != 'O' || checkO(point2, point1)) &&
                        checkOMellet(point2,point1)) {
            addLine(point1, point2, Color.black);
        }
    }

    protected void saveVonal(String filename) {
        try {
            FileOutputStream file = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(file);

            out.writeObject(fuggoleges);
            out.writeObject(vizszintes);

            out.close();
            file.close();

        } catch (IOException ex) {
            System.out.println("IOException is caught");
        }
    }

    protected void loadVonal(String filename) {
        try {
            FileInputStream fis = new FileInputStream(filename);
            ObjectInputStream ois = new ObjectInputStream(fis);

            fuggoleges = (HashMap<Point,Vonal>) ois.readObject();
            vizszintes = (HashMap<Point,Vonal>) ois.readObject();

            ois.close();
            fis.close();

        } catch (IOException ex) {
            System.out.println("IOException is caught");
        } catch (ClassNotFoundException ex) {
            System.out.println("ClassNotFoundException is caught");
        }
    }
}