#This section is all about Performance optimization

Has 4 lectures


# L1 Introduction to performance and optimizing for Latency

Topics: performance criteria/definition, Latency, performance in multithreaded applications

## Topic 2: What's performance and definitions of terms
For different scenarios and usecases performance can be measured differently

eg. 
1. High speed trading system
performance measured in **Latency** measured in unit time
Faster the trxn the better the performance

2. Video player
delivers video content to the user
So delivering video frames as fast as possible would be terrible because of movies is at 30 fps and we show at 1000 fps then user wont see anything 

So we want to show content at corrent and precise frame rate with min jitter

This performance metric is precision and accurracy of the frame rate

3. Machine learning
performs ML on large volume of data and gives recommendation every 24 hrs

More data -> better prediction

Thus performance metric is throughput and not latency or jitter

#### ***Generally for multithreading we have 2 performance metrics***
1. Latency - The time to complete a single task. Measured in time units
2. Throughput - Amount of tasks completed in a given period. Measured in task/time unit

Both these units are completely independent and improving one may have no effect on other

## Topic 2: Latency
How to improve latency of a task using multithreading?

*Break task into number of sub task and run on each thread and achieve latency of T/N (theoritical)*

Latency reduced by N
Performance improved by N

N = subtasks /threads to break the original task

But does breaking tasks have a cost? and can we break any task?

On a general purpose PC N should be close to number of cores
        N = num. cores
because *reduction in latency is a result of running these subtsaks fully in parallel*
***and only if we run these tasks on differnet cores we can truly run them parallely***

If noting else is running on the CPU, no I/O blocking, no sleep then OS will utilize hardware best but this happens rarely

Adding 1 additional thread will also reduce latency as context switching, cache performance, extra memory consumption cause oveheads

#### **Hyperthreading?**
Also called virtual cores

Most computer use this today
Single physical core can run 2 threads at the time
Achieved by having some hardware units duplicated in a physical core so two threads can run in parallel when some hardware units are shared 

Cost of breaking tasks?
we have to accept inherent cost of
- some calculations to break tasks
- time to create threads
- time for thread to be scheduled by OS
- time for longest task
- time for aggregating thread takes

When to parallelize/thread?
Point on the intersection of latency vs task size for multithreaded and single threaded

Can we break any task?
No
Type of tasks:
- parallizable and easily breakable
- unbreakable task which we are forced to run from start to finish
- partially sequential and partially can be broken

#### Summary

- Performance is a broad term
- multithreaded performance in Latency and Throughput
- latency reduced by breakign tasks in smaller tasks


# L2 Pratical of Image precessing using multithreading
Topics: Image processing sequential, Image processing multithreaded, performance / latency measurements

Task convert flowers to purpulish colors which looks natural

pixelstores - ARGB
A - alpha is transperency