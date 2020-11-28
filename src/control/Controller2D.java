package control;


import model.Scene;
import rasterize.*;
import render.Renderer;
import solids.Box;
import solids.Tetrahedron;
import transforms.*;
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
    Box box;

    private LineRasterizerGraphics rasterizer;


    public Controller2D(Window window) {
        this.window = window;
        this.panel = window.getPanel();

        initObjects(panel.getRaster());
        initListeners(panel);
        initInputs();
    }

    public void initObjects(Raster raster) {
        box = new Box(1,1,1,Color.red);
        Tetrahedron tet = new Tetrahedron(Color.yellow);
        scene = new Scene();
        scene.addSolid(box);
        scene.addSolid(tet);

        rasterizer = new LineRasterizerGraphics(raster);
        renderer = new render.Renderer(rasterizer,raster);


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
            box.setRotX(box.getRotX()+0.1);
            update();
        }
    };

    Action rotY = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            box.setRotY(box.getRotY()+0.1);
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
        renderer.render(scene);

    }

    private void hardClear() {
        panel.clear();

    }


}
