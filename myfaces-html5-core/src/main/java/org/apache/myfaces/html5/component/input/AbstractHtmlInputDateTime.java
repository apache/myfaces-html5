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
package org.apache.myfaces.html5.component.input;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFComponent;
import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFProperty;

/**
 * Convenience component for Html5 date like inputs. <br/>
 * Minimum and maximum date selectable is determined by using attached fx:validateDateTimeRange if any. Else, minimum
 * and maximum are not rendered.
 * 
 * @author Ali Ok
 * 
 */
@JSFComponent(
        name = "hx:inputDateTime",
        clazz = "org.apache.myfaces.html5.component.input.HtmlInputDateTime",
        tagClass = "org.apache.myfaces.html5.tag.input.HtmlInputDateTimeTag",
        defaultRendererType = "org.apache.myfaces.html5.DateTime",
        family = "javax.faces.Input",
        type = "org.apache.myfaces.html5.HtmlDateTime",
        implementz = "javax.faces.component.behavior.ClientBehaviorHolder", //need to define it, or the events wont be rendered
        defaultEventName="valueChange"
        )
public abstract class AbstractHtmlInputDateTime extends org.apache.myfaces.html5.component.input.Html5BaseInputText
{
    
    /**
     * Type of the input. Can be one of "datetime", "date", "time", "month", "week", "datetime-local". Defaults to "datetime". 
     */
    @JSFProperty(deferredValueType = "java.lang.String", defaultValue="datetime")
    public abstract String getType();
    
    /**
     * Indicates the granularity of the value by limiting the allowed values. The limited values will be decided considering the 
     * step base which is defined by 'minimum' property of the attached fx:validateDateTimeRange. If not specified, 'any' will be
     * rendered as the step value, thus browser will decide it.
     * <br/>
     * Specified value will be multiplied with the step scale factor which is different for each type.
     * <br/>
     * Information about the step for each type is:
     * <code>
     *   &lt;table border="1"&gt;
     *      &lt;tr&gt;
     *          &lt;th&gt;type&lt;/th&gt;
     *          &lt;th&gt;Description for step scale factor&lt;/th&gt;
     *          &lt;th&gt;Default step value(what 'any' means)&lt;/th&gt;
     *          &lt;th&gt;Applicable minimum value of step&lt;/th&gt;
     *          &lt;th&gt;Applicable maximum value of step&lt;/th&gt;
     *      &lt;tr&gt;
     *      &lt;tr&gt;
     *          &lt;td&gt;datetime&lt;/td&gt;
     *          &lt;td&gt;Step should be expressed in seconds.&lt;/td&gt;
     *          &lt;td&gt;1 minute&lt;/td&gt;
     *          &lt;td&gt;0.01 (10 miliseconds)&lt;/td&gt;
     *          &lt;td&gt;N/A&lt;/td&gt; //XXX: test it again
     *      &lt;/tr&gt;
     *      &lt;tr&gt;
     *          &lt;td&gt;date&lt;/td&gt;
     *          &lt;td&gt;Step should be expressed in days.&lt;/td&gt;
     *          &lt;td&gt;1 day&lt;/td&gt;
     *          &lt;td&gt;1 (1 day)&lt;/td&gt;
     *          &lt;td&gt;N/A&lt;/td&gt; //XXX: test it again
     *      &lt;/tr&gt;
     *      &lt;tr&gt;
     *          &lt;td&gt;time&lt;/td&gt;
     *          &lt;td&gt;Step should be expressed in seconds. If the step is less than 1 second, browser should show the miliseconds input too.&lt;/td&gt;
     *          &lt;td&gt;1 minute&lt;/td&gt;
     *          &lt;td&gt;0.01 (10 miliseconds)&lt;/td&gt;
     *          &lt;td&gt;N/A&lt;/td&gt; //XXX: test it again
     *      &lt;/tr&gt;
     *      &lt;tr&gt;
     *          &lt;td&gt;month&lt;/td&gt;
     *          &lt;td&gt;Step should be expressed in months.&lt;/td&gt;
     *          &lt;td&gt;1 month&lt;/td&gt;
     *          &lt;td&gt;1 (1 month)&lt;/td&gt;
     *          &lt;td&gt;N/A&lt;/td&gt; //XXX: test it again
     *      &lt;/tr&gt;
     *      &lt;tr&gt;
     *          &lt;td&gt;week&lt;/td&gt;
     *          &lt;td&gt;Step should be expressed in weeks.&lt;/td&gt;
     *          &lt;td&gt;1 week&lt;/td&gt;
     *          &lt;td&gt;1 (1 week)&lt;/td&gt;
     *          &lt;td&gt;N/A&lt;/td&gt; //XXX: test it again
     *      &lt;/tr&gt;
     *      &lt;tr&gt;
     *          &lt;td&gt;datetime-local&lt;/td&gt;
     *          &lt;td&gt;Step should be expressed in seconds.&lt;/td&gt;
     *          &lt;td&gt;1 minute&lt;/td&gt;
     *          &lt;td&gt;0.01 (10 miliseconds)&lt;/td&gt;
     *          &lt;td&gt;N/A&lt;/td&gt; //XXX: test it again
     *      &lt;/tr&gt;
     *   &lt;/table&gt;
     * </code>
     */
    @JSFProperty(deferredValueType="java.lang.Double", defaultValue="Double.MIN_VALUE")
    public abstract double getStep();
    
    
    @JSFProperty(tagExcluded = true, defaultValue = "Integer.MIN_VALUE")
    @Override
    // this attribute is not present in HTML5 date like inputs, so exclude it
    public int getMaxlength()
    {
        return Integer.MIN_VALUE;
    }
    
    @JSFProperty(tagExcluded = true, defaultValue = "Integer.MIN_VALUE")
    @Override
    // this attribute is not present in HTML5 date like inputs, so exclude it
    public int getSize()
    {
        return Integer.MIN_VALUE;
    }
}
