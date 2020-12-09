package anim;

import control.Controller;
import control.Controller3D;
import model.Solid;
import rasterize.Raster;
import render.Renderer;
import transforms.Mat4RotXYZ;

public class Animator extends Thread {



    private Solid solid;

    public Animator(Solid solid){
        this.solid = solid;
    }

    public void startThread() {
        this.start();
    }

    @Override
    public synchronized void run() {
        while (true){

            solid.setTransform(solid.getTransform().mul(new Mat4RotXYZ(0.1,0.1,0)));
            try {
                this.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public Solid getSolid() {
        return solid;
    }

    public void setSolid(Solid solid) {
        this.solid = solid;
    }
}
