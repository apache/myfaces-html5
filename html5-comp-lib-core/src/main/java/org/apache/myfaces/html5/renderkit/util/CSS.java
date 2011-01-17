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

public interface CSS {

    //animations are only supported on Webkit
    String ANIMATION_NAME_PROP = "-webkit-animation-name";
    String ANIMATION_DURATION_PROP = "-webkit-animation-duration";
    String ANIMATION_ITERATION_COUNT_PROP = "-webkit-animation-iteration-count";
    String ANIMATION_TIMING_FUNCTION_PROP = "-webkit-animation-timing-function";
    String ANIMATION_DIRECTION_PROP = "-webkit-animation-direction";
    String ANIMATION_DELAY_PROP = "-webkit-animation-delay";

    String TRANSITION_PROPERTY_PROP = "transition-property";
    String TRANSITION_DURATION_PROP = "transition-duration";
    String TRANSITION_TIMING_FUNCTION_PROP = "transition-timing-function";
    String TRANSITION_DELAY_PROP = "transition-delay";

    String TRANSFORMATION_FUNCTION_ROTATE = "rotate";
    String TRANSFORMATION_FUNCTION_SCALE_X = "scaleX";
    String TRANSFORMATION_FUNCTION_SCALE_Y = "scaleY";
    String TRANSFORMATION_FUNCTION_SKEW_X = "skewX";
    String TRANSFORMATION_FUNCTION_SKEW_Y = "skewY";
    String TRANSFORMATION_FUNCTION_TRANSLATE_X = "translateX";
    String TRANSFORMATION_FUNCTION_TRANSLATE_Y = "translateY";
}
