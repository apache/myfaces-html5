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
package org.apache.myfaces.html5.test;

import java.io.IOException;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.render.RenderKit;
import javax.faces.render.Renderer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class TestUtils
{
    /** Default Logger */
    private static final Log log = LogFactory.getLog(TestUtils.class);

    /** utility class, do not instantiate */
    private TestUtils()
    {
        // utility class, disable instantiation
    }

    /**
     * Add all of Tomahawk's renderers.  Currently this is not tied to 
     * faces-config.xml, so all change to the file MUST also be made here.
     * 
     * @param facesContext
     * @todo Do not add renderer if renderer is already added.
     */
    public static void addDefaultRenderers(FacesContext facesContext)
    {
        addRenderer(facesContext, "org.apache.myfaces.Div", "org.apache.myfaces.html5.Div",
                "org.apache.myfaces.html5.renderkit.panel.DivRenderer");

        addRenderer(facesContext, "javax.faces.Input", "org.apache.myfaces.html5.Text",
                "org.apache.myfaces.html5.renderkit.input.HtmlInputTextRenderer");

        addRenderer(facesContext, "javax.faces.Input", "org.apache.myfaces.html5.Color",
                "org.apache.myfaces.html5.renderkit.input.HtmlInputColorRenderer");

        addRenderer(facesContext, "javax.faces.Input", "org.apache.myfaces.html5.DateTime",
                "org.apache.myfaces.html5.renderkit.input.HtmlInputDateTimeRenderer");

        addRenderer(facesContext, "javax.faces.Input", "org.apache.myfaces.html5.Email",
                "org.apache.myfaces.html5.renderkit.input.HtmlInputEmailRenderer");

        addRenderer(facesContext, "javax.faces.Input", "org.apache.myfaces.html5.NumberSlider",
                "org.apache.myfaces.html5.renderkit.input.HtmlInputNumberSliderRenderer");

        addRenderer(facesContext, "javax.faces.Input", "org.apache.myfaces.html5.NumberSpinner",
                "org.apache.myfaces.html5.renderkit.input.HtmlInputNumberSpinnerRenderer");

        addRenderer(facesContext, "org.apache.myfaces.Media", "org.apache.myfaces.html5.Audio",
                "org.apache.myfaces.html5.renderkit.media.AudioRenderer");

        addRenderer(facesContext, "org.apache.myfaces.Media", "org.apache.myfaces.html5.Video",
                "org.apache.myfaces.html5.renderkit.media.VideoRenderer");

        addRenderer(facesContext, "org.apache.myfaces.Meter", "org.apache.myfaces.html5.Meter",
                "org.apache.myfaces.html5.renderkit.output.MeterRenderer");

        addRenderer(facesContext, "org.apache.myfaces.Progress", "org.apache.myfaces.html5.Progress",
                "org.apache.myfaces.html5.renderkit.output.ProgressRenderer");

        addRenderer(facesContext, "org.apache.myfaces.SlideView", "org.apache.myfaces.html5.SlideView",
                "org.apache.myfaces.html5.renderkit.output.SlideViewRenderer");

        addRenderer(facesContext, "org.apache.myfaces.Slide", "org.apache.myfaces.html5.Slide",
                "org.apache.myfaces.html5.renderkit.output.SlideRenderer");

    }

    /**
     * Add a renderer to the FacesContext.
     * 
     * @param facesContext Faces Context
     * @param family Componenet Family
     * @param rendererType Component Type
     * @param renderClassName Class Name of Renderer
     */
    public static void addRenderer(FacesContext facesContext, String family,
            String rendererType, String renderClassName)
    {
        Renderer renderer = (javax.faces.render.Renderer) newInstance(renderClassName);
        RenderKit kit = facesContext.getRenderKit();
        kit.addRenderer(family, rendererType, renderer);
    }

    /**
     * Tries a Class.loadClass with the context class loader of the current thread first and
     * automatically falls back to the ClassUtils class loader (i.e. the loader of the
     * myfaces.jar lib) if necessary.
     * 
     * Note: This was copied from org.apache.myfaces.shared.util.ClassUtils
     *
     * @param type fully qualified name of a non-primitive non-array class
     * @return the corresponding Class
     * @throws NullPointerException if type is null
     * @throws ClassNotFoundException
     */
    private static Class classForName(String type)
            throws ClassNotFoundException
    {
        if (type == null)
            throw new NullPointerException("type");
        try
        {
            // Try WebApp ClassLoader first
            return Class.forName(type, false, // do not initialize for faster startup
                    Thread.currentThread().getContextClassLoader());
        }
        catch (ClassNotFoundException ignore)
        {
            // fallback: Try ClassLoader for ClassUtils (i.e. the myfaces.jar lib)
            return Class.forName(type, false, // do not initialize for faster startup
                    TestUtils.class.getClassLoader());
        }
    }

    /**
     * Same as {@link #classForName(String)}, but throws a RuntimeException
     * (FacesException) instead of a ClassNotFoundException.
     *
     * Note: This was copied from org.apache.myfaces.shared.util.ClassUtils
     *
     * @return the corresponding Class
     * @throws NullPointerException if type is null
     * @throws FacesException if class not found
     */
    private static Class simpleClassForName(String type)
    {
        try
        {
            return classForName(type);
        }
        catch (ClassNotFoundException e)
        {
            log.error("Class " + type + " not found", e);
            throw new FacesException(e);
        }
    }

    /**
     * Create an instance of the class with the type of <code>type</code>.
     * 
     * Note: This was copied from org.apache.myfaces.shared.util.ClassUtils
     *
     * @param type Type of new class.
     * @return Instance of the class <code>type</code>
     * @throws FacesException
     */
    private static Object newInstance(String type) throws FacesException
    {
        if (type == null)
            return null;
        return newInstance(simpleClassForName(type));
    }

    /**
     * Create an instance of the class <code>clazz</code>.
     * 
     * Note: This was copied from org.apache.myfaces.shared.util.ClassUtils
     *
     * @param clazz Class to create an instance of.
     * @return Instance of the class <code>clazz</code>
     * @throws FacesException
     */
    private static Object newInstance(Class clazz) throws FacesException
    {
        try
        {
            return clazz.newInstance();
        }
        catch (NoClassDefFoundError e)
        {
            log.error("Class : " + clazz.getName() + " not found.", e);
            throw new FacesException(e);
        }
        catch (InstantiationException e)
        {
            log.error(e.getMessage(), e);
            throw new FacesException(e);
        }
        catch (IllegalAccessException e)
        {
            log.error(e.getMessage(), e);
            throw new FacesException(e);
        }
    }

    /**
     * Renderered a component, including it's children, then complete the reponse.
     * 
     * @param context Faces Context
     * @param component Component to be rendered.
     * @throws IOException Thrown while rendering.
     */
    public static void renderComponent(FacesContext context,
            UIComponent component) throws IOException
    {
        Renderer renderer = context.getRenderKit().getRenderer(
                component.getFamily(), component.getRendererType());
        renderer.encodeBegin(context, component);
        renderer.encodeChildren(context, component);
        renderer.encodeEnd(context, component);
        context.responseComplete();
        context.renderResponse();
    }
}
