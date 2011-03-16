/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package no.ntnu.fp.net.co;

/**
 *
 * @author alxandr
 */
public class MultiAutoResetEvent extends AutoResetEvent {

    MultiAutoResetEvent(boolean b) {
        super(b);
    }
    @Override
    public void Set() {
        synchronized(mutex) {
            event.release();
        }
    }
}
