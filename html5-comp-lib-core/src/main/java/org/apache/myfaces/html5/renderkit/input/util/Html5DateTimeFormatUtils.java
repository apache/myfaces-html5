/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.myfaces.html5.renderkit.input.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.faces.FacesException;

import org.apache.myfaces.html5.renderkit.util.HTML5;

/**
 * Utility class for parsing and formatting date values.
 * 
 * @author Ali Ok
 * 
 */
public class Html5DateTimeFormatUtils
{

    /**
     * Parses the value and converts it to date time considering the type.
     * 
     * @throws ParseException
     *             if the input is not parsable for the given type.
     */
    public static Date parseDateTime(String value, String type) throws ParseException
    {
        if (value == null || value.isEmpty())
            return null;
        
        if (HTML5.INPUT_TYPE_DATETIME.equals(type))
        {
            return _parseGlobalDateTime(value);
        }
        else if (HTML5.INPUT_TYPE_DATETIME_LOCAL.equals(type))
        {
            return _parseLocalDateTime(value);
        }
        else if (HTML5.INPUT_TYPE_DATE.equals(type))
        {
            return _parseDate(value);
        }
        else if (HTML5.INPUT_TYPE_TIME.equals(type))
        {
            return _parseTime(value);
        }
        else if (HTML5.INPUT_TYPE_MONTH.equals(type))
        {
            return _parseMonth(value);
        }
        else if (HTML5.INPUT_TYPE_WEEK.equals(type))
        {
            return _parseWeek(value);
        }
        else
        {
            throw new FacesException("Type " + type + " is not applicable.");
        }
    }

    /**
     * Formats datetime for given type.
     * 
     * @throws FacesException
     */
    public static String formatDateTime(Date value, String type) throws FacesException
    {
        if (value == null)
            return null;
        
        if (HTML5.INPUT_TYPE_DATETIME.equals(type))
        {
            return _formatGlobalDateTime(value);
        }
        else if (HTML5.INPUT_TYPE_DATETIME_LOCAL.equals(type))
        {
            return _formatLocalDateTime(value); // TODO: with which timezone?
        }
        else if (HTML5.INPUT_TYPE_DATE.equals(type))
        {
            return _formatDate(value);
        }
        else if (HTML5.INPUT_TYPE_TIME.equals(type))
        {
            return _formatTime(value);
        }
        else if (HTML5.INPUT_TYPE_MONTH.equals(type))
        {
            return _formatMonth(value);
        }
        else if (HTML5.INPUT_TYPE_WEEK.equals(type))
        {
            return _formatWeek(value);
        }
        else
        {
            throw new FacesException("Type " + type + " is not applicable.");
        }
    }

    private static final String GLOBAL_DATETIME_UTC_PATTERN = "yyyy-MM-dd'T'HH:mm'Z'";
    private static final String GLOBAL_DATETIME_UTC_PATTERN_WITH_SECONDS = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    private static final String GLOBAL_DATETIME_UTC_PATTERN_WITH_MILLIS = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    // note that HTML5 spec has colon char in the time zone, but it is not applicable for SimpleDateFormat.
    // so, if a non UTC string is being parsed, the last colon will be removed.
    private static final String GLOBAL_DATETIME_NONUTC_PATTERN = "yyyy-MM-dd'T'HH:mmZZZZ";
    private static final String GLOBAL_DATETIME_NONUTC_PATTERN_WITH_SECONDS = "yyyy-MM-dd'T'HH:mm:ssZZZZ";
    private static final String GLOBAL_DATETIME_NONUTC_PATTERN_WITH_MILLIS = "yyyy-MM-dd'T'HH:mm:ss.SSSZZZZ";

    private static final String LOCAL_DATETIME_PATTERN = "yyyy-MM-dd'T'HH:mm";
    private static final String LOCAL_DATETIME_PATTERN_WITH_SECONDS = "yyyy-MM-dd'T'HH:mm:ss";
    private static final String LOCAL_DATETIME_PATTERN_WITH_MILLIS = "yyyy-MM-dd'T'HH:mm:ss.SSS";

    private static final String DATE_PATTERN = "yyyy-MM-dd";

    private static final String TIME_PATTERN = "HH:mm";
    private static final String TIME_PATTERN_WITH_SECONDS = "HH:mm:ss";
    private static final String TIME_PATTERN_WITH_MILLIS = "HH:mm:ss.SSS";

    private static final String MONTH_PATTERN = "yyyy-MM";

    private static final String WEEK_PATTERN = "yyyy-'W'ww";

    private Html5DateTimeFormatUtils()
    {
    }

