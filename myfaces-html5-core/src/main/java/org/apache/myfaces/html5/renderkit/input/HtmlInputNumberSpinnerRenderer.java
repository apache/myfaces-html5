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
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFRenderer;
import org.apache.myfaces.html5.component.input.HtmlInputNumberSpinner;
import org.apache.myfaces.html5.renderkit.util.HTML5;
import org.apache.myfaces.html5.renderkit.util.PassThroughAttributes;

/**
 * Renderer for hx:inputNumberSpinner. <br/>
 * Extends the renderer of hx:inputNumberSlider with readonly and required pass thru properties.
 * 
 * @author Ali Ok
 * 
 */
@JSFRenderer(renderKitId = "HTML_BASIC", family = "javax.faces.Input", type = "org.apache.myfaces.html5.NumberSpinner")
public class HtmlInputNumberSpinnerRenderer extends HtmlInputNumberSliderRenderer
{
    @Override
    protected void renderInputBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException
    {
        // do nothing extra. additional attributes required and readonly will be rendered as passthrough
        super.renderInputBegin(facesContext, uiComponent);
    }

    @Override
    protected Map<String, String> getExtraPassThroughAttributes()
    {
        return PassThroughAttributes.INPUT_NUMBER_SPINNER;
    }

    @Override
    protected Class<? extends HtmlInputText> getComponentClass()
    {
        return HtmlInputNumberSpinner.class;
    }

    @Override
    protected String getInputHtmlType(UIComponent component)
    {
        return HTML5.INPUT_TYPE_NUMBER;
    }
}
