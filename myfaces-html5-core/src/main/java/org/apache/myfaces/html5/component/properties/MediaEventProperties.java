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
package org.apache.myfaces.html5.component.properties;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFProperty;

public interface MediaEventProperties
{

    /**
     * HTML: Handler for event that is fired when the playback has stopped because the end of the media resource was
     * reached.
     * 
     */
    @JSFProperty(clientEvent = "ended")
    public abstract String getOnended();

    /**
     * HTML: Handler for event that is fired when an error occurs while fetching the media data.
     * 
     */
    @JSFProperty(clientEvent = "error")
    public abstract String getOnerror();

    /**
     * HTML: Handler for event that is fired when browser can render the media data at the current playback position for
     * the first time.
     * 
     */
    @JSFProperty(clientEvent = "loadeddata")
    public abstract String getOnloadeddata();

    /**
     * HTML: Handler for event that is fired when browser has just determined the duration and dimensions of the media
     * resource and the timed tracks are ready.
     * 
     */
    @JSFProperty(clientEvent = "loadedmetadata")
    public abstract String getOnloadedmetadata();

    /**
     * HTML: Handler for event that is fired when browser begins looking for media data, as part of the resource
     * selection algorithm.
     * 
     */
    @JSFProperty(clientEvent = "loadstart")
    public abstract String getOnloadstart();

    /**
     * HTML: Handler for event that is fired when the playback has been paused. Fired after the pause() method has
     * returned.
     * 
     */
    @JSFProperty(clientEvent = "pause")
    public abstract String getOnpause();

    /**
     * HTML: Handler for event that is fired when the playback has begun. Fired after the play() method has returned, or
     * when the autoplay attribute has caused playback to begin.
     * 
     */
    @JSFProperty(clientEvent = "play")
    public abstract String getOnplay();

    /**
     * HTML: Handler for event that is fired when the playback has started.
     * 
     */
    @JSFProperty(clientEvent = "playing")
    public abstract String getOnplaying();

    /**
     * HTML: Handler for event that is fired while browser is fetching media data.
     * 
     */
    @JSFProperty(clientEvent = "progress")
    public abstract String getOnprogress();

    /**
     * HTML: Handler for event that is fired when a seek operation is finished.
     * 
     */
    @JSFProperty(clientEvent = "seeked")
    public abstract String getOnseeked();

    /**
     * HTML: Handler for event that is fired if the seek operation is taking long enough that the browser has time to
     * fire the event.
     * 
     */
    @JSFProperty(clientEvent = "seeking")
    public abstract String getOnseeking();

    /**
     * HTML: Handler for event that is fired when either the volume attribute or the muted attribute has changed. Fired
     * after the relevant attribute's setter has returned.
     * 
     */
    @JSFProperty(clientEvent = "volumechange")
    public abstract String getOnvolumechange();

    /**
     * HTML: Handler for event that is fired when the playback has stopped because the next frame is not available, but
     * the user agent expects that frame to become available in due course.
     * 
     */
    @JSFProperty(clientEvent = "waiting")
    public abstract String getOnwaiting();

}
