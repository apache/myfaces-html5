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

import org.apache.myfaces.html5.event.DropEvent;

//TODO: use the myfaces annotation and fix the template!
//@JSFBehavior(name = "fx:dragSource", id = "org.apache.myfaces.html5.DragSource")
//@FacesBehavior(value="org.apache.myfaces.html5.DragSourceBehavior")
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
     * The types of the dropTargets that drags from this dragSource can be applied. Can be comma separated set or
     * String[] or Collection<String>. <br/>
     * If defined, drags from this dragSource will work into only the dropTargets that have one of the same type. The
     * drag will be accepted if 'types' of hx:dropTarget has one of the types defined here.
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
     * Data to send to server when a successful drag&drop happens from this source. <br/>
     * The param can be received using the {@link DropEvent#getParam()} method at dropListener of the fx:dropTarget.
     * 
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
