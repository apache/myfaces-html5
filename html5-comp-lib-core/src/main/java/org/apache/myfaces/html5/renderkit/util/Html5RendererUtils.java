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
package org.apache.myfaces.html5.renderkit.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.component.UIComponent;
import javax.faces.component.UIOutput;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.Converter;

import org.apache.myfaces.html5.component.input.Html5BaseInputText;
import org.apache.myfaces.shared_html5.renderkit.RendererUtils;
import org.apache.myfaces.shared_html5.renderkit.html.HtmlRendererUtils;

/**
 * Renderer utils for common stuff.
 * <p>
 * Does not extend org.apache.myfaces.shared.renderkit.html.HtmlRendererUtils on purpose, since this class should not
 * expose methods like
 * {@link org.apache.myfaces.shared_html5.renderkit.html.HtmlRendererUtils#renderBehaviorizedFieldEventHandlersWithoutOnchangeAndOnselect(javax.faces.context.FacesContext, javax.faces.context.ResponseWriter, javax.faces.component.UIComponent, java.util.Map)}
 * 
 * @author Ali Ok
 * 
 */
public class Html5RendererUtils
{

    private static final List<String> BOOLEAN_BUT_NOT_HTML5_BOOLEAN_ATTRS = Arrays.asList(HTML5.CONTENTEDITABLE_ATTR,
            HTML5.DRAGGABLE_ATTR, HTML5.SPELLCHECK_ATTR);

    private static final String HTML_EVENT_ATTR_PREFIX = "on";

    /**
     * Renders the pass through attributes of the component. Value of the JSF properties will be written with the
     * matching Html attribute name.
     * 
     * @param writer
     * @param component
     * @param passThruAttrs
     *            map that holds jsf property names as keys and html attribute names as values matched.
     * 
     * @return true, if an attribute was written
     * @throws java.io.IOException
     */
    public static boolean renderPassThroughAttributes(ResponseWriter writer, UIComponent component,
            Map<String, String> passThruAttrs) throws IOException
    {
        boolean somethingDone = false;
        Set<String> passThruJsfProperties = passThruAttrs.keySet();
        for (String passThruJsfPropertyName : passThruJsfProperties)
        {
            String passThruHtmlAttrName = passThruAttrs.get(passThruJsfPropertyName);
            Object value = component.getAttributes().get(passThruJsfPropertyName);

            if (renderHTMLAttribute(writer, passThruJsfPropertyName, passThruHtmlAttrName, value))
            {
                somethingDone = true;
            }
        }
        return somethingDone;
    }

    /**
     * Renders the attribute if the value is not the default value defined in the component. <br/>
     * For three Html5 attributes (draggable, contenteditable and spellcheck), "true" will be written instead of the
     * attribute's name as the value if the value is true. This behavior is necessary, since Html5 spec does not define
     * those attributes as "Boolean Attribute"s. <br/>
     * See related sections of Html5 spec for more:
     * <ul>
     * <li><a href="http://www.whatwg.org/specs/web-apps/current-work/#boolean-attribute">Boolean Attribute</a></li>
     * <li><a
     * href="http://www.whatwg.org/specs/web-apps/current-work/multipage/section-index.html#attributes-1">Attributes</a>
     * </li>
     * </ul>
     * 
     * @return true, if the attribute was written
     * @throws java.io.IOException
     */
    public static boolean renderHTMLAttribute(ResponseWriter writer, String componentProperty, String attrName,
            Object value) throws IOException
    {
        if (!RendererUtils.isDefaultAttributeValue(value))
        {
            if (BOOLEAN_BUT_NOT_HTML5_BOOLEAN_ATTRS.contains(attrName))
            {
                /*
                 * these attrs are not Html5 "Booolean Attribute"s : contenteditable draggable spellcheck
                 * http://www.whatwg.org/specs/web-apps/current-work/#boolean-attribute
                 * http://www.whatwg.org/specs/web-apps/current-work/multipage/section-index.html#attributes-1
                 * 
                 * so draggable="draggable" is a syntax error. for these attrs, we need to render ="true" like
                 * draggable="true". so, to prevent writer.writeAttribute(...) render that, this is the workaround!
                 * 
                 * TODO: file a ticket on JIRA for this issue
                 */

                if (value instanceof Boolean)
                {
                    value = String.valueOf(((Boolean) value).booleanValue());
                }
            }
            writer.writeAttribute(attrName, value, componentProperty);
            return true;
        }

        return false;
    }

