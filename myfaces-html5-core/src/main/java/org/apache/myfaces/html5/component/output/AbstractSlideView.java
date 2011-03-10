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

package org.apache.myfaces.html5.component.output;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFComponent;
import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFProperty;
import org.apache.myfaces.html5.component.properties.*;
import org.apache.myfaces.html5.component.properties.effect.TransitionProperties;
import org.apache.myfaces.html5.component.util.ComponentUtils;

import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

/**
 * Provides a presentation-like slide view.<br/>
 * Page authors should nest hx:slide components inside.
 *
 * @author Ali Ok
 *
 */
@JSFComponent(
        name = "hx:slideView",
        clazz = "org.apache.myfaces.html5.component.output.SlideView",
        tagClass = "org.apache.myfaces.html5.tag.output.SlideViewTag",
        defaultRendererType = "org.apache.myfaces.html5.SlideView",
        family = "org.apache.myfaces.SlideView",
        type = "org.apache.myfaces.html5.SlideView",
        implementz = "javax.faces.component.behavior.ClientBehaviorHolder",
        defaultEventName="click"
)
public abstract class AbstractSlideView extends javax.faces.component.UIComponentBase implements
        javax.faces.component.behavior.ClientBehaviorHolder, Html5GlobalProperties, AccesskeyProperty,
        TabindexProperty, MouseEventProperties, GlobalEventProperties, WidgetVarProperty, TransitionProperties,
        PrependIdProperty, NamingContainer
{
    public static final Double DEFAULT_SLIDE_LEFT = 10.0;
    public static final Double DEFAULT_SLIDE_WIDTH = 80.0;
    public static final Double DEFAULT_SLIDE_HEIGHT = 90.0;
    public static final Double DEFAULT_SLIDE_INACTIVE_SCALE = 0.9;

    /**
     * The percent value of the left position of the active slide. Defaults to 10.
     */
    @JSFProperty(required = false, deferredValueType = "java.lang.Double", defaultValue = "DEFAULT_SLIDE_LEFT")
    public abstract double getSlideLeft();

    /**
     * The percent value of the width of the active slide. Defaults to 80.
     */
    @JSFProperty(required = false, deferredValueType = "java.lang.Double", defaultValue = "DEFAULT_SLIDE_WIDTH")
    public abstract double getSlideWidth();

    /**
     * The percent value of the height of the active slide. Defaults to 90.
     */
    @JSFProperty(required = false, deferredValueType = "java.lang.Double", defaultValue = "DEFAULT_SLIDE_HEIGHT")
    public abstract double getSlideHeight();

    /**
     * Scaling value of the inactive slides. Value should be in the interval [0.0, 1.0]. Defaults to 0.9.
     */
    @JSFProperty(required = false, deferredValueType = "java.lang.Double", defaultValue = "DEFAULT_SLIDE_INACTIVE_SCALE")
    public abstract double getInactiveSlideScale();


    /**
     * If true, arrow keys will be registered to navigate between slides.<br/>
     * Defaults to true.<br/>
     * widgetVar can be used to navigate between slides manually.
     */
    @JSFProperty(required = false, deferredValueType = "java.lang.Boolean", defaultValue = "true")
    public abstract boolean isNavigateOnArrowKeys();

    /**
     * If true, mouse wheel scroll will be registered to navigate between slides.<br/>
     * Defaults to true.
     * widgetVar can be used to navigate between slides manually.
     */
    @JSFProperty(required = false, deferredValueType = "java.lang.Boolean", defaultValue = "true")
    public abstract boolean isNavigateOnMouseWheel();


    @Override
    public String getContainerClientId(FacesContext ctx)
    {
        if (isPrependId())
        {
            return super.getContainerClientId(ctx);
        }
        UIComponent parentNamingContainer = ComponentUtils.findParentNamingContainer(this, false);
        if (parentNamingContainer != null)
        {
            return parentNamingContainer.getContainerClientId(ctx);
        }
        return null;
    }
}
