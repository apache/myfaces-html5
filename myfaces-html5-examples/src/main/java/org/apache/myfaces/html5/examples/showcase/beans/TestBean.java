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
package org.apache.myfaces.html5.examples.showcase.beans;

import java.util.Map;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.event.AbortProcessingException;

import org.apache.myfaces.html5.event.DropEvent;

@ManagedBean(name="testBean")
public class TestBean {

	public String someAction() {
		System.out.println("someAction");
		return null;
	}

	private String someParam;

	public String getSomeParam() {
		return someParam;
	}

	public void setSomeParam(String someParam) {
		this.someParam = someParam;
	}

	
	public void processDropBehavior(DropEvent event) throws AbortProcessingException{
        someParam = "DropEvent.getParam() + " + event.getParam() + "<br/>\n";
        Map<String, String> dropDataMap = event.getDropDataMap();
        if(dropDataMap != null){
            Set<String> keySet = dropDataMap.keySet();
            for (String key : keySet)
            {
                someParam += key + "  :  " + dropDataMap.get(key) + "<br/>\n";
            }
        }
    }
}
