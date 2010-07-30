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

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.FacesException;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.component.behavior.ClientBehaviorContext;
import javax.faces.render.ClientBehaviorRenderer;

import org.apache.myfaces.html5.behavior.DragSourceBehavior;
import org.apache.myfaces.html5.renderkit.util.BehaviorScriptUtils;
import org.apache.myfaces.html5.renderkit.util.ClientBehaviorEvents;
import org.apache.myfaces.html5.renderkit.util.Html5RendererUtils;

/**
 * Renderer of {@link DragSourceBehavior} behaviors. <br/>
 * This class only handles the script of "dragstart" event.
 * 
 * @author Ali Ok
 * 
 */
public class DragSourceBehaviorRenderer extends ClientBehaviorRenderer
{
    private static final Logger log = Logger.getLogger(DragSourceBehaviorRenderer.class.getName());

    @Override
    public String getScript(ClientBehaviorContext behaviorContext, ClientBehavior behavior)
    {
        if (!(behavior instanceof DragSourceBehavior))
        {
            throw new FacesException("Behavior is not a DragSourceBehavior.");
        }

        String eventName = behaviorContext.getEventName();
        if (ClientBehaviorEvents.DRAGSTART_EVENT.equals(eventName))
        {
            return _getDragStartScript((DragSourceBehavior) behavior);
        }
        else
        {
            if (log.isLoggable(Level.WARNING))
                log.warning("Event " + eventName
                        + " applied to DragSourceBehavior is not 'dragstart'. Ignoring the script.");

            return null;
        }
    }

    private String _getDragStartScript(DragSourceBehavior behavior)
    {
        String[] dropTargetTypes = Html5RendererUtils.resolveStrings(behavior.getDropTargetTypes());

        String jsDropTargetTypes = BehaviorScriptUtils.convertToSafeJavascriptLiteralArray(dropTargetTypes);
        String jsParam = BehaviorScriptUtils.convertToSafeJavascriptLiteral(behavior.getParam());
        String jsAction = BehaviorScriptUtils.convertToSafeJavascriptLiteral(behavior.getAction());

        //sample : return myfaces.html5.dragStart(event, 'move', {'A','B'), 'LAL');
        String format = "return myfaces.html5.dragStart(event, %s, %s, %s);";
        String script = String.format(format, jsAction,
                jsDropTargetTypes, jsParam);

        return script;
    }


    

}
