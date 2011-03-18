package no.ntnu.fp.gui;
import java.awt.BorderLayout;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
	
import javax.swing.JButton;

import javax.swing.WindowConstants;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.sun.org.apache.xml.internal.security.encryption.AgreementMethod;

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
public class MainPanel extends javax.swing.JPanel {
	{
		//Set Look & Feel
		try {
			javax.swing.UIManager.setLookAndFeel("com.jgoodies.looks.plastic.PlasticXPLookAndFeel");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	private static final int CALENDAR = 1;
	private static final int AGREEMENT = 2;
	private static final int MESSAGES = 3;
	private static final int EMPLOYEES = 4;
	private JPanel menu;
	private JPanel maincontainer;
	private JButton new_agreement_button;
	private JButton calendar_button;
	private JButton message_button;
	private JButton logout_button;
	private JButton employee_button;
	

	/**
	* Auto-generated main method to display this 
	* JPanel inside a new JFrame.
	*/
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.getContentPane().add(new MainPanel());
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.setSize(1000, 700);
		frame.setVisible(true);
	}
	
	public MainPanel() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		try {
			BorderLayout thisLayout = new BorderLayout();
			this.setLayout(thisLayout);
			this.setPreferredSize(new java.awt.Dimension(737, 424));
			{
				menu = new JPanel();
				this.add(menu, BorderLayout.NORTH);
				GridBagLayout menuLayout = new GridBagLayout();
				menuLayout.columnWidths = new int[] {7, 7, 7, 7, 7, 7, 7, 7, 7, 7};
				menuLayout.rowHeights = new int[] {7};
				menuLayout.columnWeights = new double[] {0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1};
				menuLayout.rowWeights = new double[] {0.1};
				menu.setLayout(menuLayout);
				menu.setPreferredSize(new java.awt.Dimension(737, 78));
				{
					new_agreement_button = new JButton();
					menu.add(new_agreement_button, new GridBagConstraints(1, -1, 1, 1, 0.0, 0.0, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
					new_agreement_button.setText("Ny Avtale");
					new_agreement_button.setIcon(new ImageIcon(getClass().getResource("/avtale.png")));					
					new_agreement_button.setHorizontalTextPosition(SwingConstants.CENTER);
					new_agreement_button.setVerticalTextPosition(SwingConstants.BOTTOM);
					new_agreement_button.setSize(50, 50);
					new_agreement_button.setPreferredSize(new java.awt.Dimension(50, 50));
					new_agreement_button.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							changeMain(AGREEMENT);
						}
					});
				}
				{
					calendar_button = new JButton();
					menu.add(calendar_button, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
					calendar_button.setText("Kalender");
					calendar_button.setIcon(new ImageIcon(getClass().getResource("/calendar.png")));
					calendar_button.setHorizontalTextPosition(SwingConstants.CENTER);
					calendar_button.setVerticalTextPosition(SwingConstants.BOTTOM);
					calendar_button.setSize(50, 50);
					calendar_button.setPreferredSize(new java.awt.Dimension(50, 50));
					calendar_button.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							changeMain(CALENDAR);
						}
					});
				}
				{
					message_button = new JButton();
					menu.add(message_button, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
					message_button.setText("Meldinger");
					message_button.setIcon(new ImageIcon(getClass().getResource("/Mail.png")));
					message_button.setHorizontalTextPosition(SwingConstants.CENTER);
					message_button.setVerticalTextPosition(SwingConstants.BOTTOM);
					message_button.setSize(50, 50);
					message_button.setPreferredSize(new java.awt.Dimension(50, 50));
					message_button.addActionListener(new ActionListener() {	
						@Override
						public void actionPerformed(ActionEvent e) {
							changeMain(EMPLOYEES);
						}
					});
				}
				{
					employee_button = new JButton();
					menu.add(employee_button, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
					employee_button.setText("Ansatte");
					employee_button.setIcon(new ImageIcon(getClass().getResource("/ansatte.png")));
					employee_button.setHorizontalTextPosition(SwingConstants.CENTER);
					employee_button.setVerticalTextPosition(SwingConstants.BOTTOM);
					employee_button.setSize(78, 78);
					employee_button.setPreferredSize(new java.awt.Dimension(50, 50));
					employee_button.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							changeMain(EMPLOYEES);
						}
					});
				}
				{
					logout_button = new JButton();
					menu.add(logout_button, new GridBagConstraints(9, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
					logout_button.setText("Logg ut");
					logout_button.setIcon(new ImageIcon(getClass().getResource("/logout.png")));
					logout_button.setHorizontalTextPosition(SwingConstants.CENTER);
					logout_button.setVerticalTextPosition(SwingConstants.BOTTOM);
					logout_button.setPreferredSize(new java.awt.Dimension(50, 50));
					logout_button.setSize(50, 50);
				}
			}
			{
				maincontainer = new JPanel();
				BorderLayout maincontainerLayout = new BorderLayout();
				maincontainer.setLayout(maincontainerLayout);
				this.add(maincontainer, BorderLayout.CENTER);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void changeMain(int panel) {
		maincontainer.removeAll();
		switch (panel){
		case CALENDAR:
			maincontainer.add(new CalendarPanel(), BorderLayout.CENTER);
			break;
		case AGREEMENT:
			maincontainer.add(new CreateMeetingPanel(),BorderLayout.CENTER);
			break;
		case MESSAGES:
			MessageListPanel message = new MessageListPanel();
			maincontainer.add(message,BorderLayout.CENTER);
			message.readMessages("mothersday@monday.com");
			//TODO legge til pålogget bruker over
			break;
		case EMPLOYEES:
			maincontainer.add(new EmployeesPanel(),BorderLayout.CENTER);
		}
		updateUI();
		
	}
	
	
}
