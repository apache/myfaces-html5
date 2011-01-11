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

/**
 * Convenience component for CSS transformation functions. Any number of the transforms can be used at once.
 *
 * @author Ali Ok
 */
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
     * Angle to rotate the ClientBehaviorHolder.
     * Value can be in: <br/>
     * <ul>
     * <li>deg: degrees (e.g. 90deg)</li>
     * <li>grad: grads</li>
     * <li>rad: radians</li>
     * <li>turn: turns (e.g. 0.5turn)</li>
     * </ul>
     * @see <a href="http://www.w3.org/TR/css3-values/#angles">http://www.w3.org/TR/css3-values/#angles</a>
     */
    @JSFProperty(deferredValueType = "java.lang.String", required = false)
    public abstract String getRotate();

    /**
     * Value to scale the ClientBehaviorHolder in X axis.<br/>
     * Value must be positive.
     */
    @JSFProperty(deferredValueType = "java.lang.Double", required = false)
    public abstract Double getScaleX();

    /**
     * Value to scale the ClientBehaviorHolder in Y axis.<br/>
     * Value must be positive.
     */
    @JSFProperty(deferredValueType = "java.lang.Double", required = false)
    public abstract Double getScaleY();

    /**
     * Angle to skew the ClientBehaviorHolder in X axis.
     * Value can be in: <br/>
     * <ul>
     * <li>deg: degrees (e.g. 90deg)</li>
     * <li>grad: grads</li>
     * <li>rad: radians</li>
     * <li>turn: turns (e.g. 0.5turn)</li>
     * </ul>
     * @see <a href="http://www.w3.org/TR/css3-values/#angles">http://www.w3.org/TR/css3-values/#angles</a>
     */
    @JSFProperty(deferredValueType = "java.lang.String", required = false)
    public abstract String getSkewX();

    /**
     * Angle to skew the ClientBehaviorHolder in Y axis.
     * Value can be in: <br/>
     * <ul>
     * <li>deg: degrees (e.g. 90deg)</li>
     * <li>grad: grads</li>
     * <li>rad: radians</li>
     * <li>turn: turns (e.g. 0.5turn)</li>
     * </ul>
     * @see <a href="http://www.w3.org/TR/css3-values/#angles">http://www.w3.org/TR/css3-values/#angles</a>
     */
    @JSFProperty(deferredValueType = "java.lang.String", required = false)
    public abstract String getSkewY();

    /**
     * Pixel value to translate the ClientBehaviorHolder in X axis.<br/>
     */
    @JSFProperty(deferredValueType = "java.lang.Double", required = false)
    public abstract Double getTranslateX();

    /**
     * Pixel value to translate the ClientBehaviorHolder in Y axis.<br/>
     */
    @JSFProperty(deferredValueType = "java.lang.Double", required = false)
    public abstract Double getTranslateY();
}
