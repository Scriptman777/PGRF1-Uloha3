package model;

import java.util.ArrayList;
import java.util.List;

public class Scene {
    List<Solid> solids;

    public Scene(){
        solids = new ArrayList<>();
    }

    public void addSolid(Solid solid) {
        solids.add(solid);
    }

    public List<Solid> getSolids() {
        return solids;
    }


}
