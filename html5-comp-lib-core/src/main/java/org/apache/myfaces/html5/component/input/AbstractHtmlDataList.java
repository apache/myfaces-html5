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

import java.util.logging.Logger;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFComponent;
import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFProperty;

@JSFComponent(
        name = "hx:dataList",
        clazz = "org.apache.myfaces.html5.component.input.HtmlDataList",
        tagClass = "org.apache.myfaces.html5.tag.input.HtmlDataListTag",
        defaultRendererType = "org.apache.myfaces.html5.DataList",
        family = "javax.faces.Output",
        type = "org.apache.myfaces.html5.HtmlDataList"
        )
        //TODO: docme 
public abstract class AbstractHtmlDataList extends javax.faces.component.UIComponentBase
{
    private static final Logger log = Logger.getLogger(AbstractHtmlDataList.class.getName());

    //TODO: docme
    @JSFProperty(deferredValueType = "java.lang.Object")
    // since this property accepts both comma separated string and List<SelectItem>
    public abstract Object getSuggestions();
}
