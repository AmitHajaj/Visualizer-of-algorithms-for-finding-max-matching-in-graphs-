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

    public void drawNode (int n, Graphics g){
        if(n%2 == 0){
            g.drawOval(175, 500, 30, 30);
            g.drawString("" + n, 175+13, 500+20);
        }
    }

}
