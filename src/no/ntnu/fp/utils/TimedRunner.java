/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package no.ntnu.fp.utils;

import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author Alxandr
 */
public class TimedRunner {
    private Timer timer;
    private Runnable runner;
    private long timeout;
    private boolean running = false;

    public TimedRunner(long timeOutInMillis, Runnable runner)
    {
        this.runner = runner;
        timeout = timeOutInMillis;
    }

    public synchronized TimedRunner start()
    {
        if(running)
            return this;
        running = true;
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask()
        {
            @Override
            public void run()
            {
                TimedRunner.this.runner.run();
            }
        }, 0, timeout);
        return this;
    }

    public synchronized void stop()
    {
        if(!running)
            return;
        running = false;
        timer.cancel();
        timer = null;
    }

    public void invoke() {
        boolean r = running;
        if(r)
            this.stop();

        runner.run();

        if(r)
            this.start();
    }
}
