package synch;

import java.util.concurrent.Semaphore;

public class Producer extends Thread {
    Buffer buffer;
    Semaphore accessBuffer;
    Semaphore lock;
    public Producer(Buffer b,Semaphore accessBuffer, Semaphore lock ){
        this.buffer=b;
        this.accessBuffer=accessBuffer;
        this.lock=lock;
    }
    public void execute() throws InterruptedException {
        lock.acquire();
        accessBuffer.acquire();
        buffer.produce();
        accessBuffer.release();
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
