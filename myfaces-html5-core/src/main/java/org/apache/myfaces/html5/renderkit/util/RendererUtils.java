/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.myfaces.html5.renderkit.util;

import org.apache.myfaces.html5.component.util.ComponentUtils;

import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.component.UIOutput;
import javax.faces.component.ValueHolder;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.el.PropertyNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Ali Ok
 */
public class RendererUtils {

    //copied from org.apache.myfaces.shared.renderkit.RendererUtils
    public static void checkParamValidity(FacesContext facesContext, UIComponent uiComponent, Class compClass)
    {
        if(facesContext == null)
            throw new NullPointerException("facesContext may not be null");
        if(uiComponent == null)
            throw new NullPointerException("uiComponent may not be null");

        //if (compClass != null && !(compClass.isAssignableFrom(uiComponent.getClass())))
        // why isAssignableFrom with additional getClass method call if isInstance does the same?
        if (compClass != null && !(compClass.isInstance(uiComponent)))
        {
            throw new IllegalArgumentException("uiComponent : " + ComponentUtils.getPathToComponent(uiComponent)+
                    " is not instance of "+compClass.getName()+" as it should be");
        }
    }

    /**
     * Convenient utility method that returns the currently given value as String,
     * using the given converter.
     * Especially usefull for dealing with primitive types.
     */
    //copied from org.apache.myfaces.shared.renderkit.RendererUtils
    public static String getConvertedStringValue(FacesContext context,
            UIComponent component, Converter converter, Object value) {
        if (converter == null) {
            if (value == null) {
                return "";
            } else if (value instanceof String) {
                return (String) value;
            } else {
                return value.toString();
            }
        }

        return converter.getAsString(context, component, value);
    }

    /**
     * Find the proper Converter for the given UIOutput component.
     * @return the Converter or null if no Converter specified or needed
     * @throws javax.faces.FacesException if the Converter could not be created
     */
    //copied from org.apache.myfaces.shared.renderkit.RendererUtils
    public static Converter findUIOutputConverter(FacesContext facesContext,
                                                  UIOutput component)
            throws FacesException
    {
        // Attention!
        // This code is duplicated in jsfapi component package.
        // If you change something here please do the same in the other class!

        Converter converter = component.getConverter();
        if (converter != null)
            return converter;

        //Try to find out by value expression
        ValueExpression expression = component.getValueExpression("value");
        if (expression == null)
            return null;

        Class valueType = expression.getType(facesContext.getELContext());
        if (valueType == null)
            return null;

        if (Object.class.equals(valueType))
            return null; //There is no converter for Object class

        try
        {
            return facesContext.getApplication().createConverter(valueType);
        }
        catch (FacesException e)
        {
            log(facesContext, "No Converter for type " + valueType.getName()
                    + " found", e);
            return null;
        }
    }

    /**
     * Finds the converter of the component in a safe way.
     *
     * @return null if no converter found or an exception occured inside
     */
    //copied from org.apache.myfaces.shared.renderkit.RendererUtils
    public static Converter findUIOutputConverterFailSafe(FacesContext facesContext, UIComponent uiComponent) {
        Converter converter;
        try {
            converter = RendererUtils.findUIOutputConverter(facesContext,
                    (UIOutput) uiComponent);
        } catch (FacesException e) {
            log.log(Level.SEVERE, "Error finding Converter for component with id "
                    + uiComponent.getClientId(facesContext), e);
            converter = null;
        }
        return converter;
    }

