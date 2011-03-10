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
import org.apache.myfaces.html5.component.properties.PlaceHolderProperty;

/**
 * Convenience component that targets HTML5 email input.
 * 
 * @author Ali Ok
 * 
 */
@JSFComponent(
        name = "hx:inputEmail", 
        clazz = "org.apache.myfaces.html5.component.HtmlInputEmail", 
        tagClass = "org.apache.myfaces.html5.tag.HtmlInputEmailTag", 
        defaultRendererType = "org.apache.myfaces.html5.Email", 
        family = "javax.faces.Input", 
        type = "org.apache.myfaces.html5.HtmlInputEmail",
        implementz = "javax.faces.component.behavior.ClientBehaviorHolder",
        defaultEventName="valueChange")
public abstract class AbstractHtmlInputEmail extends org.apache.myfaces.html5.component.input.Html5BaseInputText
        implements PlaceHolderProperty
{
    /**
     * This field defines the permission for multiple values for this input. Provided values must be separated with
     * comma.
     * 
     * @return
     */
    @JSFProperty(defaultValue = "false", deferredValueType = "java.lang.Boolean")
    public abstract boolean isMultiple();
}
