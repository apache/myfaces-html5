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

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFRenderer;
import org.apache.myfaces.html5.component.effect.AbstractBaseEffect;
import org.apache.myfaces.html5.component.effect.AbstractEffect;
import org.apache.myfaces.html5.renderkit.util.RendererUtils;

import javax.faces.context.FacesContext;

@JSFRenderer(renderKitId = "HTML_BASIC", family = "org.apache.myfaces.Effect", type = "org.apache.myfaces.html5.Effect")
public class EffectRenderer extends BaseEffectRenderer {

    @Override
    protected String getEffectDefinition(FacesContext facesContext, AbstractBaseEffect uiComponent) {
        RendererUtils.checkParamValidity(facesContext, uiComponent, AbstractEffect.class);

        AbstractEffect component = (AbstractEffect) uiComponent;

        final String property = component.getProperty();
        final String value = component.getValue();

        String format = "%s : %s; ";

        return String.format(format, property, value);
    }
}