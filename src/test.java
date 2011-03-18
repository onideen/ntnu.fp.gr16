import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.SpringLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;


public class test extends JFrame {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					test frame = new test();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public test() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 322, 161);
		SpringLayout springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);
		
		JButton button = new JButton("New button");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		springLayout.putConstraint(SpringLayout.NORTH, button, 10, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, button, 10, SpringLayout.WEST, getContentPane());
		getContentPane().add(button);
		
		JButton button_1 = new JButton("New button");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		springLayout.putConstraint(SpringLayout.NORTH, button_1, 18, SpringLayout.SOUTH, button);
		springLayout.putConstraint(SpringLayout.EAST, button_1, 0, SpringLayout.EAST, button);
		getContentPane().add(button_1);
		
		JRadioButton radioButton = new JRadioButton("New radio button");
		springLayout.putConstraint(SpringLayout.NORTH, radioButton, 0, SpringLayout.NORTH, button);
		springLayout.putConstraint(SpringLayout.WEST, radioButton, 40, SpringLayout.EAST, button);
		getContentPane().add(radioButton);
		
		JRadioButton radioButton_1 = new JRadioButton("New radio button");
		springLayout.putConstraint(SpringLayout.NORTH, radioButton_1, -29, SpringLayout.NORTH, button_1);
		springLayout.putConstraint(SpringLayout.WEST, radioButton_1, 89, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, radioButton_1, -6, SpringLayout.NORTH, button_1);
		springLayout.putConstraint(SpringLayout.EAST, radioButton_1, 186, SpringLayout.WEST, getContentPane());
		getContentPane().add(radioButton_1);
		
		JRadioButton radioButton_2 = new JRadioButton("New radio button");
		springLayout.putConstraint(SpringLayout.NORTH, radioButton_2, 28, SpringLayout.SOUTH, radioButton);
		springLayout.putConstraint(SpringLayout.EAST, radioButton_2, 0, SpringLayout.EAST, radioButton);
		getContentPane().add(radioButton_2);
	}
}
