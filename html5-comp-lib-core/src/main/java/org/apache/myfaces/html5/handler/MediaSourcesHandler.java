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
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.FacesException;
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
 * . Gets {@link MediaInfo} instances and adds them to parent component's media info set.
 * <p>
 * If 'disabled' is set to true, {@link MediaInfo} instances of "items" attribute will not be added.
 * </p>
 * 
 * @author Ali Ok
 * @see MediaSourceHolder
 */
@JSFFaceletTag(name = "fx:mediaSources")
public class MediaSourcesHandler extends TagHandler
{

    private static final Logger log = Logger.getLogger(MediaSourcesHandler.class.getName());

    /**
     * Array/collection of {@link MediaInfo} instances to use in media elements. This attribute is required.
     * 
     * @see MediaSourceHolder
     */
    @JSFFaceletAttribute(className = "javax.el.ValueExpression", deferredValueType = "java.lang.Object", required = true)
    private final TagAttribute items;

    /**
     * If set to true, {@link MediaInfo} instances of "items" attribute will not be added.
     */
    @JSFFaceletAttribute(className = "javax.el.ValueExpression", deferredValueType = "java.lang.Boolean")
    private final TagAttribute disabled;

    public MediaSourcesHandler(TagConfig config)
    {
        super(config);

        this.items = getRequiredAttribute("items"); // this is required
        this.disabled = getAttribute("disabled");
    }

    @SuppressWarnings("unchecked")
    public void apply(FaceletContext faceletContext, UIComponent parent) throws IOException
    {
        /*
         * algo: if this is not disabled and parent is a MediaSourceHolder - get value of 'items' attr - convert the
         * value to one of MediaInfo[] or Collection<Info> - add elements of array/collection into parent's MediaInfo
         * set
         */

        // if this is disabled, don't apply anything
        if (this.disabled != null && this.disabled.getBoolean(faceletContext) == true)
        {
            if (log.isLoggable(Level.FINE))
                log.fine("'disabled' is true. handler will not apply anything.");

            return;
        }

        if (!(parent instanceof MediaSourceHolder))
        {
            if (log.isLoggable(Level.WARNING))
                log.warning("parent component " + RendererUtils.getPathToComponent(parent) + " is not a MediaSourceHolder. handler will not apply anything.");
            return;
        }

        MediaSourceHolder msh = (MediaSourceHolder) parent;

        if (this.items.isLiteral())
            throw new FacesException("'items' attribute of <fx:mediaSources> cannot be literal.");

        Object objItems = this.items.getObject(faceletContext);
        if (objItems == null)
        {
            if (log.isLoggable(Level.FINE))
                log.fine("'items' is null. continue silently.");
            return;
        }

        if (objItems instanceof MediaInfo[])
        {
            if (log.isLoggable(Level.FINE))
                log.fine("type of 'items' is MediaInfo[]");
            MediaInfo[] mediaInfoArr = (MediaInfo[]) objItems;
            msh.addMediaInfo(mediaInfoArr);
        }
        else if (objItems instanceof Collection)
        {
            if (log.isLoggable(Level.FINE))
                log.fine("type of 'items' is Collection<MediaInfo>");
            Collection<MediaInfo> mediaInfoCollection = (Collection<MediaInfo>) objItems;
            try
            {
                if (log.isLoggable(Level.FINE))
                    log.fine("Trying to convert Collection<MediaInfo> to MediaInfo[]");
                MediaInfo[] mediaInfoArr = mediaInfoCollection.toArray(new MediaInfo[0]);
                msh.addMediaInfo(mediaInfoArr);
            }
            catch (ArrayStoreException e)
            {
                // if there is one non-MediaInfo element in the collection,
                // we'll fall in here during conversion to array.

                throw new FacesException(
                        "All elements of 'items' attribute of <fx:mediaSources> must be an instance of MediaInfo class.",
                        e);
            }
        }
        else
        {
            throw new FacesException(
                    "'items' attribute of <fx:mediaSources> must be an array or collection of MediaInfo instances. Type of the object provided : "
                            + objItems.getClass());
        }
    }
}
