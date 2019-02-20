package com.example.demo;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class ShareResour{
    private int number = 0;
    //方法二，Lock
    private Lock lock = new ReentrantLock();
    private Condition condition= lock.newCondition();

    public void increment() throws InterruptedException {
        lock.lock();
        while(number!=0){
            condition.await();
        }
        number++;
        System.out.println(Thread.currentThread().getName()+"\t"+number);
        condition.signalAll();
    }


    public  void decrement() throws InterruptedException {
        lock.lock();
        while(number==0){
            condition.await();
        }
        number--;
        System.out.println(Thread.currentThread().getName()+"\t"+number);
        condition.signalAll();
    }



    /*方法一：synchronized
    public synchronized  void increment() throws InterruptedException {
        //判断   干活  通知
        while(number!=0){
            this.wait();
        }
        number++;
        System.out.println(Thread.currentThread().getName()+"\t"+number);
        this.notifyAll();
    }

    public synchronized void decrement() throws InterruptedException {
        while(number==0){
            this.wait();
        }
        number--;
        System.out.println(Thread.currentThread().getName()+"\t"+number);
        this.notifyAll();
    }*/
}
public class ThreadWaitNotifyDemo {
    //四个线程，一个初始值为0的变量，两个线程+1，两个线程-1，加完通知另外线程，循环10次，回到0
    public static void main(String[] args) {
        ShareResour shareResour = new ShareResour();

         new Thread(()->{
             for (int i = 1; i <=100 ; i++) {
                 try {
                     shareResour.decrement();
                 } catch (InterruptedException e) {
                     e.printStackTrace();
                 }
             }
                 },"A").start();

         new Thread(()->{

             for (int i = 1; i <=100 ; i++) {
                 try {
                     shareResour.decrement();
                 } catch (InterruptedException e) {
                     e.printStackTrace();
                 }
             }
                 },"B").start();

        new Thread(()->{
            for (int i = 1; i <=100 ; i++) {
                try {
                    shareResour.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"C").start();

        new Thread(()->{
            for (int i = 1; i <=100 ; i++) {
                try {
                    shareResour.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"D").start();
    }
}
