package solids;

import model.Solid;
import model.Vertex;

import java.awt.*;

public class Octahedron extends Solid {

    public Octahedron(Color color) {
        super(color);
        getVertices().add(new Vertex(0,0,0)); //0
        getVertices().add(new Vertex(0,1,0)); //2
        getVertices().add(new Vertex(1,1,0)); //3
        getVertices().add(new Vertex(1,0,0)); //1
        getVertices().add(new Vertex(0.5,0.5,1)); //4
        getVertices().add(new Vertex(0.5,0.5,-1)); //5

        getIndices().add(0); getIndices().add(1);
        getIndices().add(1); getIndices().add(2);
        getIndices().add(2); getIndices().add(3);
        getIndices().add(3); getIndices().add(0);

        getIndices().add(0); getIndices().add(4);
        getIndices().add(1); getIndices().add(4);
        getIndices().add(2); getIndices().add(4);
        getIndices().add(3); getIndices().add(4);

        getIndices().add(0); getIndices().add(5);
        getIndices().add(1); getIndices().add(5);
        getIndices().add(2); getIndices().add(5);
        getIndices().add(3); getIndices().add(5);

    }
}
