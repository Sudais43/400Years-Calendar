import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Calendar400YearsGUI {
    // Array to store days in each month (non-leap year)
    private static final int[] DAYS_IN_MONTH = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private static final String[] MONTH_NAMES = {
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
    };

    // Check if a year is a leap year
    public static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    // Zeller's Congruence to find day of the week (0 = Saturday, 1 = Sunday, ..., 6 = Friday)
    public static int getDayOfWeek(int year, int month, int day) {
        if (month < 3) {
            month += 12;
            year--;
        }
        int k = year % 100;
        int j = year / 100;
        int h = (day + ((13 * (month + 1)) / 5) + k + (k / 4) + (j / 4) - (2 * j)) % 7;
        return (h < 0) ? h + 7 : h;
    }

    // Get number of days in a month, accounting for leap years
    public static int getDaysInMonth(int month, int year) {
        if (month == 2 && isLeapYear(year)) {
            return 29;
        }
        return DAYS_IN_MONTH[month - 1];
    }

    // Generate text for a monthly calendar
    public static String getMonthCalendarText(int year, int month) {
        StringBuilder calendar = new StringBuilder();
        calendar.append(String.format("%s %d\n", MONTH_NAMES[month - 1], year));
        calendar.append("Su Mo Tu We Th Fr Sa\n");

        // Get first day of the month
        int firstDay = getDayOfWeek(year, month, 1);
        firstDay = (firstDay + 6) % 7; // Adjust to Sunday = 0, Monday = 1, ..., Saturday = 6

        // Add leading spaces
        for (int i = 0; i < firstDay; i++) {
            calendar.append("   ");
        }

        // Add days of the month
        int daysInMonth = getDaysInMonth(month, year);
        for (int day = 1; day <= daysInMonth; day++) {
            calendar.append(String.format("%2d ", day));
            if ((day + firstDay) % 7 == 0 || day == daysInMonth) {
                calendar.append("\n");
            }
        }
        return calendar.toString();
    }

    // Create the GUI
    public static void createAndShowGUI() {
        // Create the main frame
        JFrame frame = new JFrame("400-Year Calendar");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        // Create year selection panel
        JPanel yearPanel = new JPanel();
        JLabel yearLabel = new JLabel("Select Year: ");
        JComboBox<Integer> yearComboBox = new JComboBox<>();
        for (int year = 2000; year <= 2399; year++) {
            yearComboBox.addItem(year);
        }
        yearPanel.add(yearLabel);
        yearPanel.add(yearComboBox);
        frame.add(yearPanel, BorderLayout.NORTH);

        // Create panel for calendar display
        JPanel calendarPanel = new JPanel(new GridLayout(3, 4, 10, 10));
        JTextArea[] monthAreas = new JTextArea[12];
        for (int i = 0; i < 12; i++) {
            monthAreas[i] = new JTextArea(6, 20);
            monthAreas[i].setEditable(false);
            monthAreas[i].setFont(new Font("Monospaced", Font.PLAIN, 12));
            calendarPanel.add(monthAreas[i]);
        }
        frame.add(calendarPanel, BorderLayout.CENTER);

        // Update calendar display for the initial year
        int initialYear = 2025;
        yearComboBox.setSelectedItem(initialYear);
        for (int month = 1; month <= 12; month++) {
            monthAreas[month - 1].setText(getMonthCalendarText(initialYear, month));
        }

        // Add listener for year selection
        yearComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedYear = (Integer) yearComboBox.getSelectedItem();
                for (int month = 1; month <= 12; month++) {
                    monthAreas[month - 1].setText(getMonthCalendarText(selectedYear, month));
                }
            }
        });

        // Display the frame
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        // Schedule the GUI creation on the Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }
}