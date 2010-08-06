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

import java.io.IOException;

import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.Converter;
import javax.faces.convert.NumberConverter;
import javax.faces.validator.DoubleRangeValidator;
import javax.faces.validator.LengthValidator;
import javax.faces.validator.LongRangeValidator;
import javax.faces.validator.RegexValidator;
import javax.faces.validator.Validator;

import org.apache.commons.lang.StringUtils;
import org.apache.myfaces.html5.component.api.validation.ClientSidePatternProvider;
import org.apache.myfaces.html5.renderkit.util.HTML5;

/**
 * Utility class to render new Html5 "pattern" markup.
 * 
 * @author Ali Ok
 * 
 */
public class InputPatternRendererUtil
{

    /**
     * Iterates over the validators and converter of the given UIInput and renders the pattern attr if found one.
     * <p>
     * If there are multiple validator instances attached to component, only the pattern of the first one will be used.
     * 
     * @return true if a pattern is found or calculated and the pattern markup is written. false otherwise.
     * @throws IOException
     */
    public static boolean renderPattern(FacesContext facesContext, UIInput component) throws IOException
    {
        String pattern = null;

        pattern = _getPatternFromValidators(component.getValidators());

        if (pattern == null)
            pattern = _getPatternFromConverter(component.getConverter());

        if (pattern != null)
        {
            ResponseWriter writer = facesContext.getResponseWriter();
            writer.writeAttribute(HTML5.PATTERN_ATTR, pattern, null);
            return true;
        }
        else
        {
            return false;
        }

    }

    private static String _getPatternFromConverter(Converter converter)
    {
        String pattern = null;

        if (converter instanceof ClientSidePatternProvider)
            pattern = ((ClientSidePatternProvider) converter).getPattern();

        if (StringUtils.isBlank(pattern))
            pattern = _getPatternFromStandardConverter(pattern, converter);

        return pattern;
    }

    private static String _getPatternFromStandardConverter(String pattern, Converter converter)
    {
        if (converter instanceof NumberConverter) // special case
        {
            pattern = _getPatternFromNumberConverter((NumberConverter) converter);
        }
        // TODO: what about DateTimeConverter
        return pattern;
    }

    private static String _getPatternFromNumberConverter(NumberConverter converter)
    {
        // TODO: read the spec and create the pattern!
        return null;
    }

    private static String _getPatternFromValidators(Validator[] validators)
    {
        String pattern = null;

        for (Validator validator : validators)
        {
            if (validator instanceof ClientSidePatternProvider)
            {
                pattern = ((ClientSidePatternProvider) validator).getPattern();
            }

            if (StringUtils.isBlank(pattern))
                pattern = _getPatternFromStandardValidator(pattern, validator);

            if (!StringUtils.isBlank(pattern))
                break;
        }

        return pattern;
    }

    private static String _getPatternFromStandardValidator(String pattern, Validator validator)
    {
        if (validator instanceof RegexValidator) // special case
        {
            pattern = _getPatternFromRegexValidator((RegexValidator) validator);
        }
        else if (validator instanceof LengthValidator) // special case
        {
            pattern = _getPatternFromLengthValidator((LengthValidator) validator);
        }
        else if (validator instanceof LongRangeValidator) // special case
        {
            pattern = _getPatternFromLongRangeValidator((LongRangeValidator) validator);
        }
        else if (validator instanceof DoubleRangeValidator) // special case
        {
            pattern = _getPatternFromDoubleRangeValidator((DoubleRangeValidator) validator);
        }
        // TODO: what about bean validation? f:validateBean

        // f:validateRequired is not about pattern, so skip it
        return pattern;
    }

    private static String _getPatternFromDoubleRangeValidator(DoubleRangeValidator validator)
    {
        // XXX: no real solution!
        return null;
    }

    private static String _getPatternFromLongRangeValidator(LongRangeValidator validator)
    {
        // XXX: no real solution!
        return null;
    }

    private static String _getPatternFromLengthValidator(LengthValidator validator)
    {
        // XXX: fix this ugly code
        int minimum = validator.getMinimum();
        int maximum = validator.getMaximum();

        boolean hasMinimum = minimum != Integer.MIN_VALUE;
        boolean hasMaximum = maximum != Integer.MAX_VALUE;

        if (hasMinimum && hasMaximum)
            return "(?:.{" + minimum + "," + maximum + "})";

        if (hasMinimum)
            return "(?:.{" + minimum + ",})";

        if (hasMaximum)
            return "(?:.{," + maximum + "})";

        return null;
    }

    private static String _getPatternFromRegexValidator(RegexValidator regexValidator)
    {
        return regexValidator.getPattern();
    }
}
