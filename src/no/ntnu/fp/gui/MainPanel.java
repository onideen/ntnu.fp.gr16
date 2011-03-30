package no.ntnu.fp.gui;

import com.u2d.type.atom.TimeSpan;
import java.awt.BorderLayout;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;

import javax.swing.WindowConstants;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import no.ntnu.fp.gui.listeners.CalendarPanelActionListener;
import no.ntnu.fp.gui.listeners.IGeneralListener;
import no.ntnu.fp.model.Communication;
import no.ntnu.fp.gui.listeners.ILoginListener;
import no.ntnu.fp.model.Message;
import no.ntnu.fp.utils.TimedRunner;

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
public class MainPanel extends javax.swing.JPanel implements ILoginListener, IGeneralListener, CalendarPanelActionListener
{


    {
        //Set Look & Feel
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public static final int CALENDAR = 1;
    public static final int APPOINTMENT = 2;
    public static final int MESSAGES = 3;
    public static final int EMPLOYEES = 4;
    public static final int LOGIN = 5;
    private JPanel menu;
    private JPanel maincontainer;
    private JButton new_agreement_button;
    private JButton calendar_button;
    private JButton message_button;
    private JButton logout_button;
    private JButton employee_button;
    private LogInPanel loginPanel;
    private CalendarViewPanel calendarPanel;
    private EmployeesPanel employeesPanel;
    private CreateMeetingPanel meetingPanel;
    private static MainPanel mainForm = null;
    TimedRunner runner;

    /**
     * Auto-generated main method to display this
     * JPanel inside a new JFrame.
     */
    public static void main(String[] args)
    {
        JFrame frame = new JFrame();
        MainPanel mp = new MainPanel();
        frame.getContentPane().add(mp);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(1010, 710);
        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter()
        {

            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        });

        mainForm = mp;
    }

    public static void refreshGUI()
    {
        if (mainForm != null)
        {
            mainForm.updateUI();
        }
    }

    public static MainPanel getMainForm()
    {
        return mainForm;
    }

    public MainPanel()
    {
        super();

        loginPanel = new LogInPanel();
        loginPanel.setLoginListener(this);

        initGUI();

        changeMain(LOGIN);

        runner = new TimedRunner(1000 * 180, new Runnable()
        {

            public void run()
            {
                List<Message> msgs = null;
                if (Communication.isLoggedIn())
                {
                    try
                    {
                        msgs = Communication.getMessages(Communication.LoggedInUserEmail);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                        System.out.println(msgs.size() + "");
                    }
                }

                if (msgs == null)
                {
                    msgs = new ArrayList<Message>();
                }

                final int length = msgs.size();

                System.out.println("Checked for messages: " + length + " new.");

                SwingUtilities.invokeLater(new Runnable()
                {

                    public void run()
                    {
                        if (length == 0)
                        {
                            message_button.setText("Meldinger");
                        }
                        else
                        {
                            message_button.setText("Meldinger (" + length + ")");
                        }
                    }
                });
            }
        }).start();
    }

    private void initGUI()
    {
        try
        {
            BorderLayout thisLayout = new BorderLayout();
            this.setLayout(thisLayout);
            this.setPreferredSize(new java.awt.Dimension(737, 424));
            {
                menu = new JPanel();
                this.add(menu, BorderLayout.NORTH);
                GridBagLayout menuLayout = new GridBagLayout();
                menuLayout.columnWidths = new int[]
                        {
                            7, 7, 7, 7, 7, 7, 7, 7, 7, 7
                        };
                menuLayout.rowHeights = new int[]
                        {
                            7
                        };
                menuLayout.columnWeights = new double[]
                        {
                            0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1
                        };
                menuLayout.rowWeights = new double[]
                        {
                            0.1
                        };
                menu.setLayout(menuLayout);
                menu.setVisible(false);
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
                    new_agreement_button.addActionListener(new ActionListener()
                    {

                        @Override
                        public void actionPerformed(ActionEvent e)
                        {
                            changeMain(APPOINTMENT);
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
                    calendar_button.addActionListener(new ActionListener()
                    {

                        @Override
                        public void actionPerformed(ActionEvent e)
                        {
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
                    message_button.addActionListener(new ActionListener()
                    {

                        @Override
                        public void actionPerformed(ActionEvent e)
                        {
                            changeMain(MESSAGES);
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
                    employee_button.addActionListener(new ActionListener()
                    {

                        public void actionPerformed(ActionEvent e)
                        {
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
                    logout_button.addActionListener(new ActionListener()
                    {

                        public void actionPerformed(ActionEvent e)
                        {
                            Communication.logout();
                            changeMain(LOGIN);
                        }
                    });
                }
            }
            {
                maincontainer = new JPanel();
                BorderLayout maincontainerLayout = new BorderLayout();
                maincontainer.setLayout(maincontainerLayout);
                this.add(maincontainer, BorderLayout.CENTER);
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void loginAction(boolean isLoggedIn)
    {
        changeMain(CALENDAR);
        runner.invoke();
    }
    public static void refresCalendar()
    {
        getMainForm().calendarPanel.getCalendarPanel().setEvents(Communication.getEvents(Communication.LoggedInUserEmail));
    }

    private int activePanel;

    public void changeMain(int panel)
    {
        maincontainer.removeAll();

        if (!Communication.isLoggedIn())
        {
            panel = LOGIN;
        }

        menu.setVisible(panel != LOGIN);

        activePanel = panel;

        switch (panel)
        {
            case CALENDAR:
                if (calendarPanel == null)
                {
                    calendarPanel = new CalendarViewPanel();
                    calendarPanel.getCalendarPanel().addCalendarPanelActionListener(this);
                }
                maincontainer.add(calendarPanel, BorderLayout.CENTER);
                break;
            case APPOINTMENT:
                if (meetingPanel == null)
                {
                    System.out.println("LAGA NY MEETING PANEL MED TOM ******************************************************");
                    meetingPanel = new CreateMeetingPanel();
                }

                maincontainer.add(meetingPanel, BorderLayout.CENTER);
                break;
            case MESSAGES:
                MessageListPanel message = new MessageListPanel();
                message.setGeneralListener(this);
                maincontainer.add(message, BorderLayout.CENTER);
                message.readMessages(Communication.LoggedInUserEmail);
                //TODO legge til pålogget bruker over
                break;
            case EMPLOYEES:
                if (employeesPanel == null)
                {
                    employeesPanel = new EmployeesPanel();
                }
                maincontainer.add(employeesPanel, BorderLayout.CENTER);
                break;
            case LOGIN:

                if (loginPanel == null)
                {
                    loginPanel = new LogInPanel();
                    loginPanel.setLoginListener(this);
                }

                maincontainer.add(loginPanel, BorderLayout.CENTER);
                break;
        }
        updateUI();
    }

    public void action()
    {
        runner.invoke();
    }

    public void eventClicked(CalendarPanel panel, no.ntnu.fp.model.Event event)
    {
        meetingPanel = new CreateMeetingPanel(event);

        changeMain(APPOINTMENT);
    }

    public void timeClicked(CalendarPanel panel, TimeSpan timeSpan)
    {
        if (activePanel != CALENDAR)
        {
            return;
        }

        meetingPanel = new CreateMeetingPanel(timeSpan.startCal(), timeSpan.startCal(), timeSpan.endCal());

        changeMain(APPOINTMENT);
    }
}
