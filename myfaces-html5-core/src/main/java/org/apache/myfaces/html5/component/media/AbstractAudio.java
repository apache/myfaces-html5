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
package org.apache.myfaces.html5.component.media;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFComponent;

/**
 * Component that represents HTML5 audio element.
 * 
 * @author Ali Ok
 * 
 */
@JSFComponent(
        name = "hx:audio",
        clazz = "org.apache.myfaces.html5.component.media.Audio",
        tagClass = "org.apache.myfaces.html5.tag.media.AudioTag",
        defaultRendererType = "org.apache.myfaces.html5.Audio",
        family = "org.apache.myfaces.Media",
        type = "org.apache.myfaces.html5.Audio",
        implementz = "javax.faces.component.behavior.ClientBehaviorHolder",
        defaultEventName="play"
    )
public abstract class AbstractAudio extends org.apache.myfaces.html5.component.media.Media
{

}
