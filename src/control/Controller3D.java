package control;


import model.Scene;
import model.Solid;
import rasterize.*;
import render.Renderer;
import solids.Box;
import solids.Tetrahedron;
import transforms.Mat4OrthoRH;
import transforms.Mat4PerspRH;
import transforms.Mat4ViewRH;
import transforms.Vec3D;
import view.Panel;
import view.Window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Controller2D implements Controller {

    private final int ROTATE = 0;
    private final int TRANSLATE = 1;
    private final int SCALE = 2;


    private final Panel panel;
    private final Window window;
    private final JLabel lblInfo;
    private Renderer renderer;
    private Scene scene;
    private int selector = 0;
    private int transformSelector = 0;
    private Color tempColor;

    private LineRasterizerGraphics rasterizer;


    public Controller2D(Window window) {
        this.window = window;
        this.panel = window.getPanel();
        this.lblInfo = window.getLblSelected();

        initObjects(panel.getRaster());
        initListeners(panel);
        initInputs();
    }

    public void initObjects(Raster raster) {
        Box box = new Box(1,1,1,Color.red);
        Tetrahedron tet = new Tetrahedron(Color.cyan);
        scene = new Scene();
        scene.addSolid(box);
        scene.addSolid(tet);

        tempColor = scene.getSolids().get(selector).getColor();
        rasterizer = new LineRasterizerGraphics(raster);
        renderer = new render.Renderer(rasterizer,raster);

        updateInfo();
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

         panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("UP"), "nextTransform");
         panel.getActionMap().put("nextTransform",nextTransform);

         panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("DOWN"), "previousTransform");
         panel.getActionMap().put("previousTransform",previousTransform);
     }

    Action nextTransform = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            if (transformSelector < 2) {
                transformSelector++;
            }
            else {
                transformSelector = 0;
            }
            updateInfo();
        }
    };

    Action previousTransform = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            if (transformSelector > 0) {
                transformSelector--;
            }
            else {
                transformSelector = 2;
            }
            updateInfo();
        }
    };

     Action nextSolid = new AbstractAction() {
         public void actionPerformed(ActionEvent e) {
             if (selector < scene.getSolids().size() - 1) {
                 scene.getSolids().get(selector).setColor(tempColor);
                 selector++;
                 tempColor = scene.getSolids().get(selector).getColor();
                 scene.getSolids().get(selector).setColor(Color.YELLOW);
                 update();
             }
         }
     };

    Action previousSolid = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            if (selector > 0) {
                scene.getSolids().get(selector).setColor(tempColor);
                selector--;
                tempColor = scene.getSolids().get(selector).getColor();
                scene.getSolids().get(selector).setColor(Color.YELLOW);
                update();
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
            switch (transformSelector){
                case 0:
                    selected.setRotX(selected.getRotX()+0.1);
                    break;
                case 1:
                    selected.setTransX(selected.getTransX()+0.1);
                    break;
                case 2:
                    selected.setScaleX(selected.getScaleX()+0.1);
                    break;
            }
            update();
        }
    };

    Action moveY = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            Solid selected = scene.getSolids().get(selector);
            switch (transformSelector){
                case 0:
                    selected.setRotY(selected.getRotY()+0.1);
                    break;
                case 1:
                    selected.setTransY(selected.getTransY()+0.1);
                    break;
                case 2:
                    selected.setScaleY(selected.getScaleY()+0.1);
                    break;
            }
            update();
        }
    };

    Action moveXinv = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            Solid selected = scene.getSolids().get(selector);
            switch (transformSelector){
                case 0:
                    selected.setRotX(selected.getRotX()-0.1);
                    break;
                case 1:
                    selected.setTransX(selected.getTransX()-0.1);
                    break;
                case 2:
                    selected.setScaleX(selected.getScaleX()-0.1);
                    break;
            }
            update();
        }
    };

    Action moveYinv = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            Solid selected = scene.getSolids().get(selector);
            switch (transformSelector){
                case 0:
                    selected.setRotY(selected.getRotY()-0.1);
                    break;
                case 1:
                    selected.setTransY(selected.getTransY()-0.1);
                    break;
                case 2:
                    selected.setScaleY(selected.getScaleY()-0.1);
                    break;
            }
            update();
        }
    };

    Action moveZ = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            Solid selected = scene.getSolids().get(selector);
            switch (transformSelector){
                case 0:
                    selected.setRotZ(selected.getRotZ()+0.1);
                    break;
                case 1:
                    selected.setTransZ(selected.getTransZ()+0.1);
                    break;
                case 2:
                    selected.setScaleZ(selected.getScaleZ()+0.1);
                    break;
            }
            update();
        }
    };

    Action moveZinv = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            Solid selected = scene.getSolids().get(selector);
            switch (transformSelector){
                case 0:
                    selected.setRotZ(selected.getRotZ()-0.1);
                    break;
                case 1:
                    selected.setTransZ(selected.getTransZ()-0.1);
                    break;
                case 2:
                    selected.setScaleZ(selected.getScaleZ()-0.1);
                    break;
            }
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
        renderer.setProjection(new Mat4OrthoRH(5,5,0.1,10));
        //renderer.setView(new Mat4ViewRH(new Vec3D(-5,0,0),new Vec3D(1,0,0),new Vec3D(0,0,1)));
        renderer.render(scene);



    }

    private void updateInfo() {
        switch (transformSelector){
            case 0:
                lblInfo.setText("Rotate");
                break;
            case 1:
                lblInfo.setText("Translate");
                break;
            case 2:
                lblInfo.setText("Scale");
                break;
        }

    }

    private void hardClear() {
        panel.clear();

    }


}
