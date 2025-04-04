package synch;

import java.util.HashSet;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;

//Vinegar (C2H4O2)
//        (35 points) A factory hires you to help synchronize the process of vinegar production - C2H4O2.
//
//        For the production process you need 2 carbon (C) atoms, 4 hydrogen (H) and 2 oxygen (О) atoms. The C2H4O2 molecules are created one by one.
//
//        Each atom is represented with the corresponding class, where the execute() method should be executed in background. The execute method should allow parallel execution of maximum 2 carbon (C) atoms, 4 hydrogen (H) and 2 oxygen (О) atoms. After its execution, each atom should print that it is present. Then, the atoms should wait until all other atoms required for the molecule are present, and than they all print Molecule bonding.. Afterwards, each of the methods should print that it is done. At the end, only one atom should print Molecule created. and should enable the creation of the next molecule.
//
//        Your task is to run in the background 20 carbon, 40 hydrogen and 20 oxygen atoms in the main method. Than, you need to wait 2 seconds for all of them to finish. If some of the atoms do not finish within 2 seconds, you should print Possible deadlock! and terminate the thread, and if all atoms have finished in the given time, you should print Process finished..
//
//        Your task is to complete the starter code according to the above requirements, while making sure you avoid a Race Condition or a Deadlock.

public class Vinegar {
    public static Semaphore CHere = new Semaphore(2);
    public static Semaphore OHere = new Semaphore(2);
    public static Semaphore HHere = new Semaphore(4);

    public static int numberOfAtoms = 0;
    public static Semaphore numberLock=new Semaphore(1);

    public static Semaphore ready = new Semaphore(0);

    public static void main(String[] args) throws InterruptedException {
        HashSet<Thread> threads = new HashSet<>();
        for (int i = 0; i < 20; i++) {
            threads.add(new C());
            threads.add(new H());
            threads.add(new H());
            threads.add(new O());
        }
        // run all threads in background
        for(Thread thread:threads){
            thread.start();
        }
        // after all of them are started, wait each of them to finish for maximum 2_000 ms
        for(Thread thread:threads){
            thread.join(2000);
        }

        // for each thread, terminate it if it is not finished
        for(Thread thread:threads){
            if(thread.isAlive()){
                System.out.println("Possible deadlock!");
                thread.interrupt();
            }
        }

        System.out.println("Process finished.");

    }

    static class C extends Thread {

        public void execute() throws InterruptedException {
            // at most 2 atoms should print this in parallel
            CHere.acquire();
            numberLock.acquire();
            numberOfAtoms++;
            System.out.println("C here.");
            // after all atoms are present, they should start with the bonding process together
            //numbe rof atoms = 2+4+2
            if(numberOfAtoms==8) {
                ready.release(8);

            }
            numberLock.release();
            ready.acquire();
            System.out.println("Molecule bonding.");
            Thread.sleep(100);// this represent the bonding process
            numberLock.acquire();
            numberOfAtoms--;
            System.out.println("C done.");
            if(numberOfAtoms==0){
                // only one atom should print the next line, representing that the molecule is created

                System.out.println("Molecule created.");
                HHere.release(4);
                OHere.release(2);
                CHere.release(2);

            }
            numberLock.release();
        }

        @Override
        public void run() {
            try {
                execute();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    static class H extends Thread{

        public void execute() throws InterruptedException {
            // at most 4 atoms should print this in parallel
            HHere.acquire();
            System.out.println("H here.");
            numberLock.acquire();
            numberOfAtoms++;

            // after all atoms are present, they should start with the bonding process together
            if(numberOfAtoms==8) {
                ready.release(8);
            }
            numberLock.release();
            ready.acquire();
            System.out.println("Molecule bonding.");

            Thread.sleep(100);// this represent the bonding process
            numberLock.acquire();
            numberOfAtoms--;
            System.out.println("H done.");
            if(numberOfAtoms==0){
                // only one atom should print the next line, representing that the molecule is created

                System.out.println("Molecule created.");
                HHere.release(4);
                OHere.release(2);
                CHere.release(2);
            }
            numberLock.release();
        }

        @Override
        public void run() {
            try {
                execute();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    static class O extends Thread {

        public void execute() throws InterruptedException {
            // at most 2 atoms should print this in parallel
            OHere.acquire();
            System.out.println("O here.");
            numberLock.acquire();
            numberOfAtoms++;

            // after all atoms are present, they should start with the bonding process together
            if(numberOfAtoms==8) {
                ready.release(8);

            }
            numberLock.release();
            ready.acquire();
            System.out.println("Molecule bonding.");
            Thread.sleep(100);// this represent the bonding process

            numberLock.acquire();
            numberOfAtoms--;
            System.out.println("O done.");
            if(numberOfAtoms==0){
                // only one atom should print the next line, representing that the molecule is created
                System.out.println("Molecule created.");
                HHere.release(4);
                OHere.release(2);
                CHere.release(2);
            }
            numberLock.release();


        }

        @Override
        public void run() {
            try {
                execute();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

}