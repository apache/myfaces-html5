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
import org.apache.myfaces.html5.component.effect.AbstractEffects;
import org.apache.myfaces.html5.renderkit.util.CSS;
import org.apache.myfaces.html5.renderkit.util.HTML5;
import org.apache.myfaces.html5.renderkit.util.Html5RendererUtils;
import org.apache.myfaces.shared_html5.renderkit.RendererUtils;
import org.apache.myfaces.shared_html5.renderkit.html.HTML;
import org.apache.myfaces.shared_html5.renderkit.html.HtmlRenderer;
import org.apache.myfaces.view.facelets.PostBuildComponentTreeOnRestoreViewEvent;

import javax.faces.component.UIComponent;
import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.event.*;
import java.io.IOException;

@ListenersFor({
        @ListenerFor(systemEventClass = PostAddToViewEvent.class),
        @ListenerFor(systemEventClass = PostBuildComponentTreeOnRestoreViewEvent.class)
})
@JSFRenderer(renderKitId = "HTML_BASIC", family = "org.apache.myfaces.Effects", type = "org.apache.myfaces.html5.Effects")
public class EffectsRenderer extends EffectOutputRenderer implements ComponentSystemEventListener {

    @Override
    public void encodeChildren(FacesContext facesContext, UIComponent uiComponent) throws IOException {
        RendererUtils.checkParamValidity(facesContext, uiComponent, AbstractEffects.class);
        AbstractEffects component = (AbstractEffects) uiComponent;
        ResponseWriter writer = facesContext.getResponseWriter();

        super.encodeChildren(facesContext, component);

        //write CSS class definition with animation definition
        writer.writeText(getTransitionDefinition(component),component, null);
    }

    protected String getTransitionDefinition(AbstractEffects component){
        final String timingFunction = component.getTimingFunction();
        final String duration = getTimeValue(component.getDuration());
        final String delay = getTimeValue(component.getDelay());

        StringBuilder builder = new StringBuilder();
        builder.append("#").append(Html5RendererUtils.escapeCssSelector(component.getTransitionComponentId()));
        builder.append(" { ");

        //for now
        appendIfNotNull(builder, "-webkit-" + CSS.TRANSITION_PROPERTY_PROP, "all");
        appendIfNotNull(builder, "-webkit-" + CSS.TRANSITION_DURATION_PROP, duration);
        appendIfNotNull(builder, "-webkit-" + CSS.TRANSITION_TIMING_FUNCTION_PROP, timingFunction);
        appendIfNotNull(builder, "-webkit-" + CSS.TRANSITION_DELAY_PROP, delay);

        appendIfNotNull(builder, "-o-" + CSS.TRANSITION_PROPERTY_PROP, "all");
        appendIfNotNull(builder, "-o-" + CSS.TRANSITION_DURATION_PROP, duration);
        appendIfNotNull(builder, "-o-" + CSS.TRANSITION_TIMING_FUNCTION_PROP, timingFunction);
        appendIfNotNull(builder, "-o-" + CSS.TRANSITION_DELAY_PROP, delay);

        builder.append("} ");

        return builder.toString();
    }

    private static void appendIfNotNull(StringBuilder builder, String propName, String propValue) {
        if(StringUtils.isBlank(propName))
            throw new RuntimeException("Propname cannot be null");

        if(!StringUtils.isBlank(propValue))
            builder.append(propName).append(": ").append(propValue).append("; ");
    }

    private static String getTimeValue(String s) {
        if(StringUtils.isBlank(s))
            return null;
        else if(s.endsWith("s") || s.endsWith("ms"))
            return s;
        else
            return s + "s";
    }


    public void processEvent(ComponentSystemEvent event) {
        UIComponent component = event.getComponent();
        FacesContext facesContext = FacesContext.getCurrentInstance();

        final AbstractEffects abstractEffects = (AbstractEffects) component;

        if(StringUtils.isBlank(abstractEffects.getTransitionComponentId())) {
            abstractEffects.setTransitionComponentId(component.getParent().getClientId(facesContext));
        }

        facesContext.getViewRoot().addComponentResource(facesContext, component, "body");
    }
}
