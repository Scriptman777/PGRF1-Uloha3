package model;

import java.util.ArrayList;
import java.util.List;

public class Solid {

    private List<Vertex> vertices;
    private List<Integer> indices;

    public Solid() {
        vertices = new ArrayList<>();
        indices = new ArrayList<>();
    }

    public List<Vertex> getVertices() {
        return vertices;
    }

    public List<Integer> getIndices() {
        return indices;
    }
}
