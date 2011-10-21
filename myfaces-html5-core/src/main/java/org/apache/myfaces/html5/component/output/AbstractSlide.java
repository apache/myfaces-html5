package org.apache.myfaces.html5.component.output;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFComponent;
import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFProperty;
import org.apache.myfaces.html5.component.properties.*;
import org.apache.myfaces.commons.util.ComponentUtils;

import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

/**
 * Provides a slide in the slide view.<br/>
 * Page authors should nest hx:slide components inside hx:slideView.
 *
 * @author Ali Ok
 *
 */
@JSFComponent(
        name = "hx:slide",
        clazz = "org.apache.myfaces.html5.component.output.Slide",
        tagClass = "org.apache.myfaces.html5.tag.output.SlideTag",
        defaultRendererType = "org.apache.myfaces.html5.Slide",
        family = "org.apache.myfaces.Slide",
        type = "org.apache.myfaces.html5.Slide",
        implementz = "javax.faces.component.behavior.ClientBehaviorHolder",
        defaultEventName="click"
)
public abstract class AbstractSlide extends javax.faces.component.UIComponentBase implements
        javax.faces.component.behavior.ClientBehaviorHolder, Html5GlobalProperties, AccesskeyProperty,
        TabindexProperty, MouseEventProperties, GlobalEventProperties, PrependIdProperty, NamingContainer
{

    @Override
    public String getContainerClientId(FacesContext ctx)
    {
        if (isPrependId())
        {
            return super.getContainerClientId(ctx);
        }
        UIComponent parentNamingContainer = ComponentUtils.findParentNamingContainer(this, false);
        if (parentNamingContainer != null)
        {
            return parentNamingContainer.getContainerClientId(ctx);
        }
        return null;
    }

}
