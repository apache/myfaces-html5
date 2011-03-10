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

package org.apache.myfaces.html5.renderkit.behavior;

import org.apache.myfaces.html5.behavior.EffectsActivationBehavior;
import org.apache.myfaces.html5.behavior.EffectsDeactivationBehavior;
import org.apache.myfaces.html5.renderkit.util.ClientBehaviorEvents;

import javax.faces.FacesException;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.component.behavior.ClientBehaviorContext;
import javax.faces.render.ClientBehaviorRenderer;

public class EffectsDeactivationBehaviorRenderer extends ClientBehaviorRenderer
{

    @Override
    public String getScript(ClientBehaviorContext behaviorContext, ClientBehavior behavior)
    {
        if (!(behavior instanceof EffectsDeactivationBehavior))
        {
            throw new FacesException("Behavior is not a EffectsDeactivationBehavior.");
        }

        EffectsDeactivationBehavior effectBehavior = (EffectsDeactivationBehavior) behavior;

        String strEffects = effectBehavior.getEffectIdToHandle();

        String format = "myfaces.html5.effect.removeEffect(this, '%s');";

        return String.format(format, strEffects);
    }
}