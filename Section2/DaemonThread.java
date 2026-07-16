package Section2;

import java.math.BigInteger;

public class DaemonThread {
    public static void main(String[] args) {
        Thread thread2 = new Thread(new LongComputationTask(new BigInteger("20000"), new BigInteger("100000000")));

        thread2.setDaemon(true);
        thread2.start();
    }

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
                res = res.multiply(base);
            }

            return res;
        }
    }
}
