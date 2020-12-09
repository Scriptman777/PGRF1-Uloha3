package model;

import transforms.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Solid {

    /*
    Solid obsahuje body, indexy, barvu a transofrmační matici
     */

    private List<Vertex> vertices;
    private List<Integer> indices;
    private Color color;


    //Transform
    private Mat4 transform;



    public Solid(Color color) {
        vertices = new ArrayList<>();
        indices = new ArrayList<>();
        this.setColor(color);
        transform = new Mat4Identity();
    }

    public List<Vertex> getVertices() {
        return vertices;
    }

    public List<Integer> getIndices() {
        return indices;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }


    public Mat4 getTransform(){
        return transform;
    }

    public void setTransform(Mat4 transform){
        this.transform = transform;
    }


}
