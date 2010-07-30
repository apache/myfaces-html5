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
import org.apache.myfaces.html5.component.api.Draggable;

public interface Html5GlobalProperties extends Draggable        //extends the Draggable to have a draggable attribute 
{

    /*     ********************** COMMON PROPS START HERE ******************** */

    /**
     * HTML: The direction of text display, either 'ltr' (left-to-right) or 'rtl' (right-to-left).
     * 
     */
    @JSFProperty
    public abstract String getDir();

    /**
     * HTML: The base language of this document.
     * 
     */
    @JSFProperty
    public abstract String getLang();

    /**
     * HTML: An advisory title for this element. Often used by the user agent as a tooltip.
     * 
     */
    @JSFProperty
    public abstract String getTitle();

    /**
     * HTML: CSS styling instructions.
     * 
     */
    @JSFProperty
    public abstract String getStyle();

    /**
     * The CSS class for this element. Corresponds to the HTML 'class' attribute.
     * 
     */
    @JSFProperty
    public abstract String getStyleClass();

    /*     ********************** COMMON PROPS END HERE ******************** */

    /*     ********************** NEW HTML5 PROPS START HERE ******************** */

    /**
     * HTML: Whether the element is relevant.
     */
    @JSFProperty(defaultValue = "false")
    public abstract boolean isHidden();

    // ///DND PROPS

    /**
     * HTML: Specifies whether the element is draggable.
     */
    @JSFProperty(defaultValue = "false")
    public abstract boolean isDraggable();

    /**
     * HTML: Handler for event that is fired when the element is dragged.
     * 
     */
    @JSFProperty(clientEvent = "drag")
    public abstract String getOndrag();

    /**
     * HTML: Handler for event that is fired when the drag operation is ended. Successfully or not, this handler will
     * run.
     * 
     */
    @JSFProperty(clientEvent = "dragend")
    public abstract String getOndragend();

    /**
     * HTML: Handler for event that is fired when the element is dragged into a valid drop target.
     * 
     */
    @JSFProperty(clientEvent = "dragenter")
    public abstract String getOndragenter();

    /**
     * HTML: Handler for event that is fired when the element leaves a valid drop target.
     * 
     */
    @JSFProperty(clientEvent = "dragleave")
    public abstract String getOndragleave();

    /**
     * HTML: Handler for event that is fired when the element is being dragged over a valid drop target.
     * 
     */
    @JSFProperty(clientEvent = "dragover")
    public abstract String getOndragover();

    /**
     * HTML: Handler for event that is fired when the drag operation starts.
     * 
     */
    @JSFProperty(clientEvent = "dragstart")
    public abstract String getOndragstart();

    /**
     * HTML: Handler for event that is fired when the element is being dropped.
     * 
     */
    @JSFProperty(clientEvent = "drop")
    public abstract String getOndrop();

    // ///DND PROPS END

    /**
     * HTML: Script to be invoked when the pointing device is wheeled over this element.
     * 
     */
    @JSFProperty(clientEvent = "mousewheel")
    public String getOnmousewheel();

}
