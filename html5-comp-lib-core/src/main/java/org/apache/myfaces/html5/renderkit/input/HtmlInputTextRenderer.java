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

import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.render.Renderer;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFRenderer;
import org.apache.myfaces.html5.component.input.HtmlInputText;
import org.apache.myfaces.html5.renderkit.input.delegate.HtmlInputTextRendererDelegate;
import org.apache.myfaces.html5.renderkit.input.delegate.HtmlSecretRendererDelegate;
import org.apache.myfaces.html5.renderkit.input.delegate.HtmlTextAreaRendererDelegate;
import org.apache.myfaces.html5.renderkit.util.JsfProperties;
import org.apache.myfaces.shared_html5.renderkit.RendererUtils;

/**
 * Renderer for < hx:inputText > component.
 * <p>
 * Uses delegate renderers for different types.
 * 
 * @author Ali Ok
 * 
 */
@JSFRenderer(renderKitId = "HTML_BASIC", family = "javax.faces.Input", type = "org.apache.myfaces.html5.Text")
public class HtmlInputTextRenderer extends Renderer
{

    protected static final String[] ALLOWED_INPUT_TYPES = new String[]
    {
            JsfProperties.INPUTTEXT_TYPE_TEXT, JsfProperties.INPUTTEXT_TYPE_SEARCH, JsfProperties.INPUTTEXT_TYPE_TEL,
            JsfProperties.INPUTTEXT_TYPE_URL, JsfProperties.INPUTTEXT_TYPE_PASSWORD, JsfProperties.INPUTTEXT_TYPE_TEXTAREA
    };

    private static final Logger log = Logger.getLogger(HtmlInputTextRendererDelegate.class.getName());

    protected Renderer _textTypeRendererDelegate = null;
    protected Renderer _passwordTypeRendererDelegate = null;
    protected Renderer _textareaTypeRendererDelegate = null;

    public HtmlInputTextRenderer()
    {
        super();

        // let's create all three delegates since renderers are created once in application lifecycle
        _textTypeRendererDelegate = new HtmlInputTextRendererDelegate();
        _passwordTypeRendererDelegate = new HtmlSecretRendererDelegate();
        _textareaTypeRendererDelegate = new HtmlTextAreaRendererDelegate();
    }

    @Override
    public String convertClientId(FacesContext context, String clientId)
    {
        // cannot get delegate here, since we don't have component
        // thus, call super
        // this method is not overridden at the possible delegates anyway
        return super.convertClientId(context, clientId);
    }

    @Override
    public void decode(FacesContext context, UIComponent component)
    {
        checkInputHtmlType(component);
        getDelegate(context, component).decode(context, component);
    }

    /**
     * Get delegate based on components type.
     */
    protected Renderer getDelegate(FacesContext context, UIComponent component)
    {
        // first, set component's input type
        checkInputHtmlType(component);

        String type = ((HtmlInputText) component).getType();
        if (type == null || type.equals("")) // should never happen; default value should be set! use renderer of
        // default case anyway.
        {
            if (log.isLoggable(Level.FINE))
                log.warning("should never happen! type is empty!");
            return _textTypeRendererDelegate;
        }
        else if (type.equals(JsfProperties.INPUTTEXT_TYPE_TEXT) || type.equals(JsfProperties.INPUTTEXT_TYPE_SEARCH)
                || type.equals(JsfProperties.INPUTTEXT_TYPE_URL) || type.equals(JsfProperties.INPUTTEXT_TYPE_TEL))
        {
            return _textTypeRendererDelegate;
        }
        else if (type.equals(JsfProperties.INPUTTEXT_TYPE_PASSWORD))
        {
            return _passwordTypeRendererDelegate;
        }
        else if (type.equals(JsfProperties.INPUTTEXT_TYPE_TEXTAREA))
        {
            return _textareaTypeRendererDelegate;
        }
        else
        {
            throw new IllegalStateException("Input type of component " + RendererUtils.getPathToComponent(component) + " is not one of the expected types: \""
                    + JsfProperties.INPUTTEXT_TYPE_TEXT + "\", \"" + JsfProperties.INPUTTEXT_TYPE_SEARCH + "\", \""
                    + JsfProperties.INPUTTEXT_TYPE_URL + "\" ,\"" + JsfProperties.INPUTTEXT_TYPE_TEL + "\". Provided: \""
                    + type + "\".");
        }
    }

    @Override
    public void encodeBegin(FacesContext context, UIComponent component) throws IOException
    {
        getDelegate(context, component).encodeBegin(context, component);
    }

    @Override
    public void encodeChildren(FacesContext context, UIComponent component) throws IOException
    {
        getDelegate(context, component).encodeChildren(context, component);
    }

    @Override
    public void encodeEnd(FacesContext context, UIComponent component) throws IOException
    {
        getDelegate(context, component).encodeEnd(context, component);
    }

    @Override
    public Object getConvertedValue(FacesContext context, UIComponent component, Object submittedValue)
            throws ConverterException
    {
        return getDelegate(context, component).getConvertedValue(context, component, submittedValue);
    }

    @Override
    public boolean getRendersChildren()
    {
        return true;
    }

    /**
     * Checks and sets the type of the component. <br/>
     * If the component doesn't have a type yet:
     * <ul>
     * <li>If it has rows property larger than 1, set type to textArea.</li>
     * <li>Otherwise, set type to text</li>
     * </ul>
     * 
     * @throws FacesException
     *             if user didn't specify one of the allowed.
     */
    protected void checkInputHtmlType(UIComponent uiComponent)
    {
        // TODO: this is called from too many places! reduce them!
        HtmlInputText component = (HtmlInputText) uiComponent;
        String type = component.getType();
        if (log.isLoggable(Level.FINE))
            log.fine("type is :" + type);

        if (type == null || type.isEmpty())
        {
            // set type to textarea, if no type is set and rows are set
            if (component.getRows() > HtmlInputText.ROWS_DEFAULT_VALUE)
            {
                if (log.isLoggable(Level.FINE))
                    log.fine("type set to textarea since type is not set, and rows attr is set");
                component.setType(JsfProperties.INPUTTEXT_TYPE_TEXTAREA);
            }
            else
                component.setType(JsfProperties.INPUTTEXT_TYPE_TEXT);

        }
        else
        {
            if (!(Arrays.asList(ALLOWED_INPUT_TYPES).contains(type)))
                throw new FacesException("\"type\" attribute of component " + RendererUtils.getPathToComponent(uiComponent) + " can be one of " + Arrays.toString(ALLOWED_INPUT_TYPES)
                        + ". You provided: \"" + type + "\"");
        }
    }

}
