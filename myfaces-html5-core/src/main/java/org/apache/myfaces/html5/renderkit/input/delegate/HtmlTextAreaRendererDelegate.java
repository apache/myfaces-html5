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

import org.apache.commons.collections.MapUtils;
import org.apache.myfaces.html5.component.input.Html5BaseInputText;
import org.apache.myfaces.html5.renderkit.util.*;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UIOutput;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.ConverterException;
import javax.faces.render.Renderer;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Delegate renderer that is used when the type of < hx:input > is one of "textarea".
 * 
 * @author Ali Ok
 * 
 */
public class HtmlTextAreaRendererDelegate extends Renderer
{

     public void encodeEnd(FacesContext facesContext, UIComponent uiComponent)
            throws IOException
    {
        RendererUtils.checkParamValidity(facesContext, uiComponent, UIInput.class);

        Html5BaseInputText component = (Html5BaseInputText) uiComponent;

        Map<String, List<ClientBehavior>> behaviors = component.getClientBehaviors();
        if (MapUtils.isNotEmpty(behaviors))
        {
            ResourceUtils.renderDefaultJsfJsInlineIfNecessary(facesContext, facesContext.getResponseWriter());
        }

        encodeTextArea(facesContext, component);

    }

     protected void encodeTextArea(FacesContext facesContext, Html5BaseInputText component) throws IOException {
       //allow subclasses to render custom attributes by separating rendering begin and end
        renderTextAreaBegin(facesContext, component);
        renderTextAreaValue(facesContext, component);
        renderTextAreaEnd(facesContext, component);

    }

    //Subclasses can set the value of an attribute before, or can render a custom attribute after calling this method
    protected void renderTextAreaBegin(FacesContext facesContext, Html5BaseInputText component) throws IOException
    {
        ResponseWriter writer = facesContext.getResponseWriter();
        writer.startElement(HTML5.TEXTAREA_ELEM, component);

        writer.writeAttribute(HTML5.ID_ATTR, component.getClientId(facesContext), null);
        writer.writeAttribute(HTML5.NAME_ATTR, component.getClientId(facesContext), null);

        Map<String, List<ClientBehavior>> behaviors  = component.getClientBehaviors();
        Html5RendererUtils.renderBehaviorizedOnchangeEventHandler(facesContext, writer, component, behaviors);
        Html5RendererUtils.renderBehaviorizedEventHandlers(facesContext, writer, component, behaviors);
        Html5RendererUtils.renderBehaviorizedFieldEventHandlersWithoutOnchange(facesContext, writer, component, behaviors);

        Html5RendererUtils.renderPassThroughClientBehaviorEventHandlers(facesContext, component,
                PassThroughClientBehaviorEvents.BASE_INPUT, behaviors);

        Html5RendererUtils.renderPassThroughAttributes(facesContext.getResponseWriter(), component,
                PassThroughAttributes.BASE_INPUT);

        Html5RendererUtils.renderPassThroughAttributes(facesContext.getResponseWriter(), component,
                PassThroughAttributes.INPUT_TEXTAREA);
    }

    //Subclasses can override the writing of the "text" value of the textarea
    protected void renderTextAreaValue(FacesContext facesContext, UIComponent uiComponent) throws IOException
    {
        ResponseWriter writer = facesContext.getResponseWriter();
        String strValue = RendererUtils.getStringValue(facesContext, uiComponent);
        writer.writeText(strValue, JsfProperties.VALUE_PROP);
    }

    protected void renderTextAreaEnd(FacesContext facesContext,
            Html5BaseInputText component) throws IOException
    {
        facesContext.getResponseWriter().endElement(HTML5.TEXTAREA_ELEM);
    }

    public void decode(FacesContext facesContext, UIComponent component)
    {
        RendererUtils.checkParamValidity(facesContext, component, Html5BaseInputText.class);
        Html5RendererUtils.decodeUIInput(facesContext, component);
        if (!((Html5BaseInputText)component).isDisabled())
        {
            Html5RendererUtils.decodeClientBehaviors(facesContext, component);
        }
    }

    public Object getConvertedValue(FacesContext facesContext, UIComponent uiComponent, Object submittedValue) throws ConverterException
    {
        RendererUtils.checkParamValidity(facesContext, uiComponent, UIOutput.class);
        return RendererUtils.getConvertedUIOutputValue(facesContext,
                                                       (UIOutput)uiComponent,
                                                       submittedValue);
    }

}
