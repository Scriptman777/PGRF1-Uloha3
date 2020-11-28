package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Solid {

    private List<Vertex> vertices;
    private List<Integer> indices;
    private Color color;

    public Solid(Color color) {
        vertices = new ArrayList<>();
        indices = new ArrayList<>();
        this.color = color;
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
}
