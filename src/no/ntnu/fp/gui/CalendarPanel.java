package no.ntnu.fp.gui;
import java.awt.BorderLayout;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.WindowConstants;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

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
public class CalendarPanel extends BaseCalendarView {
	private JPanel Calendar;
	private JLabel day2_label;
	private JLabel day3_label;
	private JLabel day5_label;
	private JLabel day7_label;
	private JLabel day6_label;
	private JLabel day4_label;
	private JLabel day1_label;
	private JLabel time_label;
	private JLabel time1_label;
	private JLabel time2_label;
	private JLabel time3_label;
	private JLabel time4_label;
	private JLabel time5_label;
	private JLabel time6_label;
	private JLabel time7_label;
	private JLabel time8_label;
	private JLabel time9_label;
	private JLabel time10_label;
	private JLabel time11_label;
	
	private static int BORDER = 5;
	
	private JPanel[][] panels;
	
	/**
	* Auto-generated main method to display this 
	* JPanel inside a new JFrame.
	*/
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.getContentPane().add(new CalendarPanel());
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	
	public CalendarPanel() {
		super();
		initGUI();
	}
	
	private void initGUI() {

		/*try {
			BorderLayout thisLayout = new BorderLayout();
			this.setLayout(thisLayout);
			this.setPreferredSize(new java.awt.Dimension(809, 455));
			{
				Calendar = new JPanel();
				GridBagLayout CalendarLayout = new GridBagLayout();
				this.add(Calendar, BorderLayout.CENTER);
				Calendar.setPreferredSize(new java.awt.Dimension(809, 455));
				CalendarLayout.rowWeights = new double[] {0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1};
				CalendarLayout.rowHeights = new int[] {7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7};
				CalendarLayout.columnWeights = new double[] {0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1};
				CalendarLayout.columnWidths = new int[] {7, 7, 7, 7, 7, 7, 7, 7};
				Calendar.setBackground(Color.BLACK);
				Calendar.setLayout(CalendarLayout);
				
				panels = new JPanel[8][12];
				
				for (int i = 0; i < panels.length; i++){
					for (int j = 0; j < panels[0].length; j++){
						panels[i][j] = new JPanel();
						Calendar.add(panels[i][j], new GridBagConstraints(i, j, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0));
					}
				}
				
				Calendar.setFocusTraversalPolicyProvider(true);
				{
					day1_label = new JLabel();
					panels[1][0].add(day1_label);
					day1_label.setText("Mandag");
				
					day2_label = new JLabel();
					panels[2][0].add(day2_label);
					day2_label.setText("Tirsdag");
				
					day3_label = new JLabel();
					panels[3][0].add(day3_label);
					day3_label.setText("Onsdag");
				
					day4_label = new JLabel();
					panels[4][0].add(day4_label);
					day4_label.setText("Torsdag");
				
				
					day5_label = new JLabel();
					panels[5][0].add(day5_label);
					day5_label.setText("Fredag");
				
					day6_label = new JLabel();
					panels[6][0].add(day6_label);
					day6_label.setText("L�rdag");
				
					day7_label = new JLabel();
					panels[7][0].add(day7_label);
					day7_label.setText("S�ndag");
				
					time_label = new JLabel();
					panels[0][0].add(time_label);
					time_label.setText("Tid");
					
					time1_label = new JLabel();
					panels[0][1].add(time1_label);
					time1_label.setText("08:00");
					
					time2_label = new JLabel();
					panels[0][2].add(time2_label);
					time2_label.setText("09:00");
					
					time3_label = new JLabel();
					panels[0][3].add(time3_label);
					time3_label.setText("10:00");
					
					time4_label = new JLabel();
					panels[0][4].add(time4_label);
					time4_label.setText("11:00");
					
					time5_label = new JLabel();
					panels[0][5].add(time5_label);
					time5_label.setText("12:00");
					
					time6_label = new JLabel();
					panels[0][6].add(time6_label);
					time6_label.setText("13:00");
					
					time7_label = new JLabel();
					panels[0][7].add(time7_label);
					time7_label.setText("14:00");
					
					time8_label = new JLabel();
					panels[0][8].add(time8_label);
					time8_label.setText("15:00");
					
					time9_label = new JLabel();
					panels[0][9].add(time9_label);
					time9_label.setText("16:00");
					
					time10_label = new JLabel();
					panels[0][10].add(time10_label);
					time10_label.setText("17:00");
					
					time11_label = new JLabel();
					panels[0][11].add(time11_label);
					time11_label.setText("18:00");
			}
			}} catch (Exception e) {
			e.printStackTrace();
		}*/
        
	}


	
}
