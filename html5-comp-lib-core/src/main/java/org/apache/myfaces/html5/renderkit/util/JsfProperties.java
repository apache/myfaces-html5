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
 * Includes constants for JSF component property names. <br/>
 * Note that, this interface does not contain all of the properties of all components. Pass thru properties will be here
 * for sure though.
 * 
 * @author Ali Ok
 * 
 */
public interface JsfProperties
{
    // common props
    String ID_PROP = "id";
    String DIR_PROP = "dir";
    String LANG_PROP = "lang";
    String STYLE_PROP = "style";
    String STYLECLASS_PROP = "styleClass";
    String TITLE_PROP = "title";
    String ACCESSKEY_PROP = "accesskey";
    String TABINDEX_PROP = "tabindex";
    String SRC_PROP = "src";
    String WIDTH_PROP = "width";
    String HEIGHT_PROP = "height";

    // new Html5 common props
    String HIDDEN_PROP = "hidden";
    String DRAGGABLE_PROP = "draggable";

    // media props
    String PRELOAD_PROP = "preload";
    String SHOW_CONTROLS_PROP = "showControls";
    String LOOP_PROP = "loop";
    String AUTOPLAY_PROP = "autoplay";
    String POSTER_PROP = "poster";

    // common event props
    String ONBLUR_PROP = "onblur";
    String ONCLICK_PROP = "onclick";
    String ONDBLCLICK_PROP = "ondblclick";
    String ONFOCUS_PROP = "onfocus";

    //DnD props
    String ONDRAG_PROP = "ondrag";
    String ONDRAGEND_PROP = "ondragend";
    String ONDRAGENTER_PROP = "ondragenter";
    String ONDRAGLEAVE_PROP = "ondragleave";
    String ONDRAGOVER_PROP = "ondragover";
    String ONDRAGSTART_PROP = "ondragstart";
    String ONDROP_PROP = "ONDROP";

    // media event props
    String ONENDED_PROP = "onended";
    String ONERROR_PROP = "onerror";
    String ONLOADEDDATA_PROP = "onloadeddata";
    String ONLOADEDMETADATA_PROP = "onloadedmetadata";
    String ONLOADSTART_PROP = "onloadstart";
    String ONPAUSE_PROP = "onpause";
    String ONPLAY_PROP = "onplay";
    String ONPLAYING_PROP = "onplaying";
    String ONPROGRESS_PROP = "onprogress";
    String ONSEEKED_PROP = "onseeked";
    String ONSEEKING_PROP = "onseeking";
    String ONVOLUMECHANGE_PROP = "onvolumechange";
    String ONWAITING_PROP = "onwaiting";

    //common key props
    String ONKEYDOWN_PROP = "onkeydown";
    String ONKEYPRESS_PROP = "onkeypress";
    String ONKEYUP_PROP = "onkeyup";

    //common mouse props
    String ONMOUSEDOWN_PROP = "onmousedown";
    String ONMOUSEMOVE_PROP = "onmousemove";
    String ONMOUSEOUT_PROP = "onmouseout";
    String ONMOUSEOVER_PROP = "onmouseover";
    String ONMOUSEUP_PROP = "onmouseup";
    String ONMOUSEWHEEL_PROP = "onmousewheel";      //is a new Html5 mouse attribute

    // html5 new input props
    String DATALIST_PROP = "datalist";
    String AUTOFOCUS_PROP = "autofocus";
    String ONFORMCHANGE_PROP = "onformchange";
    String ONFORMINPUT_PROP = "onforminput";
    String ONINPUT_PROP = "oninput";
    String ONINVALID_PROP = "oninvalid";

    // input text props
    String PLACEHOLDER_PROP = "placeholder";
    String REQUIRED_PROP = "required";
    String MAXLENGTH_PROP = "maxlength";
    String READONLY_PROP = "readonly";
    String STEP_PROP = "step";

    // input email props
    String MULTIPLE_PROP = "multiple";

    // possible types for hx:inputText
    String INPUTTEXT_TYPE_PASSWORD = "password";
    String INPUTTEXT_TYPE_TEXTAREA = "textarea";
    String INPUTTEXT_TYPE_TEXT = "text";
    String INPUTTEXT_TYPE_SEARCH = "search";
    String INPUTTEXT_TYPE_URL = "url";
    String INPUTTEXT_TYPE_TEL = "tel";

    //possible types for hx:inputDateTime
    String INPUTDATETIME_TYPE_DATETIME = "datetime";
    String INPUTDATETIME_TYPE_DATE = "date";
    String INPUTDATETIME_TYPE_TIME = "time";
    String INPUTDATETIME_TYPE_MONTH = "month";
    String INPUTDATETIME_TYPE_WEEK = "week";
    String INPUTDATETIME_TYPE_DATETIME_LOCAL = "datetime-local";
    

}
