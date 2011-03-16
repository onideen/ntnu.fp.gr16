/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package no.ntnu.fp.net.co;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author alxandr
 */
public class AutoResetEvent {
    protected final Semaphore event;
    protected final Integer mutex;

    public AutoResetEvent(boolean signalled) {
        event = new Semaphore(signalled ? 1 : 0);
        mutex = new Integer(-1);
    }

    public void Set() {
        synchronized(mutex) {
            if(event.availablePermits() == 0)
                event.release();
        }
    }

    public void Reset() {
        event.drainPermits();
    }

    public void WaitOne() throws InterruptedException {
        event.acquire();
    }

    public boolean WaitOne(int timeOut, TimeUnit unit) throws InterruptedException {
        return event.tryAcquire(timeOut, unit);
    }

    public boolean isSignalled() {
        return event.availablePermits() > 0;
    }
}
