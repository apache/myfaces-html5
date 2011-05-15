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
 * Html constants for using in renderers. <br/>
 * Note that, this interface does not contain all of the element names and attribute names specified in Html spec. It
 * only contains used ones in MyFaces Html5 components, and may not contain the attributes which have the same name with
 * the Jsf component property.
 * 
 * @author Ali Ok
 * @see org.apache.myfaces.commons.util.renderkit.HTML
 */
public interface HTML5
{

    // new html5 elements
    String VIDEO_ELEM = "video";
    String AUDIO_ELEM = "audio";
    String SOURCE_ELEM = "source";
    String DATALIST_ELEM = "datalist";

    // html elements
    String INPUT_ELEM = "input";
    String TEXTAREA_ELEM = "textarea";
    String DIV_ELEM = "div";
    String STYLE_ELEM = "style";
    String SCRIPT_ELEM = "script";
    String OPTION_ELEM = "option";

    // general attrs
    String ID_ATTR = "id";
    String HEIGHT_ATTR = "height";
    String WIDTH_ATTR = "width";
    String SRC_ATTR = "src";
    String TYPE_ATTR = "type";
    String CLASS_ATTR = "class";
    String STYLE_ATTR = "style";
    String VALUE_ATTR = "value";
    String DISABLED_ATTR = "disabled";
    String LABEL_ATTR = "label";
    String SCRIPT_TYPE_ATTR = "type";
    String SCRIPT_TYPE_TEXT_JAVASCRIPT = "text/javascript";
    String NAME_ATTR = "name";

    // media attrs
    String CONTROLS_ATTR = "controls"; // not pass thru

    // video attrs
    String POSTER_ATTR = "poster";

    // media source attrs
    String MEDIA_ATTR = "media";

    // input types
    String INPUT_TYPE_TEXT = "text";
    String INPUT_TYPE_PASSWORD = "password";
    // new HTML5 input types
    String INPUT_TYPE_COLOR = "color";
    String INPUT_TYPE_SEARCH = "search";
    String INPUT_TYPE_TEL = "tel";
    String INPUT_TYPE_URL = "url";
    String INPUT_TYPE_EMAIL = "email";
    String INPUT_TYPE_RANGE = "range";
    String INPUT_TYPE_NUMBER = "number";
    String INPUT_TYPE_DATETIME = "datetime";
    String INPUT_TYPE_DATE = "date";
    String INPUT_TYPE_TIME = "time";
    String INPUT_TYPE_MONTH = "month";
    String INPUT_TYPE_WEEK = "week";
    String INPUT_TYPE_DATETIME_LOCAL = "datetime-local";

    // input attrs
    String LIST_ATTR = "list";
    String PATTERN_ATTR = "pattern";
    String MIN_ATTR = "min";
    String MAX_ATTR = "max";
    String STEP_ATTR = "step";
    String AUTOCOMPLETE_ATTR = "autocomplete";

    // new Html5 attributes which is boolean but not Html5 boolean
    // @see Html5RendererUtils#renderHTMLAttribute(javax.faces.context.ResponseWriter, String, String, Object)
    String DRAGGABLE_ATTR = "draggable";
    String CONTENTEDITABLE_ATTR = "contenteditable";
    String SPELLCHECK_ATTR = "spellcheck";


    // meter
    String LOW_ATTR = "low";
    String HIGH_ATTR = "high";
    String OPTIMUM_ATTR = "optimum";

}
