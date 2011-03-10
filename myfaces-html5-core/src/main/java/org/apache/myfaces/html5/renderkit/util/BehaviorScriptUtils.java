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
package org.apache.myfaces.html5.renderkit.util;


/**
 * Contains utility methods for converting Java objects to Javascript objects to use in behavior renderers.
 * 
 * @author Ali Ok
 *
 */
public class BehaviorScriptUtils
{
    /**
     * Converts the given array to Javascript literal array. <br/>
     * For example the Java string array <code>{"Kobe", "LeBron", "Shaq"}</code> will be converted to <code>['Kobe','LeBron','Shaq']</code> .
     * 
     * @param arr
     * @return null if arr is null, <code>[]</code> if arr is empty array.
     */
    public static String convertToSafeJavascriptLiteralArray(String[] arr)
    {
        if (arr == null)
            return null;

        if (arr.length == 0)
        {
            return "[]";
        }

        StringBuilder builder = new StringBuilder();
        builder.append('[');
        for (String string : arr)
        {
            builder.append('\'').append(string).append('\'').append(',');
        }
        builder.deleteCharAt(builder.length() - 1); // delete the last comma
        builder.append(']');

        return builder.toString();
    }

    /**
     * Converts the given string to Javascript string. <br/>
     * For example, a call with <code>String str = "Celtics".</code> will return <code>'Celtics'</code>;
     * 
     * @param str
     * @return null if str is null;
     */
    public static String convertToSafeJavascriptLiteral(String str)
    {
        if (str == null)
            return null;

        return "'" + str + "'";
    }

    /**
     * Returns the Javascript space separated representation of the given array.<br/>
     * For example, for <code>String[] arr = {"idOne", "idTwo"}</code>, the returned value will be <code>'idOne idTwo'</code>.
     * 
     * @param arr
     * @return null if arr is null or empty.
     */
    public static String convertToSpaceSeperatedJSLiteral(String[] arr)
    {
        if (arr == null || arr.length == 0)
            return null;

        StringBuilder builder = new StringBuilder();
        builder.append("'");
        for (String string : arr)
        {
            builder.append(string).append(' ');
        }
        builder.deleteCharAt(builder.length() - 1); // delete the last space
        builder.append("'");

        return builder.toString();
    }
}
