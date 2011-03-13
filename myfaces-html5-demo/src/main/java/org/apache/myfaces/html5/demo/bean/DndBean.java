/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.myfaces.html5.demo.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AbortProcessingException;

import org.apache.myfaces.html5.demo.model.SportsTeam;
import org.apache.myfaces.html5.demo.model.SportsTeamType;
import org.apache.myfaces.html5.event.DropEvent;

@ManagedBean(name = "dndBean")
@ViewScoped
public class DndBean implements Serializable
{
    private static final long serialVersionUID = 1L;

    private String simpleDndOutput = "Nothing dropped yet";
    private String dragAnythingOutput = "Nothing dropped yet";
    private String teamDropOutput = "Nothing dropped yet";

    public void processSimpleDnd(DropEvent event) throws AbortProcessingException
    {
        simpleDndOutput = "";
        simpleDndOutput += "Drop Time : " + new Date().toLocaleString() + "<br/>\n";
        simpleDndOutput += "Drop event parameter : " + event.getParam() + "<br/>\n";
    }

    public void processDragAnything(DropEvent event) throws AbortProcessingException
    {
        dragAnythingOutput = "";
        dragAnythingOutput += "Drop Time : " + new Date().toLocaleString() + "<br/>\n";
        dragAnythingOutput += "Drop event parameter : " + event.getParam() + "<br/>\n";
        Map<String, String> dropDataMap = event.getDropDataMap();
        if (dropDataMap != null)
        {
            Set<String> keySet = dropDataMap.keySet();
            for (String key : keySet)
            {
                dragAnythingOutput += key + "  :  " + dropDataMap.get(key) + "<br/>\n";
            }
        }
    }

    public String getSimpleDndOutput()
    {
        return simpleDndOutput;
    }

    public String getDragAnythingOutput()
    {
        return dragAnythingOutput;
    }

    private List<SportsTeam> teams;
    private List<SportsTeam> footballTeams;
    private List<SportsTeam> basketballTeams;

    public DndBean()
    {
        initTeams();
    }

    public String initTeams()
    {
        teams = new ArrayList<SportsTeam>();
        footballTeams = new ArrayList<SportsTeam>();
        basketballTeams = new ArrayList<SportsTeam>();
        teams.add(new SportsTeam("fcb", "FC Barcelona", SportsTeamType.FOOTBALL));
        teams.add(new SportsTeam("rma", "Real Madrid", SportsTeamType.FOOTBALL));
        teams.add(new SportsTeam("lal", "LA Lakers", SportsTeamType.BASKETBALL));
        teams.add(new SportsTeam("boc", "Boston Celtics", SportsTeamType.BASKETBALL));

        this.simpleDndOutput = "Nothing dropped yet";
        this.dragAnythingOutput = "Nothing dropped yet";
        this.teamDropOutput = "Nothing dropped yet";
        
        return null;
    }

    public List<SportsTeam> getTeams()
    {
        return teams;
    }

    // TODO: reuse the code!
    public void processFootballTeamDrop(DropEvent event) throws AbortProcessingException
    {
        String param = event.getParam();
        if (param == null || param.isEmpty())
            return;

        SportsTeam droppedTeam = null;
        for (SportsTeam team : this.teams)
        {
            if (team.getId().equals(param))
            {
                droppedTeam = team;
                break;
            }
        }

        if (droppedTeam == null)
        {
            teamDropOutput = "No team or already dropped team is dropped.";
            return;
        }

        teams.remove(droppedTeam);
        footballTeams.add(droppedTeam);

        teamDropOutput = droppedTeam.getName() + " is moved.";
    }

    public void processBasketballTeamDrop(DropEvent event) throws AbortProcessingException
    {
        String param = event.getParam();
        if (param == null || param.isEmpty())
            return;

        SportsTeam droppedTeam = null;
        for (SportsTeam team : this.teams)
        {
            if (team.getId().equals(param))
            {
                droppedTeam = team;
                break;
            }
        }

        if (droppedTeam == null)
        {
            teamDropOutput = "No team or already dropped team is dropped.";
            return;
        }

        teams.remove(droppedTeam);
        basketballTeams.add(droppedTeam);

        teamDropOutput = droppedTeam.getName() + " is moved.";
    }

    public void processTeamDrop(DropEvent event) throws AbortProcessingException
    {
        String param = event.getParam();
        if (param == null || param.isEmpty())
            return;

        SportsTeam droppedTeam = null;
        for (SportsTeam team : this.footballTeams)
        {
            if (team.getId().equals(param))
            {
                droppedTeam = team;
                this.footballTeams.remove(team);
                break;
            }
        }
        if (droppedTeam == null)
        {
            for (SportsTeam team : this.basketballTeams)
            {
                if (team.getId().equals(param))
                {
                    droppedTeam = team;
                    this.basketballTeams.remove(team);
                    break;
                }
            }
        }
        
        if (droppedTeam == null)
        {
            teamDropOutput = "No team or already dropped team is dropped.";
            return;
        }

        teams.add(droppedTeam);

        teamDropOutput = droppedTeam.getName() + " is moved.";
    }

    public String getTeamDropOutput()
    {
        return teamDropOutput;
    }

    public List<SportsTeam> getFootballTeams()
    {
        return footballTeams;
    }

    public List<SportsTeam> getBasketballTeams()
    {
        return basketballTeams;
    }
}
