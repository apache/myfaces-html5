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

import org.apache.myfaces.html5.model.MediaInfo;

@ManagedBean(name="videoBean")
public class VideoBean
{

    public MediaInfo[] getItems()
    {
        return new MediaInfo[]{
            new MediaInfo("http://diveintohtml5.org/i/pr6.mp4", "video/mp4", "avc1.42E01E, mp4a.40.2"),
            new MediaInfo("http://diveintohtml5.org/i/pr6.webm")
        };
    }

}
