package control;


import model.Scene;
import model.Solid;
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
    private int selector = 0;

    private LineRasterizerGraphics rasterizer;


    public Controller2D(Window window) {
        this.window = window;
        this.panel = window.getPanel();

        initObjects(panel.getRaster());
        initListeners(panel);
        initInputs();
    }

    public void initObjects(Raster raster) {
        Box box = new Box(1,1,1,Color.red);
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

         panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("W"), "moveX");
         panel.getActionMap().put("moveX",moveX);

         panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("D"), "moveY");
         panel.getActionMap().put("moveY",moveY);

         panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("S"), "moveXinv");
         panel.getActionMap().put("moveXinv",moveXinv);

         panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("A"), "moveYinv");
         panel.getActionMap().put("moveYinv",moveYinv);

         panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("E"), "moveZ");
         panel.getActionMap().put("moveZ",moveZ);

         panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("Q"), "moveZinv");
         panel.getActionMap().put("moveZinv",moveZinv);

         panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("RIGHT"), "nextSolid");
         panel.getActionMap().put("nextSolid",nextSolid);

         panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("LEFT"), "previousSolid");
         panel.getActionMap().put("previousSolid",previousSolid);
     }

     Action nextSolid = new AbstractAction() {
         public void actionPerformed(ActionEvent e) {
             if (selector < scene.getSolids().size() - 1) {
                 selector++;
             }
             else {
                 selector = 0;
             }
         }
     };

    Action previousSolid = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            if (selector > 0) {
                selector--;
            }
            else {
                selector = scene.getSolids().size()-1;
            }
        }
    };

    Action clear = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            hardClear();
        }
    };

    Action moveX = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            Solid selected = scene.getSolids().get(selector);
            selected.setRotX(selected.getRotX()+0.1);
            update();
        }
    };

    Action moveY = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            Solid selected = scene.getSolids().get(selector);
            selected.setRotY(selected.getRotY()+0.1);
            update();
        }
    };

    Action moveXinv = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            Solid selected = scene.getSolids().get(selector);
            selected.setRotX(selected.getRotX()-0.1);
            update();
        }
    };

    Action moveYinv = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            Solid selected = scene.getSolids().get(selector);
            selected.setRotY(selected.getRotY()-0.1);
            update();
        }
    };

    Action moveZ = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            Solid selected = scene.getSolids().get(selector);
            selected.setRotZ(selected.getRotZ()+0.1);
            update();
        }
    };

    Action moveZinv = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            Solid selected = scene.getSolids().get(selector);
            selected.setRotZ(selected.getRotZ()-0.1);
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
