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

public class SportsTeam implements Serializable
{
    private String id;
    private String name;
    private TeamType type;

    public SportsTeam(String id, String name, TeamType type)
    {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public TeamType getType()
    {
        return type;
    }

    public void setType(TeamType type)
    {
        this.type = type;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof SportsTeam)
        {
            SportsTeam other = (SportsTeam) obj;
            if(other.getId().equals(this.id))
                return true;
        }
        
        return false;
    }
    
    @Override
    public int hashCode()
    {
        return this.id.hashCode();
    }

}

enum TeamType
{

    FOOTBALL("F"), BASKETBALL("B");

    private final String code;

    private TeamType(String code)
    {
        this.code = code;
    }

    @Override
    public String toString()
    {
        return code;
    }

}
