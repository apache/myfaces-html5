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
import org.apache.myfaces.html5.component.properties.TargetProperty;

import javax.faces.component.UIComponentBase;

/**
 * Provides support for rendering the style generated respect to children effects.
 * @author Ali Ok
 */
@JSFComponent(
        name = "fx:effectOutput",
        clazz = "org.apache.myfaces.html5.component.effect.EffectOutput",
        tagClass = "org.apache.myfaces.html5.tag.effect.EffectOutputTag",
        defaultRendererType = "org.apache.myfaces.html5.EffectOutput",
        family = "org.apache.myfaces.EffectOutput",
        type = "org.apache.myfaces.html5.EffectOutput"
)
public abstract class AbstractEffectOutput extends javax.faces.component.UIComponentBase implements TargetProperty {

}
