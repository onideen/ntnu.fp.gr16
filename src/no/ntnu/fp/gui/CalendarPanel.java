package no.ntnu.fp.gui;
import com.u2d.calendar.CellResChoice;
import com.u2d.calendar.DateTimeBounds;
import com.u2d.type.atom.TimeEO;
import com.u2d.type.atom.TimeInterval;
import com.u2d.type.atom.TimeSpan;
import java.awt.BorderLayout;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.WindowConstants;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

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

    private static final String[] WEEKS = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    private static DateFormat COLHEADER_FORMATTER= new SimpleDateFormat("EEE, MMM dd");

    private DateTimeBounds bounds;
    private final TimeSpan daySpan = new TimeSpan();
    private final TimeSpan weekSpan = new TimeSpan();
    private WeekTableModel model;

    private static int COLUMN_WIDTH = 100;
    private static int FIRST_COLUMN_WIDTH = 65;
    private static int WEEKEND_COLUMN_WIDTH = 85;


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
    private JTable table;
	
	public CalendarPanel() {
		super();
        bounds = new DateTimeBounds();
		initGUI();
	}

    private void initGUI()
    {
        adjustSpan();
        buildTable();
        add(table);
    }
	
	private void adjustSpan() {
        Calendar startOfWeek = Calendar.getInstance();
        Date time = bounds.position().dateValue();
        startOfWeek.setTime(time);

        Calendar weekStartTimeCalendar = bounds.weekStartTime().calendarValue();

        startOfWeek.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        if(startOfWeek.getTime().after(time))
            startOfWeek.set(Calendar.DAY_OF_WEEK, -7);

        startOfWeek.set(Calendar.HOUR_OF_DAY, weekStartTimeCalendar.get(Calendar.HOUR_OF_DAY));
        startOfWeek.set(Calendar.MINUTE, weekStartTimeCalendar.get(Calendar.MINUTE));
        startOfWeek.set(Calendar.SECOND, weekStartTimeCalendar.get(Calendar.SECOND));

        System.out.println("beginning of week is "+startOfWeek.getTime());

        TimeEO startHr = new TimeEO(startOfWeek.getTimeInMillis());

        Calendar dayStartTimeCalendar = bounds.dayStartTime().calendarValue();
        startHr.set(Calendar.HOUR_OF_DAY, dayStartTimeCalendar.get(Calendar.HOUR_OF_DAY));

        weekSpan.setValue(new TimeSpan(startOfWeek.getTime(), bounds.weekInterval()));
        daySpan.setValue(new TimeSpan(startHr.dateValue(), bounds.dayInterval()));
	}

    protected void buildTable() {
        model = new WeekTableModel();
        table = new JTable();

        table.setAutoCreateColumnsFromModel(false);
        table.setModel(model);

        TableColumn column = new TableColumn(0, FIRST_COLUMN_WIDTH,
                               new RowHeaderCellRenderer(), null);

        column.setMinWidth(FIRST_COLUMN_WIDTH);
        column.setMaxWidth(FIRST_COLUMN_WIDTH);
        column.setIdentifier("times");
        table.addColumn(column);

        DefaultTableCellRenderer renderer = null;
        for(int i = 1; i < model.getColumnCount(); i++) {
            int width = (i>5) ?
               WEEKEND_COLUMN_WIDTH : COLUMN_WIDTH;

            renderer = new CalendarCellRenderer();
            column = new TableColumn(i, width, renderer, null);
            column.setIdentifier(""+i);
            table.addColumn(column);
        }

        table.setGridColor(Color.lightGray);
        table.setShowGrid(true);
        table.setRowSelectionAllowed(false);
        table.setColumnSelectionAllowed(true);
        table.getTableHeader().setReorderingAllowed(false);
        table.setSelectionBackground(Color.cyan);
    }

    protected CellResChoice cellRes() { return CellResChoice.ONE_HOUR; }

	private class WeekTableModel extends AbstractTableModel {
        private int numCellsInDay;
        private TimeEO[] times;
        public WeekTableModel() {
            updateCellRes();
        }

        public void updateCellRes() {
            numCellsInDay = daySpan.numIntervals(cellRes().timeInterval());
            times = new TimeEO[numCellsInDay];

            int i=0;
            for(Iterator itr = daySpan.iterator(cellRes().timeInterval());
                itr.hasNext();) {
                times[i++] = (TimeEO)itr.next();
            }
            fireTableStructureChanged();
        }

        public int getRowCount() { return numCellsInDay; }
        public int getColumnCount() { return WEEKS.length + 1; }

        public String getColumnName(int column)
        {
            if (column == 0)
                return " ";
            TimeSpan span = weekSpan.add(Calendar.DATE, column-1);
            return COLHEADER_FORMATTER.format(span.startDate());
        }

        public boolean isCellEditable(int nRow, int nCol) { return false; }

        public Object getValueAt(int nRow, int nCol)
        {
            if (nCol > 0)
                return null; //TODO: Make better!
            return times[nRow].toString();
        }

        public String toString()    { return "Week View"; }
    }

    private class RowHeaderCellRenderer implements TableCellRenderer {
        JLabel cell;
        public RowHeaderCellRenderer() {
            cell = new JLabel();
            cell.setOpaque(true);
            Color color = UIManager.getColor("TableHeader.background");
            cell.setBackground(color);

            Font font = UIManager.getFont("TableHeader.font");
            cell.setFont(font.deriveFont(10.0f));
            cell.setVerticalAlignment(SwingConstants.TOP);
            cell.setHorizontalAlignment(SwingConstants.TRAILING);
            cell.setBorder(BorderFactory.createEmptyBorder(0,2,2,2));
        }

        public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
            cell.setText(value.toString());
            return cell;
        }
    }

    private static class CalendarCellRenderer extends DefaultTableCellRenderer {

        public CalendarCellRenderer() {
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            this.setBackground(value == null ? table.getBackground() : Color.yellow);
            return this;
        }
    }
}
