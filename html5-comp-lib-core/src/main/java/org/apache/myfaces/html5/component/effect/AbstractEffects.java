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

@JSFComponent(
        name = "fx:effects",
        clazz = "org.apache.myfaces.html5.component.effect.Effects",
        tagClass = "org.apache.myfaces.html5.tag.effect.EffectsTag",
        defaultRendererType = "org.apache.myfaces.html5.Effects",
        family = "org.apache.myfaces.Effects",
        type = "org.apache.myfaces.html5.Effects"
)
public abstract class AbstractEffects extends javax.faces.component.UIComponentBase {

    @JSFProperty(deferredValueType = "java.lang.String")
    public abstract String getEvent();

    @JSFProperty(deferredValueType = "java.lang.Object")
    public abstract Object getDeactivationEvents();

    @JSFProperty(deferredValueType = "java.lang.String")
    public abstract String getAdditionalStyleClassToActivate();

    @JSFProperty(deferredValueType = "java.lang.String")
    public abstract String getDuration();

    @JSFProperty(deferredValueType = "java.lang.String")
    public abstract String getIteration();

    @JSFProperty(deferredValueType = "java.lang.String")
    public abstract String getTimingFunction();

}
