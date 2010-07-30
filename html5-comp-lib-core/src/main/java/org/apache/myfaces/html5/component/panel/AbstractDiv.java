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
package org.apache.myfaces.html5.component.panel;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFComponent;
import org.apache.myfaces.html5.component.properties.AccesskeyProperty;
import org.apache.myfaces.html5.component.properties.GlobalEventProperties;
import org.apache.myfaces.html5.component.properties.Html5GlobalProperties;
import org.apache.myfaces.html5.component.properties.KeyEventProperties;
import org.apache.myfaces.html5.component.properties.MouseEventProperties;
import org.apache.myfaces.html5.component.properties.TabindexProperty;

/**
 * Component that supports new functionality like Html5 DnD. <br/>
 * The reason of having this component is, providing a way to use new functionality in old components. For example, to
 * make a h:column of a h:dataTable draggable, the user can put a hx:div inside the h:column wrapping the column
 * content; then putting a fx:dragSource in it.
 * 
 * @author Ali Ok
 * 
 */
@JSFComponent(
        name = "hx:div",
        clazz = "org.apache.myfaces.html5.component.panel.Div",
        tagClass = "org.apache.myfaces.html5.tag.panel.DivTag",
        defaultRendererType = "org.apache.myfaces.html5.Div",
        family = "org.apache.myfaces.Div",
        type = "org.apache.myfaces.html5.Div",
        implementz = "javax.faces.component.behavior.ClientBehaviorHolder",
        defaultEventName="drop"
)
public abstract class AbstractDiv extends javax.faces.component.UIComponentBase implements
        javax.faces.component.behavior.ClientBehaviorHolder, Html5GlobalProperties, AccesskeyProperty,
        TabindexProperty, MouseEventProperties, KeyEventProperties, GlobalEventProperties
{

}
