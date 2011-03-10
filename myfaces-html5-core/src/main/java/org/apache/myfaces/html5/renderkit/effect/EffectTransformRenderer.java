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

package org.apache.myfaces.html5.renderkit.effect;

import org.apache.commons.lang.StringUtils;
import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFRenderer;
import org.apache.myfaces.html5.component.effect.AbstractBaseEffect;
import org.apache.myfaces.html5.component.effect.AbstractEffectTransform;
import org.apache.myfaces.html5.renderkit.util.CSS;
import org.apache.myfaces.shared_html5.renderkit.RendererUtils;

import javax.faces.context.FacesContext;
import java.text.NumberFormat;
import java.util.Locale;

@JSFRenderer(renderKitId = "HTML_BASIC", family = "org.apache.myfaces.EffectTransform", type = "org.apache.myfaces.html5.EffectTransform")
public class EffectTransformRenderer extends BaseEffectRenderer {

    @Override
    protected String getEffectDefinition(FacesContext facesContext, AbstractBaseEffect uiComponent) {
        RendererUtils.checkParamValidity(facesContext, uiComponent, AbstractEffectTransform.class);

        AbstractEffectTransform component = (AbstractEffectTransform) uiComponent;

        final NumberFormat numberFormat = NumberFormat.getInstance(Locale.ENGLISH);

        final String rotate = component.getRotate();
        final String scaleX = _formatSafe(component.getScaleX(), numberFormat);
        final String scaleY = _formatSafe(component.getScaleY(), numberFormat);
        final String skewX = component.getSkewX();
        final String skewY = component.getSkewY();
        String translateX = _formatSafe(component.getTranslateX(), numberFormat);
        String translateY = _formatSafe(component.getTranslateY(), numberFormat);

        if(!StringUtils.isBlank(translateX))
            translateX = translateX + "px";
        if(!StringUtils.isBlank(translateY))
            translateY = translateY + "px";

        StringBuilder builder = new StringBuilder();
        boolean appendedAtLeastOne;

        appendedAtLeastOne = _appendSafe(CSS.TRANSFORMATION_FUNCTION_ROTATE, rotate, builder);
        appendedAtLeastOne = appendedAtLeastOne | _appendSafe(CSS.TRANSFORMATION_FUNCTION_SCALE_X, scaleX, builder);
        appendedAtLeastOne = appendedAtLeastOne | _appendSafe(CSS.TRANSFORMATION_FUNCTION_SCALE_Y, scaleY, builder);
        appendedAtLeastOne = appendedAtLeastOne | _appendSafe(CSS.TRANSFORMATION_FUNCTION_SKEW_X, skewX, builder);
        appendedAtLeastOne = appendedAtLeastOne | _appendSafe(CSS.TRANSFORMATION_FUNCTION_SKEW_Y, skewY, builder);
        appendedAtLeastOne = appendedAtLeastOne | _appendSafe(CSS.TRANSFORMATION_FUNCTION_TRANSLATE_X, translateX, builder);
        appendedAtLeastOne = appendedAtLeastOne | _appendSafe(CSS.TRANSFORMATION_FUNCTION_TRANSLATE_Y, translateY, builder);

        if(!appendedAtLeastOne)
            return "";


        builder.insert(0, "-webkit-transform : ");
        builder.append(";");

        return builder.toString();
    }

    private boolean _appendSafe(String transformationFunction, String value, StringBuilder builder) {
        if(StringUtils.isBlank(value))
            return false;
        else{
            builder.append(" ").append(transformationFunction).append("(").append(value).append(")");
            return true;
        }


    }

    private String _formatSafe(Double value, NumberFormat numberFormat) {
        if(value==null)
            return null;
        else
            return numberFormat.format(value);
    }
}