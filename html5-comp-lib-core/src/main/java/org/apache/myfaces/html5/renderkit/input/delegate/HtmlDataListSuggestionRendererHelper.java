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
import java.util.Collection;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;

import org.apache.myfaces.html5.component.input.HtmlDataList;
import org.apache.myfaces.html5.renderkit.util.HTML5;
import org.apache.myfaces.html5.renderkit.util.JsfProperties;
import org.apache.myfaces.shared_html5.renderkit.RendererUtils;

//TODO: docme
public class HtmlDataListSuggestionRendererHelper extends HtmlTextInputSuggestionRendererHelper
{

    @Override
    public boolean shouldGenerateDatalist(UIComponent uiComponent)
    {
        throw new UnsupportedOperationException(
                "Datalist generation does not make sense, since the component itself is DataList");
    }

    @Override
    public void checkSuggestions(UIComponent uiComponent)
    {
        // noop
    }

    @Override
    public void renderDataList(FacesContext facesContext, UIComponent component) throws IOException
    {
        super.renderDataList(facesContext, component);
    }

    protected void renderDataListBegin(FacesContext facesContext, UIComponent component) throws IOException
    {
        ResponseWriter writer = facesContext.getResponseWriter();
        writer.startElement(HTML5.DATALIST_ELEM, null);
        writer.writeAttribute(HTML5.ID_ATTR, component.getClientId(facesContext), JsfProperties.ID_PROP);
    }

    @Override
    protected void renderDataListOptions(FacesContext facesContext, UIComponent component, Converter converter)
            throws IOException
    {
        super.renderDataListOptions(facesContext, component, converter);
    }

    @Override
    protected void renderOptionsOfSuggestionsAttr(FacesContext facesContext, UIComponent uiComponent,
            Converter converter) throws IOException
    {
        if (uiComponent instanceof HtmlDataList)
        {
            HtmlDataList component = (HtmlDataList) uiComponent;
            Object objSuggestions = component.getSuggestions();
            if (objSuggestions == null)
                return;

            Collection<SelectItem> selectItemsCollection = extractSelectItemsCollectionFromObject(objSuggestions);

            renderOptionsFromSelectItemsCollection(facesContext, component, selectItemsCollection, converter);
        }
        else
        {
            throw new IllegalArgumentException(
                    "Component " + RendererUtils.getPathToComponent(uiComponent) + " is not instance of HtmlDataList. HtmlDataListSuggestionRendererHelper is unable to render options of suggestions attr.");
        }
    }

    @Override
    protected void renderOptionsOfChildren(FacesContext facesContext, UIComponent component, Converter converter)
            throws IOException
    {
        super.renderOptionsOfChildren(facesContext, component, converter);
    }
}
