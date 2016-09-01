package com.atconsulting.ignitehiveloader.filter;

import com.atconsulting.ignitehiveloader.CHA;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Filter which accepts only events from specific day.
 */
public class OrcLoaderSameDayFilter implements OrcLoaderFilter {
    /** Year. */
    private int year;

    /** Day. */
    private int day;

    /**
     * Default constructor.
     */
    public OrcLoaderSameDayFilter() {
        // No-op.
    }

    /**
     * Constructor.
     *
     * @param date Date.
     */
    public OrcLoaderSameDayFilter(Date date) {
        Calendar cal = GregorianCalendar.getInstance();

        cal.setTime(date);

        this.year = cal.get(Calendar.YEAR);
        this.day = cal.get(Calendar.DAY_OF_YEAR);
    }

    /** {@inheritDoc} */
    @Override public boolean evaluate(CHA.Key key, CHA val) {
        Calendar cal = GregorianCalendar.getInstance();

        cal.setTime(key.getStartCallDateTime());

        int day = cal.get(Calendar.DAY_OF_YEAR);

        if (day == this.day) {
            int year = cal.get(Calendar.YEAR);

            return year == this.year;
        }

        return false;
    }
}
