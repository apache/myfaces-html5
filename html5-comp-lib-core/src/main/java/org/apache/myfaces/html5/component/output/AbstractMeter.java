package org.apache.myfaces.html5.component.output;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFComponent;
import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFProperty;
import org.apache.myfaces.html5.component.properties.*;

@JSFComponent(
        name = "hx:meter",
        clazz = "org.apache.myfaces.html5.component.output.Meter",
        tagClass = "org.apache.myfaces.html5.tag.output.MeterTag",
        defaultRendererType = "org.apache.myfaces.html5.Meter",
        family = "org.apache.myfaces.Meter",
        type = "org.apache.myfaces.html5.Meter",
        implementz = "javax.faces.component.behavior.ClientBehaviorHolder",
        defaultEventName="click"
)
public abstract class AbstractMeter extends javax.faces.component.UIComponentBase implements
        javax.faces.component.behavior.ClientBehaviorHolder, Html5GlobalProperties, AccesskeyProperty,
        TabindexProperty, MouseEventProperties, GlobalEventProperties {

    @JSFProperty(required = true, deferredValueType = "java.lang.Double")
    public abstract Double getValue();

    @JSFProperty(required = false, deferredValueType = "java.lang.Double", defaultValue = "0.0")
    public abstract Double getMinimum();

    @JSFProperty(required = false, deferredValueType = "java.lang.Double", defaultValue = "1.0")
    public abstract Double getMaximum();

    @JSFProperty(required = false, deferredValueType = "java.lang.Double")
    public abstract Double getLow();

    @JSFProperty(required = false, deferredValueType = "java.lang.Double")
    public abstract Double getHigh();

    @JSFProperty(required = false, deferredValueType = "java.lang.Double")
    public abstract Double getOptimum();


}
