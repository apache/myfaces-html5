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

package org.apache.myfaces.html5.component.properties.effect;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFProperty;

public interface TransitionProperties {

    /**
     * Duration for animation/effect to run through. <br/>
     * Value must be in seconds or milliseconds, thus must end with 's' or 'ms'. If not, the value is considered in seconds.<br/>
     * Some of the sample values are:
     * <ul>
     * <li>'1' : 1 second</li>
     * <li>'1s' : 1 seconds</li>
     * <li>'0.1' : 0.1 second</li>
     * <li>'100ms' : 100 milliseconds</li>
     * </ul>
     */
    @JSFProperty(deferredValueType = "java.lang.String")
    public abstract String getDuration();

    /**
     * Timing function to calculate animation/effect transitions. <br/>
     * The timing functions defined by CSS3 spec are:
     * <ul>
     * <li>ease</li>
     * <li>linear</li>
     * <li>ease-in</li>
     * <li>ease-out</li>
     * <li>ease-in-out</li>
     * <li>cubic-bezier(<number>, <number>, <number>, <number>)</li>
     * </ul>
     */
    @JSFProperty(deferredValueType = "java.lang.String")
    public abstract String getTimingFunction();

    /**
     * Duration for animation/effect to wait before running. <br/>
     * Value must be in seconds or milliseconds, thus must end with 's' or 'ms'. If not, the value is considered in seconds.<br/>
     * Some of the sample values are:
     * <ul>
     * <li>'1' : 1 second</li>
     * <li>'1s' : 1 seconds</li>
     * <li>'0.1' : 0.1 second</li>
     * <li>'100ms' : 100 milliseconds</li>
     * </ul>
     */
    @JSFProperty(deferredValueType = "java.lang.String")
    public abstract String getDelay();

}
