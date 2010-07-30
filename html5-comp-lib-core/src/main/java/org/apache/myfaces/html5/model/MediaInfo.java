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
package org.apache.myfaces.html5.model;

import org.apache.myfaces.html5.component.media.Video;
import org.apache.myfaces.html5.handler.MediaSourceHandler;
import org.apache.myfaces.html5.holder.MediaSourceHolder;

/**
 * Model of media resources to use at media elements.
 * 
 * @author Ali Ok
 * 
 * @see MediaSourceHolder
 * @see MediaSourceHandler
 * @see Video
 */
public class MediaInfo
{

    /*
     * Fields are not final on purpose (to make this class extensible), even if they don't have setters.
     */
    protected String src;
    protected String contentType;
    protected String media;
    protected String codec;
    protected boolean disabled;

    /**
     * @param src
     *            URL of the media resource
     * @param contentType
     *            MIME content type of the resource (for example : "video/ogg")
     * @param codec
     *            encoding method of the media resource. eg: "avc1.64001E, mp4a.40.2". If this is defined, it is an
     *            error to not define contentType.
     * @param media
     *            Intended media type of the media resource. eg: "tv" or "3d-glasses"
     * @param disabled
     *            Is this media resource should not be rendered or used, this field should be set to true.
     * 
     */
    public MediaInfo(String src, String contentType, String codec, String media, boolean disabled)
    {
        this.src = src;
        this.contentType = contentType;
        this.codec = codec;
        this.media = media;
        this.disabled = disabled;
    }

    // overloaded constructors

    /**
     * @see #MediaInfo(String, String, String, String, boolean)
     */
    public MediaInfo(String src, String contentType, String codec, String media)
    {
        this(src, contentType, codec, media, false);
    }

    /**
     * @see #MediaInfo(String, String, String, String, boolean)
     */
    public MediaInfo(String src, String contentType, String codec)
    {
        this(src, contentType, codec, null);
    }

    /**
     * @see #MediaInfo(String, String, String, String, boolean)
     */
    public MediaInfo(String src, String contentType)
    {
        this(src, contentType, null);
    }

    /**
     * @see #MediaInfo(String, String, String, String, boolean)
     */
    public MediaInfo(String src)
    {
        this(src, null);
    }

    // getters

    /**
     * Returns the URL of the media source.
     */
    public String getSrc()
    {
        return src;
    }

    /**
     * Returns the MIME content type of the resource (for example : "video/ogg");
     */
    public String getContentType()
    {
        return contentType;
    }

    /**
     * Returns the intended media type of the media resource, to help the browser determine if this media resource is
     * useful to the user before fetching it. Returned value is a valid media query. Just like the "@media" declaration
     * in CSS.
     */
    public String getMedia()
    {
        return media;
    }

    /**
     * Returns the codecs of the resource (for example: "avc1.64001E, mp4a.40.2").
     */
    public String getCodec()
    {
        return codec;
    }

    /**
     * If set to true, media renderer will ignore this resource.
     */
    public boolean isDisabled()
    {
        return disabled;
    }

}
