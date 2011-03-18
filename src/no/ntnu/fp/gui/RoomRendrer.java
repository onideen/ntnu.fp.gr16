package no.ntnu.fp.gui;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import no.ntnu.fp.model.Room;

public class RoomRendrer extends JLabel implements ListCellRenderer {

	public RoomRendrer() {
		setOpaque(true);
	}
	
	@Override
	public Component getListCellRendererComponent(JList list, Object room,
			int index, boolean isSelected, boolean cellHasFocus) {
		
		String s = ((Room)room).getName();
		setText(s);
		
		if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
		
		
		return this;
	}

}
