/*
 * Jamm
 * Copyright (C) 2002 Dave Dribin and Keith Garner
 *  
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package jamm.util;

import java.util.Date;
import java.util.TimeZone;
import java.util.GregorianCalendar;
import java.util.Calendar;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import java.io.File;

/**
 * This tells you the diff between two dates.  For the get method, use
 * the defines from Calendar.
 *
 * @see java.util.Calendar
 */
public class DateDiff
{
    /**
     * Creates a new <code>DateDiff</code> instance.
     *
     * @param timeOne time in milliseconds
     * @param timeTwo time in milliseconds
     */
    public DateDiff(long timeOne, long timeTwo)
    {
        Date newTime;
        Date oldTime;
        
        if (timeOne >= timeTwo)
        {
            newTime = new Date(timeOne);
            oldTime = new Date(timeTwo);
        }
        else
        {
            newTime = new Date(timeTwo);
            oldTime = new Date(timeOne);
        }

        TimeZone gmtTz = TimeZone.getTimeZone("GMT");

        mNewTimeCal = new GregorianCalendar(gmtTz);
        mNewTimeCal.setTime(newTime);

        mOldTimeCal = new GregorianCalendar(gmtTz);
        mOldTimeCal.setTime(oldTime);

        mNewTime = new int[Calendar.FIELD_COUNT];
        mOldTime = new int[Calendar.FIELD_COUNT];

        calsToArray();
        
        mDiff = new int[Calendar.FIELD_COUNT];
        calcDiff();
    }

    /**
     * Takes the data out of a Calendar and sticks it into a local
     * integer array.
     */
    private void calsToArray()
    {
        mNewTime[Calendar.SECOND] = mNewTimeCal.get(Calendar.SECOND);
        mNewTime[Calendar.MINUTE] = mNewTimeCal.get(Calendar.MINUTE);
        mNewTime[Calendar.HOUR] = mNewTimeCal.get(Calendar.HOUR);
        mNewTime[Calendar.DAY_OF_MONTH] =
            mNewTimeCal.get(Calendar.DAY_OF_MONTH);
        mNewTime[Calendar.MONTH] = mNewTimeCal.get(Calendar.MONTH);
        mNewTime[Calendar.YEAR] = mNewTimeCal.get(Calendar.YEAR);

        mOldTime[Calendar.SECOND] = mOldTimeCal.get(Calendar.SECOND);
        mOldTime[Calendar.MINUTE] = mOldTimeCal.get(Calendar.MINUTE);
        mOldTime[Calendar.HOUR] = mOldTimeCal.get(Calendar.HOUR);
        mOldTime[Calendar.DAY_OF_MONTH] =
            mOldTimeCal.get(Calendar.DAY_OF_MONTH);
        mOldTime[Calendar.MONTH] = mOldTimeCal.get(Calendar.MONTH);
        mOldTime[Calendar.YEAR] = mOldTimeCal.get(Calendar.YEAR);
    }

    /**
     * The routine that actually does the calculation.
     */
    private void calcDiff()
    {
        int value = Calendar.SECOND;
        if (mNewTime[value] < mOldTime[value])
        {
            mNewTime[Calendar.MINUTE] -= 1;
            mNewTime[value] += 60;
        }
        mDiff[value] = mNewTime[value] - mOldTime[value];

        value = Calendar.MINUTE;
        if (mNewTime[value] < mOldTime[value])
        {
            mNewTime[Calendar.HOUR] -= 1;
            mNewTime[value] += 60;
        }
        mDiff[value] = mNewTime[value] - mOldTime[value];

        value = Calendar.HOUR;
        if (mNewTime[value] < mOldTime[value])
        {
            mNewTime[Calendar.DAY_OF_MONTH] -= 1;
            mNewTime[value] += 24;
        }
        mDiff[value] = mNewTime[value] - mOldTime[value];

        value = Calendar.DAY_OF_MONTH;
        mDiff[value] = mNewTime[value] - mOldTime[value];
        if (mDiff[value] < 0)
        {
            mDiff[value] += getDaysForMonth(mNewTime[Calendar.MONTH],
                                            mNewTime[Calendar.YEAR]);
            mNewTime[Calendar.MONTH] -= 1;
        }

        value = Calendar.MONTH;
        if (mNewTime[value] < mOldTime[value])
        {
            mNewTime[value] += 12;
            mNewTime[Calendar.YEAR] -= 1;
        }
        mDiff[value] = mNewTime[value] - mOldTime[value];

        value = Calendar.YEAR;
        mDiff[value] = mNewTime[value] - mOldTime[value];
    }

