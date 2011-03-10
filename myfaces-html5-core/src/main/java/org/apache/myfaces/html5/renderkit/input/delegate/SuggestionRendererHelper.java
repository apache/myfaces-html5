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
package org.apache.myfaces.html5.renderkit.input.delegate;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

/**
 * This interface defines a contract for rendering suggestions related markup.
 * 
 * @author Ali Ok
 * 
 */
public interface SuggestionRendererHelper
{

    /**
     * Check if suggestion conditions are OK. For example, if 'datalist' is defined, then other suggestion options(with
     * f:selectItem(s) children or 'suggestions' attribute) should not be used.
     * 
     */
    public void checkSuggestions(UIComponent component);

    /**
     * Returns true if a datalist should be generated for given component.
     * 
     */
    public boolean shouldGenerateDatalist(UIComponent component);

    /**
     * Renders the datalist.
     * <p>
     * Implementations of this method should not render any other markup.
     * 
     */
    public void renderDataList(FacesContext facesContext, UIComponent component) throws IOException;

}