    // Samples: 0037-12-13T00:00Z, 1979-10-14T12:00:00.001-04:00, 8592-01-01T02:09+02:09
    // Html5 Spec:
    // http://www.whatwg.org/specs/web-apps/current-work/multipage/common-microsyntaxes.html#valid-global-date-and-time-string
    private static Date _parseGlobalDateTime(String value) throws ParseException
    {
        if (value == null || value.isEmpty())
            return null;

        boolean utcDateTime = value.charAt(value.length() - 1) == 'Z';

        if (!utcDateTime)
        {
            /*
             * TimeZoned datetime values of Html5 have to define the time zone with colon, but that is not applicable
             * with SimpleDateFormat. SimpleDateFormat is unable to use a pattern like "yyyy-MM-dd'T'HH:mm:ZZ:ZZ". It
             * doesn't like the last colon, since it breaks the timezone. Since it is unable to parse a value like
             * "1992-01-01T02:09+02:00" using "yyyy-MM-dd'T'HH:mm:ZZZZ" pattern, we're removing the last comma of the
             * value to convert it to "1992-01-01T02:09+0200"
             */
            value = _deleteLastColonOfGlobalNonUTCDateTime(value);
        }

        boolean hasMillis = value.indexOf('.') != -1;

        int lastIndexOfT = value.lastIndexOf('T');
        if (lastIndexOfT == -1)
            throw new ParseException("Value has no time information, since it does not have 'T' symbol : " + value, 0);

        boolean doesntHaveMillisButHasSeconds = _hasSeconds(value.substring(lastIndexOfT));

        if (utcDateTime)
        {
            if (hasMillis)
            {
                return new SimpleDateFormat(GLOBAL_DATETIME_UTC_PATTERN_WITH_MILLIS).parse(value);
            }
            else if (doesntHaveMillisButHasSeconds)
            {
                return new SimpleDateFormat(GLOBAL_DATETIME_UTC_PATTERN_WITH_SECONDS).parse(value);
            }
            else
            {
                return new SimpleDateFormat(GLOBAL_DATETIME_UTC_PATTERN).parse(value);
            }
        }
        else
        {
            if (hasMillis)
            {
                return new SimpleDateFormat(GLOBAL_DATETIME_NONUTC_PATTERN_WITH_MILLIS).parse(value);
            }
            else if (doesntHaveMillisButHasSeconds)
            {
                return new SimpleDateFormat(GLOBAL_DATETIME_NONUTC_PATTERN_WITH_SECONDS).parse(value);
            }
            else
            {
                return new SimpleDateFormat(GLOBAL_DATETIME_NONUTC_PATTERN).parse(value);
            }
        }
    }

    // Samples: 0037-12-13T00:00, 1979-10-14T12:00:00.001, 8592-01-01T02:09
    // Html5 spec:
    // http://www.whatwg.org/specs/web-apps/current-work/multipage/common-microsyntaxes.html#valid-local-date-and-time-string
    private static Date _parseLocalDateTime(String value) throws ParseException
    {
        if (value == null || value.isEmpty())
            return null;

        boolean hasMillis = value.indexOf('.') != -1;

        int lastIndexOfT = value.lastIndexOf('T');
        if (lastIndexOfT == -1)
            throw new ParseException("Value has no time information, since it does not have 'T' symbol : " + value, 0);

        boolean doesntHaveMillisButHasSeconds = _hasSeconds(value.substring(lastIndexOfT));

        if (hasMillis)
        {
            return new SimpleDateFormat(LOCAL_DATETIME_PATTERN_WITH_MILLIS).parse(value);
        }
        else if (doesntHaveMillisButHasSeconds)
        {
            return new SimpleDateFormat(LOCAL_DATETIME_PATTERN_WITH_SECONDS).parse(value);
        }
        else
        {
            return new SimpleDateFormat(LOCAL_DATETIME_PATTERN).parse(value);
        }
    }

    // Samples: 0037-12-13, 1979-10-14, 8592-01-01
    // Html5 Spec:
    // http://www.whatwg.org/specs/web-apps/current-work/multipage/common-microsyntaxes.html#valid-date-string
    private static Date _parseDate(String value) throws ParseException
    {
        return new SimpleDateFormat(DATE_PATTERN).parse(value);
    }

    // Samples: 0037-12-13, 1979-10-14, 8592-01-01
    // Html5 Spec:
    // http://www.whatwg.org/specs/web-apps/current-work/multipage/common-microsyntaxes.html#valid-time-string
    private static Date _parseTime(String value) throws ParseException
    {
        boolean hasMillis = value.indexOf('.') != -1;
        boolean hasSeconds = _hasSeconds(value);

        if (hasMillis)
            return new SimpleDateFormat(TIME_PATTERN_WITH_MILLIS).parse(value);
        else if (hasSeconds)
            return new SimpleDateFormat(TIME_PATTERN_WITH_SECONDS).parse(value);
        else
            return new SimpleDateFormat(TIME_PATTERN).parse(value);
    }

    // Samples: 0037-12, 1979-10, 8592-01
    // Html5 Spec:
    // http://www.whatwg.org/specs/web-apps/current-work/multipage/common-microsyntaxes.html#valid-month-string
    private static Date _parseMonth(String value) throws ParseException
    {
        return new SimpleDateFormat(MONTH_PATTERN).parse(value);
    }

