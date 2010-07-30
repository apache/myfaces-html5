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
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.component.UIComponent;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.context.FacesContext;

import org.apache.myfaces.html5.component.input.Html5BaseInputText;
import org.apache.myfaces.html5.renderkit.input.delegate.SuggestionRendererHelper;
import org.apache.myfaces.html5.renderkit.util.Html5RendererUtils;
import org.apache.myfaces.html5.renderkit.util.JsfProperties;
import org.apache.myfaces.html5.renderkit.util.PassThroughClientBehaviorEvents;
import org.apache.myfaces.shared_html5.renderkit.RendererUtils;
import org.apache.myfaces.shared_html5.renderkit.html.HtmlTextRendererBase;
import org.apache.myfaces.shared_html5.renderkit.html.util.ResourceUtils;

/**
 * An extensible base of the Html5 input renderers.
 * 
 * @author Ali Ok
 * 
 */
public abstract class Html5BaseInputTextRenderer extends HtmlTextRendererBase
{

    private static final Logger log = Logger.getLogger(Html5BaseInputTextRenderer.class.getName());

    @Override
    public void encodeEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException
    {
        RendererUtils.checkParamValidity(facesContext, uiComponent, getComponentClass());

        Html5BaseInputText component = (Html5BaseInputText) uiComponent;

        // first, check whether we have behaviors, and render jsf.js if necessary
        Map<String, List<ClientBehavior>> behaviors = null;
        behaviors = ((ClientBehaviorHolder) component).getClientBehaviors();
        if (!behaviors.isEmpty())
        {
            if (log.isLoggable(Level.FINE))
                log.fine("component " + RendererUtils.getPathToComponent(uiComponent) + " has behaviors, rendering jsf.js");

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

    @Override
    protected void renderInputBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException
    {
        RendererUtils.checkParamValidity(facesContext, uiComponent, getComponentClass());

        // let parent render standard attributes
        super.renderInputBegin(facesContext, uiComponent);

        if (log.isLoggable(Level.FINE))
            log.fine("parent rendered standart stuff. rendering additional pass thru attrs");
        
        renderPassThruAttrsAndEvents(facesContext, uiComponent);
    }

    //to make this extendible
    protected void renderPassThruAttrsAndEvents(FacesContext facesContext, UIComponent uiComponent)
            throws IOException
    {
        Map<String, List<ClientBehavior>> clientBehaviors = ((ClientBehaviorHolder)uiComponent).getClientBehaviors();
        
        Html5RendererUtils.renderPassThroughClientBehaviorEventHandlers(facesContext, uiComponent, PassThroughClientBehaviorEvents.BASE_INPUT, clientBehaviors);
        
        Html5RendererUtils.renderPassThroughAttributes(facesContext.getResponseWriter(), uiComponent, getExtraPassThroughAttributes());
    }

    @Override
    protected void renderInputEnd(FacesContext facesContext, UIComponent component) throws IOException
    {
        // do nothing special. override for improving readability.
        super.renderInputEnd(facesContext, component);
    }

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
