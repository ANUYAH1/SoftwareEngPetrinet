package gui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DrawPanel extends JPanel {
    private ArrayList<Petrinet2DObjectInterface> objects;
    private DrawCanvas canvas;
    public DrawPanel(){
        this.setBorder(BorderFactory.createTitledBorder("Draw Canvas"));
        canvas = new DrawCanvas();
        this.setLayout(new BorderLayout());

        this.add(canvas,BorderLayout.CENTER);
    }




    class DrawCanvas extends JPanel {
        @Override
        public void paintComponent(Graphics g) {  // invoke via repaint()
            super.paintComponent(g);    // fill background
            setBackground(Color.WHITE); // set its background color

            // Draw the grid-lines
            g.setColor(Color.LIGHT_GRAY);




        }
    }
}
