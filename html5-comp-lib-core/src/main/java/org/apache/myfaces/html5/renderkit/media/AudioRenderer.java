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
package org.apache.myfaces.html5.renderkit.media;

import java.io.IOException;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFRenderer;
import org.apache.myfaces.html5.component.media.Audio;
import org.apache.myfaces.html5.renderkit.util.HTML5;
import org.apache.myfaces.html5.renderkit.util.PassThroughAttributes;
import org.apache.myfaces.html5.renderkit.util.PassThroughClientBehaviorEvents;
import org.apache.myfaces.shared_html5.renderkit.RendererUtils;

/**
 * Renderer for < hx:audio > component.
 * 
 * @author Ali Ok
 * 
 */
@JSFRenderer(renderKitId = "HTML_BASIC", family = "org.apache.myfaces.Media", type = "org.apache.myfaces.html5.Audio")
public class AudioRenderer extends AbstractMediaRenderer
{

    @Override
    public void encodeBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException
    {
        RendererUtils.checkParamValidity(facesContext, uiComponent, Audio.class);

        super.encodeBegin(facesContext, uiComponent);
    }

    @Override
    protected Map<String, String> getPassThroughAttributes()
    {
        return PassThroughAttributes.AUDIO;
    }
    
    @Override
    protected Map<String, String> getPassThroughClientBehaviorEvents()
    {
        return PassThroughClientBehaviorEvents.AUDIO;
    }

    @Override
    String getHtmlElementName()
    {
        return HTML5.AUDIO_ELEM;
    }
}
