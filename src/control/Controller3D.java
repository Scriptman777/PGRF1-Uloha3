package control;


import model.Scene;
import model.Solid;
import rasterize.*;
import render.Renderer;
import solids.*;
import solids.Box;
import transforms.*;
import view.Panel;
import view.Window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Controller3D implements Controller {

    private final int ROTATE = 0;
    private final int TRANSLATE = 1;
    private final int SCALE = 2;

    private final Panel panel;
    private final Window window;
    private final JLabel lblInfo;
    private Renderer renderer;
    private Camera camera;
    private Scene scene;
    private int selector = 0;
    private boolean projectionSelector = true;
    private int transformSelector = 0;
    private Color tempColor;
    private List<Color> colorList = new ArrayList<Color>();
    private Random rnd = new Random();

    private LineRasterizerGraphics rasterizer;


    public Controller3D(Window window) {
        this.window = window;
        this.panel = window.getPanel();
        this.lblInfo = window.getLblSelected();

        initObjects(panel.getRaster());
        initListeners(panel);
        initInputs();
        update();
        showHelpDialog();
    }

    public void initObjects(Raster raster) {
        Box box = new Box(1,1,1,Color.red);
        Tetrahedron tet = new Tetrahedron(Color.cyan);
        Circle cir = new Circle(Color.LIGHT_GRAY);
        Curve cur = new Curve(Color.green);
        Axis axis = new Axis();
        scene = new Scene();
        scene.addSolid(box);
        scene.addSolid(tet);
        scene.addSolid(cir);
        scene.addSolid(cur);
        scene.addSolid(axis);

        colorList.add(Color.BLUE);
        colorList.add(Color.RED);
        colorList.add(Color.CYAN);
        colorList.add(Color.GREEN);
        colorList.add(Color.WHITE);
        colorList.add(Color.MAGENTA);

        tempColor = scene.getSolids().get(selector).getColor();
        rasterizer = new LineRasterizerGraphics(raster);
        renderer = new render.Renderer(rasterizer,raster);
        camera = new Camera()
                .withAzimuth(0)
                .withZenith(0)
                .withPosition(new Vec3D(-5,0,0));


        updateInfo();
     }

     public void initInputs() {
        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("C"), "clear");
        panel.getActionMap().put("clear",clear);

         panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD8,0), "moveX");
         panel.getActionMap().put("moveX",moveX);

         panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD4,0), "moveY");
         panel.getActionMap().put("moveY",moveY);

         panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD5,0), "moveXinv");
         panel.getActionMap().put("moveXinv",moveXinv);

         panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD6,0), "moveYinv");
         panel.getActionMap().put("moveYinv",moveYinv);

         panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD9,0), "moveZ");
         panel.getActionMap().put("moveZ",moveZ);

         panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD7,0), "moveZinv");
         panel.getActionMap().put("moveZinv",moveZinv);

         panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("RIGHT"), "nextSolid");
         panel.getActionMap().put("nextSolid",nextSolid);

         panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("LEFT"), "previousSolid");
         panel.getActionMap().put("previousSolid",previousSolid);

         panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("UP"), "nextTransform");
         panel.getActionMap().put("nextTransform",nextTransform);

         panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("DOWN"), "previousTransform");
         panel.getActionMap().put("previousTransform",previousTransform);

         panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("W"), "cameraForward");
         panel.getActionMap().put("cameraForward",cameraForward);

         panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("S"), "cameraBack");
         panel.getActionMap().put("cameraBack",cameraBack);

         panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("A"), "cameraLeft");
         panel.getActionMap().put("cameraLeft",cameraLeft);

         panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("D"), "cameraRight");
         panel.getActionMap().put("cameraRight",cameraRight);

         panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("P"), "changeProj");
         panel.getActionMap().put("changeProj",changeProj);

         panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("H"), "showHelp");
         panel.getActionMap().put("showHelp",showHelp);

         panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("K"), "addCube");
         panel.getActionMap().put("addCube",addCube);

         panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("L"), "addTetra");
         panel.getActionMap().put("addTetra",addTetra);


     }

    Action addCube = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            scene.addSolid(new Box(1,1,1,colorList.get(rnd.nextInt(colorList.size()))));
            update();
        }
    };

    Action addTetra = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            scene.addSolid(new Tetrahedron(colorList.get(rnd.nextInt(colorList.size()))));
            update();
        }
    };


    Action changeProj = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            projectionSelector = !projectionSelector;
            update();
        }
    };

    Action showHelp = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            showHelpDialog();
          }
    };


    Action cameraForward = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            camera = camera.forward(0.1);
            update();
        }
    };

    Action cameraBack = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            camera = camera.backward(0.1);
            update();
        }
    };

    Action cameraLeft = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            camera = camera.left(0.1);
            update();
        }
    };

    Action cameraRight = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            camera = camera.right(0.1);
            update();
        }
    };

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
                case ROTATE:
                    selected.setRotX(selected.getRotX()+0.1);
                    break;
                case TRANSLATE:
                    selected.setTransX(selected.getTransX()+0.1);
                    break;
                case SCALE:
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
                case ROTATE:
                    selected.setRotY(selected.getRotY()+0.1);
                    break;
                case TRANSLATE:
                    selected.setTransY(selected.getTransY()+0.1);
                    break;
                case SCALE:
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
                case ROTATE:
                    selected.setRotX(selected.getRotX()-0.1);
                    break;
                case TRANSLATE:
                    selected.setTransX(selected.getTransX()-0.1);
                    break;
                case SCALE:
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
                case ROTATE:
                    selected.setRotY(selected.getRotY()-0.1);
                    break;
                case TRANSLATE:
                    selected.setTransY(selected.getTransY()-0.1);
                    break;
                case SCALE:
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
                case ROTATE:
                    selected.setRotZ(selected.getRotZ()+0.1);
                    break;
                case TRANSLATE:
                    selected.setTransZ(selected.getTransZ()+0.1);
                    break;
                case SCALE:
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
                case ROTATE:
                    selected.setRotZ(selected.getRotZ()-0.1);
                    break;
                case TRANSLATE:
                    selected.setTransZ(selected.getTransZ()-0.1);
                    break;
                case SCALE:
                    selected.setScaleZ(selected.getScaleZ()-0.1);
                    break;
            }
            update();
        }
    };


    @Override
    public void initListeners(Panel panel) {

        panel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int centX = panel.getRaster().getWidth();
                int centY = panel.getRaster().getHeight();
                camera = camera.withAzimuth(((Math.PI*(centX - e.getX()))/panel.getRaster().getWidth())-Math.PI/2);
                camera = camera.withZenith((Math.PI*(centY - e.getY()))/(panel.getRaster().getHeight())-Math.PI/2);
                update();
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

        if (projectionSelector){
            renderer.setProjection(new Mat4PerspRH(Math.PI/2, 1,0.1,10));
        }
        else {
            renderer.setProjection(new Mat4OrthoRH(5,5,0.1,10));
        }

        renderer.setView(camera.getViewMatrix());
        renderer.render(scene);



    }

    private void updateInfo() {
        switch (transformSelector){
            case ROTATE:
                lblInfo.setText("Rotate");
                break;
            case TRANSLATE:
                lblInfo.setText("Translate");
                break;
            case SCALE:
                lblInfo.setText("Scale");
                break;
        }

    }

    private void hardClear() {
        scene = new Scene();
        scene.addSolid(new Axis());
        scene.addSolid(new Box(1,1,1,Color.RED));
        selector = 0;
        update();

    }

    private void showHelpDialog() {
        JOptionPane.showMessageDialog(window, "H - zobrazení této nápovědy\nWASD - pohyb kamery\nMyš - rozhlížení\nŠipky ↑↓ - výběr transformace\nŠipky ←→ - výběr tělesa (zvýrazní se žlutě) \nNumpad 85/46/79 - transformace vybraného tělesa podél osy X/Y/Z\nP - změna projekce \nK - nová krychle \nL - nový čtyřstěn\nC - nová scéna", "Ovládání",JOptionPane.INFORMATION_MESSAGE);
    }


}
