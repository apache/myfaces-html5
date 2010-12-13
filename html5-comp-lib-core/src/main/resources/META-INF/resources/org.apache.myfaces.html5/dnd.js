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

if(myfaces.html5.dnd == undefined || myfaces.html5.dnd == null)
    myfaces.html5.dnd = {};

myfaces.html5.dnd.PARAM_MIME_TYPE 				= 'text/x-myfaces-html5-dnd-param';
myfaces.html5.dnd.COMPONENT_SOURCE_MIME_TYPE 	= 'text/x-myfaces-html5-dnd-source';
myfaces.html5.dnd.DROP_TARGETS_MIME_TYPE 		= 'text/x-myfaces-html5-drop-target-type';
myfaces.html5.dnd.COMPONENT_SOURCE 				= 'org.apache.myfaces';
myfaces.html5.dnd.ACCEPT_ALL_MIME_TYPES 	    = '*';

//myfaces.html5.dnd.COMPONENT_SOURCE_MIME_TYPE 	= 'text/x-myfaces-html5-dnd-source';


/*
 * This function is only used by MyFaces generated draggable elements, the ones that have <fx:dragSource> behavior.
 */
myfaces.html5.dnd.dragStart = function(event, effectAllowed, dropTargetTypes, paramToSendToServer) {
	//set allowed effect
    event.dataTransfer.effectAllowed = effectAllowed;   //only allow some specific event
 
    //with this, drop target will understand that source of this dnd operation is some MyFaces component
    event.dataTransfer.setData(myfaces.html5.dnd.COMPONENT_SOURCE_MIME_TYPE, myfaces.html5.dnd.COMPONENT_SOURCE);
 
    //this will be set if we want to send an optional parameter to the server-side drop listener
    if(paramToSendToServer)
        event.dataTransfer.setData(myfaces.html5.dnd.PARAM_MIME_TYPE, paramToSendToServer);
 
    //this will be set if we want to make the drop only into specific dropTargets with specific types
    if(dropTargetTypes)
        event.dataTransfer.setData(myfaces.html5.dnd.DROP_TARGETS_MIME_TYPE, myfaces.html5.common.getArrayAsString(dropTargetTypes));
       
    return true;
}
 
myfaces.html5.dnd.dragEnterOrOver = function(event, allowedEffect, dropTargetTypes, acceptedMimeTypes) {
	//check allowed mime types first. if its "*", then accept all (do not check it)
    if(acceptedMimeTypes.length!=1 || acceptedMimeTypes[0]!=myfaces.html5.dnd.ACCEPT_ALL_MIME_TYPES){
        var foundMimeTypes = acceptedMimeTypes.filter(function (mimeType){return event.dataTransfer.types.contains(mimeType)});
        //if even one of the event mime types are not allowed, stop DnD
        if(foundMimeTypes == null || foundMimeTypes.length == 0)
        {
            return true; //don't cancel the event, thus stop DnD
        }
    }

    
    //if allowed effect is specified, set it. so the browser can decide whether continue or stop dnd operation.
    if(allowedEffect){
    	event.dataTransfer.effectAllowed = allowedEffect;
    }
    
    //check drop target type
    var strAcceptedDropTargetTypesOfDragSource = event.dataTransfer.getData(myfaces.html5.dnd.DROP_TARGETS_MIME_TYPE);
    if(strAcceptedDropTargetTypesOfDragSource){		//if drag source defines a drop target type
    	//then, let's check this drop target's type matches with that
    	var acceptedDropTargetTypesFromDragSource = myfaces.html5.common.convertStringToArray(event.dataTransfer.getData(myfaces.html5.dnd.DROP_TARGETS_MIME_TYPE));
    	var foundDropTargetTypes = acceptedDropTargetTypesFromDragSource.filter(function (dropTargetType){return myfaces.html5.common.contains(dropTargetTypes, dropTargetType)});
    	
    	//if even one of the drop target types are not allowed, stop DnD 
    	if(foundDropTargetTypes.length == 0)
    		return true; //don't cancel the event, thus stop DnD
    }

    //cancel the event, so effect on screen is updated
    if (event.preventDefault)
        event.preventDefault();
 
    return false;
}
 
myfaces.html5.dnd.drop = function(event, source, rerender, acceptedMimeTypes){
	//cancel the event. this is necessary for DnD execution
    if (event.preventDefault)
        event.preventDefault();
 
    //CALL jsf.ajax.request with constructing parameters
    //this call should will the dropListener and make the rerender operation
    //also, will send event mime types and values too!
 
    var options = {};
    //set execute
    options.execute = "@this";		//XXX: do we need to parametrize this option too?

    //set render
    if(rerender)
    	options.render = rerender;
    else
    	options.render = "@none";
    
    //set param
    var paramsToSend = event.dataTransfer.getData(myfaces.html5.dnd.PARAM_MIME_TYPE);
    if(paramsToSend)
    	options[myfaces.html5.dnd.PARAM_MIME_TYPE] = paramsToSend;

    //set the data according to acceptedMimeTypes
    if(acceptedMimeTypes){
	    var foundMimeTypes;
        if(acceptedMimeTypes.length==1 && acceptedMimeTypes[0]==myfaces.html5.dnd.ACCEPT_ALL_MIME_TYPES){
            foundMimeTypes = new Array(event.dataTransfer.types.length);
            for(var i=0; i<event.dataTransfer.types.length; i++){
                foundMimeTypes[i] =  event.dataTransfer.types.item(i);
            }
        }
        else {
            foundMimeTypes = acceptedMimeTypes.filter(function (mimeType) {return event.dataTransfer.types.contains(mimeType)});
        }
        if(foundMimeTypes.length > 0){
             options["org.apache.myfaces.dnd.foundMimeTypes"] = myfaces.html5.common.getArrayAsString(foundMimeTypes);
             for(var i=0; i< foundMimeTypes.length; i++){
                 var mimeType = foundMimeTypes[i];
                 var data = event.dataTransfer.getData(mimeType);
                 if(data){
                     options[mimeType] = data;
                 }
             }
        }
    }
    
    //set event
    options["javax.faces.behavior.event"] = "drop"

    jsf.ajax.request(source, event, options);
    
    return false;

}