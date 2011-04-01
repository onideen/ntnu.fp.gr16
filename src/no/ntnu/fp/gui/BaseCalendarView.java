/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package no.ntnu.fp.gui;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import no.ntnu.fp.model.ServiceWrapper;

/**
 *
 * @author alxandr
 */
public class BaseCalendarView extends JPanel {

    private  ServiceWrapper service;
    protected ServiceWrapper getService() {
        return service;
    }

    public BaseCalendarView() {
        super(new GridBagLayout());
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BaseCalendarView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(BaseCalendarView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(BaseCalendarView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(BaseCalendarView.class.getName()).log(Level.SEVERE, null, ex);
        }
        service = new ServiceWrapper();
        Dimension size = new Dimension(985, 600);
        setSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setPreferredSize(size);
    }

}
