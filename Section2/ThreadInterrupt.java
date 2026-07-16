package Section2;

import java.math.BigInteger;

public class ThreadInterrupt {
    public static void main(String[] args) {


        // Scenario 1 eg:
        Thread thread1 = new Thread(new BlockingTask());

        // thread1.start();
        // thread1.interrupt();



        //Scenario 2 eg:
        Thread thread2 = new Thread(new LongComputationTask(new BigInteger("20000"), new BigInteger("100000000")));

        thread2.start();
        thread2.interrupt();
    }


    // For Scenario 1:
    private static class BlockingTask implements Runnable{
        @Override
        public void run(){
             
            try {
                Thread.sleep(500000);
            } catch (InterruptedException e) {
                System.out.println("Exiting the blocking Thread because: " + e.getMessage());
            }
        }
    }


    // For Scenario 2:
    private static class LongComputationTask implements Runnable{

        private BigInteger base;
        private BigInteger power;

        public LongComputationTask(BigInteger base, BigInteger power){
            this.base = base;
            this.power = power;
        }

        @Override
        public void run() {
            System.out.println(base + "^" + power + " = " + pow(base, power));
        }

        private BigInteger pow(BigInteger base, BigInteger power){
            BigInteger res = BigInteger.ONE;

            for(BigInteger i = BigInteger.ZERO; i.compareTo(power) != 0; i = i.add(BigInteger.ONE)){
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("Prematurely exiting the thread due to interrupt");
                    return BigInteger.ZERO;
                }
                res = res.multiply(base);
            }

            return res;
        }
    }
}
