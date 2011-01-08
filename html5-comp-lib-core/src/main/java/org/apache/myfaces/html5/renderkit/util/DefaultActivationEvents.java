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

package org.apache.myfaces.html5.renderkit.util;

import java.util.*;

public abstract class DefaultActivationEvents {
    public final static Map<String, Set<String>> map = new DeactivationEventMap().
    		add(event("blur").			deactivationEvent("focus")).
            add(event("drag").			deactivationEvent("dragend")).
			add(event("dragend").		deactivationEvent("drag")).
            add(event("dragenter").		deactivationEvent("dragleave")	    .deactivationEvent("drop")).
            add(event("dragleave").		deactivationEvent("dragenter")).
            add(event("dragover").		deactivationEvent("dragleave")	    .deactivationEvent("drop")).
            add(event("dragstart").		deactivationEvent("dragend")).
            add(event("focus").			deactivationEvent("blur")).
            add(event("invalid").		deactivationEvent("input")).
            add(event("keydown").		deactivationEvent("keyup")).
            add(event("loadstart").		deactivationEvent("load")).
            add(event("mousedown").		deactivationEvent("mouseup")).
            add(event("mouseout").		deactivationEvent("mouseover")).
            add(event("mouseover").		deactivationEvent("mouseout")).
            add(event("pause").			deactivationEvent("play")			.deactivationEvent("playing")).
            add(event("play").			deactivationEvent("pause")).
            add(event("playing").		deactivationEvent("pause")).
            add(event("reset").			deactivationEvent("input")).
            add(event("seeked").		deactivationEvent("seeking")).
            add(event("seeking").		deactivationEvent("seeked")).
            //add(event("abort").
            //add(event("canplay").
            //add(event("canplaythrough").
            //add(event("change").
            //add(event("click").
            //add(event("contextmenu").
            //add(event("cuechange").
            //add(event("dblclick").
            //add(event("drop").
            //add(event("durationchange").
            //add(event("emptied").
            //add(event("ended").
            //add(event("error").
            //add(event("formchange").
            //add(event("forminput").
            //add(event("input").
            //add(event("keypress").
            //add(event("keyup").
            //add(event("load").
            //add(event("loadeddata").
            //add(event("loadedmetadata").
            //add(event("mousemove").
            //add(event("mouseup").
            //add(event("mousewheel").
            //add(event("progress").
            //add(event("ratechange").
            //add(event("readystatechange").
            //add(event("scroll").
            //add(event("select").
            //add(event("show").
            //add(event("stalled").
            //add(event("submit").
            //add(event("suspend").
            //add(event("timeupdate").
            //add(event("volumechange").
            //add(event("waiting").
            unmodifiable();

    private static DeactivationEventSet event(String event) {
        return new DeactivationEventSet(event);
    }

    private static class DeactivationEventMap {
        private List<DeactivationEventSet> deactivationEventLists = new ArrayList<DeactivationEventSet>();

        private DeactivationEventMap add(DeactivationEventSet deactivationEventList){
            deactivationEventLists.add(deactivationEventList);
            return this;
        }

        private Map<String, Set<String>> unmodifiable(){
            Map<String, Set<String>> map = new HashMap<String, Set<String>>();
            for (DeactivationEventSet deactivationEventList : deactivationEventLists) {
                map.put(deactivationEventList.event, deactivationEventList.deactivationEventSet);
            }

            return map;
        }
    }

    private static class DeactivationEventSet {
        private String event;
        private Set<String> deactivationEventSet = new HashSet<String>();

        private DeactivationEventSet(String event) {
            this.event = event;
        }

        private DeactivationEventSet deactivationEvent(String deactivationEvent){
            this.deactivationEventSet.add(deactivationEvent);
            return this;
        }


    }

}
