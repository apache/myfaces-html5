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

import static org.apache.myfaces.html5.renderkit.util.AttributeMap.map;
import static org.apache.myfaces.html5.renderkit.util.HTML5.*;
import static org.apache.myfaces.html5.renderkit.util.JsfProperties.*;
import org.apache.myfaces.html5.renderkit.util.PassThroughAttributeGroups;

import java.util.Map;

/**
 * Includes pass through attributes for components.
 * @author Ali Ok
 *
 */
public interface PassThroughAttributes
{
    Map<String, String> AUDIO = map(13)
        .attrs(PassThroughAttributeGroups.HTML5_GLOBAL_PROPS)
        //media props
        .attr(PRELOAD_PROP)
        .attr(SHOW_CONTROLS_PROP, CONTROLS_ATTR)
        .attr(LOOP_PROP)
        .attr(AUTOPLAY_PROP)
        //standard attrs parent doesn't have
        .unmodifiable();

    Map<String, String> VIDEO = map(15)
        .attrs(AUDIO)
        .attr(POSTER_PROP)
        .attr(WIDTH_PROP)
        .attr(HEIGHT_PROP)
        .unmodifiable();

    Map<String, String> INPUT_COLOR = map(4)
        .attrs(PassThroughAttributeGroups.HTML5_GLOBAL_PROPS)
        .attrs(PassThroughAttributeGroups.HTML5_INPUT_PROPS)
        .unmodifiable();

    Map<String, String> INPUT_TEXT = map(6)
        .attrs(PassThroughAttributeGroups.HTML5_GLOBAL_PROPS)
        .attrs(PassThroughAttributeGroups.HTML5_INPUT_PROPS)
        .attr(PLACEHOLDER_PROP)
        .attr(REQUIRED_PROP)
        .unmodifiable();
    
    Map<String, String> INPUT_SECRET = map(5)
        .attrs(PassThroughAttributeGroups.HTML5_GLOBAL_PROPS)
        .attr(AUTOFOCUS_PROP)
        .attr(PLACEHOLDER_PROP)
        .attr(REQUIRED_PROP)
        .unmodifiable();

    Map<String, String> INPUT_TEXTAREA = map(5)
        .attrs(PassThroughAttributeGroups.HTML5_GLOBAL_PROPS)
        .attr(AUTOFOCUS_PROP)
        .attr(REQUIRED_PROP)
        .attr(MAXLENGTH_PROP)
        .unmodifiable();

    Map<String, String> INPUT_EMAIL = map(7)
        .attrs(PassThroughAttributeGroups.HTML5_GLOBAL_PROPS)
        .attrs(PassThroughAttributeGroups.HTML5_INPUT_PROPS)
        .attr(PLACEHOLDER_PROP)
        .attr(MULTIPLE_PROP)
        .attr(REQUIRED_PROP)
        .unmodifiable();

    Map<String, String> DIV = map(7)
        .attrs(PassThroughAttributeGroups.HTML_GLOBAL_PROPS)
        .attrs(PassThroughAttributeGroups.HTML5_GLOBAL_PROPS)
        .unmodifiable();

    Map<String, String> INPUT_NUMBER_SLIDER = map(4)
        .attrs(PassThroughAttributeGroups.HTML5_GLOBAL_PROPS)
        .attrs(PassThroughAttributeGroups.HTML5_INPUT_PROPS)
        .unmodifiable();

    Map<String, String> INPUT_NUMBER_SPINNER = map(6)
        .attrs(PassThroughAttributeGroups.HTML5_GLOBAL_PROPS)
        .attrs(PassThroughAttributeGroups.HTML5_INPUT_PROPS)
        .attr(REQUIRED_PROP)
        .attr(READONLY_PROP)
        .unmodifiable();

    Map<String, String> INPUT_DATE_TIME = map(6)
        .attrs(PassThroughAttributeGroups.HTML5_GLOBAL_PROPS)
        .attrs(PassThroughAttributeGroups.HTML5_INPUT_PROPS)
        .attr(REQUIRED_PROP)
        .attr(READONLY_PROP)
        .unmodifiable();
    
}
