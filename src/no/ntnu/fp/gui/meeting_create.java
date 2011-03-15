package no.ntnu.fp.gui;
import java.awt.BorderLayout;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.ButtonGroup;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.WindowConstants;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JToggleButton;
import javax.swing.ListModel;
import javax.swing.SpinnerListModel;

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
public class meeting_create extends javax.swing.JPanel {

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
	private JList all_users_list;
	private JLabel all_users_label;
	private JPanel selected_users;
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

	/**
	* Auto-generated main method to display this 
	* JPanel inside a new JFrame.
	*/
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.getContentPane().add(new meeting_create());
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	
	public meeting_create() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		try {
			GridBagLayout thisLayout = new GridBagLayout();
			thisLayout.rowWeights = new double[] {0.1};
			thisLayout.rowHeights = new int[] {7};
			thisLayout.columnWeights = new double[] {0.1, 0.1, 0.1};
			thisLayout.columnWidths = new int[] {7, 7, 7};
			this.setLayout(thisLayout);
			this.setPreferredSize(new java.awt.Dimension(740, 403));
			{
				meeting = new JPanel();
				GridBagLayout meetingLayout = new GridBagLayout();
				this.add(meeting, new GridBagConstraints(-1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
				this.add(getAll_users(), new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
				this.add(getSelected_users(), new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
				this.add(getEnd_time(), new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				meetingLayout.rowWeights = new double[] {0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1};
				meetingLayout.rowHeights = new int[] {7, 7, 7, 7, 7, 7, 7, 7};
				meetingLayout.columnWeights = new double[] {0.1, 0.1};
				meetingLayout.columnWidths = new int[] {7, 7};
				meeting.setLayout(meetingLayout);
				{
					agreement_toggle = new JToggleButton();
					meeting.add(agreement_toggle, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
					agreement_toggle.setText("Avtale");
					getMeeting_agreement().add(agreement_toggle);
				}
				{
					meeting_toggle = new JToggleButton();
					meeting.add(meeting_toggle, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
					meeting_toggle.setText("Møte");
					getMeeting_agreement().add(meeting_toggle);
					calendar = new JDateChooser();
					meeting.add(calendar, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
					meeting.add(getDate_label(), new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.VERTICAL, new Insets(0, 0, 0, 0), 0, 0));
					meeting.add(getStart_time_label(), new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.VERTICAL, new Insets(0, 0, 0, 0), 0, 0));
					meeting.add(getEnd_time_label(), new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.VERTICAL, new Insets(0, 0, 0, 0), 0, 0));
					meeting.add(getDescription_label(), new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.VERTICAL, new Insets(0, 0, 0, 0), 0, 0));
					meeting.add(getStart_time(), new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
					meeting.add(getEnd_time(), new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
					meeting.add(getDescription(), new GridBagConstraints(1, 5, 1, 2, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.VERTICAL, new Insets(0, 0, 0, 0), 0, 0));
					meeting.add(getSave_button(), new GridBagConstraints(0, 7, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 30, 5, 10), 0, 0));
					meeting.add(getRoom_label(), new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.VERTICAL, new Insets(0, 0, 0, 0), 0, 0));
					meeting.add(getRoom_chooser(), new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
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
		}
		return date_label;
	}
	
	private JLabel getStart_time_label() {
		if(start_time_label == null) {
			start_time_label = new JLabel();
			start_time_label.setText("Start: ");
		}
		return start_time_label;
	}
	
	private JLabel getEnd_time_label() {
		if(end_time_label == null) {
			end_time_label = new JLabel();
			end_time_label.setText("Slutt: ");
		}
		return end_time_label;
	}
	
	private JLabel getDescription_label() {
		if(description_label == null) {
			description_label = new JLabel();
			description_label.setText("Beskrivelse: ");
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
			BorderLayout all_usersLayout = new BorderLayout();
			all_users.setLayout(all_usersLayout);
			all_users.add(getAll_users_label(), BorderLayout.NORTH);
			all_users.add(getAll_users_list(), BorderLayout.CENTER);
		}
		return all_users;
	}
	
	private JPanel getSelected_users() {
		if(selected_users == null) {
			selected_users = new JPanel();
		}
		return selected_users;
	}
	
	private JLabel getAll_users_label() {
		if(all_users_label == null) {
			all_users_label = new JLabel();
			all_users_label.setText("Alle Brukere");
		}
		return all_users_label;
	}
	
	private JList getAll_users_list() {
		if(all_users_list == null) {
			ListModel all_users_listModel = 
				new DefaultComboBoxModel(
						new String[] { "Item One", "Item Two" });
			all_users_list = new JList();
			all_users_list.setModel(all_users_listModel);
		}
		return all_users_list;
	}

}
