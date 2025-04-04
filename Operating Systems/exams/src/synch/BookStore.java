package synch;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Semaphore;

public class BookStore {
    public static Semaphore semaphore1 = new Semaphore(0);
    public static Semaphore semaphore2 = new Semaphore(1);
    public static Semaphore semaphore3 = new Semaphore(2);

    public static void main(String[] args) throws InterruptedException {
        BookShelf bookShelf = new BookShelf();

        List<Thread> books = new LinkedList<>();
        for (int i = 0; i < 5; i++) {

            // First add Book A, then Book B,
            BookA bookA = new BookA(bookShelf);
            BookB bookB = new BookB(bookShelf);

            books.add(bookA);
            books.add(bookB);
        }

        for (Thread book : books) {
            book.start();
        }

        for (Thread book : books) {
            book.join();

        }

        System.out.println(bookShelf.toString());
        int finialCount = bookShelf.getBooks().size();
    }
}

class BookShelf {
    private ArrayBlockingQueue<String> books = new ArrayBlockingQueue<String>(100);


    public void addBook(String book) {
        books.add(book);
    }

    public String toString() {
        String s = books.toString();
        return s.substring(1, s.length() - 1).replace(",", " ");
    }

    public ArrayBlockingQueue<String> getBooks() {
        return books;
    }
}

class BookA extends Thread {
    private BookShelf bookShelf;

    public BookA(BookShelf bookShelf) {
        this.bookShelf = bookShelf;
    }
//    public static Semaphore semaphore1 = new Semaphore(0);
//    public static Semaphore semaphore2 = new Semaphore(1);
//    public static Semaphore semaphore3 = new Semaphore(2);
    public void execute() throws InterruptedException {
        BookStore.semaphore1.acquire();
        bookShelf.addBook("A");
        BookStore.semaphore2.release();

    }

    @Override
    public void run() {
        try {
            execute();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

// ...

// Class Book B extends Thread
class BookB extends Thread {
    private BookShelf bookShelf;

    public BookB(BookShelf bookShelf) {
        this.bookShelf = bookShelf;
    }
    //    public static Semaphore semaphore1 = new Semaphore(0);
//    public static Semaphore semaphore2 = new Semaphore(1);
//    public static Semaphore semaphore3 = new Semaphore(2);
    public void execute() throws InterruptedException {

        BookStore.semaphore2.acquire();
        bookShelf.addBook("B");
        BookStore.semaphore1.release();

    }

    @Override
    public void run() {
        try {
            execute();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

// ...
