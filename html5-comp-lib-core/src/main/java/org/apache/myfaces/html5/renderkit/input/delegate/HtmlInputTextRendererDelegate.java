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
package org.apache.myfaces.html5.renderkit.input.delegate;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.apache.myfaces.html5.component.input.Html5BaseInputText;
import org.apache.myfaces.html5.component.input.HtmlInputText;
import org.apache.myfaces.html5.renderkit.input.Html5BaseInputTextRenderer;
import org.apache.myfaces.html5.renderkit.input.util.InputPatternRendererUtil;
import org.apache.myfaces.html5.renderkit.util.HTML5;
import org.apache.myfaces.html5.renderkit.util.PassThroughAttributes;
import org.apache.myfaces.shared_html5.renderkit.RendererUtils;

/**
 * Delegate renderer that is used when the type of < hx:input > is one of "text", "search", "url" or "tel".
 * 
 * @author Ali Ok
 * 
 */
public class HtmlInputTextRendererDelegate extends Html5BaseInputTextRenderer
{

    private static final String[] ALLOWED_TYPES = new String[]
    {
            HTML5.INPUT_TYPE_TEXT, HTML5.INPUT_TYPE_SEARCH, HTML5.INPUT_TYPE_TEL, HTML5.INPUT_TYPE_URL
    };

    private static final Logger log = Logger.getLogger(HtmlInputTextRendererDelegate.class.getName());

    private SuggestionRendererHelper _suggestionRendererHelper;

    @Override
    protected void renderInputBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException
    {
        RendererUtils.checkParamValidity(facesContext, uiComponent, HtmlInputText.class);

        HtmlInputText component = (HtmlInputText) uiComponent;

        _checkInputHtmlType(component);

        // pass cols -> size to make super render size correctly!
        component.setSize(component.getCols());

        // let super renders inherited stuff
        super.renderInputBegin(facesContext, uiComponent);

        // render pattern here
        InputPatternRendererUtil.renderPattern(facesContext, component);
    }

    @Override
    protected Class<? extends javax.faces.component.html.HtmlInputText> getComponentClass()
    {
        return HtmlInputText.class;
    }

    @Override
    protected Map<String, String> getExtraPassThroughAttributes()
    {
        return PassThroughAttributes.INPUT_TEXT;
    }

    /**
     * This method checks whether html input type value is one of the allowed values.
     */
    private void _checkInputHtmlType(UIComponent uiComponent)
    {
        HtmlInputText component = (HtmlInputText) uiComponent;
        String type = component.getType();
        if (log.isLoggable(Level.FINE))
            log.fine("initial type is: " + type);
        if (type == null || type.isEmpty())
        { // if not set, set default value.
            if (log.isLoggable(Level.FINE))
                log.fine("setting default type " + HTML5.INPUT_TYPE_TEXT);

            component.setType(HTML5.INPUT_TYPE_TEXT);
        }
        else
        {
            if (!(Arrays.asList(ALLOWED_TYPES).contains(type)))
                throw new FacesException(
                        "\"type\" attribute can be one of "+ Arrays.toString(ALLOWED_TYPES) +". Provided: \""
                                + type + "\"");
        }
    }

    @Override
    protected String getInputHtmlType(UIComponent component)
    {
        // obj type check for component is done in #encodeEnd, no need to check it again
        return ((HtmlInputText) component).getType();
    }

    @Override
    public SuggestionRendererHelper getSuggestionRendererHelper(Html5BaseInputText component)
    {
        if (_suggestionRendererHelper == null)
            _suggestionRendererHelper = new HtmlTextInputSuggestionRendererHelper();

        return _suggestionRendererHelper;
    }

}
