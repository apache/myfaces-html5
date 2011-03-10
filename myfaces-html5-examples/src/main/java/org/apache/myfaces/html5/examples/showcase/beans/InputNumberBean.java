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

import javax.faces.bean.ManagedBean;

@ManagedBean(name="inputNumberBean")
public class InputNumberBean
{

    private int first;
    private double second;
    private int third;
    private int fourth;
    private int fifth;

    public int getFirst()
    {
        return first;
    }

    public void setFirst(int first)
    {
        this.first = first;
    }

    public double getSecond()
    {
        return second;
    }

    public void setSecond(double second)
    {
        this.second = second;
    }

    public int getThird()
    {
        return third;
    }

    public void setThird(int third)
    {
        this.third = third;
    }

    public int getFourth()
    {
        return fourth;
    }

    public void setFourth(int fourth)
    {
        this.fourth = fourth;
    }

    public int getFifth()
    {
        return fifth;
    }

    public void setFifth(int fifth)
    {
        this.fifth = fifth;
    }

}
