package no.ntnu.fp.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import no.ntnu.fp.model.Person;

public class AllEmployeesListRenderer implements ListCellRenderer  {

	@Override
	public Component getListCellRendererComponent(JList list, Object person,
			int index, boolean isSelected, boolean cellHasFocus) 
	{
		JPanel panel = new JPanel(new GridBagLayout());
		JLabel name = new JLabel(((Person)person).getName());
		JLabel email = new JLabel(((Person)person).getEmail());
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		panel.add(name, c);

		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 1;
		panel.add(email, c);

		name.setHorizontalTextPosition(JLabel.LEFT);
		email.setHorizontalTextPosition(JLabel.RIGHT);
		
		if (isSelected) {
			panel.setBackground(list.getSelectionBackground());
			name.setForeground(list.getSelectionForeground());
			email.setForeground(list.getSelectionForeground());
		}
		else {
			panel.setBackground(list.getBackground());
			name.setForeground(list.getForeground());
			email.setForeground(list.getForeground());
		}
		return panel;
	}
	
}
