/*
 * Copyright 2020 Javier Hidalgo Ochoa, javier.hidalgo@alumnos.ucn.cl
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package cl.ucn.disc.dsm.jhidalgo.news.utils;

import androidx.room.TypeConverter;

import org.threeten.bp.ZonedDateTime;

/**
 * The converter of ZonedDateTime and String for Room DataBase
 *
 * @author Javier Hidalgo Ochoa
 */
public class Converters {

    @TypeConverter
    public static ZonedDateTime toDate(String dateString) {
        if (dateString == null) {
            return null;
        } else {
            return ZonedDateTime.parse(dateString);
        }
    }

    @TypeConverter
    public static String toDateString(ZonedDateTime date) {
        if (date == null) {
            return null;
        } else {
            return date.toString();
        }
    }
}