    /**
     * How many days to add for this month.
     *
     * @param month the month
     * @param year the year the month is in
     * @return an integer with a number of days
     */
    private int getDaysForMonth(int month, int year)
    {
        int returnValue = 0;
        switch (month)
        {
            case  0:
            case  2:
            case  4:
            case  6:
            case  7:
            case  9:
            case 11:
                returnValue = 31;
                break;

            case  3:
            case  5:
            case  8:
            case 10:
                returnValue = 30;
                break;

            case  1:
                if (mNewTimeCal.isLeapYear(year))
                {
                    returnValue = 29;
                }
                else
                {
                    returnValue = 28;
                }
                break;
        }
        return returnValue;
    }

    /**
     * Returns the request time amount.  Use the defines from Calendar.
     *
     * @see java.util.Calendar
     *
     * @param value the value to return
     * @return an integer with the requested value
     */
    public int get(int value)
    {
        return mDiff[value];
    }

    /**
     * Returns a string representation of the date difference.
     *
     * @return a string with "xx years, yy months, etc"
     */
    public String toString()
    {
        List list = new ArrayList();
        
        list.add(stringValue(mDiff[Calendar.YEAR], "year", "years"));
        list.add(stringValue(mDiff[Calendar.MONTH], "month", "months"));
        list.add(stringValue(mDiff[Calendar.DAY_OF_MONTH], "day", "days"));
        list.add(stringValue(mDiff[Calendar.HOUR], "hour", "hours"));
        list.add(stringValue(mDiff[Calendar.MINUTE], "minute", "minutes"));
        list.add(stringValue(mDiff[Calendar.SECOND], "second", "seconds"));

        return stringJoin(", ", list);
    }

    /**
     * Puts the value and appropriate single or plural word into a string.
     * If value is zero it returns an empty string.
     *
     * @param value The amount of items
     * @param single the single word for the item
     * @param plural the plural word for the item
     * @return a string representing the value
     */
    private String stringValue(int value, String single, String plural)
    {
        StringBuffer sb = new StringBuffer();
        
        switch (value)
        {
            case 0:
                break;
            case 1:
                sb.append("1 ").append(single);
                break;
            default:
                sb.append(value).append(" ").append(plural);
                break;
        }

        return sb.toString();
    }

    /**
     * Joins the separate strings of stringList into a single string
     * with fields seperated by the value of expr.  Modeled after
     * perl's join() method.  One important difference is that in this
     * implementation, empty strings in the list are ignored.
     *
     * @param expr a string
     * @param stringList a List of strings
     * @return a joined string
     */
    private String stringJoin(String expr, List stringList)
    {
        StringBuffer result = new StringBuffer();
        Iterator i = stringList.iterator();
        boolean firstDone = false;

        while (!firstDone && i.hasNext())
        {
            String s = (String) i.next();
            if (s.length() != 0)
            {
                result.append(s);
                firstDone = true;
            }
        }

        while (i.hasNext())
        {
            String s = (String) i.next();
            if (s.length() != 0)
            {
                result.append(expr).append(s);
            }
        }

        return result.toString();
    }

    /**
     * Main method for testing.
     *
     * @param argv the arguements to use
     */
    public static final void main(String argv[])
    {
        String filename = new String();
        if (!(argv.length > 0))
        {
            System.out.println("You need to supply a filename");
            System.exit(1);
        }
        else
        {
            filename = argv[0];
        }

        File file = new File(filename);
        DateDiff dd =
            new DateDiff(file.lastModified(), System.currentTimeMillis());

        System.out.println(dd);
    }

    /** The object where we store our diff */
    private int mDiff[];
    /** The GregorianCalendar representation of the new time. */
    private GregorianCalendar mNewTimeCal;
    /** The GregorianCalendar representation of the old time. */
    private GregorianCalendar mOldTimeCal;
    /** The integer array respresentation of the new time. */
    private int mNewTime[];
    /** The integer array respresentation of the old time. */
    private int mOldTime[];
}
