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
import org.apache.myfaces.html5.behavior.AnimationBehavior;
import org.apache.myfaces.html5.component.animation.AbstractAnimation;
import org.apache.myfaces.html5.renderkit.util.ClientBehaviorEvents;

import javax.faces.FacesException;
import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.context.FacesContext;
import javax.faces.view.facelets.ComponentConfig;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.TagAttribute;

@JSFFaceletTag(name = "fx:animation", componentClass = "org.apache.myfaces.html5.component.animation.Animation")
public class AnimationHandler extends javax.faces.view.facelets.ComponentHandler {
    private final TagAttribute event;

    public AnimationHandler(ComponentConfig config) {
        super(config);
        event = getAttribute("event");
    }

    @Override
    public void onComponentCreated(FaceletContext faceletContext, UIComponent uiComponent, UIComponent parent) {
        super.onComponentPopulated(faceletContext, uiComponent, parent);

        String eventName = getEventName(faceletContext);

        if ((!(parent instanceof ClientBehaviorHolder))){
            throw new FacesException("Parent component is not a ClientBehaviorHolder, however event attribute is defined.");
        }

        if (!(uiComponent instanceof AbstractAnimation))
            throw new FacesException("Created component is not a AbstractAnimation");

        FacesContext context = faceletContext.getFacesContext();
        Application app = context.getApplication();
        String behaviorId = AnimationBehavior.ID;
        AnimationBehavior behavior = (AnimationBehavior) app.createBehavior(behaviorId);

        behavior.setAnimationIdToHandle(uiComponent.getClientId(faceletContext.getFacesContext()));

        if(StringUtils.isBlank(eventName))
            eventName = ((ClientBehaviorHolder) parent).getDefaultEventName();

        ((ClientBehaviorHolder) parent).addClientBehavior(eventName, behavior);
        ((ClientBehaviorHolder) parent).addClientBehavior(ClientBehaviorEvents.ANIMATIONEND_EVENT, behavior);
    }

    public String getEventName(FaceletContext faceletContext) {
        if(event==null)
            return null;

        return event.getValue(faceletContext);
    }
}
