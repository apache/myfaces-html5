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
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.apache.myfaces.html5.component.media.AbstractMedia;
import org.apache.myfaces.html5.model.MediaInfo;
import org.apache.myfaces.html5.renderkit.util.HTML5;
import org.apache.myfaces.html5.renderkit.util.Html5RendererUtils;
import org.apache.myfaces.shared_html5.renderkit.JSFAttr;
import org.apache.myfaces.shared_html5.renderkit.RendererUtils;
import org.apache.myfaces.shared_html5.renderkit.html.HtmlRenderer;

/**
 * Abstract base for media renderers.
 * 
 * @author Ali Ok
 * 
 */
public abstract class AbstractMediaRenderer extends HtmlRenderer
{
    private static final Logger log = Logger.getLogger(AbstractMediaRenderer.class.getName());

    protected static final String FACET_FALLBACK = "fallback";

    @Override
    public void encodeBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException
    {
        if (log.isLoggable(Level.FINE))
            log.fine("encodeBegin");

        super.encodeBegin(facesContext, uiComponent);

        RendererUtils.checkParamValidity(facesContext, uiComponent, AbstractMedia.class);

        ResponseWriter writer = facesContext.getResponseWriter();

        AbstractMedia component = (AbstractMedia) uiComponent;

        writer.startElement(getHtmlElementName(), uiComponent);

        // write id
        writer.writeAttribute(HTML5.ID_ATTR, component.getClientId(facesContext), null);

        // get the value and render the src attr
        String src = org.apache.myfaces.shared_html5.renderkit.RendererUtils.getStringValue(facesContext, component);
        if (log.isLoggable(Level.FINE))
            log.fine("writing src '" + src + "'");
        if (src != null && !src.isEmpty())
            writer.writeAttribute(HTML5.SRC_ATTR, src, JSFAttr.VALUE_ATTR);

        // no need to check the value of preload, it is bypassed anyway.
        // _checkPreload(component);

        renderPassThruAttrsAndEvents(facesContext, uiComponent);

    }

    // to make this extendible
    protected void renderPassThruAttrsAndEvents(FacesContext facesContext, UIComponent uiComponent) throws IOException
    {
        Map<String, List<ClientBehavior>> clientBehaviors = ((ClientBehaviorHolder) uiComponent).getClientBehaviors();

        Html5RendererUtils.renderPassThroughClientBehaviorEventHandlers(facesContext, uiComponent,
                getPassThroughClientBehaviorEvents(), clientBehaviors);

        Html5RendererUtils.renderPassThroughAttributes(facesContext.getResponseWriter(), uiComponent,
                getPassThroughAttributes());
    }

    protected abstract Map<String, String> getPassThroughClientBehaviorEvents();

    // package-private, since extensibility of this is not desired
    abstract String getHtmlElementName();

    protected abstract Map<String, String> getPassThroughAttributes();

    @Override
    public void encodeChildren(FacesContext facesContext, UIComponent uiComponent) throws IOException
    {
        // don't call super.encodeChildren(...), since it lets children to encode themselves
        if (log.isLoggable(Level.FINE))
            log.fine("encodeChildren");

        RendererUtils.checkParamValidity(facesContext, uiComponent, AbstractMedia.class);

        renderFallbackFacet(facesContext, uiComponent);

        renderMediaSources(facesContext, uiComponent);

    }

    @Override
    public void encodeEnd(FacesContext facesContext, UIComponent component) throws IOException
    {
        if (log.isLoggable(Level.FINE))
            log.fine("encodeEnd");
        // just close the element
        super.encodeEnd(facesContext, component);

        ResponseWriter writer = facesContext.getResponseWriter();

        writer.endElement(getHtmlElementName());
    }

    @Override
    public boolean getRendersChildren()
    {
        return true;
    }

    /**
     * Renders extracted media sources.
     */
    protected void renderMediaSources(FacesContext facesContext, UIComponent uiComponent) throws IOException
    {
        ResponseWriter writer = facesContext.getResponseWriter();

        // type check is done above with RendererUtils.checkParamValidity(...)
        AbstractMedia component = (AbstractMedia) uiComponent;

        // render MediaInfo instances
        Set<MediaInfo> mediaInfoSet = component.getMediaInfos();
        if (mediaInfoSet != null)
        {
            for (MediaInfo mediaInfo : mediaInfoSet)
            {
                if (mediaInfo.isDisabled())
                    continue;

                writer.startElement(HTML5.SOURCE_ELEM, null);

                // src is reqired to be present and not empty!
                if (mediaInfo.getSrc() == null || mediaInfo.getSrc().isEmpty())
                    // WIKI: add a wiki page
                    throw new FacesException("'src' field of MediaInfo has to be defined and nonempty for component " + RendererUtils.getPathToComponent(uiComponent) + ".");

                writer.writeAttribute(HTML5.SRC_ATTR, mediaInfo.getSrc(), null);

                String typeVal = _getTypeForSource(mediaInfo);
                if (typeVal != null) // write even if empty str
                    writer.writeAttribute(HTML5.TYPE_ATTR, typeVal, null);

                if (mediaInfo.getMedia() != null) // write even if empty str
                    writer.writeAttribute(HTML5.MEDIA_ATTR, mediaInfo.getMedia(), null);

                writer.endElement(HTML5.SOURCE_ELEM);
            }
        }
    }

    protected void renderFallbackFacet(FacesContext facesContext, UIComponent uiComponent) throws IOException
    {
        UIComponent fallbackFacet = uiComponent.getFacet(FACET_FALLBACK);
        if (fallbackFacet != null && fallbackFacet.isRendered())
        {
            if (log.isLoggable(Level.FINE))
                log.fine("rendering fallback facet");
            fallbackFacet.encodeAll(facesContext);
        }
    }

    /**
     * Returns the value of "type" attribute of Html5 <source> element. <br/>
     * e.g.: 'video/mp4;codecs="avc1.4D401E, mp4a.40.2"'
     */
    private String _getTypeForSource(MediaInfo mediaInfo)
    {
        String contentType = mediaInfo.getContentType();
        String codec = mediaInfo.getCodec();

        boolean contentTypeDefined = contentType != null && !contentType.isEmpty();
        boolean codecDefined = codec != null && !codec.isEmpty();

        // if codec is set, then contentType should be set too!
        if (codecDefined && !contentTypeDefined)
            // WIKI: add a wiki and ref it here
            throw new FacesException(
                    "'codec' is defined but 'contentType' is not. If 'codec' is defined, 'contentType' has to be defined too.");

        String retVal = null;
        if (contentTypeDefined)
        {
            StringBuilder builder = new StringBuilder();
            builder.append(contentType);
            // aliok: I tried <video> <source> with no codec on browser, and no problem experienced.
            if (codecDefined)
            {
                builder.append("; codec='").append(codec).append("'");
            }

            retVal = builder.toString();
        }

        return retVal;
    }

}
