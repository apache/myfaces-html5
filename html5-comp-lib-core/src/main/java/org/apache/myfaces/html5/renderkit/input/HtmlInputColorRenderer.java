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
package org.apache.myfaces.html5.renderkit.input;

import java.util.Map;
import java.util.regex.Pattern;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFRenderer;
import org.apache.myfaces.html5.component.input.Html5BaseInputText;
import org.apache.myfaces.html5.component.input.HtmlInputColor;
import org.apache.myfaces.html5.renderkit.input.delegate.HtmlTextInputSuggestionRendererHelper;
import org.apache.myfaces.html5.renderkit.input.delegate.SuggestionRendererHelper;
import org.apache.myfaces.html5.renderkit.util.HTML5;
import org.apache.myfaces.html5.renderkit.util.PassThroughAttributes;
import org.apache.myfaces.shared_html5.renderkit.RendererUtils;

/**
 * Renderer for < hx:inputColor > component.
 * 
 * @author Ali Ok
 * 
 */
@JSFRenderer(renderKitId = "HTML_BASIC", family = "javax.faces.Input", type = "org.apache.myfaces.html5.Color")
public class HtmlInputColorRenderer extends Html5BaseInputTextRenderer
{

    private HtmlTextInputSuggestionRendererHelper _suggestionRendererHelper;

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.myfaces.shared_html5.renderkit.html.HtmlTextRendererBase#getConvertedValue(javax.faces.context.
     * FacesContext, javax.faces.component.UIComponent, java.lang.Object)
     * 
     * This is overridden in order to validate the input without attaching a validator (or converter).
     */
    @Override
    public Object getConvertedValue(FacesContext facesContext, UIComponent uiComponent, Object submittedValue)
            throws ConverterException
    {
        if (submittedValue != null && !(submittedValue instanceof String))
        {
            throw new IllegalArgumentException("Submitted value of type String for component : "
                    + RendererUtils.getPathToComponent(uiComponent) + "expected");
        }

        RendererUtils.checkParamValidity(facesContext, uiComponent, HtmlInputColor.class);
        HtmlInputColor component = (HtmlInputColor) uiComponent;

        Converter converter;
        try
        {
            converter = RendererUtils.findUIOutputConverter(facesContext, component);
        }
        catch (FacesException e)
        {
            throw new ConverterException(e);
        }

        if (converter == null)
            converter = new ColorConverter();

        return converter.getAsObject(facesContext, component, (String) submittedValue);
    }

    @Override
    protected Map<String, String> getExtraPassThroughAttributes()
    {
        return PassThroughAttributes.INPUT_COLOR;
    }

    @Override
    protected String getInputHtmlType(UIComponent component)
    {
        return HTML5.INPUT_TYPE_COLOR;
    }

    @Override
    protected Class<? extends javax.faces.component.html.HtmlInputText> getComponentClass()
    {
        return HtmlInputColor.class;
    }

    @Override
    public SuggestionRendererHelper getSuggestionRendererHelper(Html5BaseInputText component)
    {
        if (_suggestionRendererHelper == null)
            _suggestionRendererHelper = new HtmlTextInputSuggestionRendererHelper();

        return _suggestionRendererHelper;
    }

}

class ColorConverter implements Converter
{

    private static Pattern SIMPLE_VALID_COLOR_PATTERN = Pattern.compile("^#([0-9a-fA-F]){6}$");

    public Object getAsObject(FacesContext context, UIComponent uiComponent, String value) throws ConverterException
    {

        if (value == null || value.toString().isEmpty())
        {
            return null;
        }
        else
        {
            HtmlInputColor component = (HtmlInputColor) uiComponent;
            String strValue = value.toString();

            if (SIMPLE_VALID_COLOR_PATTERN.matcher(strValue).matches())
            {
                return strValue;
            }
            else
            {
                String invalidColorMessage = component.getInvalidColorMessage();
                if (invalidColorMessage != null && !invalidColorMessage.isEmpty())
                {
                    throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, invalidColorMessage,
                            invalidColorMessage));
                }
                else
                {
                    // throw new ConverterException(_MessageUtils.getMessage(facesContext,
                    // facesContext.getViewRoot().getLocale(),
                    // FacesMessage.SEVERITY_ERROR, UIInput.REQUIRED_MESSAGE_ID,
                    // new Object[] { _MessageUtils.getLabel(facesContext,
                    // uiComponent) }));
                    // XXX: externalize and localize the message later!
                    throw new ConverterException(new FacesMessage("Provided value for component " + RendererUtils.getPathToComponent(uiComponent) + " is not a valid simple color: "
                            + value));
                }
            }
        }

    }

    public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException
    {
        throw new UnsupportedOperationException("This method should not be called since this converter is not for public usage.");
    }

}
