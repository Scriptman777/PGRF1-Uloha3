package solids;

import model.Solid;
import model.Vertex;

public class Tetrahedron extends Solid {

    public Tetrahedron() {
        super();
        getVertices().add(new Vertex(0,0,0));
        getVertices().add(new Vertex(1,0,0));
        getVertices().add(new Vertex(0,1,0));
        getVertices().add(new Vertex(0,0,1));

        getIndices().add(0); getIndices().add(1);
        getIndices().add(0); getIndices().add(2);
        //TODO

    }
}