    // Samples: 2005-W52, 1979-W10, 8592-W01
    // Html5 Spec:
    // http://www.whatwg.org/specs/web-apps/current-work/multipage/common-microsyntaxes.html#valid-week-string
    private static Date _parseWeek(String value) throws ParseException
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(WEEK_PATTERN);

        /*
         * A week starts on Monday according to Html5 spec. see:
         * http://www.whatwg.org/specs/web-apps/current-work/multipage/common-microsyntaxes.html#concept-week
         */
        simpleDateFormat.getCalendar().setFirstDayOfWeek(Calendar.MONDAY);

        return simpleDateFormat.parse(value);
    }

    // Samples: 0037-12-13T00:00Z, 1979-10-14T12:00:00.001-04:00, 8592-01-01T02:09+02:09
    // Html5 Spec:
    // http://www.whatwg.org/specs/web-apps/current-work/multipage/common-microsyntaxes.html#valid-global-date-and-time-string
    private static String _formatGlobalDateTime(Date value)
    {
        return new SimpleDateFormat(GLOBAL_DATETIME_UTC_PATTERN_WITH_MILLIS).format(value);
    }

    // Samples: 0037-12-13T00:00, 1979-10-14T12:00:00.001, 8592-01-01T02:09
    // Html5 spec:
    // http://www.whatwg.org/specs/web-apps/current-work/multipage/common-microsyntaxes.html#valid-local-date-and-time-string
    private static String _formatLocalDateTime(Date value)
    {
        return new SimpleDateFormat(LOCAL_DATETIME_PATTERN_WITH_MILLIS).format(value);
    }

    // Samples: 0037-12-13, 1979-10-14, 8592-01-01
    // Html5 Spec:
    // http://www.whatwg.org/specs/web-apps/current-work/multipage/common-microsyntaxes.html#valid-date-string
    private static String _formatDate(Date value)
    {
        return new SimpleDateFormat(DATE_PATTERN).format(value);
    }

    // Samples: 0037-12-13, 1979-10-14, 8592-01-01
    // Html5 Spec:
    // http://www.whatwg.org/specs/web-apps/current-work/multipage/common-microsyntaxes.html#valid-time-string
    private static String _formatTime(Date value)
    {
        return new SimpleDateFormat(TIME_PATTERN_WITH_MILLIS).format(value);
    }

    // Samples: 0037-12, 1979-10, 8592-01
    // Html5 Spec:
    // http://www.whatwg.org/specs/web-apps/current-work/multipage/common-microsyntaxes.html#valid-month-string
    private static String _formatMonth(Date value)
    {
        return new SimpleDateFormat(MONTH_PATTERN).format(value);
    }

    // Samples: 2005-W52, 1979-W10, 8592-W01
    // Html5 Spec:
    // http://www.whatwg.org/specs/web-apps/current-work/multipage/common-microsyntaxes.html#valid-week-string
    private static String _formatWeek(Date value)
    {
        /*
         * We cannot use the pattern "yyyy-'W'ww" here since it SimpleDateFormat evalates 'ww' w/o considering the year.
         * So, for date Fri Dec 31 00:00:00 EET 1999, it returns "1999-W01" instead of "2000-W01".
         * 
         * Tomahawk has implemented its own formatter, but I didn't want to use it, since it is complicated.
         * http://myfaces
         * .apache.org/tomahawk-project/tomahawk/apidocs/org/apache/myfaces/dateformat/SimpleDateFormatter.html
         * 
         * So, here is the workaround.
         */

        Calendar cal = Calendar.getInstance();
        cal.setTime(value);

        /*
         * A week starts on Monday according to Html5 spec. see:
         * http://www.whatwg.org/specs/web-apps/current-work/multipage/common-microsyntaxes.html#concept-week
         */
        cal.setFirstDayOfWeek(Calendar.MONDAY);

        int year = cal.get(Calendar.YEAR);
        int week = cal.get(Calendar.WEEK_OF_YEAR);

        if (cal.get(Calendar.MONTH) == Calendar.DECEMBER)
        {
            if (week == 1)
            {
                year++;
            }
        }

        return String.format("%04d-W%02d", year, week);
    }

    private static boolean _hasSeconds(String value) throws ParseException
    {
        // example strings : '22:03', '22:03:05', '22:03:05.455'

        int indexOfMinuteBeginning = value.indexOf(':');

        // if the second information is present, then lastIndexOfColon must be the beginning position of the seconds(for
        // example '1979-10-14T12:00:00-0400').
        // else it must be the beginning position of the minutes(for example: '8592-01-01T02:09Z' ).
        int lastIndexOfColon = value.lastIndexOf(':');

        return lastIndexOfColon > indexOfMinuteBeginning;
    }

    private static String _deleteLastColonOfGlobalNonUTCDateTime(String value) throws ParseException
    {
        int indexOfTimeZoneSign = value.lastIndexOf(':');
        if (indexOfTimeZoneSign == -1)
            throw new ParseException("A valid nonUTC date time must contain time zone information.", 0);

        value = value.substring(0, indexOfTimeZoneSign) + value.substring(indexOfTimeZoneSign + 1, value.length());

        return value;
    }
}
