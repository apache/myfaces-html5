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

package org.apache.myfaces.html5.renderkit.util;

import javax.faces.application.Resource;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ResourceUtils {

    private final static String RENDERED_JSF_JS = "org.apache.myfaces.RENDERED_JSF_JS";

    public final static String JAVAX_FACES_LIBRARY_NAME = "javax.faces";
    public final static String JSF_JS_RESOURCE_NAME = "jsf.js";

    private final static String RENDERED_SCRIPT_RESOURCES_SET = "org.apache.myfaces.RENDERED_SCRIPT_RESOURCES_SET";

    //copied from org.apache.myfaces.shared.renderkit.html.util.ResourceUtils
    //modified
    public static void renderDefaultJsfJsInlineIfNecessary(FacesContext facesContext, ResponseWriter writer) throws IOException
    {
        if (facesContext.getAttributes().containsKey(RENDERED_JSF_JS))
        {
            return;
        }

        // Check first if we have lucky, we are using myfaces and the script has
        // been previously rendered
        if (isRenderedScript(facesContext, JAVAX_FACES_LIBRARY_NAME, JSF_JS_RESOURCE_NAME))
        {
            facesContext.getAttributes().put(RENDERED_JSF_JS, Boolean.TRUE);
            return;
        }

        // Check if this is an ajax request. If so, we don't need to include it, because that was
        // already done and in the worst case, jsf script was already loaded on the page.
        if (facesContext.getPartialViewContext() != null && facesContext.getPartialViewContext().isAjaxRequest())
        {
            return;
        }

        //Fast shortcut, don't create component instance and do what HtmlScriptRenderer do.
        Resource resource = facesContext.getApplication().getResourceHandler().createResource(JSF_JS_RESOURCE_NAME, JAVAX_FACES_LIBRARY_NAME);
        markScriptAsRendered(facesContext, JAVAX_FACES_LIBRARY_NAME, JSF_JS_RESOURCE_NAME);
        writer.startElement(HTML5.SCRIPT_ELEM, null);
        writer.writeAttribute(HTML5.SCRIPT_TYPE_ATTR, HTML5.SCRIPT_TYPE_TEXT_JAVASCRIPT, null);
        writer.writeURIAttribute(HTML5.SRC_ATTR, resource.getRequestPath(), null);
        writer.endElement(HTML5.SCRIPT_ELEM);

        //mark as rendered
        facesContext.getAttributes().put(RENDERED_JSF_JS, Boolean.TRUE);
        return;
    }

    //copied from org.apache.myfaces.shared.renderkit.html.util.ResourceUtils
    public static boolean isRenderedScript(FacesContext facesContext, String libraryName, String resourceName)
    {
        return getRenderedScriptResources(facesContext).containsKey(libraryName != null ? libraryName+'/'+resourceName : resourceName);
    }

    /**
     * Return a set of already rendered resources by this renderer on the current
     * request.
     *
     * @param facesContext
     * @return
     */
    @SuppressWarnings("unchecked")
    //copied from org.apache.myfaces.shared.renderkit.html.util.ResourceUtils
    private static Map<String, Boolean> getRenderedScriptResources(FacesContext facesContext)
    {
        Map<String, Boolean> map = (Map<String, Boolean>) facesContext.getAttributes().get(RENDERED_SCRIPT_RESOURCES_SET);
        if (map == null)
        {
            map = new HashMap<String, Boolean>();
            facesContext.getAttributes().put(RENDERED_SCRIPT_RESOURCES_SET,map);
        }
        return map;
    }

    //copied from org.apache.myfaces.shared.renderkit.html.util.ResourceUtils
    public static void markScriptAsRendered(FacesContext facesContext, String libraryName, String resourceName)
    {
        getRenderedScriptResources(facesContext).put(libraryName != null ? libraryName+'/'+resourceName : resourceName, Boolean.TRUE);
    }
}
