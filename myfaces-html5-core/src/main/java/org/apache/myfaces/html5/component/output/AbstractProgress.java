package org.apache.myfaces.html5.component.output;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFComponent;
import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFProperty;
import org.apache.myfaces.html5.component.properties.*;

/**
 * Convenience component for Html5 progress element. <br/>
 *
 * @author Ali Ok
 *
 */
@JSFComponent(
        name = "hx:progress",
        clazz = "org.apache.myfaces.html5.component.output.Progress",
        tagClass = "org.apache.myfaces.html5.tag.output.ProgressTag",
        defaultRendererType = "org.apache.myfaces.html5.Progress",
        family = "org.apache.myfaces.Progress",
        type = "org.apache.myfaces.html5.Progress",
        implementz = "javax.faces.component.behavior.ClientBehaviorHolder",
        defaultEventName="click"
)
public abstract class AbstractProgress extends javax.faces.component.UIComponentBase implements
        javax.faces.component.behavior.ClientBehaviorHolder, Html5GlobalProperties, AccesskeyProperty,
        TabindexProperty, MouseEventProperties, GlobalEventProperties
{

    /**
     * Value of the progress element. Value should be in the interval of [0, maximum]
     */
    @JSFProperty(required = false, deferredValueType = "java.lang.Double")
    public abstract Double getValue();

    /**
     * Maximum value of the progress element. If the value of the progress element reaches the maximum value, then it means the progress is done.
     */
    @JSFProperty(required = false, deferredValueType = "java.lang.Double", defaultValue = "1.0")
    public abstract Double getMaximum();
    
}
