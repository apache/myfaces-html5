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
import org.apache.myfaces.html5.component.effect.AbstractEffect;
import org.apache.myfaces.html5.component.effect.AbstractEffectShake;
import org.apache.myfaces.shared_html5.renderkit.RendererUtils;
import org.apache.myfaces.view.facelets.PostBuildComponentTreeOnRestoreViewEvent;

import javax.faces.context.FacesContext;
import javax.faces.event.ListenerFor;
import javax.faces.event.ListenersFor;
import javax.faces.event.PostAddToViewEvent;
import java.text.NumberFormat;
import java.util.Locale;

@ListenersFor({
        @ListenerFor(systemEventClass = PostAddToViewEvent.class),
        @ListenerFor(systemEventClass = PostBuildComponentTreeOnRestoreViewEvent.class)
})
@JSFRenderer(renderKitId = "HTML_BASIC", family = "org.apache.myfaces.EffectShake", type = "org.apache.myfaces.html5.EffectShake")
public class EffectShakeRenderer extends BaseEffectRenderer {

    @Override
    protected void checkKeyFrameProperties(FacesContext facesContext, AbstractEffect uiComponent) {
        //do nothing
    }

    @Override
    protected String getKeyFrameBodyDefinition(FacesContext facesContext, AbstractEffect uiComponent) {
        RendererUtils.checkParamValidity(facesContext, uiComponent, AbstractEffectShake.class);

        AbstractEffectShake component = (AbstractEffectShake) uiComponent;

        final double rotation = component.getRotation();        //default value set, if not defined

        String format = "0%% { -webkit-transform: rotate(0deg)} 25%% { -webkit-transform: rotate(%sdeg)} 75%%  { -webkit-transform: rotate(-%sdeg)} 100%% { -webkit-transform: rotate(0deg)}";

        final NumberFormat numberFormat = NumberFormat.getInstance(Locale.ENGLISH);

        final String strRotation = numberFormat.format(rotation);

        return String.format(format, strRotation, strRotation);
    }
}