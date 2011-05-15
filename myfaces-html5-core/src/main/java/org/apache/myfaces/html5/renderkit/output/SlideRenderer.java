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

package org.apache.myfaces.html5.renderkit.output;

import org.apache.commons.lang.StringUtils;
import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFRenderer;
import org.apache.myfaces.html5.component.output.AbstractSlide;
import org.apache.myfaces.html5.renderkit.util.*;

import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;
import javax.faces.component.UIComponent;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@ResourceDependencies(
{
        @ResourceDependency(name = "jsf.js", library = "javax.faces", target = "head"),
        @ResourceDependency(name = "common.js", library = "org.apache.myfaces.html5", target = "head"),
        @ResourceDependency(name = "effect.js", library = "org.apache.myfaces.html5", target = "head"),
        @ResourceDependency(name = "slide.js", library = "org.apache.myfaces.html5", target = "head"),
        @ResourceDependency(name = "html5.css", library = "org.apache.myfaces.html5", target = "head")
})
@JSFRenderer(renderKitId = "HTML_BASIC", family = "org.apache.myfaces.Slide", type = "org.apache.myfaces.html5.Slide")
public class SlideRenderer extends Renderer
{
    @Override
    public void encodeBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {
        super.encodeBegin(facesContext, uiComponent);

        RendererUtils.checkParamValidity(facesContext, uiComponent, AbstractSlide.class);

        ResponseWriter writer = facesContext.getResponseWriter();

        AbstractSlide component = (AbstractSlide) uiComponent;

        writer.startElement("div", uiComponent);

        // write id
        writer.writeAttribute(HTML5.ID_ATTR, component.getClientId(facesContext), null);

        renderPassThruAttrsAndEvents(facesContext, uiComponent);
    }

    // to make this extendible
    protected void renderPassThruAttrsAndEvents(FacesContext facesContext, UIComponent uiComponent) throws IOException
    {
        Map<String, List<ClientBehavior>> clientBehaviors = ((ClientBehaviorHolder) uiComponent).getClientBehaviors();

        Html5RendererUtils.renderPassThroughClientBehaviorEventHandlers(facesContext, uiComponent,
                PassThroughClientBehaviorEvents.SLIDE, clientBehaviors);

        Html5RendererUtils.renderPassThroughAttributes(facesContext.getResponseWriter(), uiComponent,
                PassThroughAttributes.SLIDE);
    }

    @Override
    public void encodeEnd(FacesContext facesContext, UIComponent component) throws IOException
    {
        // just close the element
        super.encodeEnd(facesContext, component);

        ResponseWriter writer = facesContext.getResponseWriter();

        writer.endElement("div");
    }
}

