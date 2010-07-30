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

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.apache.commons.validator.EmailValidator;
import org.apache.myfaces.shared_html5.renderkit.RendererUtils;

/**
 * Converter for use in hx:inputEmail.
 * 
 * @author Ali Ok
 * 
 */
public class Html5EmailConverter implements Converter
{
    private static final char MULTIPLE_EMAIL_DELIMITER = ',';

    public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException
    {
        if (value == null)
            return null;

        if (value.isEmpty())
            return null;

        String[] emails = value.split(",");
        if (emails != null && emails.length > 0)
        {
            for (String email : emails)
            {
                email = email.trim();
                if (!EmailValidator.getInstance().isValid(email))
                {
                    throw new ConverterException(new FacesMessage("Provided value for component "
                            + RendererUtils.getPathToComponent(component) + " is not a valid email: " + email));
                }
            }

            return emails;
        }
        else
        {
            return null;
        }
    }

    public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException
    {
        if (value == null)
            return null;

        if (value instanceof String)
        {
            String strVal = (String) value;
            return strVal;
        }
        else if (value instanceof String[])
        {
            String[] strArrVal = (String[]) value;
            if (strArrVal.length == 0)
                return null;

            StringBuilder builder = new StringBuilder();
            for (String email : strArrVal)
            {
                builder.append(email.trim()).append(MULTIPLE_EMAIL_DELIMITER);
            }

            return builder.substring(0, builder.length() - 1);
        }
        else
        {
            throw new ConverterException(new FacesMessage("Provided value for component "
                    + RendererUtils.getPathToComponent(component) + " is not String or String[]: " + value.toString()));
        }
    }
}
