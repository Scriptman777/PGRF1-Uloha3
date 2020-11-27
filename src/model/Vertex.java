package model;

import transforms.Mat4;
import transforms.Point3D;

public class Vertex {
    private Point3D position;

    public Vertex(double x,double y,double z){
        position = new Point3D(x,y,z);
    }

    public Vertex(Point3D p) {
        position = p;
    }


    public Vertex trasform(Mat4 matrix) {
        return new Vertex(getPosition().mul(matrix));
    }

    public Point3D getPosition() {
        return position;
    }
}
