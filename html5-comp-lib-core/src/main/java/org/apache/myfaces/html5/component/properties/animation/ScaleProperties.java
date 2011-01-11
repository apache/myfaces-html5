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

package org.apache.myfaces.html5.component.properties.animation;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFProperty;

public interface ScaleProperties {
    static final double DEFAULT_MIN_SIZE = 0.5;
    static final double DEFAULT_MAX_SIZE = 1.0;

    /**
     * The minimum extreme point value to scale the ClientBehaviorHolder during the animation.<br/>
     * Defaults to 0.5.
     */
    @JSFProperty(deferredValueType = "java.lang.Double", defaultValue = "DEFAULT_MIN_SIZE")
    public abstract double getMinSize();

    /**
     * The maximum extreme point value to scale the ClientBehaviorHolder during the animation.<br/>
     * Defaults to 1 (back to original size).
     */
    @JSFProperty(deferredValueType = "java.lang.Double", defaultValue = "DEFAULT_MAX_SIZE")
    public abstract double getMaxSize();
}
