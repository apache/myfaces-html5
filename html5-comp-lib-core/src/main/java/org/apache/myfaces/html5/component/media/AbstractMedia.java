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
package org.apache.myfaces.html5.component.media;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFComponent;
import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFProperty;
import org.apache.myfaces.html5.component.properties.AccesskeyProperty;
import org.apache.myfaces.html5.component.properties.GlobalEventProperties;
import org.apache.myfaces.html5.component.properties.Html5GlobalProperties;
import org.apache.myfaces.html5.component.properties.KeyEventProperties;
import org.apache.myfaces.html5.component.properties.MediaEventProperties;
import org.apache.myfaces.html5.component.properties.MouseEventProperties;
import org.apache.myfaces.html5.component.properties.TabindexProperty;
import org.apache.myfaces.html5.holder.MediaSourceHolder;
import org.apache.myfaces.html5.model.MediaInfo;

/**
 * Base class for Video and Audio components.
 * 
 * @author Ali Ok
 * 
 */
@JSFComponent(
        clazz = "org.apache.myfaces.html5.component.media.Media",
        implementz = "javax.faces.component.behavior.ClientBehaviorHolder",
        defaultEventName="play",
        configExcluded = true)
public abstract class AbstractMedia extends javax.faces.component.html.HtmlOutputText implements MediaSourceHolder, Html5GlobalProperties,
        AccesskeyProperty, TabindexProperty, MouseEventProperties, KeyEventProperties, MediaEventProperties,
        GlobalEventProperties
{
    protected Set<MediaInfo> mediaInfoSet;

    /*
     * (non-Javadoc)
     * 
     * @see javax.faces.component.html.HtmlOutputText#isEscape()
     * 
     * This property is excluded since escape is meaningless on media elements.
     */
    @Override
    @JSFProperty(tagExcluded = true)
    public abstract boolean isEscape();

    /**
     * Returns the preloading behavior of the component. Default to null, which delegates preloading method selection to
     * browser. Can be one of
     * <ul>
     * <li>"none": Do not preload the media from the server</li>
     * <li>"metadata": Fetch metadata (length, quality, etc.)</li>
     * <li>"auto": Load the data from the server, even if user have not choose to play it.</li>
     * </ul>
     * 
     * Browsers should use "metadata" preloading, if not specified.
     * 
     */
    @JSFProperty(deferredValueType = "java.lang.String")
    public abstract String getPreload();

    /**
     * This property is to show/hide browser's media controls. If true, browser's media controls are shown (default). If
     * false, controls are not shown and page author needs to provide controls explicitly.
     */
    @JSFProperty(deferredValueType = "java.lang.Boolean", defaultValue = "true")
    public abstract boolean isShowControls();

    /**
     * If this property is set, media will seek back to start when it reaches the end.
     * 
     * @return
     */
    @JSFProperty(deferredValueType = "java.lang.Boolean", defaultValue = "false")
    public abstract boolean isLoop();

    /**
     * If this property is set, media will start playing as soon as the page loads.
     * 
     * @return
     */
    @JSFProperty(deferredValueType = "java.lang.Boolean", defaultValue = "false")
    public abstract boolean isAutoplay();

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.apache.myfaces.html5.component.holder.MediaSourceHolder#addMediaInfo(org.apache.myfaces.html5.model.MediaInfo
     * )
     */
    public void addMediaInfo(MediaInfo... mediaInfo)
    {
        if (this.mediaInfoSet == null)
            this.mediaInfoSet = new HashSet<MediaInfo>(5); // most likely, there wont be mediaInfo instances more than
                                                           // 5.

        this.mediaInfoSet.addAll(Arrays.asList(mediaInfo));
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.myfaces.html5.component.holder.MediaSourceHolder#getMediaInfos()
     */
    public Set<MediaInfo> getMediaInfos()
    {
        return this.mediaInfoSet;
    }

}
