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

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFFaceletTag;
import org.apache.myfaces.html5.renderkit.util.ClientBehaviorEvents;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.component.behavior.Behavior;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.context.FacesContext;
import javax.faces.view.facelets.BehaviorConfig;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.TagAttribute;
import java.io.IOException;

@JSFFaceletTag(name = "fx:effect", behaviorClass = "org.apache.myfaces.html5.behavior.EffectBehavior")
public class EffectHandler extends javax.faces.view.facelets.BehaviorHandler {
    public EffectHandler(BehaviorConfig config) {
        super(config);
    }

    @Override
    public void apply(FaceletContext faceletContext, UIComponent parent) throws IOException {
        FacesContext context = faceletContext.getFacesContext();
        Application app = context.getApplication();
        String behaviorId = getBehaviorId();
        Behavior behavior = app.createBehavior(behaviorId);

        final String eventName = getEventName();

        ((ClientBehaviorHolder) parent).addClientBehavior(eventName, (ClientBehavior) behavior);
        ((ClientBehaviorHolder) parent).addClientBehavior(ClientBehaviorEvents.ANIMATIONEND_EVENT, (ClientBehavior) behavior);

        this.applyNextHandler(faceletContext, parent);
    }

}
