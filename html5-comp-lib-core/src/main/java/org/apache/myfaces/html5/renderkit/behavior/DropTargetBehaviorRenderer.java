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
package org.apache.myfaces.html5.renderkit.behavior;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.component.behavior.ClientBehaviorContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.faces.render.ClientBehaviorRenderer;

import org.apache.myfaces.html5.behavior.DropTargetBehavior;
import org.apache.myfaces.html5.event.DropEvent;
import org.apache.myfaces.html5.renderkit.util.BehaviorScriptUtils;
import org.apache.myfaces.html5.renderkit.util.ClientBehaviorEvents;
import org.apache.myfaces.html5.renderkit.util.Html5RendererUtils;

/**
 * Renderer of the fx:dropTarget behavior.
 * 
 * @author Ali Ok
 * 
 */
public class DropTargetBehaviorRenderer extends ClientBehaviorRenderer
{

    private static final Logger log = Logger.getLogger(DropTargetBehaviorRenderer.class.getName());

    /**
     * Mime type to define the source of the drag. If Javascript transfer data has a data set with this key, than we can
     * assume the source of the drag is a MyFaces Html5 component.
     */
    public static final String DEFAULT_MYFACES_MIME_TYPE = "text/x-myfaces-html5-dnd-source";

    /**
     * On a succesful drop, types of the data sent to server will be post to server with this key.
     */
    private static final String MYFACES_DND_FOUND_MIME_TYPES_KEY = "org.apache.myfaces.dnd.foundMimeTypes";

    /**
     * Mime type of the param sent to server. Param defined in fx:dragSource is passed to drag&drop events with this key
     * and sent to server with this key.
     */
    private static final String MYFACES_HTML5_DND_PARAM_MIME_TYPE = "text/x-myfaces-html5-dnd-param";

    private static Set<String> ALLOWED_ACTIONS = new HashSet<String>(Arrays.asList("copy", "move", "link", "copyLink",
            "copyMove", "linkMove", "all", "none"));

    @Override
    public void decode(FacesContext context, UIComponent component, ClientBehavior behavior)
    {

        if (!(behavior instanceof DropTargetBehavior))
        {
            throw new FacesException("The behavior must be an instance of DropTargetBehavior");
        }

        super.decode(context, component, behavior);

        DropTargetBehavior dropTargetBehavior = (DropTargetBehavior) behavior;
        // XXX: do we need a disabled attr on dropTargetBehavior?
        // if (dropTargetBehavior.isDisabled() || !component.isRendered()) {
        if (!component.isRendered())
        {
            return;
        }

        dispatchBehaviorEvent(context, component, dropTargetBehavior);
    }

    /**
     * Dispatches the {@link DropEvent} with appropriate parameter and data information.
     */
    protected void dispatchBehaviorEvent(FacesContext context, UIComponent component,
            DropTargetBehavior dropTargetBehavior)
    {
        Map<String, String> requestParameterMap = context.getExternalContext().getRequestParameterMap();

        // get value of param set in fx:dragSource
        String param = requestParameterMap.get(MYFACES_HTML5_DND_PARAM_MIME_TYPE);
        if (param == null || param.isEmpty())
            param = null;

        // get other data values with accepted mime types
        Map<String, String> dropDataMap = null;
        String strReceivedDataMimeTypes = requestParameterMap.get(MYFACES_DND_FOUND_MIME_TYPES_KEY);
        if (strReceivedDataMimeTypes != null && !strReceivedDataMimeTypes.isEmpty())
        {
            dropDataMap = new HashMap<String, String>();

            String[] receivedDataMimeTypes = Html5RendererUtils.resolveStrings(strReceivedDataMimeTypes);
            for (String mimeType : receivedDataMimeTypes)
            {
                if (mimeType.equals(MYFACES_HTML5_DND_PARAM_MIME_TYPE))
                    // param is set already, pass
                    continue;

                String data = requestParameterMap.get(mimeType);
                if (data != null && !data.isEmpty())
                {
                    dropDataMap.put(mimeType, data);
                }
            }
        }

        DropEvent event = new DropEvent(component, dropTargetBehavior, dropDataMap, param);

        // XXX: do we need immediate stuff on drop event?
        // PhaseId phaseId = dropTargetBehavior.isImmediate() || isComponentImmediate(component) ?
        // PhaseId.APPLY_REQUEST_VALUES :
        // PhaseId.INVOKE_APPLICATION;

        event.setPhaseId(PhaseId.INVOKE_APPLICATION);

        component.queueEvent(event);
    }

