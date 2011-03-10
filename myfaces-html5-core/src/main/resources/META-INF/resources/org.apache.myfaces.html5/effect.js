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
 * Adds effects(actually css class names) to given element.
 */
myfaces.html5.effect.addEffect = function(element, effects) {
    var currentClassName = element.className;
    if(currentClassName.length!=0 && !myfaces.html5.common.strEndsWith(currentClassName, " "))
        currentClassName = currentClassName + " ";
    element.className = currentClassName + effects;
}

/*
 * Adds effects(actually css class names) to given element.
 */
myfaces.html5.effect.removeEffect = function(element, effects) {
    var currentClassName = element.className;
    if(myfaces.html5.common.strEndsWith(currentClassName, effects)){
        var newClassName = currentClassName.substr(0, currentClassName.length - effects.length);
        if(myfaces.html5.common.strEndsWith(newClassName, " "))
            newClassName = newClassName.substr(0, newClassName.length-1);
        element.className = newClassName;
    }
    else{
        //TODO: if development environment, give an alert or log!
    }
}