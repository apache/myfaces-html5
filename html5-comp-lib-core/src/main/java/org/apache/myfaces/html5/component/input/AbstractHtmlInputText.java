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

import javax.faces.component.behavior.ClientBehaviorHolder;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFComponent;
import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFProperty;
import org.apache.myfaces.html5.component.properties.PlaceHolderProperty;

/**
 * Extends standard inputText by new HTML5 capabilities.
 * 
 * @author Ali Ok
 * 
 */
@JSFComponent(
        name = "hx:inputText", 
        clazz = "org.apache.myfaces.html5.component.input.HtmlInputText", 
        tagClass = "org.apache.myfaces.html5.tag.input.HtmlInputTextTag", 
        defaultRendererType = "org.apache.myfaces.html5.Text", 
        family = "javax.faces.Input", 
        type = "org.apache.myfaces.html5.HtmlInputText",
        implementz = "javax.faces.component.behavior.ClientBehaviorHolder",
        defaultEventName="valueChange")
public abstract class AbstractHtmlInputText extends org.apache.myfaces.html5.component.input.Html5BaseInputText implements ClientBehaviorHolder, PlaceHolderProperty
{
    
    public static final int ROWS_DEFAULT_VALUE = 1;

    /**
     * HTML type of the input.
     * <br/>
     * Possible values are "text"(default), "search", "url", "tel", "textarea" and "password". 
     * If this is set to "textarea", pattern is ignored.
     * @see AbstractHtmlInputText#getSuggestions()
     * @see AbstractHtmlInputText#getList()
     * @see AbstractHtmlInputText#getCols()
     * @see AbstractHtmlInputText#getRows() 
     * @return
     */
    //default value not set with annotation because of some exceptional things
    //default value is set manually in renderer
    @JSFProperty(deferredValueType = "java.lang.String")    
    public abstract String getType();
    
    
    /**
     * Column count to set size, defaults to 10. Value of this property is used as size in "text", "search", "tel",
     * "url" and "password" types. It is also used as cols in "textarea" type.
     */
    @JSFProperty(deferredValueType = "java.lang.Integer", defaultValue="10")
    public abstract int getCols();
    
    
    /**
     * Number of rows to display, defaults to 1. Value of this property is used as rows in "textarea" type. It is
     * ignored if the type is one of "text", "search", "tel", "url" and "password". If type is not set, and this
     * property is set to a number larger than 1, type will be automatically set to "textarea".
     */
    @JSFProperty(defaultValue="ROWS_DEFAULT_VALUE", deferredValueType = "java.lang.Integer")
    public abstract int getRows();
    

}
