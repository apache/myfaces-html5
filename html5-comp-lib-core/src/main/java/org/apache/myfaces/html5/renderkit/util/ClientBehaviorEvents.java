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

/**
 * Holds the client behavior events. <br/>
 * Note that this interface does not contain all of the events specified in Html5 spec, it contains only the used ones
 * in MyFaces Html5 Components.
 * 
 * @author Ali Ok
 */
public interface ClientBehaviorEvents
{
    // DnD Events
    String DRAG_EVENT = "drag";
    String DROP_EVENT = "drop";
    String DRAGENTER_EVENT = "dragenter";
    String DRAGLEAVE_EVENT = "dragleave";
    String DRAGOVER_EVENT = "dragover";
    String DRAGSTART_EVENT = "dragstart";
    String DRAGEND_EVENT = "dragend";

    // Form events that are new with Html5
    String FORMCHANGE_EVENT = "formchange";
    String FORMINPUT_EVENT = "forminput";
    String INPUT_EVENT = "input";
    String INVALID_EVENT = "invalid";

    // Mouse event that is new with Html5
    String MOUSEWHEEL_EVENT = "mousewheel";

    // Video and audio events
    String ENDED_EVENT = "ended";
    String ERROR_EVENT = "error";
    String LOADEDDATA_EVENT = "loadeddata";
    String LOADEDMETADATA_EVENT = "loadedmetadata";
    String LOADSTART_EVENT = "loadstart";
    String PAUSE_EVENT = "pause";
    String PLAY_EVENT = "play";
    String PLAYING_EVENT = "playing";
    String PROGRESS_EVENT = "progress";
    String SEEKED_EVENT = "seeked";
    String SEEKING_EVENT = "seeking";
    String VOLUMECHANGE_EVENT = "volumechange";
    String WAITING_EVENT = "waiting";

    // Events that are NOT new with Html5
    String BLUR_EVENT = "blur";
    String CLICK_EVENT = "click";
    String DBLCLICK_EVENT = "dblclick";
    String FOCUS_EVENT = "focus";
    String KEYDOWN_EVENT = "keydown";
    String KEYPRESS_EVENT = "keypress";
    String KEYUP_EVENT = "keyup";
    String MOUSEDOWN_EVENT = "mousedown";
    String MOUSEMOVE_EVENT = "mousemove";
    String MOUSEOUT_EVENT = "mouseout";
    String MOUSEOVER_EVENT = "mouseover";
    String MOUSEUP_EVENT = "mouseup";
}
