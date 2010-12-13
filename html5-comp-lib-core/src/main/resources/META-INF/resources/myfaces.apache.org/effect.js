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

var myfaces;
if(myfaces == undefined || myfaces == null)
	myfaces = {};

if(myfaces.html5 == undefined || myfaces.html5 == null)
    myfaces.html5 = {};

if(myfaces.html5.effect == undefined || myfaces.html5.effect == null)
    myfaces.html5.effect = {};


/*
 * This function is only used by MyFaces generated draggable elements, the ones that have <fx:dragSource> behavior.
 */
myfaces.html5.effect.dragStart = function(event, effectAllowed, dropTargetTypes, paramToSendToServer) {
	//set allowed effect
    event.dataTransfer.effectAllowed = effectAllowed;   //only allow some specific event
 
    //with this, drop target will understand that source of this dnd operation is some MyFaces component
    event.dataTransfer.setData(myfaces.html5.COMPONENT_SOURCE_MIME_TYPE, myfaces.html5.COMPONENT_SOURCE);
 
    //this will be set if we want to send an optional parameter to the server-side drop listener
    if(paramToSendToServer)
        event.dataTransfer.setData(myfaces.html5.PARAM_MIME_TYPE, paramToSendToServer);    
 
    //this will be set if we want to make the drop only into specific dropTargets with specific types
    if(dropTargetTypes)
        event.dataTransfer.setData(myfaces.html5.DROP_TARGETS_MIME_TYPE, myfaces.html5._getArrayAsString(dropTargetTypes));    
       
    return true;
}