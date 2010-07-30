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
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.validator.Validator;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFRenderer;
import org.apache.myfaces.html5.component.input.Html5BaseInputText;
import org.apache.myfaces.html5.component.input.HtmlInputDateTime;
import org.apache.myfaces.html5.renderkit.input.delegate.HtmlTextInputSuggestionRendererHelper;
import org.apache.myfaces.html5.renderkit.input.delegate.SuggestionRendererHelper;
import org.apache.myfaces.html5.renderkit.input.util.Html5DateTimeConverter;
import org.apache.myfaces.html5.renderkit.input.util.Html5DateTimeFormatUtils;
import org.apache.myfaces.html5.renderkit.util.HTML5;
import org.apache.myfaces.html5.renderkit.util.JsfProperties;
import org.apache.myfaces.html5.renderkit.util.PassThroughAttributes;
import org.apache.myfaces.html5.validator.DateTimeRangeValidator;
import org.apache.myfaces.shared_html5.renderkit.RendererUtils;

/**
 * Renderer of hx:inputDateTime. <br/>
 * Renders the properties and looks for attached fx:dateTimeRangeValidator instance to determine minimum and maximum
 * dates selectable.
 * 
 * @author Ali Ok
 * 
 */
@JSFRenderer(renderKitId = "HTML_BASIC", family = "javax.faces.Input", type = "org.apache.myfaces.html5.DateTime")
public class HtmlInputDateTimeRenderer extends Html5BaseInputTextRenderer
{
    // see
    // http://www.whatwg.org/specs/web-apps/current-work/multipage/common-input-element-attributes.html#attr-input-step
    private static final String DEFAULT_STEP_HTML_VALUE = "any";

    private static final Logger log = Logger.getLogger(HtmlInputDateTimeRenderer.class.getName());

    private HtmlTextInputSuggestionRendererHelper _suggestionRendererHelper;

    private static String[] ALLOWED_INPUT_TYPES = new String[]
    {
            JsfProperties.INPUTDATETIME_TYPE_DATETIME, JsfProperties.INPUTDATETIME_TYPE_DATE,
            JsfProperties.INPUTDATETIME_TYPE_TIME, JsfProperties.INPUTDATETIME_TYPE_MONTH,
            JsfProperties.INPUTDATETIME_TYPE_WEEK, JsfProperties.INPUTDATETIME_TYPE_DATETIME_LOCAL
    };

    @Override
    public Object getConvertedValue(FacesContext facesContext, UIComponent uiComponent, Object submittedValue)
            throws ConverterException
    {
        // override this method to convert the input to java.util.Date

        if (submittedValue != null && !(submittedValue instanceof String))
        {
            throw new IllegalArgumentException("Submitted value of type String for component : "
                    + RendererUtils.getPathToComponent(uiComponent) + " expected");
        }

        RendererUtils.checkParamValidity(facesContext, uiComponent, HtmlInputDateTime.class);

        _checkInputHtmlType(uiComponent);

        HtmlInputDateTime component = (HtmlInputDateTime) uiComponent;

        Converter converter = RendererUtils.findUIOutputConverter(facesContext, component);
        if (converter == null)
        {
            // min and max validation is done at DateTimeRangeValidator, not in converter.
            converter = new Html5DateTimeConverter();
            component.setConverter(converter);
        }

        return converter.getAsObject(facesContext, component, (String) submittedValue);

    }

