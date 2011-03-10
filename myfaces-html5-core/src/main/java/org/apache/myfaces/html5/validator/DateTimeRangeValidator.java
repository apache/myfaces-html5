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
package org.apache.myfaces.html5.validator;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.component.PartialStateHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFProperty;
import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFValidator;
import org.apache.myfaces.html5.component.input.HtmlInputDateTime;
import org.apache.myfaces.html5.renderkit.input.util.Html5DateTimeFormatUtils;
import org.apache.myfaces.html5.renderkit.util.HTML5;
import org.apache.myfaces.shared_html5.renderkit.RendererUtils;

/**
 * Validate that the date entered is within a given range. Rendered min/max attributes of hx:inputDateTime is driven by
 * this validator too. <br />
 * 
 * @author Ali Ok
 * 
 */
@JSFValidator(name = "fx:validateDateTimeRange", bodyContent = "empty", tagClass = "org.apache.myfaces.html5.tag.input.ValidateDateTimeRangeTag", id = "org.apache.myfaces.html5.DateTimeRange")
public class DateTimeRangeValidator implements Validator, PartialStateHolder
{
    private static final Logger log = Logger.getLogger(DateTimeRangeValidator.class.getName());

    private Object _minimum = null;
    private Object _maximum = null;
    private String exceedMaximumMessage;
    private String lessThanMinimumMessage;
    private String notInRangeMessage;

    private Date _resolvedMinimum;
    private Date _resolvedMaximum;

    private boolean _transient;

    public void validate(FacesContext context, UIComponent uiComponent, Object value) throws ValidatorException
    {
        if (context == null)
            throw new NullPointerException("facesContext");
        if (uiComponent == null)
            throw new NullPointerException("uiComponent");

        if (value == null)
        {
            return;
        }

        if (uiComponent instanceof HtmlInputDateTime)
        {
            HtmlInputDateTime component = (HtmlInputDateTime) uiComponent;

            if (value instanceof Date)
            {
                Date dateValue = (Date) value;
                Date resolvedMinimum = null;
                try
                {
                    resolvedMinimum = getResolvedMinimum(component.getType());
                }
                catch (ParseException e)
                {
                    throw new ValidatorException(new FacesMessage("Unable to resolve minimum for component "
                            + RendererUtils.getPathToComponent(uiComponent) + "."), e);
                }

                Date resolvedMaximum = null;
                try
                {
                    resolvedMaximum = getResolvedMaximum(component.getType());
                }
                catch (ParseException e)
                {
                    throw new ValidatorException(new FacesMessage("Unable to resolve maximum for component "
                            + RendererUtils.getPathToComponent(uiComponent) + "."), e);
                }

                if (resolvedMinimum != null && resolvedMaximum != null)
                {
                    if (!resolvedMinimum.before(resolvedMaximum))
                    {
                        // not a ValidatorException since state is illegal
                        throw new FacesException("Minimum value is before than maximum for component "
                                + RendererUtils.getPathToComponent(uiComponent) + ".");
                    }
                    else
                    {
                        if (dateValue.before(resolvedMinimum) || dateValue.after(resolvedMaximum))
                        {
                            if (this.notInRangeMessage != null && !this.notInRangeMessage.isEmpty())
                                throw new ValidatorException(new FacesMessage(this.notInRangeMessage));
                            else
                                throw new ValidatorException(
                                        new FacesMessage("Submitted value is not in allowed range for component "
                                                + RendererUtils.getPathToComponent(uiComponent) + ". Range is "
                                                + resolvedMinimum.toString() + " - " + resolvedMaximum.toString() + "."));
                        }
                    }
                }

                if (resolvedMinimum != null && dateValue.before(resolvedMinimum))
                {
                    if (this.lessThanMinimumMessage != null && !this.lessThanMinimumMessage.isEmpty())
                        throw new ValidatorException(new FacesMessage(this.lessThanMinimumMessage));
                    else
                        throw new ValidatorException(new FacesMessage("Value is before minimum for component "
                                + RendererUtils.getPathToComponent(uiComponent) + ". Minimum value is "
                                + resolvedMinimum.toString() + "."));
                }

                if (resolvedMaximum != null && dateValue.after(resolvedMaximum))
                {
                    if (this.exceedMaximumMessage != null && !this.exceedMaximumMessage.isEmpty())
                        throw new ValidatorException(new FacesMessage(this.exceedMaximumMessage));
                    else
                        throw new ValidatorException(new FacesMessage("Value is after maximum for component "
                                + RendererUtils.getPathToComponent(uiComponent) + ". Maximum value is "
                                + resolvedMaximum.toString() + "."));
                }
            }
        }
        else
        {
            // noop
            if (log.isLoggable(Level.WARNING))
                log.warning("DateTimeRangeValidator can only be applied to instances of HtmlInputDateTime components.");

        }
    }

