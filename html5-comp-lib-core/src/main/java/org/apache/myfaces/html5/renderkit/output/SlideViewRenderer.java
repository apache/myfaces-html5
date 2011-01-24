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
import org.apache.myfaces.html5.component.output.AbstractSlideView;
import org.apache.myfaces.html5.renderkit.util.*;
import org.apache.myfaces.shared_html5.renderkit.RendererUtils;
import org.apache.myfaces.shared_html5.renderkit.html.HtmlRenderer;

import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;
import javax.faces.component.UIComponent;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import static org.apache.myfaces.html5.renderkit.util.CssSelectorBuilder.selector;

@ResourceDependencies(
{
        @ResourceDependency(name = "jsf.js", library = "javax.faces", target = "head"),
        @ResourceDependency(name = "common.js", library = "org.apache.myfaces.html5", target = "head"),
        @ResourceDependency(name = "effect.js", library = "org.apache.myfaces.html5", target = "head"),
        @ResourceDependency(name = "slide.js", library = "org.apache.myfaces.html5", target = "head"),
        @ResourceDependency(name = "html5.css", library = "org.apache.myfaces.html5", target = "head")
})
@JSFRenderer(renderKitId = "HTML_BASIC", family = "org.apache.myfaces.SlideView", type = "org.apache.myfaces.html5.SlideView")
public class SlideViewRenderer extends HtmlRenderer
{
    @Override
    public void encodeBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {
        super.encodeBegin(facesContext, uiComponent);

        RendererUtils.checkParamValidity(facesContext, uiComponent, AbstractSlideView.class);

        ResponseWriter writer = facesContext.getResponseWriter();

        AbstractSlideView component = (AbstractSlideView) uiComponent;

        String clientId = component.getClientId(facesContext);
        String escapedClientId = Html5RendererUtils.escapeCssSelector(clientId);

        writer.startElement(HTML5.STYLE_ELEM, component);

        double left = component.getSlideLeft();
        double width = component.getSlideWidth();
        double height = component.getSlideHeight();
        final double inactiveSlideScale = component.getInactiveSlideScale();
        final String timingFunction = component.getTimingFunction();
        final String duration = Html5RendererUtils.getTimeValue(component.getDuration());
        final String delay = Html5RendererUtils.getTimeValue(component.getDelay());

        StringBuilder builder = new StringBuilder();
        new CssBuilder().selector(selector(HTML5.DIV_ELEM).id(escapedClientId).child(selector(HTML5.DIV_ELEM).clazz(Skin.SLIDE)).build())
                .percentRule(CSS.WIDTH_PROP, width)
                .percentRule(CSS.HEIGHT_PROP, height)
                .rule("-webkit-transform", "scale(" + inactiveSlideScale +")")
                .rule("-o-transform", "scale(" + inactiveSlideScale +")")
                .rule("-moz-transform", "scale(" + inactiveSlideScale +")")
                .append(builder);

        new CssBuilder().selector(selector(HTML5.DIV_ELEM).id(escapedClientId).child(selector(HTML5.DIV_ELEM).clazz(Skin.SLIDE_TRANSITIONED)).build())
                //once for webkit
                .rule("-webkit-" + CSS.TRANSITION_PROPERTY_PROP ,"all")
                .rule("-webkit-" + CSS.TRANSITION_DURATION_PROP , duration)
                .rule("-webkit-" + CSS.TRANSITION_TIMING_FUNCTION_PROP, timingFunction)
                .rule("-webkit-" + CSS.TRANSITION_DELAY_PROP, delay)
                //and once for opera
                .rule("-o-" + CSS.TRANSITION_PROPERTY_PROP ,"all")
                .rule("-o-" + CSS.TRANSITION_DURATION_PROP, duration)
                .rule("-o-" + CSS.TRANSITION_TIMING_FUNCTION_PROP, timingFunction)
                .rule("-o-" + CSS.TRANSITION_DELAY_PROP, delay)
                .append(builder);

        new CssBuilder().selector(selector(HTML5.DIV_ELEM).id(escapedClientId).child(selector(HTML5.DIV_ELEM).clazz(Skin.SLIDE_PREVIOUS)).build())
                .percentRule(CSS.LEFT_PROP, left - width)
                .append(builder);

        new CssBuilder().selector(selector(HTML5.DIV_ELEM).id(escapedClientId).child(selector(HTML5.DIV_ELEM).clazz(Skin.SLIDE_ACTIVE)).build())
                .percentRule(CSS.LEFT_PROP, left)
                .append(builder);

        new CssBuilder().selector(selector(HTML5.DIV_ELEM).id(escapedClientId).child(selector(HTML5.DIV_ELEM).clazz(Skin.SLIDE_NEXT)).build())
                .percentRule(CSS.LEFT_PROP, left + width)
                .append(builder);

        new CssBuilder().selector(selector(HTML5.DIV_ELEM).id(escapedClientId).child(selector(HTML5.DIV_ELEM).clazz(Skin.SLIDE_HIDDEN_LEFT)).build())
                .percentRule(CSS.LEFT_PROP, left - width*2)
                .append(builder);

        new CssBuilder().selector(selector(HTML5.DIV_ELEM).id(escapedClientId).child(selector(HTML5.DIV_ELEM).clazz(Skin.SLIDE_HIDDEN_RIGHT)).build())
                .percentRule(CSS.LEFT_PROP, left + width*2)
                .append(builder);

        writer.write(builder.toString());
        writer.endElement(HTML5.STYLE_ELEM);


        //write component html side
        writer.startElement(HTML5.DIV_ELEM, uiComponent);

        // write id
        writer.writeAttribute(HTML5.ID_ATTR, component.getClientId(facesContext), null);

        renderPassThruAttrsAndEvents(facesContext, uiComponent);
    }

    // to make this extendible
    protected void renderPassThruAttrsAndEvents(FacesContext facesContext, UIComponent uiComponent) throws IOException
    {
        Map<String, List<ClientBehavior>> clientBehaviors = ((ClientBehaviorHolder) uiComponent).getClientBehaviors();

        Html5RendererUtils.renderPassThroughClientBehaviorEventHandlers(facesContext, uiComponent,
                PassThroughClientBehaviorEvents.SLIDE_VIEW, clientBehaviors);

        Html5RendererUtils.renderPassThroughAttributes(facesContext.getResponseWriter(), uiComponent,
                PassThroughAttributes.SLIDE_VIEW);
    }

    @Override
    public void encodeEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException
    {
        // just close the element
        super.encodeEnd(facesContext, uiComponent);

        ResponseWriter writer = facesContext.getResponseWriter();

        writer.endElement(HTML5.DIV_ELEM);

        AbstractSlideView component = (AbstractSlideView) uiComponent;

        String clientId = component.getClientId(facesContext);
        String widgetVar = null;
        if(StringUtils.isNotBlank(component.getWidgetVar()))
            widgetVar = component.getWidgetVar();
        else
            widgetVar = Html5RendererUtils.generateWidgetVar(clientId);

        writer.startElement(HTML5.SCRIPT_ELEM, component);
        String jsDefinition = MessageFormat.format("var {0} = new myfaces.html5.slide.SlideView(document.getElementById(''{1}''));", widgetVar, clientId);
        writer.write(jsDefinition);
        writer.endElement(HTML5.SCRIPT_ELEM);
    }
}