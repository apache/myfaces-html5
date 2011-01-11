package org.apache.myfaces.html5.component.output;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFComponent;
import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFProperty;
import org.apache.myfaces.html5.component.properties.*;

/**
 * Convenience tag for the new Html5 meter element.
 *
 * @author Ali Ok
 */
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

    /**
     * Value of the meter. Value should be in the interval of [minimum, maximum]
     */
    @JSFProperty(required = true, deferredValueType = "java.lang.Double")
    public abstract Double getValue();

    /**
     * Minimum value of the component, defaults to 0.<br/>
     * Starting point of the meter.
     */
    @JSFProperty(required = false, deferredValueType = "java.lang.Double", defaultValue = "0.0")
    public abstract Double getMinimum();

    /**
     * Maximum value of the component, defaults to 1.<br/>
     * Ending point of the meter.
     */
    @JSFProperty(required = false, deferredValueType = "java.lang.Double", defaultValue = "1.0")
    public abstract Double getMaximum();

    /**
     * Limit of low value of the meter.<br/>
     * Values smaller than this value might be displayed in a special way(e.g. in a red color) by the browser.
     */
    @JSFProperty(required = false, deferredValueType = "java.lang.Double")
    public abstract Double getLow();

    /**
     * Limit of high value of the meter.<br/>
     * Values larger than this value might be displayed in a special way(e.g. in a red color) by the browser.
     */
    @JSFProperty(required = false, deferredValueType = "java.lang.Double")
    public abstract Double getHigh();

    /**
     * Optimum value of the meter.<br/>
     * Values close to this value might be displayed in a special way(e.g. in a green color) by the browser.
     */
    @JSFProperty(required = false, deferredValueType = "java.lang.Double")
    public abstract Double getOptimum();


}
