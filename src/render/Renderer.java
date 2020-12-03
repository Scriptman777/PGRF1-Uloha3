package render;

import model.Scene;
import model.Solid;
import model.Vertex;
import rasterize.LineRasterizer;
import rasterize.LineRasterizerGraphics;
import rasterize.Raster;
import solids.Axis;
import transforms.Mat4;
import transforms.Mat4Identity;
import transforms.Point3D;
import transforms.Vec3D;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Renderer {
    private Mat4 model = new Mat4Identity();
    private Mat4 view = new Mat4Identity();
    private Mat4 projection = new Mat4Identity();
    private LineRasterizerGraphics lineRasterizer;
    private Raster raster;

    public Renderer(LineRasterizerGraphics rs, Raster raster){
        this.lineRasterizer = rs;
        this.raster = raster;
    }

    public void setModel(Mat4 model){
        this.model = model;
    }

    public void setProjection(Mat4 projection){
        this.projection = projection;
    }

    public void setView(Mat4 view) {
        this.view = view;
    }

    public void render(Scene scene) {

        for (Solid sol: scene.getSolids()) {
            renderSolid(sol);
        }
    }

    public void renderSolid(Solid solid) {
        //modelovací transformace - posun/otočení etc
        //pohledová transformace
        //perspektiva/projekce - zobrazovací objem
        setModel(solid.getTransform());


        if (solid instanceof Axis) {
            //TODO
        }
        else {
            //Transform
            Mat4 m = (model.mul(view)).mul(projection);
            List<Vertex> transformedVerts = new ArrayList<>();
            for (Vertex vert: solid.getVertices()) {
                transformedVerts.add(vert.trasform(m));
            }

            //Vykreslení hran
            for(int i = 0; i < solid.getIndices().size(); i+=2) {
                int indexA = solid.getIndices().get(i);
                int indexB = solid.getIndices().get(i+1);
                Vertex a = transformedVerts.get(indexA);
                Vertex b = transformedVerts.get(indexB);
                renderEdge(a,b,solid.getColor());
            }
        }



    }

    private void renderEdge(Vertex a,Vertex b,Color color) {

        //Ořezání - fast clip
        if (!isInView(a) | !isInView(b)) { return; }

        //TODO

        //Dehomogenizace
        Vec3D va = new Vec3D();
        if (a.getPosition().dehomog().isPresent()){
            va = a.getPosition().dehomog().get();
        }

        Vec3D vb = new Vec3D();
        if (b.getPosition().dehomog().isPresent()){
            vb = b.getPosition().dehomog().get();
        }



        //Viewport transform
        int x1 = (int) ((va.getX() + 1)*(raster.getWidth() - 1)/2);
        int x2 = (int) ((vb.getX() + 1)*(raster.getWidth() - 1)/2);
        int y1 = (int) ((1 - va.getY())*(raster.getHeight() - 1)/2);
        int y2 = (int) ((1 - vb.getY())*(raster.getHeight() - 1)/2);
        //vykreslení LineRasterizerem
        lineRasterizer.setColor(color);
        lineRasterizer.drawLine(x1,y1,x2,y2);
    }

    private boolean isInView(Vertex a) {
        Point3D pos = a.getPosition();
        return (-pos.getW() <= pos.getX()) && (pos.getY() <= pos.getW()) && (0 <= pos.getZ()) && (pos.getZ() <= pos.getW());


    }

}
