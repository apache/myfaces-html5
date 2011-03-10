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

/**
 *
 * @author Ali Ok
 */
public interface TargetProperty {

    /**
     * The <em>target</em> area for which this resource will be rendered.  For example, <em>target="head"</em> would
     * cause the resource to be rendered within the <em>head</em> element.<br/>
     * If not defined and component is not nested inside of a ClientBehaviorHolder <em>head</em> will be used.<br/>
     * If component is nested inside of a ClientBehaviorHolder and this attribute is not defined <em>body</em> will be used.<br/>
     */
    @JSFProperty(deferredValueType = "java.lang.String", defaultValue = "body")
    public abstract String getTarget();

}
