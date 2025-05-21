import java.util.*;

public class Calendar400Years {
    // Array to store days in each month (non-leap year)
    private static final int[] DAYS_IN_MONTH = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private static final String[] MONTH_NAMES = {
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
    };
    private static final String[] DAY_NAMES = {
            "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"
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
        return (h < 0) ? h + 7 : h; // Ensure non-negative result
    }

    // Get number of days in a month, accounting for leap years
    public static int getDaysInMonth(int month, int year) {
        if (month == 2 && isLeapYear(year)) {
            return 29;
        }
        return DAYS_IN_MONTH[month - 1];
    }

    // Print a monthly calendar
    public static void printMonthCalendar(int year, int month) {
        System.out.printf("\n%s %d\n", MONTH_NAMES[month - 1], year);
        System.out.println("Su Mo Tu We Th Fr Sa");

        // Get first day of the month
        int firstDay = getDayOfWeek(year, month, 1);
        // Adjust firstDay to match calendar output (0 = Sunday, 1 = Monday, ..., 6 = Saturday)
        firstDay = (firstDay + 6) % 7;

        // Print leading spaces
        for (int i = 0; i < firstDay; i++) {
            System.out.print("   ");
        }

        // Print days of the month
        int daysInMonth = getDaysInMonth(month, year);
        for (int day = 1; day <= daysInMonth; day++) {
            System.out.printf("%2d ", day);
            if ((day + firstDay) % 7 == 0 || day == daysInMonth) {
                System.out.println();
            }
        }
    }

    // Print calendar for a given year
    public static void printYearCalendar(int year) {
        System.out.printf("\n=== Calendar for %d ===\n", year);
        for (int month = 1; month <= 12; month++) {
            printMonthCalendar(year, month);
        }
    }

    public static void main(String[] args) {
        // Generate calendars for 400 years (2000 to 2399)
        for (int year = 2000; year <= 2399; year++) {
            printYearCalendar(year);
        }
    }
}