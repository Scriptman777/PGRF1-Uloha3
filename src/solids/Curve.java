package solids;

import model.Solid;
import model.Vertex;
import transforms.Cubic;
import transforms.Point3D;

import java.awt.*;

public class Curve extends Solid {

    /*
    Solid "Curve" není pouze křivka, jedná se o "plochu" složenou ze tří křivek a čar spojujících jejich body - jakýsi wireframe
     */

    public Curve(Color color) {
        super(color);
        Cubic cub = new Cubic(Cubic.BEZIER,new Point3D(0,1,1),new Point3D(0,2,1),new Point3D(1,2,0),new Point3D(1,2,1));
        for (double i = 0; i < 1; i+=0.05) {
            getVertices().add(new Vertex(cub.compute(i)));
        }

        Cubic cub2 = new Cubic(Cubic.BEZIER,new Point3D(0,1,2),new Point3D(0,2,2),new Point3D(1,2,1),new Point3D(1,2,2));
        for (double i = 0; i < 1; i+=0.05) {
            getVertices().add(new Vertex(cub2.compute(i)));
        }

        Cubic cub3 = new Cubic(Cubic.BEZIER,new Point3D(0,1,3),new Point3D(0,2,3),new Point3D(1,2,2),new Point3D(1,2,3));
        for (double i = 0; i < 1; i+=0.05) {
            getVertices().add(new Vertex(cub3.compute(i)));
        }

        for (int j = 0; j < getVertices().size()-1; j++) {
            if (j != 19 && j != 39){
                getIndices().add(j);
                getIndices().add(j+1);
            }
        }

        for (int c = 0; c < 19; c++){
            getIndices().add(c);
            getIndices().add(c+20);
            getIndices().add(c+20);
            getIndices().add(c+40);
        }



    }


}
