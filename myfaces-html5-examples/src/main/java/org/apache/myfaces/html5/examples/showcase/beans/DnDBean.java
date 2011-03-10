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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.AbortProcessingException;

import org.apache.myfaces.html5.event.DropEvent;

@ManagedBean(name="dndBean")
@SessionScoped
public class DnDBean implements Serializable {

	private String someParam;

	public String getSomeParam() {
		return someParam;
	}

	public void setSomeParam(String someParam) {
		this.someParam = someParam;
	}
	
	public void processDropBehavior(DropEvent event) throws AbortProcessingException{
	    someParam = "";
	    someParam += "Drop Time : " + new Date() + "<br/>\n";
        someParam += "DropEvent.getParam() : " + event.getParam() + "<br/>\n";
        Map<String, String> dropDataMap = event.getDropDataMap();
        if(dropDataMap != null){
            Set<String> keySet = dropDataMap.keySet();
            for (String key : keySet)
            {
                someParam += key + "  :  " + dropDataMap.get(key) + "<br/>\n";
            }
        }
    }
	
	private List<SportsTeam> teams;
	
	public DnDBean()
    {
	    initTeams();
    }

    public String initTeams()
    {
        teams = new ArrayList<SportsTeam>();
	    
	    teams.add(new SportsTeam("FCB", "FC Barcelona", TeamType.FOOTBALL));
	    teams.add(new SportsTeam("GS", "Galatasaray SK", TeamType.FOOTBALL));
	    teams.add(new SportsTeam("RM", "Real Madrid", TeamType.FOOTBALL));
	    teams.add(new SportsTeam("KS", "Kayserispor", TeamType.FOOTBALL));
	    teams.add(new SportsTeam("LAL", "LA Lakers", TeamType.BASKETBALL));
	    teams.add(new SportsTeam("BOC", "Boston Celtics", TeamType.BASKETBALL));
	    
	    return null;
    }
	
	public List<SportsTeam> getTeams()
    {
        return teams;
    }
	
	public void processTeamDrop(DropEvent event) throws AbortProcessingException{
	    String param = event.getParam();
	    if(param == null || param.isEmpty())
	        return;
	    
	    SportsTeam droppedTeam = null;
	    for (SportsTeam team : this.teams)
        {
            if(team.getId().equals(param)){
                droppedTeam = team;
                break;
            }
        }
	    
	    boolean removed = teams.remove(droppedTeam);
	    
	    someParam = "Team with Id " + param + " removed : " + removed; 
	}
	

	/////////////
	
	public String someAction() {
        System.out.println("someAction");
        return null;
    }
	
	public String getTest()
    {
	    return "test";
    }
}
