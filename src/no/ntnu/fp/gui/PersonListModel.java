package no.ntnu.fp.gui;

import no.ntnu.fp.model.*;
import javax.swing.DefaultListModel;

public class PersonListModel extends DefaultListModel {
	
	@Override
	public boolean contains(Object elem) {

		for (int i = 0; i < getSize(); i++) {
			Person p = (Person)get(i);			
			if (p.getEmail().equals(((Person)elem).getEmail()))
					return true;
		}
		
		return false;
	}
}
