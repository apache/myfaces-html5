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

import org.apache.commons.lang.StringUtils;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

@ManagedBean(name = "userAgentBean")
@SessionScoped
public class UserAgentBean implements Serializable{

    private boolean webkit;
    private boolean mozilla;
    private boolean opera;
    private boolean ie;

    @PostConstruct
    public void initialize(){
        final String userAgent = getUserAgent();
        if(StringUtils.isBlank(userAgent))
            return;

        //poor detection :(
        webkit = userAgent.contains("WebKit") || userAgent.contains("Webkit");
        if(webkit)
            return;

        opera = userAgent.contains("Opera");
        if(opera)
            return;

        ie = userAgent.contains("MSIE");
        if(ie)
            return;

        mozilla = userAgent.contains("Mozilla");
    }

    public String getUserAgent(){
         final HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
         return request.getHeader("User-Agent");
    }

    public boolean isWebkit() {
        return webkit;
    }

    public boolean isMozilla() {
        return mozilla;
    }

    public boolean isOpera() {
        return opera;
    }

    public boolean isIe() {
        return ie;
    }
}
