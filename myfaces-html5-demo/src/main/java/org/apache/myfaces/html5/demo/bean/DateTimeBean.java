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
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "dateTimeBean")
@ViewScoped
public class DateTimeBean implements Serializable
{
    private static final long serialVersionUID = 1L;

    private Date date01;
    private Date date02;
    private Date date03;
    private Date date04;
    private Date date05;
    private Date date06;

    public Date getDate01()
    {
        return date01;
    }

    public void setDate01(Date date01)
    {
        this.date01 = date01;
    }

    public Date getDate02()
    {
        return date02;
    }

    public void setDate02(Date date02)
    {
        this.date02 = date02;
    }

    public Date getDate03()
    {
        return date03;
    }

    public void setDate03(Date date03)
    {
        this.date03 = date03;
    }

    public Date getDate04()
    {
        return date04;
    }

    public void setDate04(Date date04)
    {
        this.date04 = date04;
    }

    public Date getDate05()
    {
        return date05;
    }

    public void setDate05(Date date05)
    {
        this.date05 = date05;
    }

    public Date getDate06()
    {
        return date06;
    }

    public void setDate06(Date date06)
    {
        this.date06 = date06;
    }

}
