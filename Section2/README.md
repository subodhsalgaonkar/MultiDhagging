## L1 - First multithread program, thread creation using runnable interface, Thread Debugging, Thread class capabilities

### Thread Creation in java
In java all the thread related properties and methods are encapsulated in the Thread class by the JDK
So to create a new thread we need to create a thread object, in which we need to pass an object of a class that implements the runnable interface

```Java
Thread thread = new Thread(new Runnable{
    @override
    public void run(){
        // TODO: code that will run on the new thread
    }
});
```
To launch a new thread we need to start the thread using the start method on the Thread object - Which instructs the JVM to create a new thread and pass it to the OS

Thread class methods:
static method(no need to create a Thread object) currentThread which gives the Thread object of the current Thread
getName() - non static method
sleep() - puts current thread to sleep for milisecs / instructs the Thread not to schedule thread for that time

### Thread capabilities

JVM gave name Thread-0, meaningful name will help us in debugging
obj.setName() - sets the threads name

using thread object we can set the static component of the dynamic priority programatically
obj.setPriority()
Valid args -
```Java
//predefined values
Thread.MAX_PRIORITY
Thread.MIN_PRIORITY
Thread.NORM_PRIORITY //default
//and argguments from 1-10
```

### Thread Debugginf in a IDE
Add break points by left clicking next to line numbers


Normally unchecked exceptions that happen in java bring down the entire thread uncless we catch them explicitly and handle them in a particular way

With 
threadObj.setUncaughtExceptionHandler() we can set an exception handler for the entire thread at it's inception

This handler is called if an exception is thrown insde the thread and did not get caught anywhere

In the handler we usually might clean up some resources or log additional data to trouble shoot the issue after the fact


## L2 Thread Termination and Daemon Thread

Thread coordination with the example of stoping one thread from another thread
few ways to do it, the **thread interrupt** method and the **daemon thread**

### Thread termination
Why and when?
- Thread consumes resources like CPU and cache memory while running, even when not doing anything it consumes some memory and kernal resources

- So if a thread is done executing but the application is still running we want to clean up the threads resources

- If it is misbehaving

- If we want to close the application (by default even if one thread is running then the applicaiton will not stop)
So to close the application we need to close all threads 

#### 1. Thread.interrupt() 
each thread obj has a interrupt method
If exec from thread A we can send interrupt to thread B to interrupt thread B

Scenario 1:
If the thread is executing a method that throws an InterruptedException
Scenario 2:
If the thread we are trying to interrupt is handling the interrupt signal explicitly  


##### Scenario 1 eg.
```Java
public class ThreadInterrupt {
    public static void main(String[] args) {
        Thread thread = new Thread(new BlockingTask());

        thread.start();
    }

    private static class BlockingTask implements Runnable{
        @Override
        public void run(){
            // TODO: 
            try {
                Thread.sleep(500000);
            } catch (InterruptedException e) {
                System.out.println("Exiting the blocking Thread because: " + e.getMessage());
            }
        }
    }
}
```

So even if the main thread completes the execution the application waits for the blocking thread, to stop the blocking thread we call 
```Java
thread.interrupt();
```

SO now the interrupt is thrown and the catch block is executed


##### Scenario 2 eg
```Java
package Section2;

import java.math.BigInteger;

public class ThreadInterrupt {
    public static void main(String[] args) {

        //Scenario 2 eg:
        Thread thread2 = new Thread(new LongComputationTask(new BigInteger("20000"), new BigInteger("100000000000000000")));

        thread2.start();
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
                if(Thread.currentThread().isInterrupted()){
                    System.out.println("Prematurely exiting the thread due to interrupt");
                    return BigInteger.ZERO;
                }
                res = res.multiply(base);
            }

            return res;
        }
    }
}

```

SO when the values get very large the thread continues to run and if we want to close the application, so we need to either wait for that calculation to happen or interrupt the thread to close the application

**But thread.interrupt() is not enough**
As the interrupt is sent but we dont have anything to handle it, so the therad continues to run

To solve this **Find the hotspot(max time consuming) in the code** 
And check if the thread is interrupted from the outside using the 
```Java
Thread.currentThread().isInterrupted()
```
method


#### Daemon Threads
Background threads which do not prevent the application form exiting if the main thread terminates

we can set the thread to a daemon thread by changin it property using it's object using the setter
``` Java
thread.setDaemon(true);
```

Scenarios where we use Daemon threads:
- Background tasks, that should not block our application from terminating
    eg. In the text editor file saving thread is a daemon
- Code in a worker thread is not under our control, and we don't want it to block our application from terminating.
    eg. Worker therad that uses external libraries


Summary:
- Learned to stop threads using thread.interrupt() method
- How to handle cases when interrupt signal is not responded, we need to do it ourseleves
- learned to prevent a thread from blocking our app from exiting by setting the thread to Daemon thread   
