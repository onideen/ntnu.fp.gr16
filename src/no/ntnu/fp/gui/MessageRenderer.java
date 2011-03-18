/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package no.ntnu.fp.gui;

import java.awt.Color;
import java.awt.Component;
import javax.swing.*;
import no.ntnu.fp.model.Message;

/**
 *
 * @author Erlend Dahl
 */
public class MessageRenderer implements ListCellRenderer {


    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

            MessagePanel p = new MessagePanel();
            p.readMessage((Message)value);
            p.setBackground(cellHasFocus ? Color.gray : Color.WHITE);

            return p;

    }

}
