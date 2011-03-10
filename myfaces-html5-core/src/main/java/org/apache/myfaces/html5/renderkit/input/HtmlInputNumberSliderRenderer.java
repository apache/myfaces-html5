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
package org.apache.myfaces.html5.renderkit.input;

import java.io.IOException;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;
import javax.faces.validator.DoubleRangeValidator;
import javax.faces.validator.LongRangeValidator;
import javax.faces.validator.Validator;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFRenderer;
import org.apache.myfaces.html5.component.input.Html5BaseInputText;
import org.apache.myfaces.html5.component.input.HtmlInputNumberSlider;
import org.apache.myfaces.html5.renderkit.input.delegate.HtmlTextInputSuggestionRendererHelper;
import org.apache.myfaces.html5.renderkit.input.delegate.SuggestionRendererHelper;
import org.apache.myfaces.html5.renderkit.util.HTML5;
import org.apache.myfaces.html5.renderkit.util.PassThroughAttributes;
import org.apache.myfaces.shared_html5.renderkit.RendererUtils;

/**
 * Renderer for hx:inputNumberSlider.
 * 
 * @author Ali Ok
 * 
 */
@JSFRenderer(renderKitId = "HTML_BASIC", family = "javax.faces.Input", type = "org.apache.myfaces.html5.NumberSlider")
public class HtmlInputNumberSliderRenderer extends Html5BaseInputTextRenderer
{

    private static final int DEFAULT_SEGMENT_COUNT = 100;

    private static final double DEFAULT_MIN = 0;

    private static final double DEFAULT_MAX = 100;

    private HtmlTextInputSuggestionRendererHelper _suggestionRendererHelper;

    //TODO: add an implicit NumberConverter if not defined
    @Override
    protected void renderInputBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException
    {
        super.renderInputBegin(facesContext, uiComponent);

        HtmlInputNumberSlider component = (HtmlInputNumberSlider) uiComponent;

        double min = DEFAULT_MIN;
        double max = DEFAULT_MAX;

        // get min and max
        Validator[] validators = component.getValidators();
        for (Validator validator : validators)
        {
            if (validator instanceof DoubleRangeValidator)
            {
                DoubleRangeValidator doubleRangeValidator = (DoubleRangeValidator) validator;
                min = doubleRangeValidator.getMinimum();
                max = doubleRangeValidator.getMaximum();
                break;
            }
            else if (validator instanceof LongRangeValidator)
            {
                LongRangeValidator longRangeValidator = (LongRangeValidator) validator;
                min = longRangeValidator.getMinimum();
                max = longRangeValidator.getMaximum();
                break;
            }
        }

        if (max <= min)
        {
            throw new FacesException("Maximum must be larger than minimum for component " + RendererUtils.getPathToComponent(uiComponent) + ". "
                    + "These values are calculated from the first attached DoubleRangeValidator or LongRangeValidator");
        }

        double calculatedStep = _calculateStep(component, min, max);

        // and render them
        facesContext.getResponseWriter().writeAttribute(HTML5.MIN_ATTR, min, null);
        facesContext.getResponseWriter().writeAttribute(HTML5.MAX_ATTR, max, null);
        facesContext.getResponseWriter().writeAttribute(HTML5.STEP_ATTR, calculatedStep, null);

        // don't render Html5 required attr, since it is not supported by <input type="range">
        // but required will be checked in validation stage
    }

    /**
     * Calculates the step using either the step property of segmentCount property. It is an error to specify both.
     */
    private double _calculateStep(HtmlInputNumberSlider component, double min, double max)
    {
        double calculatedStep;
        double step = component.getStep();
        int segmentCount = component.getSegmentCount();

        if (step != Double.MIN_VALUE && segmentCount != Integer.MIN_VALUE)
        { // if both are set
            throw new FacesException(
                    "Only one of 'step' or 'segmentCount' properties must be defined for component " + RendererUtils.getPathToComponent(component) + ". Undefined one will be calculated.");
        }

        if (step != Double.MIN_VALUE)
        { // if only step is set
            if (step <= 0)
            { // if it is set but it is negative
                throw new FacesException("'step' property of component " + RendererUtils.getPathToComponent(component) + " must be positive");
            }
            calculatedStep = step;
        }
        else if (segmentCount != Integer.MIN_VALUE)
        { // if only segmentCount is set
            if (segmentCount <= 0)
            { // if it is set but it is negative
                throw new FacesException("'segmentCount' property of component " + RendererUtils.getPathToComponent(
                        component) + " must be positive");
            }
            calculatedStep = (max - min) / segmentCount;
        }
        else
        {
            // both step and segmentCount is not set, use the default value
            calculatedStep = (max - min) / DEFAULT_SEGMENT_COUNT;
        }

        return calculatedStep;
    }

    @Override
    protected Map<String, String> getExtraPassThroughAttributes()
    {
        return PassThroughAttributes.INPUT_NUMBER_SLIDER;
    }

    @Override
    public SuggestionRendererHelper getSuggestionRendererHelper(Html5BaseInputText component)
    {
        if (_suggestionRendererHelper == null)
            _suggestionRendererHelper = new HtmlTextInputSuggestionRendererHelper();

        return _suggestionRendererHelper;
    }

    @Override
    protected Class<? extends HtmlInputText> getComponentClass()
    {
        return HtmlInputNumberSlider.class;
    }

    @Override
    protected String getInputHtmlType(UIComponent component)
    {
        return HTML5.INPUT_TYPE_RANGE;
    }
}
