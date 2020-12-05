package solids;

import model.Solid;
import model.Vertex;

import java.awt.*;

public class Circle extends Solid {

    public Circle(Color color) {
        super(color);
        for (int i=0;i<360;i+=10){
            double y = Math.cos(Math.toRadians(i));
            double z = Math.sin(Math.toRadians(i));
            getVertices().add(new Vertex(0,y,z));
        }

        for (int j = 0; j<35;j++) {
            getIndices().add(j);
            getIndices().add(j+1);
        }
        getIndices().add(0);
        getIndices().add(getVertices().size()-1);





    }

}
