/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package no.ntnu.fp.utils;

import javax.swing.SwingUtilities;

/**
 *
 * @author alxandr
 */
public class Loader {
    public static void loadAndRun(final LoadRunner loader, final LoadCallback callback)
    {
        Thread thread = new Thread(new Runnable() {

            public void run()
            {
                final Object obj = loader.run();
                SwingUtilities.invokeLater(new Runnable() {

                    public void run()
                    {
                        callback.run(obj);
                    }
                });
            }
        });
        thread.setDaemon(true);
        thread.start();
    }
}
