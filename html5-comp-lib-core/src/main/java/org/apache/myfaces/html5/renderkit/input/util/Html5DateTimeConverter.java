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
import java.util.Date;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.apache.myfaces.html5.component.input.HtmlInputDateTime;
import org.apache.myfaces.shared_html5.renderkit.RendererUtils;

/**
 * Converter for use in hx:inputDateTime.
 * 
 * @author Ali Ok
 * 
 */
public class Html5DateTimeConverter implements Converter
{

    public Object getAsObject(FacesContext context, UIComponent uiComponent, String value) throws ConverterException
    {
        if (uiComponent instanceof HtmlInputDateTime)
        {
            HtmlInputDateTime component = (HtmlInputDateTime) uiComponent;
            String type = component.getType();

            try
            {
                return Html5DateTimeFormatUtils.parseDateTime(value, type);
            }
            catch (ParseException e)
            {
                throw new ConverterException(new FacesMessage("Unable to parse input " + value + " for " + RendererUtils.getPathToComponent(uiComponent) + " with type " + type), e);
            }
        }
        else
        {
            throw new FacesException("Component " + RendererUtils.getPathToComponent(uiComponent) + " is not a HtmlInputDateTime");
        }

    }

    public String getAsString(FacesContext context, UIComponent uiComponent, Object objValue) throws ConverterException
    {
        if (objValue == null)
            return null;

        if (!(objValue instanceof Date))
            throw new FacesException("Value is not a java.util.Date for component " + RendererUtils.getPathToComponent(uiComponent) + ".");

        if (uiComponent instanceof HtmlInputDateTime)
        {
            Date value = (Date) objValue;

            HtmlInputDateTime component = (HtmlInputDateTime) uiComponent;
            String type = component.getType();

            return Html5DateTimeFormatUtils.formatDateTime(value, type);
        }
        else
        {
            throw new FacesException("Component " + RendererUtils.getPathToComponent(uiComponent) + " is not a HtmlInputDateTime");
        }

    }
}
