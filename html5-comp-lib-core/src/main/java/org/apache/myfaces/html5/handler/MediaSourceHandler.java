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

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.component.UIComponent;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.TagAttribute;
import javax.faces.view.facelets.TagConfig;
import javax.faces.view.facelets.TagHandler;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFFaceletAttribute;
import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFFaceletTag;
import org.apache.myfaces.html5.holder.MediaSourceHolder;
import org.apache.myfaces.html5.model.MediaInfo;
import org.apache.myfaces.shared_html5.renderkit.RendererUtils;

/**
 * Provides media sources for media components. This should be nested inside of an instance of {@link MediaSourceHolder}
 * . Creates a {@link MediaInfo} and adds it to parent component's media info set.
 * <br/>
 * Even if 'disabled' is set to true, created {@link MediaInfo} instance will be added. In this case, renderer of the
 * parent media component should ignore that instance.
 * 
 * @author Ali Ok
 * @see MediaSourceHolder
 */
@JSFFaceletTag(name = "fx:mediaSource")
public class MediaSourceHandler extends TagHandler
{

    private static final Logger log = Logger.getLogger(MediaSourceHandler.class.getName());

    /**
     * URL of the media source. This attribute is required.
     */
    @JSFFaceletAttribute(className = "javax.el.ValueExpression", deferredValueType = "java.lang.String", required = true)
    private final TagAttribute src;

    /**
     * MIME content type of the resource (for example : "video/ogg")
     */
    @JSFFaceletAttribute(className = "javax.el.ValueExpression", deferredValueType = "java.lang.String")
    private final TagAttribute contentType;

    /**
     * Codecs of the resource (for example: "avc1.64001E, mp4a.40.2"). If this property is defined, contentType property
     * should be defined too.
     */
    @JSFFaceletAttribute(className = "javax.el.ValueExpression", deferredValueType = "java.lang.String")
    private final TagAttribute codec;

    /**
     * This property defines the intended media type of the media resource, to help the browser determine if this media
     * resource is useful to the user before fetching it. Its value must be a valid media query. Just like the "@media"
     * declaration in CSS.
     */
    @JSFFaceletAttribute(className = "javax.el.ValueExpression", deferredValueType = "java.lang.String")
    private final TagAttribute media;

    /**
     * If set to true, renderer of the parent{@link MediaSourceHolder} will ignore this resource.
     */
    @JSFFaceletAttribute(className = "javax.el.ValueExpression", deferredValueType = "java.lang.Boolean")
    private final TagAttribute disabled;

    public MediaSourceHandler(TagConfig config)
    {
        super(config);

        this.src = getRequiredAttribute("src"); // this is required
        this.codec = getAttribute("codec");
        this.contentType = getAttribute("contentType");
        this.media = getAttribute("media");
        this.disabled = getAttribute("disabled");

    }

    public void apply(FaceletContext faceletContext, UIComponent parent) throws IOException
    {
        if (!(parent instanceof MediaSourceHolder))
        {
            if (log.isLoggable(Level.WARNING))
                log.warning("parent component " + RendererUtils.getPathToComponent(parent) + " is not a MediaSourceHolder. handler will not apply anything.");
            return;
        }
        else
        {
            String valueVal = null;
            String codecVal = null;
            String contentTypeVal = null;
            String mediaVal = null;
            boolean disabledVal = false;

            // src is required, no need to check nullness
            valueVal = this.src.getValue(faceletContext);

            if (this.codec != null)
                codecVal = this.codec.getValue(faceletContext);

            if (this.contentType != null)
                contentTypeVal = this.contentType.getValue(faceletContext);

            if (this.media != null)
                mediaVal = this.media.getValue(faceletContext);

            if (this.disabled != null)
                disabledVal = this.disabled.getBoolean(faceletContext);

            MediaInfo mediaInfo = new MediaInfo(valueVal, contentTypeVal, codecVal, mediaVal, disabledVal);

            if (log.isLoggable(Level.FINE))
                log.fine("MediaInfo instance created, adding it into parent's set.");

            // add mediaInfo to parent
            MediaSourceHolder msh = (MediaSourceHolder) parent;
            msh.addMediaInfo(mediaInfo);
        }
    }
}
