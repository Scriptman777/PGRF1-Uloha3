package control;


import model.Scene;
import rasterize.*;
import render.Renderer;
import solids.Box;
import solids.Tetrahedron;
import transforms.Mat4;
import transforms.Mat4RotX;
import transforms.Mat4RotY;
import view.Panel;
import view.Window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Controller2D implements Controller {

    private final Panel panel;
    private final Window window;
    private Renderer renderer;
    private Scene scene;

    private LineRasterizerGraphics rasterizer;

    double tempX = 0;
    double tempY = 0;


    public Controller2D(Window window) {
        this.window = window;
        this.panel = window.getPanel();

        initObjects(panel.getRaster());
        initListeners(panel);
        initInputs();
    }

    public void initObjects(Raster raster) {
        rasterizer = new LineRasterizerGraphics(raster);
        renderer = new render.Renderer(rasterizer,raster);
        scene = new Scene();

     }

     public void initInputs() {
        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("C"), "clear");
        panel.getActionMap().put("clear",clear);

         panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("W"), "rotX");
         panel.getActionMap().put("rotX",rotX);

         panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("D"), "rotY");
         panel.getActionMap().put("rotY",rotY);
     }

    Action clear = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            hardClear();
        }
    };

    Action rotX = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            tempX += 0.1;
            update();
        }
    };

    Action rotY = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            tempY += 0.1;
            update();
        }
    };


    @Override
    public void initListeners(Panel panel) {



        panel.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                if (e.isControlDown()) return;

                if (e.isShiftDown()) {

                } else if (SwingUtilities.isLeftMouseButton(e)) {
                    update();



                }
            }
        });



        panel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                panel.resize();
                initObjects(panel.getRaster());
            }
        });
    }

    private void update() {
        panel.clear();
        Box box = new Box(1,1,1,Color.red);
        scene.addSolid(box);
        Tetrahedron tet = new Tetrahedron(Color.yellow);
        scene.addSolid(tet);



        Mat4 rotX = new Mat4RotX(tempX);
        Mat4 rotY = new Mat4RotY(tempY);
        renderer.setModel(rotX.mul(rotY));
        renderer.render(scene);

    }

    private void hardClear() {
        panel.clear();

    }


}
