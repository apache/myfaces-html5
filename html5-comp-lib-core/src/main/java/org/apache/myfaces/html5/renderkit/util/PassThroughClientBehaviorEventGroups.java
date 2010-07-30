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
import static org.apache.myfaces.html5.renderkit.util.JsfProperties.*;
import static org.apache.myfaces.html5.renderkit.util.ClientBehaviorEvents.*;

import java.util.Map;

/**
 * Includes pass through behavior event groups.
 * <p>
 * These groups are not intended to be exposed public, so that's why this is extracted from {@link PassThroughClientBehaviorEvents}. 
 * @author Ali Ok
 *
 */
interface PassThroughClientBehaviorEventGroups
{

    Map<String, String> HTML_GLOBAL_BEHAVIOR_EVENTS = map(12)
        .event(ONBLUR_PROP, BLUR_EVENT)
        .event(ONCLICK_PROP, CLICK_EVENT)
        .event(ONDBLCLICK_PROP, DBLCLICK_EVENT)
        .event(ONFOCUS_PROP, FOCUS_EVENT)
        .event(ONKEYDOWN_PROP, KEYDOWN_EVENT)
        .event(ONKEYPRESS_PROP, KEYPRESS_EVENT)
        .event(ONKEYUP_PROP, KEYUP_EVENT)
        .event(ONMOUSEDOWN_PROP, MOUSEDOWN_EVENT)
        .event(ONMOUSEMOVE_PROP, MOUSEMOVE_EVENT)
        .event(ONMOUSEOUT_PROP, MOUSEOUT_EVENT)
        .event(ONMOUSEOVER_PROP, MOUSEOVER_EVENT)
        .event(ONMOUSEUP_PROP, MOUSEUP_EVENT)
        .unmodifiable();
    
    Map<String, String> DND_BEHAVIOR_EVENTS = map(6)
        .event(ONDRAG_PROP, DRAG_EVENT)
        .event(ONDRAGEND_PROP, DRAGEND_EVENT)
        .event(ONDRAGENTER_PROP, DRAGENTER_EVENT)
        .event(ONDRAGLEAVE_PROP, DRAGLEAVE_EVENT)
        .event(ONDRAGOVER_PROP, DRAGOVER_EVENT)
        .event(ONDRAGSTART_PROP, DRAGSTART_EVENT)
        .event(ONDROP_PROP, DROP_EVENT)
        .unmodifiable();

    
    Map<String, String> HTML5_INPUT_BEHAVIOR_EVENTS = map(4)
        .event(ONFORMCHANGE_PROP, FORMCHANGE_EVENT)
        .event(ONFORMINPUT_PROP, FORMINPUT_EVENT)
        .event(ONINPUT_PROP, INPUT_EVENT)
        .event(ONINVALID_PROP, INVALID_EVENT)
        .unmodifiable();
    
    
    Map<String, String> HTML5_GLOBAL_BEHAVIOR_EVENTS = map(7)
        .events(DND_BEHAVIOR_EVENTS)
        .event(ONMOUSEWHEEL_PROP, MOUSEWHEEL_EVENT)
        .unmodifiable();
    
}
