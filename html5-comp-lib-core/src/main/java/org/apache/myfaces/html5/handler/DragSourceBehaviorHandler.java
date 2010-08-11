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
package org.apache.myfaces.html5.handler;

import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.FacesException;
import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.component.behavior.Behavior;
import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.context.FacesContext;
import javax.faces.view.facelets.BehaviorConfig;
import javax.faces.view.facelets.ComponentHandler;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.TagAttribute;
import javax.faces.view.facelets.TagException;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFFaceletAttribute;
import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFFaceletTag;
import org.apache.myfaces.html5.behavior.DragSourceBehavior;
import org.apache.myfaces.html5.component.api.Draggable;
import org.apache.myfaces.html5.event.DropEvent;
import org.apache.myfaces.html5.renderkit.util.ClientBehaviorEvents;
import org.apache.myfaces.shared_html5.renderkit.RendererUtils;

/**
 * Tag handler for {@link DragSourceBehavior}. Sets the "draggable" attribute of the parent and attaches the behavior to
 * the parent. <br/>
 * 
 * @author Ali Ok
 * 
 */
@JSFFaceletTag(name = "fx:dragSource")
public class DragSourceBehaviorHandler extends javax.faces.view.facelets.BehaviorHandler
{
    private static final Logger log = Logger.getLogger(DragSourceBehaviorHandler.class.getName());

    /**
     * Action of for drag operation. Can be one of below:
     * <ul>
     * <li>copy: A copy of the source item may be made at the new location.</li>
     * <li>move: An item may be moved to a new location.</li>
     * <li>link: A link may be established to the source at the new location.</li>
     * <li>none: The item may not be dropped.</li>
     * </ul>
     * <br/>
     * 
     * If nothing is specified, the action will be defined by the browser and can be adjusted using the modifier keys.
     * If dropTarget does not accept the action of this dragSource, then the DnD will fail.
     * 
     */
    @JSFFaceletAttribute(name = "action", className = "javax.el.ValueExpression", deferredValueType = "java.lang.String")
    private final TagAttribute _action;

    /**
     * The types of the dropTargets that drags from this dragSource can be applied. Can be comma separated set or
     * String[] or Collection<String>. <br/>
     * If defined, drags from this dragSource will work into only the dropTargets that have one of the same type. The
     * drag will be accepted if 'types' of hx:dropTarget has one of the types defined here.
     */
    @JSFFaceletAttribute(name = "dropTargetTypes", className = "javax.el.ValueExpression", deferredValueType = "java.lang.Object")
    private final TagAttribute _dropTargetTypes;

    /**
     * Data to send to server when a successful drag&drop happens from this source. <br/>
     * The param can be received using the {@link DropEvent#getParam()} method at dropListener of the fx:dropTarget.
     */
    @JSFFaceletAttribute(name = "param", className = "javax.el.ValueExpression", deferredValueType = "java.lang.String")
    private final TagAttribute _param;

    public DragSourceBehaviorHandler(BehaviorConfig config)
    {
        super(config);
        _action = getAttribute("action");
        _dropTargetTypes = getAttribute("dropTargetTypes");
        _param = getAttribute("param");
    }

    @Override
    public void apply(FaceletContext faceletContext, UIComponent parent)
    {

        if (!ComponentHandler.isNew(parent))
        {
            if (log.isLoggable(Level.FINE))
                log.fine("Component " + RendererUtils.getPathToComponent(parent)
                        + " is not new, thus return without any operation.");

            return;
        }

        if (parent instanceof ClientBehaviorHolder)
        {
            ClientBehaviorHolder holder = _getClientBehaviorHolder(parent);

            FacesContext context = faceletContext.getFacesContext();
            Application app = context.getApplication();
            String behaviorId = getBehaviorId();
            Behavior behavior = app.createBehavior(behaviorId);

            if (!(behavior instanceof DragSourceBehavior))
            {
                throw new FacesException("Behavior is not a DragSourceBehavior");
            }

            // manually added all of the properties, so no need for this:
            // setAttributes(faceletContext, behavior);

            // set parent as draggable
            if (parent instanceof Draggable)
            {
                ((Draggable) parent).setDraggable(true);
            }
            else
            {
                if (log.isLoggable(Level.WARNING))
                    log.warning("Parent " + RendererUtils.getPathToComponent(parent)
                            + " does not implement Draggable interface, thus unable to set the draggable attribute. "
                            + "Renderer of the parent must handle the decision of being draggable manually.");
            }

            DragSourceBehavior dragSourceBehavior = (DragSourceBehavior) behavior;

            // evaluating the _param's value expression doesn't this work if I put the dragSource in a datatable and try
            // to set the valueexpression using the var of table.
            // XXX: see https://issues.apache.org/jira/browse/MYFACES-2616
            // see the thread http://www.mail-archive.com/dev@myfaces.apache.org/msg46764.html
            // thus need to pass the valuexpression to the behavior, then the renderer can evaluate it. AjaxBehavior
            // does this with a map.
            // using the same approach in DropTargetBehavior too...
            if (_action != null)
            {
                if (_action.isLiteral())
                {
                    dragSourceBehavior.setAction(_action.getValue(faceletContext));
                }
                else
                {
                    dragSourceBehavior.setValueExpression("action",
                            _action.getValueExpression(faceletContext, String.class));
                }

            }
            if (_dropTargetTypes != null)
            {
                if (_action.isLiteral())
                {
                    dragSourceBehavior.setDropTargetTypes(_dropTargetTypes.getObject(faceletContext));
                }
                else
                {
                    dragSourceBehavior.setValueExpression("dropTargetTypes",
                            _dropTargetTypes.getValueExpression(faceletContext, Object.class));
                }
            }
            if (_param != null)
            {
                if (_param.isLiteral())
                {
                    dragSourceBehavior.setParam(_param.getValue(faceletContext));
                }
                else
                {
                    dragSourceBehavior.setValueExpression("param",
                            _param.getValueExpression(faceletContext, String.class));
                }

            }

            holder.addClientBehavior(ClientBehaviorEvents.DRAGSTART_EVENT, dragSourceBehavior);
        }
        else
        {
            if (log.isLoggable(Level.WARNING))
                log.warning("Parent is not a ClientBehaviorHolder.");

        }
    }

    private ClientBehaviorHolder _getClientBehaviorHolder(UIComponent parent)
    {
        if (!(parent instanceof ClientBehaviorHolder))
        {
            throw new TagException(getTag(),
                    "DragSourceBehavior must be attached to a ClientBehaviorHolder parent. Component "
                            + RendererUtils.getPathToComponent(parent) + "is not a ClientBehaviorHolder.");
        }

        ClientBehaviorHolder holder = (ClientBehaviorHolder) parent;

        _checkEvent(holder, ClientBehaviorEvents.DRAGSTART_EVENT);

        return holder;
    }

    private void _checkEvent(ClientBehaviorHolder holder, String eventName)
    {
        Collection<String> eventNames = holder.getEventNames();

        if (!eventNames.contains(eventName))
        {
            StringBuilder message = new StringBuilder();
            message.append("Unable to attach DragSourceBehavior.  ");
            message.append("DragSourceBehavior may only be attached to ");
            message.append("ClientBehaviorHolders that support the '");
            message.append(eventName);
            message.append("' event.  The parent ClientBehaviorHolder "
                    + RendererUtils.getPathToComponent((UIComponent) holder) + " only ");
            message.append("supports the following events: ");

            for (String supportedEventName : eventNames)
            {
                message.append(supportedEventName);
                message.append(" ");
            }

            throw new TagException(getTag(), message.toString());
        }
    }

}
