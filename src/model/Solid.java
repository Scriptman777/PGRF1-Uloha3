package model;

import transforms.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Solid {

    private List<Vertex> vertices;
    private List<Integer> indices;
    private Color color;

    //Transforms - Rotation
    private double rotX = 0;
    private double rotY = 0;
    private double rotZ = 0;

    //Transforms - Translation
    private double transX = 0;
    private double transY = 0;
    private double transZ = 0;

    //Transforms - Scale
    private double scaleX = 1;
    private double scaleY = 1;
    private double scaleZ = 1;

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

    public double getRotX() {
        return rotX;
    }

    public void setRotX(double rotX) {
        this.rotX = rotX;
        updateMatrix();
    }

    public double getRotY() {
        return rotY;
    }

    public void setRotY(double rotY) {
        this.rotY = rotY;
        updateMatrix();
    }

    public double getRotZ() {
        return rotZ;
    }

    public void setRotZ(double rotZ) {
        this.rotZ = rotZ;
        updateMatrix();
    }

    public double getTransX() {
        return transX;
    }

    public void setTransX(double transX) {
        this.transX = transX;
        updateMatrix();
    }

    public double getTransY() {
        return transY;
    }

    public void setTransY(double transY) {
        this.transY = transY;
        updateMatrix();
    }

    public double getTransZ() {
        return transZ;
    }

    public void setTransZ(double transZ) {
        this.transZ = transZ;
        updateMatrix();
    }

    public double getScaleX() {
        return scaleX;
    }

    public void setScaleX(double scaleX) {
        this.scaleX = scaleX;
        updateMatrix();
    }

    public double getScaleY() {
        return scaleY;
    }

    public void setScaleY(double scaleY) {
        this.scaleY = scaleY;
        updateMatrix();
    }

    public double getScaleZ() {
        return scaleZ;

    }

    public void setScaleZ(double scaleZ) {
        this.scaleZ = scaleZ;
        updateMatrix();
    }

    private void updateMatrix(){
        Mat4 scale = new Mat4Scale(scaleX,scaleY,scaleZ);
        Mat4 trans = new Mat4Transl(transX,transY,transZ);
        Mat4 rotat = new Mat4RotXYZ(rotX,rotY,rotZ);
        transform = scale.mul(trans).mul(rotat);

    }

    public Mat4 getTransform(){
        return transform;
    }


}
