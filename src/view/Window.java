package view;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {

    private final Panel panel;

    private JLabel lblSelected;
    private JPanel info;




    public Window() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("UHK FIM PGRF : " + this.getClass().getName());


        panel = new Panel();

        info = new JPanel();
        lblSelected = new JLabel("Selected modes will appear here");
        info.add(lblSelected);





        add(info, BorderLayout.SOUTH);
        add(panel, BorderLayout.CENTER);
        setVisible(true);
        pack();

        setLocationRelativeTo(null);

        panel.setFocusable(true);
        panel.grabFocus();







    }



    public Panel getPanel() {
        return panel;
    }


    public JLabel getLblSelected() {
        return lblSelected;
    }
}
