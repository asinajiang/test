package com.rumwei.func.thread;


/**
 * Base class for background thread
 */
public abstract class ServiceThread implements Runnable {

    private static final long JOIN_TIME = 5 * 1000;
    protected final Thread thread;
    protected volatile boolean hasNotified = false;
    protected volatile boolean stopped = false;

    public ServiceThread() {
        this.thread = new Thread(this, this.getServiceName());
    }

    public abstract String getServiceName();

    public void start() {
        this.thread.start();
    }

    public void shutdown() {
        this.shutdown(false);
    }

    public void shutdown(final boolean interrupt) {
        this.stopped = true;
        System.out.println("shutdown thread " + this.getServiceName() + " interrupt " + interrupt);
        System.out.println(Thread.currentThread().getName());
        synchronized (this) {
            if (!this.hasNotified) {
                this.hasNotified = true;
                this.notify();
            }
        }

        try {
            if (interrupt) {
                this.thread.interrupt();
            }

            long beginTime = System.currentTimeMillis();
            this.thread.join(this.getJointime());
            long elapsedTime = System.currentTimeMillis() - beginTime;
            System.out.println("join thread " + this.getServiceName() + " elapsed time(ms) " + elapsedTime + " "
                + this.getJointime());
        } catch (InterruptedException e) {
            System.out.println("Interrupted"+e.getMessage());
        }
    }

    public long getJointime() {
        return JOIN_TIME;
    }

    public boolean isStopped() {
        return stopped;
    }
}
