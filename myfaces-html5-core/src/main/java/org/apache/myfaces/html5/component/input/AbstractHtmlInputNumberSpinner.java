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
 * Convenience component for Html5 number input. <br/>
 * Just like hx:inputNumberSlider, minimum and maximum values for the component is rendered based on
 * f:validateDoubleRange or f:validateLongRange if attached. If they are not attached, a default value of 0 is used for
 * minimum and 100 is used for maximum.
 * 
 * @author Ali Ok
 * 
 */
@JSFComponent(
        name = "hx:inputNumberSpinner",
        clazz = "org.apache.myfaces.html5.component.input.HtmlInputNumberSpinner",
        tagClass = "org.apache.myfaces.html5.tag.input.HtmlInputNumberSpinnerTag",
        defaultRendererType = "org.apache.myfaces.html5.NumberSpinner",
        family = "javax.faces.Input",
        type = "org.apache.myfaces.html5.HtmlNumberSpinner",
        implementz = "javax.faces.component.behavior.ClientBehaviorHolder", //need to define it, or the events wont be rendered
        defaultEventName="valueChange"
        )
public class AbstractHtmlInputNumberSpinner extends org.apache.myfaces.html5.component.input.HtmlInputNumberSlider
{

    @JSFProperty(defaultValue = "false", deferredValueType = "java.lang.Boolean")
    @Override
    public boolean isReadonly()
    {
        return false;
    }
}