    /**
     * Renders the client behavior event handlers for the component by investigating both client behaviors and values of
     * the behaviorized attributes.
     */
    public static void renderPassThroughClientBehaviorEventHandlers(FacesContext facesContext, UIComponent uiComponent,
            Map<String, String> passThroughClientBehaviors, Map<String, List<ClientBehavior>> clientBehaviors)
            throws IOException
    {

        Set<String> keySet = passThroughClientBehaviors.keySet();
        for (String property : keySet)
        {
            String eventName = passThroughClientBehaviors.get(property);
            String htmlAttrName = HTML_EVENT_ATTR_PREFIX + eventName;

            org.apache.myfaces.shared_html5.renderkit.html.HtmlRendererUtils.renderBehaviorizedAttribute(facesContext,
                    facesContext.getResponseWriter(), property, uiComponent, eventName, clientBehaviors, htmlAttrName);
        }

    }

    /**
     * Resolves string values from comma separated strings, string arrays or string collections.
     * 
     * @param value
     * @return null if value param is null or empty
     * @throws IllegalArgumentException
     *             if value is not comma separated strings or String[] or Collection<String>.
     * @see Html5RendererUtils#resolveStrings(Object, String[])
     */
    public static String[] resolveStrings(Object value) throws IllegalArgumentException
    {
        return resolveStrings(value, null);
    }

    /**
     * Resolves string values from comma separated strings, string arrays or string collections.
     * 
     * @param value
     *            Object to resolve
     * @param defaultValue
     *            Return value if value param is null or empty
     * @throws IllegalArgumentException
     *             if value is not comma separated strings or String[] or Collection<String>.
     */
    @SuppressWarnings("unchecked")
    public static String[] resolveStrings(Object value, String[] defaultValue) throws IllegalArgumentException
    {

        if (value == null)
        {
            return defaultValue;
        }

        Collection<String> valuesCollection = null;

        if (value instanceof String)
        {

            String strValues = (String) value;
            if (strValues.isEmpty())
                return defaultValue;

            // if value is comma separated words, split it
            String[] strValueElements = strValues.split(",");

            valuesCollection = new ArrayList<String>(strValueElements.length);

            for (String strValueElement : strValueElements)
            {
                strValueElement = strValueElement.trim();

                if (!strValueElement.isEmpty())
                    valuesCollection.add(strValueElement);
            }
        }
        else if (value instanceof String[])
        {
            valuesCollection = Arrays.asList((String[]) value);
        }
        else if (value instanceof Collection<?>)
        {
            valuesCollection = (Collection<String>) value;
        }
        else
        {
            throw new IllegalArgumentException(
                    "Value should be one of comma separeted strings, String[] or Collection of \"String\"s");
        }

        try
        {
            if (valuesCollection.isEmpty())
                return defaultValue;
            else
                return valuesCollection.toArray(new String[0]);
        }
        catch (ArrayStoreException e)
        {
            // if there is one non-String element in the collection, we'll fall in here during conversion to array.
            throw new IllegalArgumentException("All elements of the value must be an instance of String.", e);
        }
    }

    /**
     * Finds the converter of the component in a safe way.
     * 
     * @return null if no converter found or an exception occured inside
     */
    public static Converter findUIOutputConverterFailSafe(FacesContext facesContext, UIComponent component)
    {
        if(!(component instanceof UIOutput))
            return null;
        
        return HtmlRendererUtils.findUIOutputConverterFailSafe(facesContext, component);
    }

}
