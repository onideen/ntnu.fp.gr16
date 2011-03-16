package no.ntnu.fp.gui;
import java.awt.BorderLayout;
import no.ntnu.fp.model.*;


import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JToggleButton;
import javax.swing.ListModel;
import javax.swing.SpinnerListModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;

/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class CreateMeetingPanel extends javax.swing.JPanel {

	{
		//Set Look & Feel
		try {
			javax.swing.UIManager.setLookAndFeel("com.jgoodies.looks.plastic.PlasticXPLookAndFeel");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private JPanel meeting;
	private JToggleButton meeting_toggle;
	private JButton save_button;
	private JButton add_users;
	private JPanel buttons_panel;
	private JLabel new_agreement_label;
	private JLabel selected_users_label;
	private JButton unselect;
	private JScrollPane jScrollPane1;
	private JList selected_users_list;
	private JButton select;
	private JPanel buttons_select_unselect_panel;
	private JScrollPane selected_users_scroll;
	private JList all_users_list;
	private JLabel all_users_label;
	private JPanel all_users;
	private JComboBox room_chooser;
	private JLabel room_label;
	private JEditorPane description;
	private JLabel description_label;
	private JLabel end_time_label;
	private JLabel start_time_label;
	private JComboBox jComboBox1;
	private JComboBox end_time;
	private JComboBox start_time;
	private JLabel date_label;
	private ButtonGroup meeting_agreement;
	private JToggleButton agreement_toggle;
	private JDateChooser calendar;
	private DefaultListModel selected_users_listModel;
	private DefaultListModel all_users_listModel;
	
	
	private Event event;
	
	/**
	* Auto-generated main method to display this 
	* JPanel inside a new JFrame.
	*/
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.getContentPane().add(new CreateMeetingPanel());
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	
	public CreateMeetingPanel() {
		super();
		event = Communication.getEvent(73);
		initGUI();
	}
	
	private void initGUI() {
		try {
			GridBagLayout thisLayout = new GridBagLayout();
			thisLayout.rowWeights = new double[] {0.1};
			thisLayout.rowHeights = new int[] {7};
			thisLayout.columnWeights = new double[] {0.0, 0.0};
			thisLayout.columnWidths = new int[] {293, 567};
			this.setLayout(thisLayout);
			this.setPreferredSize(new java.awt.Dimension(873, 406));
			{
				meeting = new JPanel();
				GridBagLayout meetingLayout = new GridBagLayout();
				this.add(meeting, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 30));
				this.add(getAll_users(), new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
				this.add(getEnd_time(), new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				meetingLayout.rowWeights = new double[] {0.1, 0.1, 0.1, 0.1, 0.1, 0.0, 0.0, 0.1};
				meetingLayout.rowHeights = new int[] {7, 7, 7, 7, 7, 25, 44, 7};
				meetingLayout.columnWeights = new double[] {0.0, 0.1};
				meetingLayout.columnWidths = new int[] {10, 7};
				meeting.setLayout(meetingLayout);
				{
					calendar = new JDateChooser();
					meeting.add(calendar, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 10), 0, 0));
					meeting.add(getDate_label(), new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.VERTICAL, new Insets(0, 0, 0, 0), 0, 0));
					meeting.add(getStart_time_label(), new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.VERTICAL, new Insets(0, 0, 0, 0), 0, 0));
					meeting.add(getEnd_time_label(), new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.VERTICAL, new Insets(0, 0, 0, 0), 0, 0));
					meeting.add(getDescription_label(), new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.VERTICAL, new Insets(0, 0, 0, 0), 0, 0));
					meeting.add(getStart_time(), new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 10), 0, 0));
					meeting.add(end_time, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 10), 0, 0));
					meeting.add(getDescription(), new GridBagConstraints(1, 5, 1, 2, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 10), 0, 0));
					meeting.add(getRoom_label(), new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.VERTICAL, new Insets(0, 0, 0, 0), 0, 0));
					meeting.add(getRoom_chooser(), new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 10), 0, 0));
					meeting.add(getNew_agreement_label(), new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0, GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					meeting.add(getButtons_panel(), new GridBagConstraints(1, 7, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		addEmployees();
	}
	
	private void addEmployees() {
		if (event != null) {
			List<Person> attendees = Communication.getAttendees(event.getEid()); 
		
			for (Person person : attendees) 
				selected_users_listModel.addElement(person);
		}
		
		List<Person> employees = Communication.getEmployees();
		for (Person person : employees) {
			if ( event != null && ! selected_users_listModel.contains(person))
				all_users_listModel.addElement(person);
		}
		
	}

	private ButtonGroup getMeeting_agreement() {
		if(meeting_agreement == null) {
			meeting_agreement = new ButtonGroup();
		}
		return meeting_agreement;
	}
	
	private JLabel getDate_label() {
		if(date_label == null) {
			date_label = new JLabel();
			date_label.setText("Dato: ");
			date_label.setLabelFor(calendar);
			date_label.setPreferredSize(new java.awt.Dimension(100, 0));
			date_label.setHorizontalAlignment(SwingConstants.TRAILING);
		}
		return date_label;
	}
	
	private JLabel getStart_time_label() {
		if(start_time_label == null) {
			start_time_label = new JLabel();
			start_time_label.setText("Start: ");
			start_time_label.setPreferredSize(new java.awt.Dimension(100, 0));
			start_time_label.setHorizontalAlignment(SwingConstants.TRAILING);
		}
		return start_time_label;
	}
	
	private JLabel getEnd_time_label() {
		if(end_time_label == null) {
			end_time_label = new JLabel();
			end_time_label.setText("Slutt: ");
			end_time_label.setPreferredSize(new java.awt.Dimension(100, 0));
			end_time_label.setHorizontalAlignment(SwingConstants.TRAILING);
		}
		return end_time_label;
	}
	
	private JLabel getDescription_label() {
		if(description_label == null) {
			description_label = new JLabel();
			description_label.setText("Beskrivelse: ");
			description_label.setPreferredSize(new java.awt.Dimension(100, 0));
			description_label.setHorizontalAlignment(SwingConstants.TRAILING);
		}
		return description_label;
	}
	
	private JComboBox getStart_time() {
		if(start_time == null) {
			ComboBoxModel start_timeModel = 
				new DefaultComboBoxModel(hentKlokkeslett());
			start_time = new JComboBox(start_timeModel);
			start_time.setEditable(true);
			start_time.setRequestFocusEnabled(false);
			start_time.setSelectedIndex(9);
		}
		return start_time;
	}

	private String[] hentKlokkeslett() {
		String[] comboChoose = new String[24];
		for (int i = 0; i < 24; i++){
			if (i < 10)
				comboChoose[i] = "0" + i + ":00"; 
			else 
				comboChoose[i] = i + ":00"; 
		}
		return comboChoose;
		
	}
	
	private JComboBox getEnd_time() {
		if(end_time == null) {
			ComboBoxModel end_comboModel = 
				new DefaultComboBoxModel(hentKlokkeslett());
			end_time = new JComboBox(end_comboModel);
			end_time.setRequestFocusEnabled(false);
			end_time.setEditable(true);
			end_time.setSelectedIndex(10);
		}
		return end_time;
	}
	
	private JEditorPane getDescription() {
		if(description == null) {
			description = new JEditorPane();
			description.setSize(100, 81);
			description.setPreferredSize(new java.awt.Dimension(100, 21));
		}
		return description;
	}
	
	private JButton getSave_button() {
		if(save_button == null) {
			save_button = new JButton();
			save_button.setText("Lagre");
		}
		return save_button;
	}
	
	private JLabel getRoom_label() {
		if(room_label == null) {
			room_label = new JLabel();
			room_label.setText("Rom: ");
			room_label.setPreferredSize(new java.awt.Dimension(100, 0));
			room_label.setHorizontalAlignment(SwingConstants.TRAILING);
		}
		return room_label;
	}
	
	private JComboBox getRoom_chooser() {
		if(room_chooser == null) {
			ComboBoxModel room_chooserModel = 
				new DefaultComboBoxModel(
						new String[] { "Rom 1", "Rom 2" });
			room_chooser = new JComboBox();
			room_chooser.setModel(room_chooserModel);
			room_chooser.setEditable(true);
			room_chooser.setEnabled(false);
		}
		return room_chooser;
	}
	
	private JPanel getAll_users() {
		if(all_users == null) {
			all_users = new JPanel();
			GridBagLayout all_usersLayout = new GridBagLayout();
			all_usersLayout.rowWeights = new double[] {0.1, 0.1, 0.1, 0.1};
			all_usersLayout.rowHeights = new int[] {7, 7, 7, 7};
			all_usersLayout.columnWeights = new double[] {0.0, 0.0, 0.0};
			all_usersLayout.columnWidths = new int[] {245, 50, 257};
			all_users.setLayout(all_usersLayout);
			all_users.add(getButtons_select_unselect_panel(), new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
			all_users.add(getAll_users_label(), new GridBagConstraints(-1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			all_users.add(getSelected_users_scroll(), new GridBagConstraints(2, 1, 1, 3, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
			all_users.add(getAllEmployeeScroll(), new GridBagConstraints(0, 1, 1, 3, 0.0, 0.0, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
			all_users.add(getSelected_users_label(), new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		}
		return all_users;
	}

	private JLabel getAll_users_label() {
		if(all_users_label == null) {
			all_users_label = new JLabel();
			all_users_label.setText("Alle Brukere");
		}
		return all_users_label;
	}
	
	private JList getAll_users_list() {

		all_users_listModel = new PersonListModel();
		all_users_list = new JList();
		all_users_list.setCellRenderer(new AllEmployeesListRenderer());
		all_users_list.setModel(all_users_listModel);
		all_users_list.setPreferredSize(new java.awt.Dimension(60, 70)); 
		return all_users_list;
		
	}
	
	private JScrollPane getAllEmployeeScroll() {
		if(jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setPreferredSize(new java.awt.Dimension(100, 387));
			jScrollPane1.setViewportView(getAll_users_list());
			jScrollPane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		}
		return jScrollPane1;
	}
	
	private JScrollPane getSelected_users_scroll() {
		if(selected_users_scroll == null) {
			selected_users_scroll = new JScrollPane();
			selected_users_scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			selected_users_scroll.setViewportView(getSelected_users_list());
		}
		return selected_users_scroll;
	}
	
	private JList getSelected_users_list() {
		if(selected_users_list == null) {
			selected_users_listModel = new PersonListModel();
			selected_users_list = new JList();
			selected_users_list.setCellRenderer(new AllEmployeesListRenderer());
			selected_users_list.setModel(selected_users_listModel);
			selected_users_list.setPreferredSize(new java.awt.Dimension(60, 70));
			selected_users_list.setSize(177, 385);
		}
		return selected_users_list;
	}
	
	private JPanel getButtons_select_unselect_panel() {
		if(buttons_select_unselect_panel == null) {
			buttons_select_unselect_panel = new JPanel();
			GridBagLayout buttons_select_unselect_panelLayout = new GridBagLayout();
			buttons_select_unselect_panelLayout.rowWeights = new double[] {0.1, 0.1, 0.1, 0.1};
			buttons_select_unselect_panelLayout.rowHeights = new int[] {7, 7, 7, 7};
			buttons_select_unselect_panelLayout.columnWeights = new double[] {0.1};
			buttons_select_unselect_panelLayout.columnWidths = new int[] {7};
			buttons_select_unselect_panel.setLayout(buttons_select_unselect_panelLayout);
			buttons_select_unselect_panel.setPreferredSize(new java.awt.Dimension(60, 70));
			buttons_select_unselect_panel.add(getSelect(), new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.SOUTH, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			buttons_select_unselect_panel.add(getUnselect(), new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		}
		return buttons_select_unselect_panel;
	}
	
	private JButton getSelect() {
		if(select == null) {
			select = new JButton();
			select.setText("-->");
			select.setSize(40, 40);
			select.setPreferredSize(new java.awt.Dimension(40, 40));
			select.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					addSelectedEmployees();	
				}
			});
		}
		return select;
	}
	
	protected void addSelectedEmployees() {
		
		for (Object person : all_users_list.getSelectedValues()) {
			all_users_listModel.removeElement(person);
			selected_users_listModel.addElement(person);
		}
		
	}

	private JButton getUnselect() {
		if(unselect == null) {
			unselect = new JButton();
			unselect.setText("<--");
			unselect.setPreferredSize(new java.awt.Dimension(40, 40));
			unselect.setSize(40, 40);
			unselect.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					unselectEmployees();
				}
			});
		}
		return unselect;
	}
	
	protected void unselectEmployees() {
		for (Object person : selected_users_list.getSelectedValues()) {
			selected_users_listModel.removeElement(person);
			all_users_listModel.addElement(person);
		}
		
	}

	private JLabel getSelected_users_label() {
		if(selected_users_label == null) {
			selected_users_label = new JLabel();
			selected_users_label.setText("Valgte brukere");
		}
		return selected_users_label;
	}
	
	private JLabel getNew_agreement_label() {
		if(new_agreement_label == null) {
			new_agreement_label = new JLabel();
			new_agreement_label.setText("Legg til ny avtale");
		}
		return new_agreement_label;
	}
	
	private JPanel getButtons_panel() {
		if(buttons_panel == null) {
			buttons_panel = new JPanel();
			GridBagLayout buttons_panelLayout = new GridBagLayout();
			buttons_panelLayout.rowWeights = new double[] {0.1};
			buttons_panelLayout.rowHeights = new int[] {7};
			buttons_panelLayout.columnWeights = new double[] {0.1, 0.1};
			buttons_panelLayout.columnWidths = new int[] {7, 7};
			buttons_panel.setLayout(buttons_panelLayout);
			buttons_panel.add(getSave_button(), new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			buttons_panel.add(getAdd_users(), new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		}
		return buttons_panel;
	}
	
	private JButton getAdd_users() {
		if(add_users == null) {
			add_users = new JButton();
			add_users.setText("Legg til deltakere");
		}
		return add_users;
	}

}