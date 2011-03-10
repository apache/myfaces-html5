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

package org.apache.myfaces.html5.component.effect;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFComponent;
import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFProperty;
import org.apache.myfaces.html5.component.properties.EventProperty;
import org.apache.myfaces.html5.component.properties.TargetProperty;
import org.apache.myfaces.html5.component.properties.effect.TransitionProperties;

/**
 * Container for effects.<br/>
 * fx:effect... components should be nested inside this component.
 *
 * @author Ali Ok
 */
@JSFComponent(
        name = "fx:effects",
        clazz = "org.apache.myfaces.html5.component.effect.Effects",
        tagClass = "org.apache.myfaces.html5.tag.effect.EffectsTag",
        defaultRendererType = "org.apache.myfaces.html5.Effects",
        family = "org.apache.myfaces.Effects",
        type = "org.apache.myfaces.html5.Effects"
)
public abstract class AbstractEffects extends org.apache.myfaces.html5.component.effect.EffectOutput implements TransitionProperties{
    private String transitionComponentId;

    public void setTransitionComponentId(String transitionComponentId) {
        this.transitionComponentId = transitionComponentId;
    }

    public String getTransitionComponentId() {
        return transitionComponentId;
    }

    @JSFProperty(tagExcluded = true)
    public abstract String getEvent();

    /**
     * Event(s) to deactivate to effect. The effect is activated on the event defined with the 'event' attribute, and deactivated
     * on any of the events defined with this attribute. Value can be strings separated with comma, list of strings or array of strings.
     * <br/>
     * For example to to activate the effect on mouse hover and deactivate it on click or mouse out: event='mouseover' deactivationEvents='mouseout, click'
     * attributes should be defined.<br/>
     * If nothing is defined, the renderer will use deactivation events that make sense(i.e. 'drop' and 'dragleave' for 'dragover' event).
     *
     */
    @JSFProperty(deferredValueType = "java.lang.Object")
    public abstract Object getDeactivationEvents();

    /**
     * Css style class name to activate additionally with the effect.
     */
    @JSFProperty(deferredValueType = "java.lang.String")
    public abstract String getAdditionalStyleClassToActivate();
}
