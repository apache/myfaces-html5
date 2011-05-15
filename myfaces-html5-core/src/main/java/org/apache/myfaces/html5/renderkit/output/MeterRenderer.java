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

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFRenderer;
import org.apache.myfaces.html5.component.output.AbstractMeter;
import org.apache.myfaces.html5.renderkit.panel.DivRenderer;
import org.apache.myfaces.html5.renderkit.util.*;

import javax.faces.FacesException;
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

@JSFRenderer(renderKitId = "HTML_BASIC", family = "org.apache.myfaces.Meter", type = "org.apache.myfaces.html5.Meter")
public class MeterRenderer extends Renderer
{
    private static final Logger log = Logger.getLogger(DivRenderer.class.getName());

    @Override
    public void encodeBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {
        if (log.isLoggable(Level.FINE))
            log.fine("encodeBegin");

        super.encodeBegin(facesContext, uiComponent);

        RendererUtils.checkParamValidity(facesContext, uiComponent, AbstractMeter.class);

        ResponseWriter writer = facesContext.getResponseWriter();

        AbstractMeter component = (AbstractMeter) uiComponent;

        writer.startElement("meter", uiComponent);

        // write id
        writer.writeAttribute(HTML5.ID_ATTR, component.getClientId(facesContext), null);

        final Double value = component.getValue();
        final Double maximum = component.getMaximum();
        final Double minimum = component.getMinimum();

        if(minimum > maximum){
            throw new FacesException("Minimum value of meter component cannot be larger than maximum value, but " + minimum + " > " + maximum);
        }

        if(value < minimum){
            throw new FacesException("Value of meter component cannot be smaller than minimum value, but " + value + " < " + minimum);
        }

        if(value > maximum ){
            throw new FacesException("Value of meter component cannot be larger than maximum value, but " + value + " > " + maximum);
        }

        final Double low = component.getLow();
        final Double high = component.getHigh();
        final Double optimum = component.getOptimum();

        if(low!=null && (low < minimum || low>maximum)){
            throw new FacesException("Low value of meter component must be between minimum value and maximum value. Low :" + low + " , minimum : " + minimum + " , maximum : " + maximum);
        }

        if(high!=null && (high < minimum || high>maximum)){
            throw new FacesException("High value of meter component must be between minimum value and maximum value. High :" + high + " , minimum : " + minimum + " , maximum : " + maximum);
        }

        if(optimum!=null && (optimum < minimum || optimum>maximum)){
            throw new FacesException("Optimum value of meter component must be between minimum value and maximum value. Optimum :" + optimum + " , minimum : " + minimum + " , maximum : " + maximum);
        }

        if(low!=null && high!=null && low>high){
            throw new FacesException("Low value of meter component cannot be larger than high value, but " + low + " > " + high);
        }

        if(low!=null && optimum!=null && optimum < low ){
            throw new FacesException("Optimum value of meter component cannot be smaller than low value, but " + optimum + " < " + low);
        }

        if(high!=null && optimum!=null && optimum > high ){
            throw new FacesException("Optimum value of meter component cannot be larger than high value, but " + optimum + " > " + high);
        }


        renderPassThruAttrsAndEvents(facesContext, uiComponent);
    }

    // to make this extendible
    protected void renderPassThruAttrsAndEvents(FacesContext facesContext, UIComponent uiComponent) throws IOException
    {
        Map<String, List<ClientBehavior>> clientBehaviors = ((ClientBehaviorHolder) uiComponent).getClientBehaviors();

        Html5RendererUtils.renderPassThroughClientBehaviorEventHandlers(facesContext, uiComponent,
                PassThroughClientBehaviorEvents.METER, clientBehaviors);

        Html5RendererUtils.renderPassThroughAttributes(facesContext.getResponseWriter(), uiComponent,
                PassThroughAttributes.METER);
    }

    @Override
    public void encodeEnd(FacesContext facesContext, UIComponent component) throws IOException
    {
        if (log.isLoggable(Level.FINE))
            log.fine("encodeEnd");
        // just close the element
        super.encodeEnd(facesContext, component);

        ResponseWriter writer = facesContext.getResponseWriter();

        writer.endElement("meter");
    }
}