    @Override
    public String getScript(ClientBehaviorContext behaviorContext, ClientBehavior behavior)
    {
        if (!(behavior instanceof DropTargetBehavior))
        {
            throw new FacesException("Behavior is not a DropTargetBehavior");
        }

        String eventName = behaviorContext.getEventName();
        if (eventName.equals(ClientBehaviorEvents.DRAGENTER_EVENT)
                || eventName.equals(ClientBehaviorEvents.DRAGOVER_EVENT))
        {
            return _getDragEnterOrOverScript((DropTargetBehavior) behavior);
        }
        else if (eventName.equals(ClientBehaviorEvents.DROP_EVENT))
        {
            return _getDropScript(behaviorContext, (DropTargetBehavior) behavior);
        }
        else
        {
            if (log.isLoggable(Level.WARNING))
                log.warning("Event "
                        + eventName
                        + " is not one of "
                        + Arrays.toString(new String[]
                        {
                                ClientBehaviorEvents.DRAGENTER_EVENT, ClientBehaviorEvents.DRAGOVER_EVENT,
                                ClientBehaviorEvents.DROP_EVENT
                        }) + ". Ignoring the script.");

            return null;
        }
    }

    private String _getDragEnterOrOverScript(DropTargetBehavior behavior)
    {
        String action = behavior.getAction() != null ? behavior.getAction().toString() : null;
        String[] types = Html5RendererUtils.resolveStrings(behavior.getTypes());
        String[] acceptMimeTypes = Html5RendererUtils.resolveStrings(behavior.getAcceptMimeTypes(), new String[]
        {
            DEFAULT_MYFACES_MIME_TYPE
        });

        // it is better to check the action is one of allowed, isn't it? So, if user types 'cpy' instead of 'copy',
        // he/she will easily understand what is wrong. Other way, he/she has to watch the browser's javascript console.
        _checkAction(action);

        String jsAction = BehaviorScriptUtils.convertToSafeJavascriptLiteral(action);
        String jsTypes = BehaviorScriptUtils.convertToSafeJavascriptLiteralArray(types);
        String jsAcceptMimeTypes = BehaviorScriptUtils.convertToSafeJavascriptLiteralArray(acceptMimeTypes);

        // sample:: return dragEnterOrOver(event,'move',['firstdropTargetType'], ['text/x-myfaces-html5-dnd-source']);
        String format = "return myfaces.html5.dragEnterOrOver(event, %s, %s, %s);";
        String script = String.format(format, jsAction, jsTypes, jsAcceptMimeTypes);

        return script;
    }

    private void _checkAction(String action)
    {
        if (action == null || action.isEmpty())
            return;

        if (ALLOWED_ACTIONS.contains(action))
            return;
        else
            throw new FacesException("Action of dropTarget is not one of allowed values "
                    + Arrays.toString(ALLOWED_ACTIONS.toArray(new String[0])) + " but " + action);
    }

    private String _getDropScript(ClientBehaviorContext behaviorContext, DropTargetBehavior behavior)
    {

        String sourceId = behaviorContext.getSourceId();
        String[] rerender = Html5RendererUtils.resolveStrings(behavior.getRerender());
        String[] acceptMimeTypes = Html5RendererUtils.resolveStrings(behavior.getAcceptMimeTypes(), new String[]
        {
            DEFAULT_MYFACES_MIME_TYPE
        });

        String jsSourceId = (sourceId == null) ? "this" : BehaviorScriptUtils.convertToSafeJavascriptLiteral(sourceId);
        String jsAcceptMimeTypes = BehaviorScriptUtils.convertToSafeJavascriptLiteralArray(acceptMimeTypes);
        String jsRerender = BehaviorScriptUtils.convertToSpaceSeperatedJSLiteral(rerender);

        // sample:: return myfaces.html5.drop(event, 'drop_zone', '@this someId',
        // ['text/x-myfaces-html5-dnd-source','text/plain']);
        String format = "return myfaces.html5.drop(event, %s, %s, %s);";
        String script = String.format(format, jsSourceId, jsRerender, jsAcceptMimeTypes);

        return script;
    }

}
