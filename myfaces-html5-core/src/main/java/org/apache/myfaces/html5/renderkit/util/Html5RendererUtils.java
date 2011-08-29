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

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.myfaces.html5.component.util.ComponentUtils;

import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.component.behavior.ClientBehaviorContext;
import javax.faces.component.behavior.ClientBehaviorHint;
import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

/**
 * @author Ali Ok
 */
public class Html5RendererUtils
{

    private static final List<String> BOOLEAN_BUT_NOT_HTML5_BOOLEAN_ATTRS = Arrays.asList(HTML5.CONTENTEDITABLE_ATTR,
            HTML5.DRAGGABLE_ATTR, HTML5.SPELLCHECK_ATTR);

    private static final String HTML_EVENT_ATTR_PREFIX = "on";

    private static final String DEFAULT_WIDGET_PREFIX = "widget_";

    private static final String LINE_SEPARATOR = System.getProperty("line.separator", "\r\n");
    private static final char TABULATOR = '\t';

    public static final String NON_SUBMITTED_VALUE_WARNING
            = "There should always be a submitted value for an input if it is rendered,"
            + " its form is submitted, and it was not originally rendered disabled or read-only."
            + "  You cannot submit a form after disabling an input element via javascript."
            + "  Consider setting read-only to true instead"
            + " or resetting the disabled value back to false prior to form submission.";

    private static final Logger log = Logger.getLogger(Html5RendererUtils.class.getName());

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
                 * XXX: file a ticket on JIRA for this issue
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

