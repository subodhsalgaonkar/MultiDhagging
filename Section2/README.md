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