package no.ntnu.fp.gui;
import java.awt.BorderLayout;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;

import javax.swing.WindowConstants;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

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

	private JPanel menu;
	private JPanel maincontainer;
	private JButton new_agreement_button;
	private JButton new_meeting_button;
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
		frame.pack();
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
					menu.add(new_agreement_button, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
					new_agreement_button.setText("Ny Avtale");
					new_agreement_button.setIcon(new ImageIcon(getClass().getResource("/gumby.gif")));					
					new_agreement_button.setHorizontalTextPosition(SwingConstants.CENTER);
					new_agreement_button.setVerticalTextPosition(SwingConstants.BOTTOM);
				}
				{
					new_meeting_button = new JButton();
					menu.add(new_meeting_button, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
					new_meeting_button.setIcon(new ImageIcon(getClass().getResource("/gumby.gif")));
					new_meeting_button.setText("Nytt møte");
					new_meeting_button.setHorizontalTextPosition(SwingConstants.CENTER);
					new_meeting_button.setVerticalTextPosition(SwingConstants.BOTTOM);
				}
				{
					calendar_button = new JButton();
					menu.add(calendar_button, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
					calendar_button.setText("Kalender");
					calendar_button.setIcon(new ImageIcon(getClass().getResource("/gumby.gif")));
					calendar_button.setHorizontalTextPosition(SwingConstants.CENTER);
					calendar_button.setVerticalTextPosition(SwingConstants.BOTTOM);
				}
				{
					message_button = new JButton();
					menu.add(message_button, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
					message_button.setText("Meldinger");
					message_button.setIcon(new ImageIcon(getClass().getResource("/gumby.gif")));
					message_button.setHorizontalTextPosition(SwingConstants.CENTER);
					message_button.setVerticalTextPosition(SwingConstants.BOTTOM);
				}
				{
					employee_button = new JButton();
					menu.add(employee_button, new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
					employee_button.setText("Ansatte");
					employee_button.setIcon(new ImageIcon(getClass().getResource("/gumby.gif")));
					employee_button.setHorizontalTextPosition(SwingConstants.CENTER);
					employee_button.setVerticalTextPosition(SwingConstants.BOTTOM);
				}
				{
					logout_button = new JButton();
					menu.add(logout_button, new GridBagConstraints(9, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
					logout_button.setText("Logg ut");
					logout_button.setIcon(new ImageIcon(getClass().getResource("/gumby.gif")));
					logout_button.setHorizontalTextPosition(SwingConstants.CENTER);
					logout_button.setVerticalTextPosition(SwingConstants.BOTTOM);
				}
			}
			{
				maincontainer = new JPanel();
				this.add(maincontainer, BorderLayout.CENTER);
			}
			this.addAncestorListener(new AncestorListener() {
				public void ancestorRemoved(AncestorEvent evt) {
					System.out.println("this.ancestorRemoved, event="+evt);
					//TODO add your code for this.ancestorRemoved
				}
				public void ancestorAdded(AncestorEvent evt) {
					System.out.println("this.ancestorAdded, event="+evt);
					//TODO add your code for this.ancestorAdded
				}
				public void ancestorMoved(AncestorEvent evt) {
					System.out.println("this.ancestorMoved, event="+evt);
					//TODO add your code for this.ancestorMoved
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
