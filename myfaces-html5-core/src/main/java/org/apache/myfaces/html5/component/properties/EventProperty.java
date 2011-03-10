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

package org.apache.myfaces.html5.component.properties;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFProperty;

public interface EventProperty {

    /**
     * A String identifying the type of event the Ajax action will apply to. If specified, it must be one of
     * the events supported by the component the Ajax behavior is being
     * applied to.For HTML components this would be the set of supported DOM
     * events for the component, plus "action" for Faces ActionSource components and
     * "valueChange" for Faces EditableValueHolder components. If not specified, the
     * default event is determined for the component. The DOM event name is the
     * actual DOM event name (for example: "click") as opposed to (for example: "onclick").
     */
    @JSFProperty(deferredValueType = "java.lang.String")
    public abstract String getEvent();

}
