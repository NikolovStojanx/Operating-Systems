package synch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Main {

    static Semaphore accessBuffer = new Semaphore(1);
    static Semaphore controller_keys = new Semaphore(10);
    static Semaphore lock = new Semaphore(0);
    static Semaphore lockvalue = new Semaphore(1);
    static int numcont = 0;
    public static void main(String[] args) {
        List<Thread> producers = new ArrayList<>();
        Buffer buffer = new Buffer();
        for(int i=0;i<10;i++){
            producers.add(new Producer(buffer, accessBuffer, lock ));
        }
        List<Thread> controllers = new ArrayList<>();
        for(int i=0;i<10;i++){
            controllers.add(new Controller(buffer, accessBuffer, controller_keys,  lock , numcont, lockvalue));
        }

        for(int i=0;i<10;i++){
            producers.get(i).start();
            controllers.get(i).start();
        }
    }

}


