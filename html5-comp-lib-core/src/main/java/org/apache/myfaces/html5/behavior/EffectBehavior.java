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

package org.apache.myfaces.html5.behavior;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFBehavior;

import javax.el.ValueExpression;
import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;
import java.util.*;

@ResourceDependencies(
{
        @ResourceDependency(name = "jsf.js", library = "javax.faces", target = "head"),
        @ResourceDependency(name = "common.js", library = "org.apache.myfaces.html5", target = "head"),
        @ResourceDependency(name = "effect.js", library = "org.apache.myfaces.html5", target = "head")
})
@JSFBehavior(name = "fx:effect", id = "org.apache.myfaces.html5.EffectBehavior")
public class EffectBehavior extends javax.faces.component.behavior.ClientBehaviorBase {

    public static final String RENDERER_ID = "org.apache.myfaces.html5.EffectBehavior";

    private Set<String> effectsToHandle = new HashSet<String>();

    public void addEffectToHandle(String effectId){
        this.effectsToHandle.add(effectId);
    }

    public Set<String> getEffectsToHandle() {
        return Collections.unmodifiableSet(effectsToHandle);
    }

    @Override
    public String getRendererType()
    {
        return RENDERER_ID;
    }
}
