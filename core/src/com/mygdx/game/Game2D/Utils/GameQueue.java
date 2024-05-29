package com.mygdx.game.Game2D.Utils;

import java.util.ArrayDeque;
import java.util.Deque;

public class GameQueue {

    private static final Deque<Runnable> queue = new ArrayDeque<>();

    public static synchronized boolean isEmpty(){
        synchronized (queue){
            return queue.isEmpty();
        }
    }

    public static void add(Runnable runnable){
        synchronized (queue){
            queue.addLast(runnable);
        }
    }

    public static synchronized void removeFirst() {
        synchronized (queue){
            try {
                Thread thread = new Thread(queue.peek());
                thread.start();
                thread.join();
            } catch (InterruptedException ignored) {}

            queue.removeFirst();
        }
    }
}
