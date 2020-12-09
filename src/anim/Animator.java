package anim;


import model.Solid;
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
        while (!Thread.interrupted()){

            solid.setTransform(solid.getTransform().mul(new Mat4RotXYZ(0.1,0.1,0)));
            try {
                this.sleep(50);
            } catch (InterruptedException e) {
                this.interrupt();
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
