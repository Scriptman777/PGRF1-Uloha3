package render;

import model.Scene;
import model.Solid;
import model.Vertex;
import rasterize.LineRasterizer;
import rasterize.LineRasterizerGraphics;
import rasterize.Raster;
import transforms.Mat4;
import transforms.Mat4Identity;
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

    public void render(Scene scene, Raster raster) {

        //TODO forach Solids, render dem
        //renderSolid
    }

    public void renderSolid(Solid solid) {
        //modelovací transformace - posun/otočení etc
        //pohledová transformace
        //perspektiva/projekce - zobrazovací objem

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
            renderEdge(a,b);
        }

    }

    private void renderEdge(Vertex a,Vertex b) {

        //Ořezání - fast clip
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

        //Projekce
        //Viewport transform
        //vykreslení LineRasterizerem

        int x1 = (int) ((va.getX() + 1)*(raster.getWidth() - 1)/2);
        int x2 = (int) ((vb.getX() + 1)*(raster.getWidth() - 1)/2);
        int y1 = (int) ((1 - va.getY())*(raster.getHeight() - 1)/2);
        int y2 = (int) ((1 - vb.getY())*(raster.getHeight() - 1)/2);
        lineRasterizer.drawLine(x1,y1,x2,y2);



    }

}
