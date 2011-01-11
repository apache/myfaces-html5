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

package org.apache.myfaces.html5.handler;

import org.apache.commons.lang.StringUtils;
import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFFaceletTag;
import org.apache.myfaces.html5.behavior.EffectsActivationBehavior;
import org.apache.myfaces.html5.behavior.EffectsDeactivationBehavior;
import org.apache.myfaces.html5.component.effect.AbstractEffects;
import org.apache.myfaces.html5.renderkit.util.DefaultDeactivationEvents;
import org.apache.myfaces.html5.renderkit.util.Html5RendererUtils;

import javax.faces.FacesException;
import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.context.FacesContext;
import javax.faces.view.facelets.ComponentConfig;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.TagAttribute;
import java.util.Set;

@JSFFaceletTag(name = "fx:effects", componentClass = "org.apache.myfaces.html5.component.effect.Effects")
public class EffectsHandler extends javax.faces.view.facelets.ComponentHandler {
    private final TagAttribute event;
    private final TagAttribute deactivationEvents;

    public EffectsHandler(ComponentConfig config) {
        super(config);
        event = getAttribute("event");
        deactivationEvents = getAttribute("deactivationEvents");
    }

    @Override
    public void onComponentCreated(FaceletContext faceletContext, UIComponent uiComponent, UIComponent parent) {
        super.onComponentPopulated(faceletContext, uiComponent, parent);

        if (!(parent instanceof ClientBehaviorHolder))
            throw new FacesException("Parent is not ClientBehaviorHolder");

        if (!(uiComponent instanceof AbstractEffects))
            throw new FacesException("Created component is not a AbstractEffects");

        AbstractEffects component = (AbstractEffects) uiComponent;

        FacesContext context = faceletContext.getFacesContext();
        Application app = context.getApplication();

        EffectsActivationBehavior activationBehavior = (EffectsActivationBehavior) app.createBehavior(EffectsActivationBehavior.ID);

        String effectsIdToHandle = component.getClientId(faceletContext.getFacesContext());
        if(!StringUtils.isBlank(component.getAdditionalStyleClassToActivate()))
            effectsIdToHandle = effectsIdToHandle + " " + component.getAdditionalStyleClassToActivate();

        activationBehavior.setEffectIdToHandle(effectsIdToHandle);

        String eventName = getEventName(faceletContext);
        if(StringUtils.isBlank(eventName))
            eventName = ((ClientBehaviorHolder) parent).getDefaultEventName();

        ((ClientBehaviorHolder) parent).addClientBehavior(eventName, activationBehavior);

        if(!StringUtils.isBlank(eventName) || deactivationEvents!=null){
            EffectsDeactivationBehavior deactivationBehavior = (EffectsDeactivationBehavior) app.createBehavior(EffectsDeactivationBehavior.ID);

            deactivationBehavior.setEffectIdToHandle(effectsIdToHandle);

            String[] deactivationEventNames = getDeactivationEventNames(faceletContext);
            if(deactivationEventNames!=null){
                for (String deactivationEventName : deactivationEventNames) {
                    ((ClientBehaviorHolder) parent).addClientBehavior(deactivationEventName, deactivationBehavior);
                }
            }
        }
    }

    public String getEventName(FaceletContext faceletContext) {
        if(event==null)
            return null;

        return event.getValue(faceletContext);
    }

    public String[] getDeactivationEventNames(FaceletContext faceletContext) {
        if(deactivationEvents==null)
            return getDefaultDeactivationEvents(this.getEventName(faceletContext));

        final Object objDeactivationEventNames = deactivationEvents.getObject(faceletContext);

        return Html5RendererUtils.resolveStrings(objDeactivationEventNames, getDefaultDeactivationEvents(this.getEventName(faceletContext)));
    }

    private String[] getDefaultDeactivationEvents(String eventName) {
        final Set<String> stringSet = DefaultDeactivationEvents.map.get(eventName);
        if(stringSet==null || stringSet.isEmpty())
            return null;
        return stringSet.toArray(new String[stringSet.size()]);
    }
}
