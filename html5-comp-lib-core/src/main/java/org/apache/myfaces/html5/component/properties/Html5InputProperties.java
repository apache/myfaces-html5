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

public interface Html5InputProperties
{
    /**
     * If this property is set to true, it will allow the user to just start typing without having to manually focus the
     * main control when the page is loaded. Default to false.
     * 
     */
    @JSFProperty(defaultValue = "false", deferredValueType = "java.lang.Boolean")
    public abstract boolean isAutoFocus();

    /**
     * Static(not Ajax) suggestion values. This attribute should not be defined if "list" is set.
     * 
     * @see list property
     */
    @JSFProperty(deferredValueType = "java.lang.Object")
    // since this property accepts both comma separated string and List<SelectItem>
    public abstract Object getSuggestions();

    /**
     * Id of <hx:datalist> or HTML <datalist> for suggestions mechanism. By this way, suggestion options(datalist) can
     * be shared across several input elements. If this attribute is set, other suggestion mechanisms(with
     * f:selectItem(s) children or 'suggestions' attribute) should not be used
     * 
     */
    @JSFProperty(deferredValueType = "java.lang.String")
    public abstract String getDataList();

    /**
     * HTML: Script to be invoked when the state of the owner form is changed.
     * 
     */
    @JSFProperty(clientEvent = "formchange")
    public String getOnformchange();

    /**
     * HTML: Script to be invoked when the owner form gets user input.
     * 
     */
    @JSFProperty(clientEvent = "forminput")
    public String getOnforminput();

    /**
     * HTML: Script to be invoked when the element gets user input.
     * 
     */
    @JSFProperty(clientEvent = "input")
    public String getOninput();

    /**
     * HTML: Script to be invoked when the owner form is validated and this element could not pass the validation.
     * 
     */
    @JSFProperty(clientEvent = "invalid")
    public String getOninvalid();

}