    /**
     * See JSF Spec. 8.5 Table 8-1
     * @param value
     * @return boolean
     */
    //copied from org.apache.myfaces.shared.renderkit.RendererUtils
    public static boolean isDefaultAttributeValue(Object value)
    {
        if (value == null)
        {
            return true;
        }
        else if (value instanceof Boolean)
        {
            return !((Boolean) value).booleanValue();
        }
        else if (value instanceof Number)
        {
            if (value instanceof Integer)
            {
                return ((Number)value).intValue() == Integer.MIN_VALUE;
            }
            else if (value instanceof Double)
            {
                return ((Number)value).doubleValue() == Double.MIN_VALUE;
            }
            else if (value instanceof Long)
            {
                return ((Number)value).longValue() == Long.MIN_VALUE;
            }
            else if (value instanceof Byte)
            {
                return ((Number)value).byteValue() == Byte.MIN_VALUE;
            }
            else if (value instanceof Float)
            {
                return ((Number)value).floatValue() == Float.MIN_VALUE;
            }
            else if (value instanceof Short)
            {
                return ((Number)value).shortValue() == Short.MIN_VALUE;
            }
        }
        return false;
    }

    //copied from org.apache.myfaces.shared.renderkit._SharedRendererUtils
    private static final Logger log = Logger.getLogger(RendererUtils.class.getName());
    private static void log(FacesContext context, String msg, Exception e)
    {
        log.log(Level.SEVERE, msg, e);
    }

    //copied from org.apache.myfaces.shared.renderkit.RendererUtils
    public static String getStringValue(FacesContext facesContext,
                                        UIComponent component)
    {
        try
        {
            if (!(component instanceof ValueHolder))
            {
                throw new IllegalArgumentException("Component : " + ComponentUtils.getPathToComponent(component)+"is not a ValueHolder");
            }

            if (component instanceof EditableValueHolder)
            {
                Object submittedValue = ((EditableValueHolder)component).getSubmittedValue();
                if (submittedValue != null)
                {
                        if (log.isLoggable(Level.FINE)) log.fine("returning 1 '" + submittedValue + "'");
                        return submittedValue.toString();
                }
            }

            Object value;

            if(component instanceof EditableValueHolder) {

                EditableValueHolder holder = (EditableValueHolder) component;

                if(holder.isLocalValueSet()) {
                    value = holder.getLocalValue();
                } else {
                    value = getValue(component);
                }
            }
            else {
                value = getValue(component);
            }

            Converter converter = ((ValueHolder)component).getConverter();
            if (converter == null  && value != null)
            {

                try
                {
                    converter = facesContext.getApplication().createConverter(value.getClass());
                    if (log.isLoggable(Level.FINE)) log.fine("the created converter is " + converter);
                }
                catch (FacesException e)
                {
                    log.log(Level.SEVERE, "No converter for class " + value.getClass().getName() + " found (component id=" + component.getId() + ").", e);
                    // converter stays null
                }
            }

            if (converter == null)
            {
                if (value == null)
                {
                    if (log.isLoggable(Level.FINE)) log.fine("returning an empty string");
                    return "";
                }

                if (log.isLoggable(Level.FINE)) log.fine("returning an .toString");
                return value.toString();

            }

            if (log.isLoggable(Level.FINE)) log.fine("returning converter get as string " + converter);
            return converter.getAsString(facesContext, component, value);

        }
        catch(PropertyNotFoundException ex)
        {
            log.log(Level.SEVERE, "Property not found - called by component : "+ComponentUtils.getPathToComponent(component),ex);

            throw ex;
        }
    }

    private static Object getValue(UIComponent component) {
        Object value;
        try
        {
            value = ((ValueHolder) component).getValue();
        }
        catch(Exception ex)
        {
            throw new FacesException("Could not retrieve value of component with path : "+
                    ComponentUtils.getPathToComponent(component),ex);
        }
        return value;
    }

    //copied from org.apache.myfaces.shared.renderkit.RendererUtils
    public static Object getConvertedUIOutputValue(FacesContext facesContext, UIOutput output, Object submittedValue)
            throws ConverterException
    {
        if (submittedValue != null && !(submittedValue instanceof String))
        {
            submittedValue = submittedValue.toString();
        }

        Converter converter;
        try
        {
            converter = findUIOutputConverter(facesContext, output);
        }
        catch (FacesException e)
        {
            throw new ConverterException(e);
        }

        return converter == null ? submittedValue : converter
                .getAsObject(facesContext, output, (String) submittedValue);
    }
}
