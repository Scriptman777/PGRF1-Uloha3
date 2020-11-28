package solids;

import model.Solid;
import model.Vertex;

import java.awt.*;

public class Box extends Solid {


    public Box(double a, double b, double c, Color color) {
        super(color);
        //Dělení 2 hned na začátku aby s nemuselo provádět mnohokrát
        a = a/2;
        b = b/2;
        c = c/2;

        //bottom
        getVertices().add(new Vertex(-a,-b,-c));
        getVertices().add(new Vertex(-a,-b,c));
        getVertices().add(new Vertex(a,-b,c));
        getVertices().add(new Vertex(a,-b,-c));
        //top
        getVertices().add(new Vertex(-a,b,-c));
        getVertices().add(new Vertex(-a,b,c));
        getVertices().add(new Vertex(a,b,c));
        getVertices().add(new Vertex(a,b,-c));

        //bottom
        getIndices().add(0); getIndices().add(1);
        getIndices().add(1); getIndices().add(2);
        getIndices().add(2); getIndices().add(3);
        getIndices().add(3); getIndices().add(0);

        //top
        getIndices().add(4); getIndices().add(5);
        getIndices().add(5); getIndices().add(6);
        getIndices().add(6); getIndices().add(7);
        getIndices().add(7); getIndices().add(4);

        //vertical lines
        getIndices().add(0); getIndices().add(4);
        getIndices().add(1); getIndices().add(5);
        getIndices().add(2); getIndices().add(6);
        getIndices().add(3); getIndices().add(7);

    }

}
