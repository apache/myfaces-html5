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

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class CssBuilder{

    List<String> selectors = new ArrayList<String>();
    List<String> rules = new ArrayList<String>();


    public CssBuilder selector(String... selectors){
        for (String selector : selectors) {
            this.selectors.add(selector);
        }
        return this;
    }

    public CssBuilder percentRule(String property, Number value){
        this.rules.add(property + ":" + value.toString() + "%;");
        return this;
    }

    public CssBuilder rule(String property, String value){
        if(StringUtils.isNotBlank(property) && StringUtils.isNotBlank(value))
            this.rules.add(property + ":" + value + ";");

        return this;
    }

    public String build(){
        StringBuilder builder = new StringBuilder();
        for (String selector : selectors) {
            builder.append(selector).append(',');
        }
        builder.deleteCharAt(builder.length() - 1);

        builder.append('{');

        for (String rule : rules) {
            builder.append(rule);
        }

        builder.append("}");

        return builder.toString();
    }

    public void append(StringBuilder builder){
        builder.append(this.build());
    }

}

