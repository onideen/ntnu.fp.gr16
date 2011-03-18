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
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) 
	{
            Person person = (Person)value;

		EmployeePanel p = new EmployeePanel();
                p.readPerson(person);

                if (isSelected) {
			p.setBackground(list.getSelectionBackground());
		}
		else {
			p.setBackground(list.getBackground());
		}

                return p;
	}
	
}
