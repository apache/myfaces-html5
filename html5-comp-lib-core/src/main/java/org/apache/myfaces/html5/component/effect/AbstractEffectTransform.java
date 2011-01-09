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

package org.apache.myfaces.html5.component.effect;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFComponent;
import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFProperty;

@JSFComponent(
        name = "fx:effectTransform",
        clazz = "org.apache.myfaces.html5.component.effect.EffectTransform",
        tagClass = "org.apache.myfaces.html5.tag.effect.EffectTransformTag",
        defaultRendererType = "org.apache.myfaces.html5.EffectTransform",
        family = "org.apache.myfaces.EffectTransform",
        type = "org.apache.myfaces.html5.EffectTransform"
)
public abstract class AbstractEffectTransform extends org.apache.myfaces.html5.component.effect.BaseEffect{

    /**
     *
     * Can be : <br/>
     * <ul>
     * <li>deg: degrees</li>
     * <li>grad: grads</li>
     * <li>rad: radians</li>
     * <li>turn: turns</li>
     * </ul>
     * @see <a href="http://www.w3.org/TR/css3-values/#angles">http://www.w3.org/TR/css3-values/#angles</a>
     */
    @JSFProperty(deferredValueType = "java.lang.String", required = false)
    public abstract String getRotate();

    @JSFProperty(deferredValueType = "java.lang.Double", required = false)
    public abstract Double getScaleX();

    @JSFProperty(deferredValueType = "java.lang.Double", required = false)
    public abstract Double getScaleY();

    /**
     *
     * Can be : <br/>
     * <ul>
     * <li>deg: degrees</li>
     * <li>grad: grads</li>
     * <li>rad: radians</li>
     * <li>turn: turns</li>
     * </ul>
     * @see <a href="http://www.w3.org/TR/css3-values/#angles">http://www.w3.org/TR/css3-values/#angles</a>
     */
    @JSFProperty(deferredValueType = "java.lang.String", required = false)
    public abstract String getSkewX();

    /**
     *
     * Can be : <br/>
     * <ul>
     * <li>deg: degrees</li>
     * <li>grad: grads</li>
     * <li>rad: radians</li>
     * <li>turn: turns</li>
     * </ul>
     * @see <a href="http://www.w3.org/TR/css3-values/#angles">http://www.w3.org/TR/css3-values/#angles</a>
     */
    @JSFProperty(deferredValueType = "java.lang.String", required = false)
    public abstract String getSkewY();

    @JSFProperty(deferredValueType = "java.lang.Double", required = false)
    public abstract Double getTranslateX();

    @JSFProperty(deferredValueType = "java.lang.Double", required = false)
    public abstract Double getTranslateY();
}
