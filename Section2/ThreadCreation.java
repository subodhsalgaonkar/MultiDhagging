package Section2;

public class ThreadCreation {
    public static void main(String[] args) throws InterruptedException {
        Runnable r = () -> {
            System.out.println("We are currently in: " + Thread.currentThread().getName());
            System.out.println("Thread priority: " + Thread.currentThread().getPriority());
        };
        
        Thread thread = new Thread(r);

        thread.setName("Janhavi's feet hurt bad");

        thread.setPriority(Thread.MAX_PRIORITY);

        System.out.println("We are currently in: " + Thread.currentThread().getName() + " Before starting new thread");
        thread.start();
        System.out.println("We are currently in: " + Thread.currentThread().getName() + " After starting new thread");
        
        Thread.sleep(1000);
    }
}