    @Override
    protected void renderInputBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException
    {
        RendererUtils.checkParamValidity(facesContext, uiComponent, HtmlInputDateTime.class);
        HtmlInputDateTime component = (HtmlInputDateTime) uiComponent;

        if (RendererUtils.findUIOutputConverter(facesContext, component) == null)
        {
            component.setConverter(new Html5DateTimeConverter());
        }

        _checkInputHtmlType(uiComponent);
        super.renderInputBegin(facesContext, uiComponent);

        ResponseWriter writer = facesContext.getResponseWriter();

        // XXX: support the symbols of java.text.SimpleDateFormat later. it is kind of confusing.
        double step = component.getStep();
        if (step == Double.MIN_VALUE) // means not specified
        {
            writer.writeAttribute(HTML5.STEP_ATTR, DEFAULT_STEP_HTML_VALUE, JsfProperties.STEP_PROP);
        }
        else if (step < 0)
        {
            throw new FacesException("'step' cannot be negative for component "
                    + RendererUtils.getPathToComponent(uiComponent) + ". Provided " + step);
        }
        else
        {
            writer.writeAttribute(HTML5.STEP_ATTR, step, JsfProperties.STEP_PROP);
        }

        String strMinimum = _getMinimumStr(component);
        if (strMinimum != null)
            writer.writeAttribute(HTML5.MIN_ATTR, strMinimum, null);

        String strMaximum = _getMaximumStr(component);
        if (strMaximum != null)
            writer.writeAttribute(HTML5.MAX_ATTR, strMaximum, null);
    }

    /**
     * Extract minimum date selectable from attached fx:validateDateTimeRange
     */
    private String _getMinimumStr(HtmlInputDateTime component)
    {
        Validator[] validators = component.getValidators();
        for (Validator validator : validators)
        {
            if (validator instanceof DateTimeRangeValidator)
            {
                DateTimeRangeValidator dateTimeRangeValidator = (DateTimeRangeValidator) validator;
                Date minimum;
                try
                {
                    minimum = dateTimeRangeValidator.getResolvedMinimum(component.getType());
                }
                catch (ParseException e)
                {
                    throw new FacesException("Unable to resolve minimum value of component " + RendererUtils.getPathToComponent(component) + ".", e);
                }
                if (minimum != null)
                    return Html5DateTimeFormatUtils.formatDateTime(minimum, component.getType());
            }
        }

        return null;
    }

    /**
     * Extract maximum date selectable from attached fx:validateDateTimeRange
     */
    private String _getMaximumStr(HtmlInputDateTime component)
    {
        Validator[] validators = component.getValidators();
        for (Validator validator : validators)
        {
            if (validator instanceof DateTimeRangeValidator)
            {
                DateTimeRangeValidator dateTimeRangeValidator = (DateTimeRangeValidator) validator;
                Date maximum;
                try
                {
                    maximum = dateTimeRangeValidator.getResolvedMaximum(component.getType());
                }
                catch (ParseException e)
                {
                    throw new FacesException("Unable to resolve maximum value of component "
                            + RendererUtils.getPathToComponent(component) + ".", e);
                }
                if (maximum != null)
                    return Html5DateTimeFormatUtils.formatDateTime(maximum, component.getType());
            }
        }

        return null;
    }

    @Override
    protected Map<String, String> getExtraPassThroughAttributes()
    {
        return PassThroughAttributes.INPUT_DATE_TIME;
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
        return HtmlInputDateTime.class;
    }

    /**
     * This method checks whether html input type value is one of the allowed values.
     */
    private void _checkInputHtmlType(UIComponent uiComponent)
    {
        HtmlInputDateTime component = (HtmlInputDateTime) uiComponent;
        String type = component.getType();
        if (log.isLoggable(Level.FINE))
            log.fine("initial type is: " + type);
        if (type == null || type.isEmpty())
        { // if not set, set default value.
            if (log.isLoggable(Level.FINE))
                log.fine("setting default type " + JsfProperties.INPUTDATETIME_TYPE_DATETIME);

            component.setType(JsfProperties.INPUTDATETIME_TYPE_DATETIME);
        }
        else
        {
            if (!(Arrays.asList(ALLOWED_INPUT_TYPES).contains(type)))
                throw new FacesException("\"type\" attribute of component "
                        + RendererUtils.getPathToComponent(uiComponent) + " can be one of "
                        + Arrays.toString(ALLOWED_INPUT_TYPES) + " . Provided: \"" + type + "\"");
        }
    }

    @Override
    protected String getInputHtmlType(UIComponent component)
    {
        // obj type check for component is done in #encodeEnd, no need to check it again
        return ((HtmlInputDateTime) component).getType();
    }
}
