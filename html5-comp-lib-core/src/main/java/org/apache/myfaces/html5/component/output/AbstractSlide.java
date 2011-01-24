package org.apache.myfaces.html5.component.output;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFComponent;
import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFProperty;
import org.apache.myfaces.html5.component.properties.*;

import javax.faces.component.NamingContainer;

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
        TabindexProperty, MouseEventProperties, GlobalEventProperties
{

}
