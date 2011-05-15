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

import org.apache.myfaces.html5.component.input.Html5BaseInputText;
import org.apache.myfaces.html5.renderkit.input.delegate.SuggestionRendererHelper;
import org.apache.myfaces.html5.renderkit.util.*;

import javax.faces.component.UIComponent;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * An extensible base of the Html5 input renderers.
 * 
 * @author Ali Ok
 * 
 */
public abstract class Html5BaseInputTextRenderer extends Renderer
{

    private static final Logger log = Logger.getLogger(Html5BaseInputTextRenderer.class.getName());

    @Override
    public void encodeEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException
    {
        RendererUtils.checkParamValidity(facesContext, uiComponent, getComponentClass());

        Html5BaseInputText component = (Html5BaseInputText) uiComponent;

        // first, check whether we have behaviors, and render jsf.js if necessary
        Map<String, List<ClientBehavior>> behaviors = component.getClientBehaviors();
        if (!behaviors.isEmpty())
        {
            ResourceUtils.renderDefaultJsfJsInlineIfNecessary(facesContext, facesContext.getResponseWriter());
        }

        // check suggestions
        SuggestionRendererHelper suggestionRendererHelper = getSuggestionRendererHelper(component);
        if (suggestionRendererHelper != null)
            suggestionRendererHelper.checkSuggestions(component);

        // are suggestions available?
        boolean shouldGenerateDatalist = false;

        if (suggestionRendererHelper != null)
        {
            shouldGenerateDatalist = suggestionRendererHelper.shouldGenerateDatalist(component);
            if (shouldGenerateDatalist)
            {
                String datalistId = facesContext.getViewRoot().createUniqueId();
                if (log.isLoggable(Level.FINE))
                    log.fine("datalist will be created with id '" + datalistId + "'");
                component.getAttributes().put(JsfProperties.DATALIST_PROP, datalistId);
            }
        }

        // render the input
        if (log.isLoggable(Level.FINE))
            log.fine("will render input");
        renderInput(facesContext, component);

        // render suggestions
        if (suggestionRendererHelper != null && shouldGenerateDatalist)
        {
            if (log.isLoggable(Level.FINE))
                log.fine("will render generated datalist");
            suggestionRendererHelper.renderDataList(facesContext, component);
        }

    }

    protected void renderInput(FacesContext facesContext, UIComponent component)
        throws IOException
    {
        //allow subclasses to render custom attributes by separating rendering begin and end
        renderInputBegin(facesContext, component);
        renderInputEnd(facesContext, component);
    }

    protected void renderInputBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException
    {
        RendererUtils.checkParamValidity(facesContext, uiComponent, getComponentClass());

        ResponseWriter writer = facesContext.getResponseWriter();

        Html5BaseInputText component = (Html5BaseInputText) uiComponent;

        String clientId = component.getClientId(facesContext);
        String value = RendererUtils.getStringValue(facesContext, component);

        writer.startElement(HTML5.INPUT_ELEM, component);
        writer.writeAttribute(HTML5.ID_ATTR, clientId, null);
        writer.writeAttribute(HTML5.NAME_ATTR, clientId, null);

        //allow extending classes to modify html input element's type
        String inputHtmlType = getInputHtmlType(component);
        writer.writeAttribute(HTML5.TYPE_ATTR, inputHtmlType, null);

        if (value != null)
        {
            writer.writeAttribute(HTML5.VALUE_ATTR, value, JsfProperties.VALUE_PROP);
        }

        Map<String, List<ClientBehavior>> behaviors = component.getClientBehaviors();

        Html5RendererUtils.renderBehaviorizedOnchangeEventHandler(facesContext, writer, component, behaviors);
        Html5RendererUtils.renderBehaviorizedEventHandlers(facesContext, writer, component, behaviors);
        Html5RendererUtils.renderBehaviorizedFieldEventHandlersWithoutOnchange(facesContext, writer, component, behaviors);

        renderPassThruAttrsAndEvents(facesContext, uiComponent);
    }

    //to make this extendible
    protected void renderPassThruAttrsAndEvents(FacesContext facesContext, UIComponent uiComponent)
            throws IOException
    {
        Map<String, List<ClientBehavior>> clientBehaviors = ((ClientBehaviorHolder)uiComponent).getClientBehaviors();

        Html5RendererUtils.renderPassThroughAttributes(facesContext.getResponseWriter(), uiComponent, PassThroughAttributes.BASE_INPUT);
        Html5RendererUtils.renderPassThroughClientBehaviorEventHandlers(facesContext, uiComponent, PassThroughClientBehaviorEvents.BASE_INPUT, clientBehaviors);

        Html5RendererUtils.renderPassThroughAttributes(facesContext.getResponseWriter(), uiComponent, getExtraPassThroughAttributes());
    }

    protected void renderInputEnd(FacesContext facesContext, UIComponent component) throws IOException
    {
        ResponseWriter writer = facesContext.getResponseWriter();

        writer.endElement(HTML5.INPUT_ELEM);
    }

    /**
     * Returns the HTML type attribute of HTML input element, which is being rendered.
     */
    protected abstract String getInputHtmlType(Html5BaseInputText component);

    /**
     * Returns pass through attributes that are not present in {@link javax.faces.component.html.HtmlInputText} Child
     * component classes can override this method to modify pass through attributes, which are rendered at
     * {@link Html5BaseInputTextRenderer#renderInputBegin(FacesContext, UIComponent)}
     * 
     */
    protected abstract Map<String, String> getExtraPassThroughAttributes();

    /**
     * Component class to check the type of rendered component.
     * 
     */
    protected abstract Class<? extends javax.faces.component.html.HtmlInputText> getComponentClass();

    /**
     * Returns the {@link SuggestionRendererHelper} instance, to render suggestion related markup.
     * 
     */
    public abstract SuggestionRendererHelper getSuggestionRendererHelper(Html5BaseInputText component);

    @Override
    public boolean getRendersChildren()
    {
        return true;
    }
}