            renderBehaviorizedAttribute(facesContext,
                    facesContext.getResponseWriter(), property, uiComponent, eventName, clientBehaviors, htmlAttrName);
        }

    }

    public static boolean renderBehaviorizedOnchangeEventHandler(
            FacesContext facesContext, ResponseWriter writer, UIComponent uiComponent,
            Map<String, List<ClientBehavior>> clientBehaviors) throws IOException {
        boolean hasChange = Html5RendererUtils.hasClientBehavior(ClientBehaviorEvents.CHANGE_EVENT, clientBehaviors, facesContext);
        boolean hasValueChange = Html5RendererUtils.hasClientBehavior(ClientBehaviorEvents.VALUECHANGE_EVENT, clientBehaviors, facesContext);

        if (hasChange && hasValueChange) {
            String chain = Html5RendererUtils.buildBehaviorChain(facesContext,
                    uiComponent, ClientBehaviorEvents.CHANGE_EVENT, null, ClientBehaviorEvents.VALUECHANGE_EVENT, null, clientBehaviors,
                    (String) uiComponent.getAttributes().get(JsfProperties.ONCHANGE_PROP), null);

            return Html5RendererUtils.renderHTMLAttribute(writer, JsfProperties.ONCHANGE_PROP, JsfProperties.ONCHANGE_PROP, chain);
        } else if (hasChange) {
            return Html5RendererUtils.renderBehaviorizedAttribute(facesContext, writer, JsfProperties.ONCHANGE_PROP, uiComponent,
                    ClientBehaviorEvents.CHANGE_EVENT, clientBehaviors, JsfProperties.ONCHANGE_PROP);
        } else if (hasValueChange) {
            return Html5RendererUtils.renderBehaviorizedAttribute(facesContext, writer, JsfProperties.ONCHANGE_PROP, uiComponent,
                    ClientBehaviorEvents.VALUECHANGE_EVENT, clientBehaviors, JsfProperties.ONCHANGE_PROP);
        } else {
            return Html5RendererUtils.renderHTMLAttribute(writer, JsfProperties.ONCHANGE_PROP, JsfProperties.ONCHANGE_PROP, uiComponent.getAttributes().get(JsfProperties.ONCHANGE_PROP));
        }
    }

    public static void renderBehaviorizedEventHandlers(
            FacesContext facesContext, ResponseWriter writer, UIComponent uiComponent,
            Map<String, List<ClientBehavior>> clientBehaviors) throws IOException {
        renderBehaviorizedAttribute(facesContext, writer, JsfProperties.ONCLICK_PROP, uiComponent,
                ClientBehaviorEvents.CLICK_EVENT, clientBehaviors, JsfProperties.ONCLICK_PROP);
        renderBehaviorizedAttribute(facesContext, writer, JsfProperties.ONDBLCLICK_PROP, uiComponent,
                ClientBehaviorEvents.DBLCLICK_EVENT, clientBehaviors, JsfProperties.ONDBLCLICK_PROP);
        renderBehaviorizedAttribute(facesContext, writer, JsfProperties.ONMOUSEDOWN_PROP, uiComponent,
                ClientBehaviorEvents.MOUSEDOWN_EVENT, clientBehaviors, JsfProperties.ONMOUSEDOWN_PROP);
        renderBehaviorizedAttribute(facesContext, writer, JsfProperties.ONMOUSEUP_PROP, uiComponent,
                ClientBehaviorEvents.MOUSEUP_EVENT, clientBehaviors, JsfProperties.ONMOUSEUP_PROP);
        renderBehaviorizedAttribute(facesContext, writer, JsfProperties.ONMOUSEOVER_PROP, uiComponent,
                ClientBehaviorEvents.MOUSEOVER_EVENT, clientBehaviors, JsfProperties.ONMOUSEOVER_PROP);
        renderBehaviorizedAttribute(facesContext, writer, JsfProperties.ONMOUSEMOVE_PROP, uiComponent,
                ClientBehaviorEvents.MOUSEMOVE_EVENT, clientBehaviors, JsfProperties.ONMOUSEMOVE_PROP);
        renderBehaviorizedAttribute(facesContext, writer, JsfProperties.ONMOUSEOUT_PROP, uiComponent,
                ClientBehaviorEvents.MOUSEOUT_EVENT, clientBehaviors, JsfProperties.ONMOUSEOUT_PROP);
        renderBehaviorizedAttribute(facesContext, writer, JsfProperties.ONKEYPRESS_PROP, uiComponent,
                ClientBehaviorEvents.KEYPRESS_EVENT, clientBehaviors, JsfProperties.ONKEYPRESS_PROP);
        renderBehaviorizedAttribute(facesContext, writer, JsfProperties.ONKEYDOWN_PROP, uiComponent,
                ClientBehaviorEvents.KEYDOWN_EVENT, clientBehaviors, JsfProperties.ONKEYDOWN_PROP);
        renderBehaviorizedAttribute(facesContext, writer, JsfProperties.ONKEYUP_PROP, uiComponent,
                ClientBehaviorEvents.KEYUP_EVENT, clientBehaviors, JsfProperties.ONKEYUP_PROP);
    }

    public static void renderBehaviorizedFieldEventHandlersWithoutOnchange(
            FacesContext facesContext, ResponseWriter writer, UIComponent uiComponent,
            Map<String, List<ClientBehavior>> clientBehaviors) throws IOException {
        renderBehaviorizedAttribute(facesContext, writer, JsfProperties.ONFOCUS_PROP, uiComponent,
                ClientBehaviorEvents.FOCUS_EVENT, clientBehaviors, JsfProperties.ONFOCUS_PROP);
        renderBehaviorizedAttribute(facesContext, writer, JsfProperties.ONBLUR_PROP, uiComponent,
                ClientBehaviorEvents.BLUR_EVENT, clientBehaviors, JsfProperties.ONBLUR_PROP);
        renderBehaviorizedAttribute(facesContext, writer, JsfProperties.ONSELECT_PROP, uiComponent,
                ClientBehaviorEvents.SELECT_EVENT, clientBehaviors, JsfProperties.ONSELECT_PROP);
    }

    public static boolean hasClientBehavior(String eventName,
                                            Map<String, List<ClientBehavior>> behaviors,
                                            FacesContext facesContext) {
        if (behaviors == null) {
            return false;
        }
        return (behaviors.get(eventName) != null);
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
            if (strValues.length() == 0)
                return defaultValue;

            // if value is comma separated words, split it
            String[] strValueElements = strValues.split(",");

            valuesCollection = new ArrayList<String>(strValueElements.length);

            for (String strValueElement : strValueElements)
            {
                strValueElement = strValueElement.trim();

                if (strValueElement.length() > 0)
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
     * Escapes the given string for use as a CSS selector.
     * @return Escaped selector
     */
    public static String escapeCssSelector(String selector) {
        if(selector==null)
            return null;

        return selector.replace(":", "\\:");
    }

    public static String getTimeValue(String s) {
        if(StringUtils.isBlank(s))
            return null;
        else if(s.endsWith("s") || s.endsWith("ms"))
            return s;
        else
            return s + "s";
    }

    public static String generateWidgetVar(String clientId){
        return DEFAULT_WIDGET_PREFIX + escapeJavaScriptVariableName(clientId);
    }

    private static String escapeJavaScriptVariableName(String str) {
        if(str==null)
            return null;

        return str.replace(":", "_");
    }

    /**
     * @since 4.0.0
     * @param facesContext
     * @param component
     */
    //copied from org.apache.myfaces.shared.renderkit.html.HtmlRendererUtils
    public static void decodeClientBehaviors(FacesContext facesContext,
            UIComponent component)
    {
        if (component instanceof ClientBehaviorHolder)
        {
            ClientBehaviorHolder clientBehaviorHolder = (ClientBehaviorHolder) component;

            Map<String,List<ClientBehavior>> clientBehaviors =
                clientBehaviorHolder.getClientBehaviors();

            if (clientBehaviors != null && !clientBehaviors.isEmpty())
            {
                Map<String,String> paramMap = facesContext.getExternalContext().
                    getRequestParameterMap();

                String behaviorEventName = paramMap.get("javax.faces.behavior.event");

                if (behaviorEventName != null)
                {
                    List<ClientBehavior> clientBehaviorList = clientBehaviors.get(behaviorEventName);

                    if (clientBehaviorList != null && !clientBehaviorList.isEmpty())
                    {
                        String clientId = paramMap.get("javax.faces.source");

                        if (component.getClientId().equals(clientId))
                        {
                            for (ClientBehavior clientBehavior : clientBehaviorList)
                            {
                                clientBehavior.decode(facesContext, component);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Render an attribute taking into account the passed event and
     * the component property. The event will be rendered on the selected
     * htmlAttrName
     *
     * @param facesContext
     * @param writer
     * @param component
     * @param clientBehaviors
     * @param eventName
     * @param componentProperty
     * @param htmlAttrName
     * @return
     * @throws IOException
     * @since 4.0.1
     */
    //copied from org.apache.myfaces.shared.renderkit.html.HtmlRendererUtils
    public static boolean renderBehaviorizedAttribute(
            FacesContext facesContext, ResponseWriter writer,
            String componentProperty, UIComponent component,
            String eventName, Map<String, List<ClientBehavior>> clientBehaviors,
            String htmlAttrName) throws IOException {
        return renderBehaviorizedAttribute(facesContext, writer,
                componentProperty, component,
                eventName, null, clientBehaviors,
                htmlAttrName, (String) component.getAttributes().get(componentProperty));
    }

    /**
     * Render an attribute taking into account the passed event,
     * the component property and the passed attribute value for the component
     * property. The event will be rendered on the selected htmlAttrName.
     *
     * @param facesContext
     * @param writer
     * @param componentProperty
     * @param component
     * @param eventName
     * @param clientBehaviors
     * @param htmlAttrName
     * @param attributeValue
     * @return
     * @throws IOException
     */
    //copied from org.apache.myfaces.shared.renderkit.html.HtmlRendererUtils
    public static boolean renderBehaviorizedAttribute(
            FacesContext facesContext, ResponseWriter writer,
            String componentProperty, UIComponent component,
            String eventName, Collection<ClientBehaviorContext.Parameter> eventParameters, Map<String, List<ClientBehavior>> clientBehaviors,
            String htmlAttrName, String attributeValue) throws IOException {
        List<ClientBehavior> cbl = (clientBehaviors != null) ? clientBehaviors.get(eventName) : null;

        if (cbl == null || cbl.size() == 0) {
            return renderHTMLAttribute(writer, componentProperty, htmlAttrName, attributeValue);
        }

        if (cbl.size() > 1 || (cbl.size() == 1 && attributeValue != null)) {
            return renderHTMLAttribute(writer, componentProperty, htmlAttrName,
                    buildBehaviorChain(facesContext,
                            component, eventName, eventParameters, clientBehaviors,
                            attributeValue, StringUtils.EMPTY));
        } else {
            //Only 1 behavior and attrValue == null, so just render it directly
            return renderHTMLAttribute(writer, componentProperty, htmlAttrName,
                    cbl.get(0).getScript(
                            ClientBehaviorContext
                                    .createClientBehaviorContext(facesContext, component,
                                            eventName, component.getClientId(facesContext),
                                            eventParameters)));
        }
    }

    /**
     * @param facesContext
     * @param uiComponent
     * @param clientBehaviors
     * @param eventName
     * @param userEventCode
     * @param serverEventCode
     * @param params
     * @return
     * @since 4.0.0
     */
    //copied from org.apache.myfaces.shared.renderkit.html.HtmlRendererUtils
    //modified
    public static String buildBehaviorChain(FacesContext facesContext,
                                            UIComponent uiComponent,
                                            String eventName, Collection<ClientBehaviorContext.Parameter> params,
                                            Map<String, List<ClientBehavior>> clientBehaviors,
                                            String userEventCode, String serverEventCode) {
        ExternalContext externalContext = facesContext.getExternalContext();
        List<String> finalParams = new ArrayList<String>(3);
        if(StringUtils.isNotBlank(userEventCode)){
            // escape every ' in the user event code since it will
            // be a string attribute of jsf.util.chain
            finalParams.add('\'' + escapeJavaScriptForChain(userEventCode) + '\'');
        }

        ScriptContext behaviorCode = new ScriptContext();
        ScriptContext retVal = new ScriptContext();

        getClientBehaviorScript(facesContext, uiComponent, eventName, clientBehaviors,
                behaviorCode, params);
        if (behaviorCode!=null && StringUtils.isNotBlank(behaviorCode.toString())) {
            finalParams.add(behaviorCode.toString());
        }
        if (StringUtils.isNotBlank(serverEventCode)) {
            finalParams.add('\'' + escapeJavaScriptForChain(serverEventCode) + '\'');
        }
        Iterator<String> it = finalParams.iterator();

        // It's possible that there are no behaviors to render.  For example, if we have
        // <f:ajax disabled="true" /> as the only behavior.

        if (it.hasNext()) {
            //according to the spec jsf.util.chain has to be used to build up the behavior and scripts
            retVal.append("jsf.util.chain(document.getElementById('"
                    + uiComponent.getClientId(facesContext) + "'), event,");
            while (it.hasNext()) {
                retVal.append(it.next());
                if (it.hasNext()) {
                    retVal.append(", ");
                }
            }
            retVal.append(");");
        }

        return retVal.toString();
    }

    public static String buildBehaviorChain(FacesContext facesContext,
                                            UIComponent uiComponent,
                                            String eventName1, Collection<ClientBehaviorContext.Parameter> params,
                                            String eventName2, Collection<ClientBehaviorContext.Parameter> params2,
                                            Map<String, List<ClientBehavior>> clientBehaviors,
                                            String userEventCode,
                                            String serverEventCode) {

        List<String> finalParams = new ArrayList<String>(3);
        if (StringUtils.isNotBlank(userEventCode)) {
            finalParams.add('\'' + escapeJavaScriptForChain(userEventCode) + '\'');
        }

        ScriptContext behaviorCode = new ScriptContext();
        ScriptContext retVal = new ScriptContext();

        boolean submitting1 = getClientBehaviorScript(facesContext, uiComponent, eventName1, clientBehaviors,
                behaviorCode, params);
        boolean submitting2 = getClientBehaviorScript(facesContext, uiComponent, eventName2, clientBehaviors,
                behaviorCode, params2);

        // ClientBehaviors for both events have to be checked for the Submitting hint
        boolean submitting = submitting1 || submitting2;

        if (StringUtils.isNotBlank(behaviorCode.toString())) {
            finalParams.add(behaviorCode.toString());
        }
        if (StringUtils.isNotBlank(serverEventCode)) {
            finalParams.add('\'' + escapeJavaScriptForChain(serverEventCode) + '\'');
        }
        Iterator<String> it = finalParams.iterator();

        // It's possible that there are no behaviors to render.  For example, if we have
        // <f:ajax disabled="true" /> as the only behavior.

        if (it.hasNext()) {
            if (!submitting) {
                retVal.append("return ");
            }
            //according to the spec jsf.util.chain has to be used to build up the behavior and scripts
            retVal.append("jsf.util.chain(document.getElementById('"
                    + uiComponent.getClientId(facesContext) + "'), event,");
            while (it.hasNext()) {
                retVal.append(it.next());
                if (it.hasNext()) {
                    retVal.append(", ");
                }
            }
            retVal.append(");");
            if (submitting) {
                retVal.append(" return false;");
            }
        }

        return retVal.toString();

    }

    /**
     * builds the chained behavior script which then can be reused
     * in following order by the other script building parts
     * <p/>
     * user defined event handling script
     * behavior script
     * renderer default script
     *
     * @param eventName    event name ("onclick" etc...)
     * @param uiComponent  the component which has the attachement (or should have)
     * @param facesContext the facesContext
     * @param params       params map of params which have to be dragged into the request
     * @return a string representation of the javascripts for the attached event behavior, an empty string if none is present
     * @since 4.0.0
     */
    //copied from org.apache.myfaces.shared.renderkit.html.HtmlRendererUtils
    //modified
    private static boolean getClientBehaviorScript(FacesContext facesContext,
                                                   UIComponent uiComponent, String eventName,
                                                   Map<String, List<ClientBehavior>> clientBehaviors,
                                                   ScriptContext target, Collection<ClientBehaviorContext.Parameter> params) {
        if (!(uiComponent instanceof ClientBehaviorHolder)) {
            target.append(StringUtils.EMPTY);
            return false;
        }

        ExternalContext externalContext = facesContext.getExternalContext();

        boolean renderClientBehavior = MapUtils.isNotEmpty(clientBehaviors);
        if (!renderClientBehavior) {
            target.append(StringUtils.EMPTY);
            return false;
        }

        List<ClientBehavior> attachedEventBehaviors = clientBehaviors
                .get(eventName);
        if (attachedEventBehaviors == null
                || attachedEventBehaviors.size() == 0) {
            target.append(StringUtils.EMPTY);
            return false;
        }

        ClientBehaviorContext context = ClientBehaviorContext
                .createClientBehaviorContext(facesContext, uiComponent,
                        eventName, uiComponent.getClientId(facesContext),
                        params);


        boolean submitting = false;
        Iterator<ClientBehavior> clientIterator = attachedEventBehaviors
                .iterator();
        while (clientIterator.hasNext()) {
            ClientBehavior clientBehavior = clientIterator.next();
            String script = clientBehavior.getScript(context);

            // The script _can_ be null, and in fact is for <f:ajax disabled="true" />

            if (script != null) {
                //either strings or functions, but I assume string is more appropriate since it allows access to the
                //origin as this!
                target.append("'" + escapeJavaScriptForChain(script) + "'");
                if (clientIterator.hasNext()) {
                    target.append(", ");
                }
            }
            if (!submitting) {
                submitting = clientBehavior.getHints().contains(ClientBehaviorHint.SUBMITTING);
            }
        }
        return submitting;
    }

    /**
     * This function correctly escapes the given JavaScript code
     * for the use in the jsf.util.chain() JavaScript function.
     * It also handles double-escaping correclty.
     *
     * @param javaScript
     * @return
     */
    //copied from org.apache.myfaces.shared.renderkit.html.HtmlRendererUtils
    public static String escapeJavaScriptForChain(String javaScript)
    {
        // first replace \' with \\'
        //String escaped = StringUtils.replace(javaScript, "\\'", "\\\\'");

        // then replace ' with \'
        // (this will replace every \' in the original to \\\')
        //escaped = StringUtils.replace(escaped, '\'', "\\'");

        //return escaped;

        StringBuffer out = null;
        for (int pos = 0; pos < javaScript.length(); pos++)
        {
            char c = javaScript.charAt(pos);

            if (c == '\\' || c == '\'')
            {
                if (out == null)
                {
                    out = new StringBuffer(javaScript.length() + 8);
                    if (pos > 0)
                    {
                        out.append(javaScript, 0, pos);
                    }
                }
                out.append('\\');
            }
            if (out != null)
            {
                out.append(c);
            }
        }

        if (out == null)
        {
            return javaScript;
        }
        else
        {
            return out.toString();
        }
    }

    /**
     * Utility to set the submitted value of the provided component from the
     * data in the current request object.
     * <p/>
     * Param component is required to be an EditableValueHolder. On return
     * from this method, the component's submittedValue property will be
     * set if the submitted form contained that component.
     */
    //copied from org.apache.myfaces.shared.renderkit.html.HtmlRendererUtils
    public static void decodeUIInput(FacesContext facesContext,
                                     UIComponent component) {
        if (!(component instanceof EditableValueHolder)) {
            throw new IllegalArgumentException("Component "
                    + component.getClientId(facesContext)
                    + " is not an EditableValueHolder");
        }
        Map paramMap = facesContext.getExternalContext()
                .getRequestParameterMap();
        String clientId = component.getClientId(facesContext);

        if (isDisabledOrReadOnly(component))
            return;

        if (paramMap.containsKey(clientId)) {
            ((EditableValueHolder) component).setSubmittedValue(paramMap
                    .get(clientId));
        } else {
            log.warning(NON_SUBMITTED_VALUE_WARNING +
                    " Component : " +
                    ComponentUtils.getPathToComponent(component));
        }
    }

    //copied from org.apache.myfaces.shared.renderkit.html.HtmlRendererUtils
    public static boolean isDisabled(UIComponent component) {
        return isTrue(component.getAttributes().get("disabled"));
    }

    //copied from org.apache.myfaces.shared.renderkit.html.HtmlRendererUtils
    public static boolean isReadOnly(UIComponent component) {
        return isTrue(component.getAttributes().get("readonly"));
    }

    //copied from org.apache.myfaces.shared.renderkit.html.HtmlRendererUtils
    private static boolean isTrue(Object obj) {
        if (obj instanceof String) {
            return new Boolean((String) obj);
        }

        if (!(obj instanceof Boolean))
            return false;

        return ((Boolean) obj).booleanValue();
    }

    //copied from org.apache.myfaces.shared.renderkit.html.HtmlRendererUtils
    //modified
    public static boolean isDisabledOrReadOnly(UIComponent component) {
        return  isDisabled(component) ||
                isReadOnly(component);
    }

    /**
     * The ScriptContext offers methods and fields
     * to help with rendering out a script and keeping a
     * proper formatting.
     */
    //copied from org.apache.myfaces.shared.renderkit.html.HtmlRendererUtils
    public static class ScriptContext {
        private long currentIndentationLevel;
        private StringBuffer buffer = new StringBuffer();
        private boolean prettyPrint = false;
        /**
         * automatic formatting will render
         * new-lines and indents if blocks are opened
         * and closed - attention: you need to append
         * opening and closing brackets of blocks separately in this case!
         */
        private boolean automaticFormatting = true;

        public ScriptContext() {

        }

        public ScriptContext(boolean prettyPrint) {
            this.prettyPrint = prettyPrint;
        }

        public ScriptContext(StringBuffer buf, boolean prettyPrint) {
            this.prettyPrint = prettyPrint;
            this.buffer = buf;
        }

        public void increaseIndent() {
            currentIndentationLevel++;
        }

        public void decreaseIndent() {
            currentIndentationLevel--;

            if (currentIndentationLevel < 0)
                currentIndentationLevel = 0;
        }

        public void prettyLine() {
            if (prettyPrint) {
                append(LINE_SEPARATOR);

                for (int i = 0; i < getCurrentIndentationLevel(); i++)
                    append(TABULATOR);
            }
        }

        public void prettyLineIncreaseIndent() {
            increaseIndent();
            prettyLine();
        }

        public void prettyLineDecreaseIndent() {
            decreaseIndent();
            prettyLine();
        }

        public long getCurrentIndentationLevel() {
            return currentIndentationLevel;
        }

        public void setCurrentIndentationLevel(long currentIndentationLevel) {
            this.currentIndentationLevel = currentIndentationLevel;
        }

        public ScriptContext append(String str) {

            if (automaticFormatting && str.length() == 1) {
                boolean openBlock = str.equals("{");
                boolean closeBlock = str.equals("}");

                if (openBlock) {
                    prettyLine();
                } else if (closeBlock) {
                    prettyLineDecreaseIndent();
                }

                buffer.append(str);

                if (openBlock) {
                    prettyLineIncreaseIndent();
                } else if (closeBlock) {
                    prettyLine();
                }
            } else {
                buffer.append(str);
            }
            return this;
        }

        public ScriptContext append(char c) {
            buffer.append(c);
            return this;
        }

        public ScriptContext append(int i) {
            buffer.append(i);
            return this;
        }

        public String toString() {
            return buffer.toString();
        }
    }
}