    /**
     * Resolves the minimum date from the minimum property which accepts both java.util.Date and String.
     * 
     * @throws ParseException
     *             if the value of minimum property is String and cannot be parsed for the given type.
     */
    public Date getResolvedMinimum(String type) throws ParseException
    {
        // no synchronization necessary
        if (_resolvedMinimum == null)
            _resolvedMinimum = _resolveDateFromObject(_minimum, type);

        return _resolvedMinimum;
    }

    /**
     * Resolves the maximum date from the maximum property which accepts both java.util.Date and String.
     * 
     * @throws ParseException
     *             if the value of maximum property is String and cannot be parsed for the given type.
     */
    public Date getResolvedMaximum(String type) throws ParseException
    {
        // no synchronization necessary
        if (_resolvedMaximum == null)
            _resolvedMaximum = _resolveDateFromObject(_maximum, type);

        return _resolvedMaximum;
    }

    //Resolve the date based on parent component's type
    private Date _resolveDateFromObject(Object value, String type) throws ParseException
    {
        if (value == null)
            return null;

        if (value instanceof String)
        {
            String strValue = (String) value;
            return Html5DateTimeFormatUtils.parseDateTime(strValue, type);
        }
        else if (value instanceof Date)
        {
            Date dateValue = (Date) value;
            if (HTML5.INPUT_TYPE_TIME.equals(type))
            {
                // XXX: may be it's better to leave this operation to user?
                // we need to clear the date info (y, m, d) if the type is "time"
                Calendar cal = Calendar.getInstance();
                cal.setTime(dateValue);
                cal.set(Calendar.YEAR, 1970);
                cal.set(Calendar.MONTH, Calendar.JANUARY);
                cal.set(Calendar.DAY_OF_MONTH, 1);
                return cal.getTime();
            }
            else
            {
                return dateValue;
            }
        }
        else
        {
            throw new IllegalArgumentException("Value " + type + "is not String nor java.util.Date. Unable to resolve.");
        }

    }

    /**
     * Minimum date that can be selected on client-side and is used on validation at server-side.
     * Value must be either String, or java.util.Date. If String is given, the value must be in the format of parent hx:inputDateTime's type.
     */
    @JSFProperty(deferredValueType = "java.lang.Object")
    public Object getMinimum()
    {
        return _minimum;
    }

    public void setMinimum(Object minimum)
    {
        this._minimum = minimum;
        clearInitialState();
    }

    /**
     * Maximum date that can be selected on client-side and is used on validation at server-side.
     * Value must be either String, or java.util.Date. If String is given, the value must be in the format of parent hx:inputDateTime's type.
     */
    @JSFProperty(deferredValueType = "java.lang.Object")
    public Object getMaximum()
    {
        return _maximum;
    }

    public void setMaximum(Object maximum)
    {
        this._maximum = maximum;
        clearInitialState();
    }

    /**
     * Message to show if the minimum is not set and submitted value exceeds specified maximum value.
     */
    @JSFProperty(deferredValueType = "java.lang.String")
    public String getExceedMaximumMessage()
    {
        return exceedMaximumMessage;
    }

    public void setExceedMaximumMessage(String exceedMaximumMessage)
    {
        this.exceedMaximumMessage = exceedMaximumMessage;
        clearInitialState();
    }

    /**
     * Message to show if the maximum is not set and submitted value is before than specified minimum value.
     */
    @JSFProperty(deferredValueType = "java.lang.String")
    public String getLessThanMinimumMessage()
    {
        return lessThanMinimumMessage;
    }

    public void setLessThanMinimumMessage(String lessThanMinimumMessage)
    {
        this.lessThanMinimumMessage = lessThanMinimumMessage;
        clearInitialState();
    }

    /**
     * Message to show if the minimum and minimum is set and submitted value is not in that range.
     */
    @JSFProperty(deferredValueType = "java.lang.String")
    public String getNotInRangeMessage()
    {
        return notInRangeMessage;
    }

    public void setNotInRangeMessage(String notInRangeMessage)
    {
        this.notInRangeMessage = notInRangeMessage;
        clearInitialState();
    }

    // RESTORE/SAVE STATE
    public Object saveState(FacesContext context)
    {
        if (!initialStateMarked())
        {
            Object values[] = new Object[2];
            values[0] = _maximum;
            values[1] = _minimum;
            return values;
        }
        return null;
    }

    public void restoreState(FacesContext context, Object state)
    {
        if (state != null)
        {
            Object values[] = (Object[]) state;
            _maximum = (Double) values[0];
            _minimum = (Double) values[1];
        }
    }

    public boolean isTransient()
    {
        return _transient;
    }

    public void setTransient(boolean transientValue)
    {
        _transient = transientValue;
    }

    private boolean _initialStateMarked = false;

    public void clearInitialState()
    {
        _initialStateMarked = false;
    }

    public boolean initialStateMarked()
    {
        return _initialStateMarked;
    }

    public void markInitialState()
    {
        _initialStateMarked = true;
    }

}
