package synch;

import java.util.concurrent.Semaphore;

public class Controller extends Thread{
    Buffer buffer;
    Semaphore accessBuffer;
    Semaphore controller_keys;
    int numcont;
    Semaphore lock;
    Semaphore lockvalue;
    public Controller(Buffer b,Semaphore accessBuffer,Semaphore controller_keys, Semaphore lock ,int numcont,Semaphore lockvalue){
        this.buffer=b;
        this.accessBuffer= accessBuffer;
        this.controller_keys=controller_keys;
        this.numcont=numcont;
        this.lock=lock;
        this.lockvalue=lockvalue;
    }

    public void execute() throws InterruptedException {
        controller_keys.acquire();
        lockvalue.acquire();
        if(numcont==0) {
            lock.release();
            lockvalue.release();
        }

        numcont++;
        lockvalue.release();
        accessBuffer.acquire();
        buffer.check();
        accessBuffer.release();
        lockvalue.acquire();
        numcont--;
        lockvalue.release();
        controller_keys.release();
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
