package no.ntnu.fp.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

import javax.swing.WindowConstants;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import no.ntnu.fp.model.Communication;
import no.ntnu.fp.gui.listeners.LoginListener;
import no.ntnu.fp.utils.LoadCallback;
import no.ntnu.fp.utils.Loader;
import no.ntnu.fp.utils.ServiceLoaders;

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
public class LogInPanel extends javax.swing.JPanel
{

    private JPanel logInPanel;
    private JTextField usernameTextField;
    private JPasswordField passwordField;
    private JButton logInButton;
    private JLabel passwordLabel;
    private JLabel usernameLabel;

    /**
     * Auto-generated main method to display this
     * JPanel inside a new JFrame.
     */
    public static void main(String[] args)
    {
        JFrame frame = new JFrame();
        frame.getContentPane().add(new LogInPanel());
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public LogInPanel()
    {
        super();
        initGUI();
    }

    private void initGUI()
    {
        try
        {
            this.setPreferredSize(new java.awt.Dimension(809, 455));
            this.setLayout(null);
            {
                logInPanel = new JPanel();
                this.add(logInPanel);
                GridBagLayout logInPanelLayout = new GridBagLayout();
                logInPanelLayout.rowWeights = new double[]
                        {
                            0.1, 0.1, 0.1
                        };
                logInPanelLayout.rowHeights = new int[]
                        {
                            7, 7, 7
                        };
                logInPanelLayout.columnWeights = new double[]
                        {
                            0.1, 0.1
                        };
                logInPanelLayout.columnWidths = new int[]
                        {
                            7, 7
                        };
                logInPanel.setLayout(logInPanelLayout);
                logInPanel.setBounds(155, 141, 390, 141);
                {
                    usernameTextField = new JTextField();
                    logInPanel.add(usernameTextField, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
                }
                {
                    usernameLabel = new JLabel();
                    logInPanel.add(usernameLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 10), 0, 0));
                    usernameLabel.setText("Brukernavn");
                }
                {
                    passwordField = new JPasswordField();
                    passwordField.addActionListener(new ActionListener()
                    {

                        @Override
                        public void actionPerformed(ActionEvent e)
                        {
                            LogIn();
                        }
                    });
                    logInPanel.add(passwordField, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
                }
                {
                    passwordLabel = new JLabel();
                    logInPanel.add(passwordLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 10), 0, 0));
                    passwordLabel.setText("Passord");
                }
                {
                    logInButton = new JButton();
                    logInPanel.add(logInButton, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
                    logInButton.setText("Logg inn");
                    logInButton.addActionListener(new ActionListener()
                    {

                        @Override
                        public void actionPerformed(ActionEvent e)
                        {
                            LogIn();
                        }
                    });
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    LoginListener Listener = null;

    public void setLoginListener(LoginListener l)
    {
        Listener = l;
    }

    private void LogIn()
    {
        Loader.loadAndRun(ServiceLoaders.login(usernameTextField.getText(), passwordField.getText()),
                          new LoadCallback()
        {
            public void run(Object object)
            {
                Boolean success = (Boolean) object;
                if (success)
                {
                    if (Listener != null)
                    {
                        Listener.loginAction(true);
                        return;
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Feil brukernavn og/eller passord.");
                }
            }
        });
    }
}
