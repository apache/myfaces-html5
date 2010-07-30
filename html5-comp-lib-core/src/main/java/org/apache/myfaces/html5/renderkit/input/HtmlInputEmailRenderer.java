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
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFRenderer;
import org.apache.myfaces.html5.component.HtmlInputEmail;
import org.apache.myfaces.html5.component.input.Html5BaseInputText;
import org.apache.myfaces.html5.renderkit.input.delegate.HtmlTextInputSuggestionRendererHelper;
import org.apache.myfaces.html5.renderkit.input.delegate.SuggestionRendererHelper;
import org.apache.myfaces.html5.renderkit.input.util.Html5EmailConverter;
import org.apache.myfaces.html5.renderkit.input.util.InputPatternRendererUtil;
import org.apache.myfaces.html5.renderkit.util.HTML5;
import org.apache.myfaces.html5.renderkit.util.PassThroughAttributes;
import org.apache.myfaces.shared_html5.renderkit.RendererUtils;

/**
 * Renderer for < hx:inputEmail > component.
 * 
 * @author Ali Ok
 * 
 */
@JSFRenderer(renderKitId = "HTML_BASIC", family = "javax.faces.Input", type = "org.apache.myfaces.html5.Email")
public class HtmlInputEmailRenderer extends Html5BaseInputTextRenderer
{

    private SuggestionRendererHelper _suggestionRendererHelper;

    @Override
    // overridden to validate the input against being an email and do a conversion if multiple=true
    public Object getConvertedValue(FacesContext facesContext, UIComponent uiComponent, Object submittedValue)
            throws ConverterException
    {
        if (submittedValue != null && !(submittedValue instanceof String))
        {
            throw new IllegalArgumentException("Submitted value of type String for component : "
                    + RendererUtils.getPathToComponent(uiComponent) + " expected");
        }

        RendererUtils.checkParamValidity(facesContext, uiComponent, HtmlInputEmail.class);

        HtmlInputEmail component = (HtmlInputEmail) uiComponent;
        Converter converter = RendererUtils.findUIOutputConverter(facesContext, component);
        if (converter == null)
        {
            // min and max validation is done at DateTimeRangeValidator, not in converter.
            converter = new Html5EmailConverter();
            component.setConverter(converter);
        }

        return converter.getAsObject(facesContext, component, (String) submittedValue);
    }

    @Override
    protected void renderInputBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException
    {
        RendererUtils.checkParamValidity(facesContext, uiComponent, HtmlInputEmail.class);

        HtmlInputEmail component = (HtmlInputEmail) uiComponent;

        if (RendererUtils.findUIOutputConverter(facesContext, component) == null)
        {
            component.setConverter(new Html5EmailConverter());
        }

        // let super renders inherited stuff
        super.renderInputBegin(facesContext, uiComponent);

        // render pattern here
        InputPatternRendererUtil.renderPattern(facesContext, component);
    }

    @Override
    protected Map<String, String> getExtraPassThroughAttributes()
    {
        return PassThroughAttributes.INPUT_EMAIL;
    }

    @Override
    protected String getInputHtmlType(UIComponent component)
    {
        return HTML5.INPUT_TYPE_EMAIL;
    }

    @Override
    protected Class<? extends javax.faces.component.html.HtmlInputText> getComponentClass()
    {
        return HtmlInputEmail.class;
    }

    @Override
    public SuggestionRendererHelper getSuggestionRendererHelper(Html5BaseInputText component)
    {
        if (_suggestionRendererHelper == null)
            _suggestionRendererHelper = new HtmlTextInputSuggestionRendererHelper();

        return _suggestionRendererHelper;
    }

}
