package view;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {

    private final Panel panel;
    private final JPanel menuPoly;
    private final JRadioButton radioNewPoly;
    private final JRadioButton radioFill;
    private final JRadioButton radioClip;
    private final JRadioButton radioPattern;
    private final JRadioButton radioPattern2;
    private final JButton btnClip;



    public Window() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("UHK FIM PGRF : " + this.getClass().getName());


        panel = new Panel();
        menuPoly = new JPanel();
        menuPoly.setLayout(new BoxLayout(menuPoly,BoxLayout.PAGE_AXIS));

        radioNewPoly = new JRadioButton("Polygon");
        menuPoly.add(radioNewPoly);
        radioFill = new JRadioButton("Fill");
        menuPoly.add(radioFill);
        radioPattern = new JRadioButton("Pattern - Dots");
        menuPoly.add(radioPattern);
        radioPattern2 = new JRadioButton("Pattern - Lines");
        menuPoly.add(radioPattern2);
        radioClip = new JRadioButton("Clip Polygon");
        menuPoly.add(radioClip);
        btnClip = new JButton("Clip!");
        menuPoly.add(btnClip);


        ButtonGroup bg = new ButtonGroup();
        bg.add(radioNewPoly);
        bg.add(radioFill);
        bg.add(radioClip);
        bg.add(radioPattern);
        bg.add(radioPattern2);




        add(panel, BorderLayout.CENTER);
        add(menuPoly, BorderLayout.WEST);
        setVisible(true);
        pack();

        setLocationRelativeTo(null);






    }



    public Panel getPanel() {
        return panel;
    }

    public JRadioButton getRadioFill() { return radioFill; }

    public JRadioButton getRadioNewPoly() { return radioNewPoly; }

    public JRadioButton getRadioClip() { return radioClip; }

    public JButton getButtonClip() { return btnClip; }

    public JRadioButton getRadioPattern () {return radioPattern; }

    public JRadioButton getRadioPattern2 () {return radioPattern2; }




}
