package anim;

import control.Controller3D;

public class Updater extends Thread {

    private Controller3D controller;

    public Updater(Controller3D controller){
        this.controller = controller;
    }

    public void startThread(){
        this.start();
    }

    @Override
    public void run() {
        while (true){
            controller.update();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
    }
}
