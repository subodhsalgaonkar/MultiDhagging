package Section2;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ThreadJoining {
    public static void main(String[] args) throws InterruptedException {
        List<Long> inputNumbers = Arrays.asList(0L, 3453L, 3453L, 2324L, 4656L, 23L, 55565555L);

        List<FactorialThread> threads = new ArrayList<>();

        for(long inputNumber: inputNumbers){
            threads.add(new FactorialThread(inputNumber));
        }

        for(FactorialThread thread: threads){
            thread.start();
        }


        for(FactorialThread thread: threads){
            thread.join(10);
        }

        for(int i = 0; i < inputNumbers.size(); i++){
            FactorialThread thread = threads.get(i);

            if(thread.isFinished){
                System.out.println("The factorial of the number: " + inputNumbers.get(i) + " is: " + thread.getResult());
            }
            else{
                System.out.println("The factorial calculation for the number: " + inputNumbers.get(i) + " is still in progress");
                thread.interrupt();
            }
        }
    }

    public static class FactorialThread extends Thread {
        private boolean isFinished = false;
        private BigInteger result = BigInteger.ZERO;
        private long inputNumber;

        public FactorialThread(long inputNumber){
            this.inputNumber = inputNumber;
        }

        @Override
        public void run(){
            this.result = factorial(inputNumber);
            this.isFinished = true;
        }

        private BigInteger factorial(long inputNumber){
            BigInteger tempResult = BigInteger.ONE;

            for(long i = inputNumber; i > 0; i--){
                if(Thread.currentThread().isInterrupted()){
                    System.out.println("The thread has been prematurely ended as the application allows only 1 sec for calculation and the temporary result calculated in that time is: " + tempResult);
                    return BigInteger.ZERO;
                }
                tempResult = tempResult.multiply(new BigInteger(Long.toString(i)));
            }

            return tempResult;
        }

        public boolean isFinished(){
            return isFinished;
        }

        public BigInteger getResult(){
            return result;
        }
    }
}
