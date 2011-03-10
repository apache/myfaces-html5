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
 * Convenience component that targets HTML5 color input. 
 * <br/>
 * Expects value to be set as defined in <a
 * href="http://www.whatwg.org/specs/web-apps/current-work/multipage/common-microsyntaxes.html#valid-simple-color">valid
 * simple color definition</a>. A simple valid color is basically hex representation of the colors, likewise used in
 * CSS. e.g: #FFAABB, #12ab3C. 
 * <br/>
 * Extends the functionality of < h:inputText >.
 * 
 * @author Ali Ok
 * 
 */
@JSFComponent(
        name = "hx:inputColor",
        clazz = "org.apache.myfaces.html5.component.input.HtmlInputColor",
        tagClass = "org.apache.myfaces.html5.tag.input.HtmlInputColorTag",
        defaultRendererType = "org.apache.myfaces.html5.Color",
        family = "javax.faces.Input",
        type = "org.apache.myfaces.html5.HtmlInputColor",
        implementz = "javax.faces.component.behavior.ClientBehaviorHolder", //need to define it, or the events wont be rendered
        defaultEventName="valueChange"
        )
public abstract class AbstractHtmlInputColor extends org.apache.myfaces.html5.component.input.Html5BaseInputText
{

    @JSFProperty(tagExcluded=true, defaultValue="false")
    @Override
    public boolean isReadonly()
    {
        //this attribute is not present in HTML5 color input
        return false;
    }
    
    @JSFProperty(tagExcluded=true, defaultValue="Integer.MIN_VALUE")
    @Override
    public int getSize()
    {
        //this attribute is not present in HTML5 color input
        return Integer.MIN_VALUE;
    }
    
    /**
     * Text to be displayed to the user as an error message when input data is not a valid simple color during a
     * postback. See <a
     * href="http://www.whatwg.org/specs/web-apps/current-work/multipage/common-microsyntaxes.html#valid-simple-color"
     * >valid simple color definition</a>.
     */
    @JSFProperty
    public abstract String getInvalidColorMessage();
    
}
