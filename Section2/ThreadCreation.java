package Section2;

public class ThreadCreation {
    public static void main(String[] args) throws InterruptedException {
        Runnable r = () -> {
            System.out.println("We are currently in: " + Thread.currentThread().getName());
            System.out.println("Thread priority: " + Thread.currentThread().getPriority());

            throw new RuntimeException("Intentional exception");
        };
        
        Thread thread = new Thread(r);

        thread.setName("Janhavi's feet hurt bad");

        thread.setPriority(Thread.MAX_PRIORITY);

        thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e){
                System.out.println("A critical error happener in thread: " + t.getName() + " the error is " + e.getMessage());
            }
        });

        System.out.println("We are currently in: " + Thread.currentThread().getName() + " Before starting new thread");
        thread.start();
        System.out.println("We are currently in: " + Thread.currentThread().getName() + " After starting new thread");
        
        // Thread.sleep(1000);
    }
}