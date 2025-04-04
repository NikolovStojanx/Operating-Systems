package synch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class SiO2 {
// Si Si
    //O O
    //SiOO-->bond,
    static Semaphore siHere = new Semaphore(1);
    static Semaphore OHere = new Semaphore(2);

    public static void main(String[] args) {
        List<Thread> si_threads=new ArrayList<>();
        List<Thread> o_threads=new ArrayList<>();
        for(int i =0;i<5;i++){
            si_threads.add(new Si());
        }
        for(int i =0;i<10;i++){
            o_threads.add(new O());
        }
        for(int i =0;i<5;i++){
            si_threads.get(i).start();
        }
        for(int i =0;i<10;i++){
            o_threads.get(i).start();
        }

    }

    public static void bond(){
        System.out.println("Bonding...SiO2");
    }

    public static class Si extends Thread{
        public void proc_Si() throws InterruptedException {
            siHere.acquire();
            //waiting 2 atoms of 0
            OHere.acquire();
            OHere.acquire();
            bond();
            siHere.release();

        }

        @Override
        public void run() {
            try {
                proc_Si();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static class O extends Thread{
        public void proc_O(){
            //O --> keys+=1
            OHere.release();

        }

        @Override
        public void run() {
            proc_O();
        }
    }
}
