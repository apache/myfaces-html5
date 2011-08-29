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

import org.apache.myfaces.html5.demo.model.ApacheProject;
import org.apache.myfaces.html5.demo.model.ApacheProjectLanguage;
import org.apache.myfaces.html5.event.DropEvent;

@ManagedBean(name = "dndBean")
@ViewScoped
public class DndBean implements Serializable
{
    private static final long serialVersionUID = 1L;

    private String simpleDndOutput = "Nothing dropped yet";
    private String dragAnythingOutput = "Nothing dropped yet";
    private String projectDropOutput = "Nothing dropped yet";

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

    private List<ApacheProject> projects;
    private List<ApacheProject> cProjects;
    private List<ApacheProject> javaProjects;

    public DndBean()
    {
        initProjects();
    }

    public String initProjects()
    {
        projects = new ArrayList<ApacheProject>();
        cProjects = new ArrayList<ApacheProject>();
        javaProjects = new ArrayList<ApacheProject>();
        projects.add(new ApacheProject("spamassassin", "SpamAssasin", ApacheProjectLanguage.C));
        projects.add(new ApacheProject("subversion", "Subversion", ApacheProjectLanguage.C));
        projects.add(new ApacheProject("myfaces", "MyFaces", ApacheProjectLanguage.JAVA));
        projects.add(new ApacheProject("tomcat", "Tomcat", ApacheProjectLanguage.JAVA));

        this.simpleDndOutput = "Nothing dropped yet";
        this.dragAnythingOutput = "Nothing dropped yet";
        this.projectDropOutput = "Nothing dropped yet";
        
        return null;
    }

    public List<ApacheProject> getProjects()
    {
        return projects;
    }

    // TODO: reuse the code!
    public void processJavaProjectDrop(DropEvent event) throws AbortProcessingException
    {
        String param = event.getParam();
        if (param == null || param.length() == 0)
            return;

        ApacheProject droppedProject = null;
        for (ApacheProject project : this.projects)
        {
            if (project.getId().equals(param))
            {
                droppedProject = project;
                break;
            }
        }

        if (droppedProject == null)
        {
            projectDropOutput = "No project or already dropped project is dropped.";
            return;
        }

        projects.remove(droppedProject);
        javaProjects.add(droppedProject);

        projectDropOutput = droppedProject.getName() + " is moved.";
    }

    public void processCProjectDrop(DropEvent event) throws AbortProcessingException
    {
        String param = event.getParam();
        if (param == null || param.length() == 0)
            return;

        ApacheProject droppedProject = null;
        for (ApacheProject project : this.projects)
        {
            if (project.getId().equals(param))
            {
                droppedProject = project;
                break;
            }
        }

        if (droppedProject == null)
        {
            projectDropOutput = "No project or already dropped project is dropped.";
            return;
        }

        projects.remove(droppedProject);
        cProjects.add(droppedProject);

        projectDropOutput = droppedProject.getName() + " is moved.";
    }

    public void processProjectDrop(DropEvent event) throws AbortProcessingException
    {
        String param = event.getParam();
        if (param == null || param.length() == 0)
            return;

        ApacheProject droppedProject = null;
        for (ApacheProject project : this.javaProjects)
        {
            if (project.getId().equals(param))
            {
                droppedProject = project;
                this.javaProjects.remove(project);
                break;
            }
        }
        if (droppedProject == null)
        {
            for (ApacheProject project : this.cProjects)
            {
                if (project.getId().equals(param))
                {
                    droppedProject = project;
                    this.cProjects.remove(project);
                    break;
                }
            }
        }
        
        if (droppedProject == null)
        {
            projectDropOutput = "No project or already dropped project is dropped.";
            return;
        }

        projects.add(droppedProject);

        projectDropOutput = droppedProject.getName() + " is moved.";
    }

    public String getProjectDropOutput()
    {
        return projectDropOutput;
    }

    public List<ApacheProject> getJavaProjects()
    {
        return javaProjects;
    }

    public List<ApacheProject> getCProjects()
    {
        return cProjects;
    }
}
