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
package org.apache.myfaces.html5.renderkit.panel;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.component.UIComponent;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFRenderer;
import org.apache.myfaces.html5.component.panel.AbstractDiv;
import org.apache.myfaces.html5.renderkit.util.HTML5;
import org.apache.myfaces.html5.renderkit.util.Html5RendererUtils;
import org.apache.myfaces.html5.renderkit.util.PassThroughAttributes;
import org.apache.myfaces.html5.renderkit.util.PassThroughClientBehaviorEvents;
import org.apache.myfaces.shared_html5.renderkit.RendererUtils;
import org.apache.myfaces.shared_html5.renderkit.html.HtmlRenderer;
import org.apache.myfaces.shared_impl.renderkit.html.HtmlRendererUtils;

/**
 * Div renderer.
 * 
 * @author Ali Ok
 * 
 */
@JSFRenderer(renderKitId = "HTML_BASIC", family = "org.apache.myfaces.Div", type = "org.apache.myfaces.html5.Div")
public class DivRenderer extends HtmlRenderer
{

    private static final Logger log = Logger.getLogger(DivRenderer.class.getName());

    @Override
    public void encodeBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException
    {
        if (log.isLoggable(Level.FINE))
            log.fine("encodeBegin");

        super.encodeBegin(facesContext, uiComponent);

        RendererUtils.checkParamValidity(facesContext, uiComponent, AbstractDiv.class);

        ResponseWriter writer = facesContext.getResponseWriter();

        AbstractDiv component = (AbstractDiv) uiComponent;

        writer.startElement(HTML5.DIV_ELEM, uiComponent);

        // write id
        writer.writeAttribute(HTML5.ID_ATTR, component.getClientId(facesContext), null);

        renderPassThruAttrsAndEvents(facesContext, uiComponent);

    }

    // to make extensible rendering those, extracted into a protected method.
    protected void renderPassThruAttrsAndEvents(FacesContext facesContext, UIComponent uiComponent) throws IOException
    {
        Map<String, List<ClientBehavior>> clientBehaviors = ((ClientBehaviorHolder) uiComponent).getClientBehaviors();

        Html5RendererUtils.renderPassThroughClientBehaviorEventHandlers(facesContext, uiComponent,
                PassThroughClientBehaviorEvents.DIV, clientBehaviors);

        Html5RendererUtils.renderPassThroughAttributes(facesContext.getResponseWriter(), uiComponent,
                PassThroughAttributes.DIV);
    }

    @Override
    public void encodeEnd(FacesContext facesContext, UIComponent component) throws IOException
    {
        if (log.isLoggable(Level.FINE))
            log.fine("encodeEnd");
        // just close the element
        super.encodeEnd(facesContext, component);

        ResponseWriter writer = facesContext.getResponseWriter();

        writer.endElement(HTML5.DIV_ELEM);
    }

    @Override
    public boolean getRendersChildren()
    {
        return false;
    }

    @Override
    public void decode(FacesContext context, UIComponent component)
    {
        // Check for npe
        super.decode(context, component);

        HtmlRendererUtils.decodeClientBehaviors(context, component);
    }

}
