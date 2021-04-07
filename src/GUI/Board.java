package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.jar.JarException;

public class Board extends JPanel {

    public Board() {
    }

    public void paint(Graphics g){

    }

    public void drawNode (int n, int x, int y, Graphics g){
        g.drawOval(x, y, 30, 30);
        g.drawString("" + n, x+13, y+20);
    }

}
