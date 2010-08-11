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
package org.apache.myfaces.html5.behavior;

import java.util.HashMap;
import java.util.Map;

import javax.el.ValueExpression;
import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;
import javax.faces.context.FacesContext;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFBehavior;
import org.apache.myfaces.html5.event.DropEvent;
import org.apache.myfaces.html5.event.DropListener;
import org.apache.myfaces.html5.handler.DropTargetBehaviorHandler;

/**
 * Provides Html5 drop functionality to its parent.
 * 
 * @author Ali Ok
 * 
 */
@ResourceDependencies(
{
        @ResourceDependency(name = "jsf.js", library = "javax.faces", target = "head"),
        @ResourceDependency(name = "html5.js", library = "myfaces.apache.org", target = "head")
})
@JSFBehavior(name = "fx:dropTarget", id = "org.apache.myfaces.html5.DragDropTargetBehavior")
public class DropTargetBehavior extends javax.faces.component.behavior.ClientBehaviorBase implements ValueExpressionHolder
{

    public static final String RENDERER_ID = "org.apache.myfaces.html5.DropTargetBehavior";
    
    private static final String ATTR_ACTION = "action";
    private static final String ATTR_TYPES = "types";
    private static final String ATTR_ACCEPT_MIME_TYPES = "acceptMimeTypes";
    private static final String ATTR_RERENDER = "rerender";

    private Map<String, ValueExpression> _valueExpressions = new HashMap<String, ValueExpression>();
    private ClientBehaviorDeltaStateHelper<DropTargetBehavior> deltaStateHelper = new ClientBehaviorDeltaStateHelper<DropTargetBehavior>(this);
    
    /**
     * Adds a {@link DropListener} to notify when a {@link DropEvent} occured. 
     */
    public void addDropTargetBehaviorListener(DropListener listener)
    {
        super.addBehaviorListener(listener);
    }

    /**
     * @see DropTargetBehavior#addBehaviorListener(javax.faces.event.BehaviorListener)
     */
    public void removeDropTargetBehaviorListener(DropListener listener)
    {
        removeBehaviorListener(listener);
    }

    @Override
    public String getRendererType()
    {
        return RENDERER_ID;
    }

    /**
     * @see DropTargetBehaviorHandler#_action
     */
    public String getAction()
    {
        return (String) deltaStateHelper.eval(ATTR_ACTION);
    }

    public void setAction(String action)
    {
        deltaStateHelper.put(ATTR_ACTION, action);
    }

    /**
     * @see DropTargetBehaviorHandler#_types
     */
    public Object getTypes()
    {
        return deltaStateHelper.eval(ATTR_TYPES);
    }

    public void setTypes(Object types)
    {
        deltaStateHelper.put(ATTR_TYPES, types);
    }

    /**
     * @see DropTargetBehaviorHandler#_acceptMimeTypes
     */
    // TODO: implement accepting all mime types with supporting '*' for the value.
    public Object getAcceptMimeTypes()
    {
        return deltaStateHelper.eval(ATTR_ACCEPT_MIME_TYPES);
    }

    public void setAcceptMimeTypes(Object acceptMimeTypes)
    {
        deltaStateHelper.put(ATTR_ACCEPT_MIME_TYPES, acceptMimeTypes);
    }

    /**
     * @see DropTargetBehaviorHandler#_rerender
     */
    public Object getRerender()
    {
        return deltaStateHelper.eval(ATTR_RERENDER);
    }

    public void setRerender(Object rerender)
    {
        deltaStateHelper.put(ATTR_RERENDER, rerender);
    }

    
    public void setValueExpression(String name, ValueExpression item)
    {
        if (item == null)
        {
            getValueExpressionMap().remove(name);
            deltaStateHelper.remove(name);
        }
        else
        {
            getValueExpressionMap().put(name, item);
        }
    }

    private Map<String, ValueExpression> getValueExpressionMap()
    {
        return _valueExpressions;
    }
    
    public ValueExpression getValueExpression(String name) 
    {
        return getValueExpressionMap().get(name);
    }
    
    @Override
    public void restoreState(FacesContext facesContext, Object o)
    {
        if (o == null)
        {
            return;
        }
        Object[] values = (Object[]) o;
        if (values[0] != null) 
        {
            super.restoreState(facesContext, values[0]);
        }
        deltaStateHelper.restoreState(facesContext, values[1]);
    }

    @Override
    public Object saveState(FacesContext facesContext)
    {
        if (initialStateMarked())
        {
            Object parentSaved = super.saveState(facesContext);
            Object deltaStateHelperSaved = deltaStateHelper.saveState(facesContext);
            
            if (parentSaved == null && deltaStateHelperSaved == null)
            {
                //No values
                return null;
            }   
            return new Object[]{parentSaved, deltaStateHelperSaved};
        }
        else
        {
            Object[] values = new Object[2];
            values[0] = super.saveState(facesContext);
            values[1] = deltaStateHelper.saveState(facesContext);
            return values;
        }
    }
}
