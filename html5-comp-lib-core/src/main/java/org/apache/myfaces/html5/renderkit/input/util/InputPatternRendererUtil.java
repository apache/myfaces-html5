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
import javax.faces.validator.RegexValidator;
import javax.faces.validator.Validator;

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
     * Iterates over the validators of the given UIInput and renders the pattern attr if found one.
     * <p>
     * If there are multiple RegexValidator instances attached to components, only the pattern of the first one will be
     * used.
     * 
     * @return true if the RegexValidator is found an the pattern markup is written. false otherwise.
     * @throws IOException
     */
    public static boolean renderPattern(FacesContext facesContext, UIInput component) throws IOException
    {
        Validator[] validators = component.getValidators();
        for (Validator validator : validators)
        {
            if (validator instanceof RegexValidator)
            {
                RegexValidator regexValidator = (RegexValidator) validator;
                String pattern = regexValidator.getPattern();
                ResponseWriter writer = facesContext.getResponseWriter();
                writer.writeAttribute(HTML5.PATTERN_ATTR, pattern, null);
                return true;
            }
        }
        return false;
    }
}
