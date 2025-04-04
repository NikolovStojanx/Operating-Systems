package synch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class UniversityToilet {

    static Semaphore toiletSemaphore = new Semaphore(1);
    static Semaphore mLock = new Semaphore(1);
    static Semaphore fLock = new Semaphore(1);
    static int mCounter = 0;
    static int fCounter=0;



    //Shared Memory
    static class Toilet {
        String gender;
        Toilet(String gender){
            this.gender = gender;
        }
        public void exit(){
            System.out.println("Exiting .... " + gender);
        }
        public void enter(){
            System.out.println("Entering .... " +gender);
        }
    }
    static  class Male extends Thread{
        private Toilet toilet;

        Male(Toilet toilet){
            this.toilet = toilet;
        }

        public void enter() throws InterruptedException {
            mLock.acquire();
            if(mCounter == 0)
                toiletSemaphore.acquire();
            mCounter++;
            toilet.enter();
            mLock.release();
        }

        public void exit() throws InterruptedException {
            mLock.acquire();
            toilet.exit();
            mCounter--;
            if(mCounter == 0)
                toiletSemaphore.release();
            mLock.release();

        }

        public void run(){

            try {
                this.enter();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                this.exit();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class Female extends Thread{
        private Toilet toilet;

        Female(Toilet toilet){
            this.toilet = toilet;
        }

        public void enter () throws InterruptedException {
            fLock.acquire();
            if(fCounter == 0)
                toiletSemaphore.acquire();
            fCounter++;
            toilet.enter();
            fLock.release();
        }

        public void exit () throws InterruptedException {
            fLock.acquire();
            toilet.exit();
            fCounter--;
            if(fCounter == 0)
                toiletSemaphore.release();

            fLock.release();

        }

        @Override
        public void run() {

            try {
                this.enter();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                this.exit();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        List<Female> femaleList = new ArrayList<>();
        List<Male> maleList = new ArrayList<>();
        Toilet toiletF = new Toilet("Female");
        for(int i = 0; i < 10; i++){

            femaleList.add(new Female(toiletF));
        }
        Toilet toiletM = new Toilet("Male");
        for(int i = 0; i <10; i++){
            maleList.add(new Male(toiletM));
        }

        for(Female female: femaleList)
            female.start();

        for(Male male: maleList)
            male.start();

        for(Female female: femaleList)
            female.join(2000);

        for(Male male: maleList) {
            try {
                male.join(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
