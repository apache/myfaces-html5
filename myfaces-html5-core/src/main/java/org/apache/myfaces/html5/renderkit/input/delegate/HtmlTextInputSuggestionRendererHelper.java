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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItem;
import javax.faces.component.UISelectItems;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;

import org.apache.myfaces.html5.component.input.Html5BaseInputText;
import org.apache.myfaces.html5.component.input.HtmlInputText;
import org.apache.myfaces.html5.renderkit.util.HTML5;
import org.apache.myfaces.html5.renderkit.util.Html5RendererUtils;
import org.apache.myfaces.html5.renderkit.util.JsfProperties;
import org.apache.myfaces.shared_html5.renderkit.RendererUtils;
import org.apache.myfaces.shared_html5.renderkit.html.HTML;
import org.apache.myfaces.shared_html5.util.SelectItemsIterator;

/**
 * Implementation of {@link SuggestionRendererHelper} for usage in {@link HtmlInputText}s.
 * 
 * @author Ali Ok
 * 
 */
public class HtmlTextInputSuggestionRendererHelper implements SuggestionRendererHelper
{
    static final char TABULATOR = '\t';

    // not using singleton since that approach is evil, not testable and needs thread safety which means performance penalty

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.apache.myfaces.html5.renderkit.input.delegate.SuggestionRendererHelper#checkSuggestions(org.apache.myfaces
     * .html5.component.input.Html5BaseInputText)
     */
    public void checkSuggestions(UIComponent uiComponent)
    {
        if (uiComponent instanceof Html5BaseInputText)
        {
            Html5BaseInputText component = (Html5BaseInputText) uiComponent;

            /*
             * if 'datalist' is defined, then other suggestion mechanisms(with f:selectItem(s) children or 'suggestions'
             * attribute) should not be used
             */
            String idOfDatalist = component.getDataList();
            if (idOfDatalist != null && !idOfDatalist.isEmpty() && shouldGenerateDatalist(component))
            {
                // WIKI: put a wiki page about this error
                throw new FacesException(
                        "Either \"list\" attribute or \"suggestions\" attribute and children with type SelectItem mechanism can be used for suggestions. Component " + RendererUtils.getPathToComponent(uiComponent) + "has both!");
            }
        }
        else
        {
            throw new IllegalArgumentException(
                    "Component " + RendererUtils.getPathToComponent(uiComponent) + " is not instance of Html5BaseInputText. HtmlTextInputSuggestionRendererHelper is unable to check suggestions.");
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.apache.myfaces.html5.renderkit.input.delegate.SuggestionRendererHelper#shouldGenerateDatalist(org.apache.
     * myfaces.html5.component.input.Html5BaseInputText)
     */
    public boolean shouldGenerateDatalist(UIComponent uiComponent)
    {
        if (uiComponent instanceof Html5BaseInputText)
        {
            Html5BaseInputText component = (Html5BaseInputText) uiComponent;
            Object suggestions = component.getSuggestions();
            if (suggestions != null)
                return true;

            List<UIComponent> children = component.getChildren();
            if (children != null && !children.isEmpty())
            {
                for (UIComponent child : children)
                {
                    if (child instanceof UISelectItem || child instanceof UISelectItems)
                        return true;
                }
            }

            return false;
        }
        else
        {
            throw new IllegalArgumentException(
                    "Component " + RendererUtils.getPathToComponent(uiComponent) + " is not instance of Html5BaseInputText. HtmlTextInputSuggestionRendererHelper is unable to determine whether datalist will be generated or not.");
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.apache.myfaces.html5.renderkit.input.delegate.SuggestionRendererHelper#renderDataList(javax.faces.context
     * .FacesContext, org.apache.myfaces.html5.component.input.Html5BaseInputText)
     */
    public void renderDataList(FacesContext facesContext, UIComponent component) throws IOException
    {
        renderDataListBegin(facesContext, component);

        // create converter and pass it to methods here to prevent duplicate creation of the converter
        Converter converter = Html5RendererUtils.findUIOutputConverterFailSafe(facesContext, component);

        renderDataListOptions(facesContext, component, converter);

        renderDataListEnd(facesContext, component);
    }

    protected void renderDataListBegin(FacesContext facesContext, UIComponent component) throws IOException
    {
        ResponseWriter writer = facesContext.getResponseWriter();
        writer.startElement(HTML5.DATALIST_ELEM, null);
        writer.writeAttribute(HTML5.ID_ATTR, component.getAttributes().get(JsfProperties.DATALIST_PROP),
                JsfProperties.ID_PROP);
    }

    protected void renderDataListOptions(FacesContext facesContext, UIComponent component, Converter converter)
            throws IOException
    {
        renderOptionsOfSuggestionsAttr(facesContext, component, converter);
        renderOptionsOfChildren(facesContext, component, converter);
    }

    protected void renderDataListEnd(FacesContext facesContext, UIComponent component) throws IOException
    {
        ResponseWriter writer = facesContext.getResponseWriter();
        writer.endElement(HTML5.DATALIST_ELEM);
    }

    protected void renderOptionsOfSuggestionsAttr(FacesContext facesContext, UIComponent uiComponent,
            Converter converter) throws IOException
    {
        if (uiComponent instanceof Html5BaseInputText)
        {
            Html5BaseInputText component = (Html5BaseInputText) uiComponent;
            Object objSuggestions = component.getSuggestions();
            if (objSuggestions == null)
                return;

            Collection<SelectItem> selectItemsCollection = extractSelectItemsCollectionFromObject(objSuggestions);

            renderOptionsFromSelectItemsCollection(facesContext, component, selectItemsCollection, converter);
        }
        else
        {
            throw new IllegalArgumentException(
                    "Component " + RendererUtils.getPathToComponent(uiComponent) + " is not instance of Html5BaseInputText. HtmlTextInputSuggestionRendererHelper is unable to render options of suggestions attr.");
        }
    }

    @SuppressWarnings("unchecked")
    protected Collection<SelectItem> extractSelectItemsCollectionFromObject(Object objSuggestions)
    {
        Collection<SelectItem> selectItemsCollection = null;

        if (objSuggestions instanceof String)
        {
            // if suggestions property is comma separated words, create new SelectItem objects from splits

            String strSuggestions = (String) objSuggestions;
            String[] strSuggestionElements = strSuggestions.split(",");

            selectItemsCollection = new ArrayList<SelectItem>(strSuggestionElements.length);

            for (String strSuggesionElement : strSuggestionElements)
            {
                strSuggesionElement.trim();
                selectItemsCollection.add(new SelectItem(strSuggesionElement, strSuggesionElement));
            }
        }
        else if (objSuggestions instanceof SelectItem[])
        {
            selectItemsCollection = Arrays.asList((SelectItem[]) objSuggestions);
        }
        else if (objSuggestions instanceof Collection<?>)
        {
            selectItemsCollection = (Collection<SelectItem>) objSuggestions;
        }
        else
        {
            // WIKI: Add a wiki page
            throw new FacesException(
                    "\"suggestions\" property should be one of comma separeted strings, SelectItem[] or Collection of \"SelectItem\"s");
        }
        return selectItemsCollection;
    }

    protected void renderOptionsOfChildren(FacesContext facesContext, UIComponent component, Converter converter)
            throws IOException
    {
        List<SelectItem> selectItemsList = getSelectItemsList(facesContext, component);
        renderOptionsFromSelectItemsCollection(facesContext, component, selectItemsList, converter);
    }

    protected void renderOptionsFromSelectItemsCollection(FacesContext facesContext, UIComponent component,
            Collection<SelectItem> selectItemsCollection, Converter converter) throws IOException
    {
        /*
         * ignore SelectItemGroup, since grouping(<optgroup>) is not allowed in suggestions. ignore selected items,
         * since selection is applicable in suggestions ignore isNoSelectionOption(), since it is about validation and
         * we're not forcing user to select/type one of suggestions
         */

        ResponseWriter writer = facesContext.getResponseWriter();

        for (Iterator<SelectItem> iterator = selectItemsCollection.iterator(); iterator.hasNext();)
        {
            // might throw an exception! don't handle, and let the user handle the exception
            SelectItem selectItem = iterator.next();

            writer.write(TABULATOR);

            writer.startElement(HTML.OPTION_ELEM, null);

            /*
             * we're writing an attribute, not text here: using <option value="value" label="Label" /> notation, not
             * <option value="value">Label</option>
             */
            String itemLabel = selectItem.getLabel();
            // writeAttribute method escapes the label anyway. so ignore SelectItem#isEscape()...
            writer.writeAttribute(HTML.LABEL_ATTR, itemLabel, null);

            String itemStrValue = RendererUtils.getConvertedStringValue(facesContext, component, converter, selectItem);
            writer.writeAttribute(HTML5.VALUE_ATTR, itemStrValue, null);

            writer.writeAttribute(HTML.DISABLED_ATTR, selectItem.isDisabled(), null);

            writer.endElement(HTML.OPTION_ELEM);

        }
    }

    protected List<SelectItem> getSelectItemsList(FacesContext facesContext, UIComponent component)
    {
        List<SelectItem> list = new ArrayList<SelectItem>();

        for (Iterator<SelectItem> iter = new SelectItemsIterator(component, facesContext); iter.hasNext();)
        {
            list.add(iter.next());
        }

        return list;
    }

}
