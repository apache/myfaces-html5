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
import org.apache.myfaces.html5.behavior.EffectBehavior;
import org.apache.myfaces.html5.component.effect.AbstractEffect;
import org.apache.myfaces.html5.component.effect.AbstractEffectPulse;
import org.apache.myfaces.html5.renderkit.util.CSS;
import org.apache.myfaces.html5.renderkit.util.HTML5;
import org.apache.myfaces.shared_html5.renderkit.RendererUtils;
import org.apache.myfaces.shared_html5.renderkit.html.HTML;
import org.apache.myfaces.shared_html5.renderkit.html.HtmlRenderer;

import javax.faces.component.UIComponent;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.ComponentSystemEventListener;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class BaseEffectRenderer extends HtmlRenderer implements ComponentSystemEventListener {

    private static final Logger log = Logger.getLogger(BaseEffectRenderer.class.getName());

    @Override
    public void encodeBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {
        if (log.isLoggable(Level.FINE))
            log.fine("encodeBegin");

        super.encodeBegin(facesContext, uiComponent);

        RendererUtils.checkParamValidity(facesContext, uiComponent, AbstractEffect.class);

        AbstractEffect component = (AbstractEffect) uiComponent;

        checkKeyFrameProperties(facesContext, component);
        checkAnimationProperties(facesContext, component);

        ResponseWriter writer = facesContext.getResponseWriter();

        writer.startElement(HTML.STYLE_ELEM, component);

        // write id
        final String id = component.getClientId(facesContext);
        writer.writeAttribute(HTML5.ID_ATTR, id, null);

        writer.writeText(getKeyFrameDefinition(facesContext, component),component, null);
        writer.writeText(getAnimationDefinition(facesContext, component),component, null);
    }

    @Override
    public void encodeEnd(FacesContext facesContext, UIComponent component) throws IOException {
        if (log.isLoggable(Level.FINE))
            log.fine("encodeEnd");
        // just close the element
        super.encodeEnd(facesContext, component);

        ResponseWriter writer = facesContext.getResponseWriter();

        writer.endElement(HTML.STYLE_ELEM);
    }

    protected abstract void checkKeyFrameProperties(FacesContext facesContext, AbstractEffect component);

    protected void checkAnimationProperties(FacesContext facesContext, AbstractEffect component) {
        //do nothing
    }

    protected String getKeyFrameDefinition(FacesContext facesContext, AbstractEffect uiComponent){
        String format = "@-webkit-keyframes %s  {%s} ";

        final String id = uiComponent.getClientId(facesContext);
        String keyFrameBodyDefinition = getKeyFrameBodyDefinition(facesContext, uiComponent);

        return String.format(format, id, keyFrameBodyDefinition);
    }

    protected abstract String getKeyFrameBodyDefinition(FacesContext facesContext, AbstractEffect uiComponent);

    protected String getAnimationDefinition(FacesContext facesContext, AbstractEffect component){
        final String id = component.getClientId(facesContext);
        final String duration = getTimeValue(component.getDuration());
        final String iteration = component.getIteration();
        final String timingFunction = component.getTimingFunction();
        final String direction = component.getDirection();
        final String delay = getTimeValue(component.getDelay());

        StringBuilder builder = new StringBuilder();
        builder.append(".").append(id);
        builder.append(" { ");

        appendIfNotNull(builder, CSS.ANIMATION_NAME_PROP, id);
        appendIfNotNull(builder, CSS.ANIMATION_DURATION_PROP, duration);
        appendIfNotNull(builder, CSS.ANIMATION_ITERATION_COUNT_PROP, iteration);
        appendIfNotNull(builder, CSS.ANIMATION_TIMING_FUNCTION_PROP, timingFunction);
        appendIfNotNull(builder, CSS.ANIMATION_DIRECTION_PROP, direction);
        appendIfNotNull(builder, CSS.ANIMATION_DELAY_PROP, delay);

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

        if (component.getParent() instanceof ClientBehaviorHolder) {
            final ClientBehaviorHolder parent = (ClientBehaviorHolder) component.getParent();

            Set<EffectBehavior> parentBehaviors = new HashSet<EffectBehavior>();

            for (List<ClientBehavior> clientBehaviorList : parent.getClientBehaviors().values()) {
                for (ClientBehavior behavior : clientBehaviorList) {
                    if (behavior instanceof EffectBehavior) {
                        parentBehaviors.add((EffectBehavior) behavior);
                    }
                }
            }

            if (parentBehaviors.isEmpty())
                throw new RuntimeException("Effects must be placed inside a fx:effect.");

            //TODO: check if there is any condition whether parent behavior is not single
//            if(parentBehaviors.size()>1)
//                throw new RuntimeException();

            for (EffectBehavior parentBehavior : parentBehaviors) {
                parentBehavior.addEffectToHandle(component.getId());
            }
        }

        //XXX: other alternative than body? think about ajax PPR
        facesContext.getViewRoot().addComponentResource(facesContext, component, "body");
    }

}
