/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.ntnu.fp.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Calendar;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import no.ntnu.fp.model.Event;

/**
 *
 * @author alxandr
 */
public class CalendarDoubleView extends BaseCalendarView implements ActionListener, AdjustmentListener
{

    public static void main(String[] args)
    {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        CalendarDoubleView calp = new CalendarDoubleView();
        frame.setContentPane(calp);
        frame.setVisible(true);
        frame.pack();
    }
    private CalendarPanel calendarPanel;
    private CalendarPanel calendarPanel2;
    private JLabel yearLabel;
    private JPanel paginationPanel;
    private JButton prevWeekButton;
    private JButton nextWeekButton;
    private JLabel weekLabel;
    private JLabel yourLabel;
    private JLabel otherLabel;

    public CalendarDoubleView()
    {
        super();

        setupGUI();
        setupListeners();
        SwingUtilities.invokeLater(new Runnable()
        {

            public void run()
            {
                calendarPanel.getScrollPane().getVerticalScrollBar().setValue(500);
                calendarPanel2.getScrollPane().getVerticalScrollBar().setValue(500);
            }
        });
    }

    public CalendarDoubleView(List<Event> events, List<Event> otherEvents, String otherName)
    {
        this();

        calendarPanel.setEvents(events);
        calendarPanel2.setEvents(otherEvents);
        otherLabel.setText("Kalenderen til " + otherName + ":");
    }

    public CalendarPanel[] getCalendarPanels() {
        return new CalendarPanel[] {
            calendarPanel,
            calendarPanel2
        };
    }

    public void setOther(List<Event> otherEvents, String otherName)
    {
        calendarPanel2.setEvents(otherEvents);
        otherLabel.setText("Kalenderen til " + otherName + ":");
    }

    private void setupListeners()
    {
        calendarPanel.getScrollPane().getVerticalScrollBar().addAdjustmentListener(this);
        calendarPanel2.getScrollPane().getVerticalScrollBar().addAdjustmentListener(this);
    }

    public CalendarPanel getCalendarPanel()
    {
        return calendarPanel;
    }

    private void setupGUI()
    {
        calendarPanel = new CalendarPanel(this, 172);
        GridBagConstraints s = new GridBagConstraints();
        s.anchor = GridBagConstraints.NORTHWEST;
        s.fill = GridBagConstraints.BOTH;
        s.gridy = 1;
        calendarPanel.setPreferredSize(new Dimension(985, 205));
        add(calendarPanel, s);

        yourLabel = new JLabel("Din kalender:");
        s.gridy = 0;
        add(yourLabel, s);

        otherLabel = new JLabel("Den andre kalenderen:");
        s.gridy = 2;
        add(otherLabel, s);

        calendarPanel2 = new CalendarPanel(this, 172);
        s.gridy = 3;
        calendarPanel2.setPreferredSize(new Dimension(985, 205));
        add(calendarPanel2, s);

        s.gridy = 5;
        s.anchor = GridBagConstraints.CENTER;
        s.fill = GridBagConstraints.NONE;
        yearLabel = new JLabel("" + calendarPanel.getWeek().get(Calendar.YEAR));
        add(yearLabel, s);

        paginationPanel = new JPanel();
        s.gridy = 4;
        //s.anchor = GridBagConstraints.NORTHWEST;
        //s.fill = GridBagConstraints.BOTH;
        paginationPanel.setPreferredSize(new Dimension(200, 40));
        add(paginationPanel, s);

        prevWeekButton = new JButton("<");
        paginationPanel.add(prevWeekButton);
        prevWeekButton.addActionListener(this);

        weekLabel = new JLabel("" + calendarPanel.getWeek().get(Calendar.WEEK_OF_YEAR));
        paginationPanel.add(weekLabel);

        nextWeekButton = new JButton(">");
        paginationPanel.add(nextWeekButton);
        nextWeekButton.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e)
    {
        Object src = e.getSource();
        if (src == nextWeekButton)
        {
            Calendar cal = calendarPanel.getWeek();
            cal.add(Calendar.WEEK_OF_YEAR, 1);
            calendarPanel.setWeek(cal);
            calendarPanel2.setWeek(cal);
            int week = cal.get(Calendar.WEEK_OF_YEAR);
            String wk = "" + week;
            if (wk.length() == 1)
            {
                wk = "0" + wk;
            }
            weekLabel.setText("" + wk);
            yearLabel.setText("" + cal.get(Calendar.YEAR));
        }
        else if (src == prevWeekButton)
        {
            Calendar cal = calendarPanel.getWeek();
            cal.add(Calendar.WEEK_OF_YEAR, -1);
            calendarPanel.setWeek(cal);
            calendarPanel2.setWeek(cal);
            int week = cal.get(Calendar.WEEK_OF_YEAR);
            String wk = "" + week;
            if (wk.length() == 1)
            {
                wk = "0" + wk;
            }
            weekLabel.setText("" + wk);
            yearLabel.setText("" + cal.get(Calendar.YEAR));
        }
    }

    public void adjustmentValueChanged(AdjustmentEvent e)
    {
        calendarPanel.getScrollPane().getVerticalScrollBar().setValue(e.getValue());
        calendarPanel2.getScrollPane().getVerticalScrollBar().setValue(e.getValue());
    }
}
