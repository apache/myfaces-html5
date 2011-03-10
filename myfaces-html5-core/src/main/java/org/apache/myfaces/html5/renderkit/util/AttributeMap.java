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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Map that is used for holding the pass through attributes and events for components.
 * 
 * @author Ali Ok
 * @see PassThroughAttributes
 * @see PassThroughClientBehaviorEvents
 */
class AttributeMap<K, V>
{
    private Map<String, String> _innerMap;

    private AttributeMap()
    {
        this._innerMap = new HashMap<String, String>();
    }

    private AttributeMap(int initialCapacity)
    {
        this._innerMap = new HashMap<String, String>(initialCapacity, 0.5F);
    }

    AttributeMap<K, V> attr(String jsfAttrName, String htmlAttrName)
    {
        this._innerMap.put(jsfAttrName, htmlAttrName);
        return this;
    }

    /**
     * Assumes the html attribute name is same with jsf property name.
     */
    AttributeMap<K, V> attr(String jsfAttrName)
    {
        this._innerMap.put(jsfAttrName, jsfAttrName);
        return this;
    }

    AttributeMap<K, V> attrs(Map<String, String> attrs)
    {
        this._innerMap.putAll(attrs);
        return this;
    }

    AttributeMap<K, V> event(String jsfAttrName, String eventName)
    {
        this._innerMap.put(jsfAttrName, eventName);
        return this;
    }

    AttributeMap<K, V> events(Map<String, String> events)
    {
        this._innerMap.putAll(events);
        return this;
    }

    Map<String, String> unmodifiable()
    {
        return Collections.unmodifiableMap(this._innerMap);
    }

    // stuff for static import
    static AttributeMap<String, String> map()
    {
        return new AttributeMap<String, String>();
    }

    /**
     * @param initialCapacity
     *            For performance optimization.
     */
    static AttributeMap<String, String> map(int initialCapacity)
    {
        return new AttributeMap<String, String>(initialCapacity);
    }

}
