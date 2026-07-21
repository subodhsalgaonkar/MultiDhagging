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

But does breaskin tasks have a cost? and can we break any task?