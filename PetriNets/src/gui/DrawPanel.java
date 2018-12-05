package gui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DrawPanel extends JPanel {
    private ArrayList<Petrinet2DObjectInterface> objects;
    private DrawCanvas canvas;
    // Grid dimensions
    private final int  rows  = 20;
    private final int columns =20;

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

            int width = getSize().width;
            int height = getSize().height;

            int htOfRow = height / (rows);
            for (int k = 0; k < rows; k++)
                g.drawLine(0, k * htOfRow , width, k * htOfRow );

            int wdOfRow = width / (columns);
            for (int k = 0; k < columns; k++)
                g.drawLine(k*wdOfRow , 0, k*wdOfRow , height);




        }
    }
}
