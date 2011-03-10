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
package org.apache.myfaces.html5.renderkit.util;

import static org.apache.myfaces.html5.renderkit.util.AttributeMap.*;
import static org.apache.myfaces.html5.renderkit.util.JsfProperties.*;
import static org.apache.myfaces.html5.renderkit.util.HTML5.*;

import java.util.Map;

/**
 * Includes pass through property groups.
 * <p>
 * These groups are not intended to be exposed public, so that's why this is extracted from {@link PassThroughAttributes}. 
 * @author Ali Ok
 *
 */
interface PassThroughAttributeGroups {
    
    Map<String, String> HTML_GLOBAL_PROPS = map(7)
        .attr(DIR_PROP)
        .attr(LANG_PROP)
        .attr(STYLE_PROP)
        .attr(STYLECLASS_PROP, CLASS_ATTR)
        .attr(TITLE_PROP)
        .attr(ACCESSKEY_PROP)
        .attr(TABINDEX_PROP)
        .unmodifiable();
    
    Map<String, String> DND_PROPS = map(1)
        .attr(DRAGGABLE_PROP)
        .unmodifiable();
    
    Map<String, String> HTML5_INPUT_PROPS = map(2)
        .attr(DATALIST_PROP, LIST_ATTR)
        .attr(AUTOFOCUS_PROP)
        .unmodifiable();
    
    Map<String, String> HTML5_GLOBAL_PROPS = map(2)
        .attrs(DND_PROPS)
        .attr(HIDDEN_PROP)
        .unmodifiable();
}