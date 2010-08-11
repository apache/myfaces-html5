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

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.el.ValueExpression;
import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;
import javax.faces.context.FacesContext;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFBehavior;


/**
 * Adds the Html5 drag functionality to its parent. <br/>
 * This behavior should be attached to "dragstart" event of the parent.
 * 
 * @author Ali Ok
 * 
 */
@ResourceDependencies(
{
        @ResourceDependency(name = "jsf.js", library = "javax.faces", target = "head"),
        @ResourceDependency(name = "html5.js", library = "myfaces.apache.org", target = "head")
})
@JSFBehavior(name = "fx:dragSource", id = "org.apache.myfaces.html5.DragSource")
public class DragSourceBehavior extends javax.faces.component.behavior.ClientBehaviorBase implements ValueExpressionHolder
{

    public static final String RENDERER_ID = "org.apache.myfaces.html5.DragSourceBehavior";

    private static final Serializable ATTR_ACTION = "action";
    private static final Serializable ATTR_DROP_TARGET_TYPES = "dropTargetTypes";
    private static final Serializable ATTR_PARAM = "param";

    private Map<String, ValueExpression> _valueExpressions = new HashMap<String, ValueExpression>();
    private ClientBehaviorDeltaStateHelper<DragSourceBehavior> deltaStateHelper = new ClientBehaviorDeltaStateHelper<DragSourceBehavior>(this);

    @Override
    public String getRendererType()
    {
        return RENDERER_ID;
    }

    /**
     * @see DragSourceBehavior#_action
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
     * @see DragSourceBehavior#_dropTargetTypes
     */
    public Object getDropTargetTypes()
    {
        return deltaStateHelper.eval(ATTR_DROP_TARGET_TYPES);
    }

    public void setDropTargetTypes(Object dropTargetTypes)
    {
        deltaStateHelper.put(ATTR_DROP_TARGET_TYPES, dropTargetTypes);
    }

    /**
     * @see DragSourceBehavior#_param
     */
    public String getParam()
    {
        return (String)deltaStateHelper.eval(ATTR_PARAM);
    }

    public void setParam(String param)
    {
        deltaStateHelper.put(ATTR_PARAM, param);
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
