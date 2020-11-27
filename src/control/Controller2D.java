package control;


import rasterize.*;
import render.Renderer;
import view.Panel;
import view.Window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Controller2D implements Controller {

    private final Panel panel;
    private final Window window;
    private Renderer renderer;

    private LineRasterizerGraphics rasterizer;

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

     }

     public void initInputs() {
        panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("C"), "clear");
        panel.getActionMap().put("clear",clear);
     }

    Action clear = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            hardClear();
        }
    };


    @Override
    public void initListeners(Panel panel) {

        window.getButtonClip().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                clip();
            }
        });


        panel.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                if (e.isControlDown()) return;

                if (e.isShiftDown()) {

                } else if (SwingUtilities.isLeftMouseButton(e)) {

                    if (window.getRadioNewPoly().isSelected()) {
                        polygon.points.add(new Point(e.getX(),e.getY()));
                        update();

                    } else if(window.getRadioFill().isSelected()) {

                        seedFill.setSeed(new Point(e.getX(),e.getY()));
                        seedFill.fill();

                    } else if(window.getRadioClip().isSelected()) {
                        clipPolygon.points.add(new Point(e.getX(),e.getY()));
                        update();
                    }
                    else if(window.getRadioPattern().isSelected()) {
                        seedFillPattern.setSeed(new Point(e.getX(),e.getY()));
                        seedFillPattern.setPattern(new PatternFillDots());
                        seedFillPattern.fill();
                    }
                    else if(window.getRadioPattern2().isSelected()) {
                        seedFillPattern.setSeed(new Point(e.getX(),e.getY()));
                        seedFillPattern.setPattern(new PatternFillLines());
                        seedFillPattern.fill();
                    }




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
        renderer

    }

    private void hardClear() {
        panel.clear();

    }


}
