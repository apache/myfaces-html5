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
 * Includes pass through behavior event definitions for components.
 * @author Ali Ok
 *
 */
public interface PassThroughClientBehaviorEvents
{

    Map<String, String> AUDIO = map(32)
        .events(PassThroughClientBehaviorEventGroups.HTML5_GLOBAL_BEHAVIOR_EVENTS)
        //media event props
        .event(ONENDED_PROP, ENDED_EVENT)
        .event(ONERROR_PROP, ERROR_EVENT)
        .event(ONLOADEDDATA_PROP, LOADEDDATA_EVENT)
        .event(ONLOADEDMETADATA_PROP, LOADEDMETADATA_EVENT)
        .event(ONLOADSTART_PROP, LOADSTART_EVENT)
        .event(ONPAUSE_PROP, PAUSE_EVENT)
        .event(ONPLAY_PROP, PLAY_EVENT)
        .event(ONPLAYING_PROP, PLAYING_EVENT)
        .event(ONPROGRESS_PROP, PROGRESS_EVENT)
        .event(ONSEEKED_PROP, SEEKED_EVENT)
        .event(ONSEEKING_PROP, SEEKING_EVENT)
        .event(ONVOLUMECHANGE_PROP, VOLUMECHANGE_EVENT)
        .event(ONWAITING_PROP, WAITING_EVENT)
        //standard events parent doesn't have
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
    
    Map<String, String> VIDEO = map(32)
        .events(AUDIO)
        .unmodifiable();
    
    Map<String, String> BASE_INPUT = map(11)
        .events(PassThroughClientBehaviorEventGroups.HTML5_GLOBAL_BEHAVIOR_EVENTS)
        .events(PassThroughClientBehaviorEventGroups.HTML5_INPUT_BEHAVIOR_EVENTS)
        .unmodifiable();

    Map<String, String> DIV = map(11)
        .events(PassThroughClientBehaviorEventGroups.HTML_GLOBAL_BEHAVIOR_EVENTS)
        .events(PassThroughClientBehaviorEventGroups.HTML5_GLOBAL_BEHAVIOR_EVENTS)
        .unmodifiable();
    
    
}
