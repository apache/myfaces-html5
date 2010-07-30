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
package org.apache.myfaces.html5.component.input;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFComponent;
import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFProperty;

/**
 * Convenience component for Html5 input range. <br/>
 * Minimum and maximum values for the component is rendered based on f:validateDoubleRange or f:validateLongRange if
 * attached. If they are not attached, a default value of 0 is used for minimum and 100 is used for maximum.
 * 
 * @author Ali Ok
 * 
 */
@JSFComponent(
        name = "hx:inputNumberSlider",
        clazz = "org.apache.myfaces.html5.component.input.HtmlInputNumberSlider",
        tagClass = "org.apache.myfaces.html5.tag.input.HtmlInputNumberSliderTag",
        defaultRendererType = "org.apache.myfaces.html5.NumberSlider",
        family = "javax.faces.Input",
        type = "org.apache.myfaces.html5.HtmlNumberSlider",
        implementz = "javax.faces.component.behavior.ClientBehaviorHolder", //need to define it, or the events wont be rendered
        defaultEventName="valueChange"
        )
public abstract class AbstractHtmlInputNumberSlider extends org.apache.myfaces.html5.component.input.Html5BaseInputText
{

    @JSFProperty(tagExcluded = true, defaultValue = "Integer.MIN_VALUE")
    @Override
    //not present in Html5 range input, so exclude it
    public int getMaxlength()
    {
        return Integer.MIN_VALUE;
    }

    @JSFProperty(tagExcluded = true, defaultValue = "false")
    @Override
    //not present in Html5 range input, so exclude it
    public boolean isReadonly()
    {
        return false;
    }

    @JSFProperty(tagExcluded = true, defaultValue = "Integer.MIN_VALUE")
    @Override
    //not present in Html5 range input, so exclude it
    public int getSize()
    {
        return Integer.MIN_VALUE;
    }

    /**
     * Gap between each segment. If both 'step' and 'segmentCount' is not defined, 'step' is default to (max-min)/(100).
     */
    @JSFProperty(deferredValueType="java.lang.Double", defaultValue="Double.MIN_VALUE")
    public abstract double getStep();
    
    /**
     * Used to calculate step with minimum and maximum. Formula is:  step ~= (max-min)/segmentCount. 
     * <br/>
     * Defaults to 100, if step is not defined too. It is and error to define both 'step' and 'segmentCount'.
     */
    @JSFProperty(deferredValueType="java.lang.Integer", defaultValue="Integer.MIN_VALUE")
    public abstract int getSegmentCount();
}
