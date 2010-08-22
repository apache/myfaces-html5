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
package org.apache.myfaces.html5.event;

import java.util.Map;
import java.util.logging.Logger;

import javax.faces.component.UIComponent;
import javax.faces.event.BehaviorEvent;
import javax.faces.event.FacesListener;

import org.apache.myfaces.html5.behavior.DropTargetBehavior;

/**
 * This event is fired when a successful drop is done.
 * <br/>
 * 
 * Holds the parameter and content-type <=> value map sent with the event.
 * 
 * @author Ali Ok
 *
 */
public class DropEvent extends BehaviorEvent
{
    private static final long serialVersionUID = 1L;
    private static final Logger log = Logger.getLogger(DropEvent.class.getName());

    private final Map<String, String> _dropDataMap;
    private final String _param;

    public DropEvent(UIComponent component, DropTargetBehavior behavior, Map<String, String> dropDataMap, String param)
    {
        super(component, behavior);
        _dropDataMap = dropDataMap;
        this._param = param;
    }
    
    @Override
    public boolean isAppropriateListener(FacesListener faceslistener)
    {
        return faceslistener instanceof DropListener;
    }

    @Override
    public void processListener(FacesListener faceslistener)
    {
        ((DropListener)faceslistener).processDropEvent(this);
        
    }
    
    /**
     * Returns the values sent received from the client after a successful drop.
     * <br/>
     * ie.
     * 
     * <table>
     * <tr>
     * <th>Content-type</th>
     * <th>Value</th>
     * </tr>
     * 
     * 
     *  
     * @return
     */
    public Map<String, String> getDropDataMap()
    {
        return _dropDataMap;
    }
    
  //TODO: doc me
    public String getParam()
    {
        return _param;
    }
    
}
