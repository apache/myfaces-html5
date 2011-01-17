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

package org.apache.myfaces.html5.renderkit.effect;

import org.apache.commons.lang.StringUtils;
import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFRenderer;
import org.apache.myfaces.html5.component.effect.AbstractEffectOutput;
import org.apache.myfaces.html5.component.effect.AbstractEffects;
import org.apache.myfaces.html5.renderkit.util.CSS;
import org.apache.myfaces.html5.renderkit.util.HTML5;
import org.apache.myfaces.html5.renderkit.util.Html5RendererUtils;
import org.apache.myfaces.shared_html5.renderkit.RendererUtils;
import org.apache.myfaces.shared_html5.renderkit.html.HTML;
import org.apache.myfaces.shared_html5.renderkit.html.HtmlRenderer;
import org.apache.myfaces.view.facelets.PostBuildComponentTreeOnRestoreViewEvent;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.event.*;
import java.io.IOException;

@ListenersFor({
        @ListenerFor(systemEventClass = PostAddToViewEvent.class),
        @ListenerFor(systemEventClass = PostBuildComponentTreeOnRestoreViewEvent.class)
})
@JSFRenderer(renderKitId = "HTML_BASIC", family = "org.apache.myfaces.EffectOutput", type = "org.apache.myfaces.html5.EffectOutput")
public class EffectOutputRenderer extends HtmlRenderer implements ComponentSystemEventListener {

    @Override
    public boolean getRendersChildren() {
        return true;
    }

    @Override
    public void encodeBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {
        super.encodeBegin(facesContext, uiComponent);

        RendererUtils.checkParamValidity(facesContext, uiComponent, AbstractEffectOutput.class);

        AbstractEffectOutput component = (AbstractEffectOutput) uiComponent;

        ResponseWriter writer = facesContext.getResponseWriter();

        writer.startElement(HTML.STYLE_ELEM, component);
    }

    @Override
    public void encodeChildren(FacesContext facesContext, UIComponent uiComponent) throws IOException {
        RendererUtils.checkParamValidity(facesContext, uiComponent, AbstractEffectOutput.class);

        AbstractEffectOutput component = (AbstractEffectOutput) uiComponent;

        ResponseWriter writer = facesContext.getResponseWriter();

        // write id
        final String id = component.getClientId(facesContext);
        writer.writeAttribute(HTML5.ID_ATTR, id, null);

        //let child components render themselves
        writer.writeText("." + Html5RendererUtils.escapeCssSelector(id) + " { ", component, null);
        //TODO: allow only BaseEffect children!

        super.encodeChildren(facesContext, component);

        writer.writeText(" } ", component, null);
    }

    @Override
    public void encodeEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {
        // just close the element
        super.encodeEnd(facesContext, uiComponent);

        ResponseWriter writer = facesContext.getResponseWriter();

        writer.endElement(HTML.STYLE_ELEM);
    }

    public void processEvent(ComponentSystemEvent event) {
        UIComponent component = event.getComponent();
        FacesContext facesContext = FacesContext.getCurrentInstance();

        final AbstractEffectOutput abstractEffects = (AbstractEffectOutput) component;

        String target = abstractEffects.getTarget();

        //TODO: think about ajax PPR
        facesContext.getViewRoot().addComponentResource(facesContext, component, target);
    }
}
