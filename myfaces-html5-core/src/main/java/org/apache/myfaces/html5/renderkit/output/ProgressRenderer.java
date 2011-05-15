package org.apache.myfaces.html5.renderkit.output;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFRenderer;
import org.apache.myfaces.html5.component.output.AbstractProgress;
import org.apache.myfaces.html5.renderkit.panel.DivRenderer;
import org.apache.myfaces.html5.renderkit.util.*;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Progress renderer.
 *
 * @author Ali Ok
 *
 */
@JSFRenderer(renderKitId = "HTML_BASIC", family = "org.apache.myfaces.Progress", type = "org.apache.myfaces.html5.Progress")
public class ProgressRenderer extends Renderer
{
    private static final Logger log = Logger.getLogger(DivRenderer.class.getName());

    @Override
    public void encodeBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {
        if (log.isLoggable(Level.FINE))
            log.fine("encodeBegin");

        super.encodeBegin(facesContext, uiComponent);

        RendererUtils.checkParamValidity(facesContext, uiComponent, AbstractProgress.class);

        ResponseWriter writer = facesContext.getResponseWriter();

        AbstractProgress component = (AbstractProgress) uiComponent;

        writer.startElement("progress", uiComponent);

        // write id
        writer.writeAttribute(HTML5.ID_ATTR, component.getClientId(facesContext), null);

        final Double value = component.getValue();
        if(value!= null && value < 0){
            throw new FacesException("Value of progress component cannot be negative, but " + value);
        }

        final Double maximum = component.getMaximum();
        if(maximum!=null && maximum < 0){
            throw new FacesException("Maximum value of progress component cannot be negative, but " + maximum);
        }

        if(value!=null && maximum!=null){
            if(value > maximum){
                throw new FacesException("Value of progress component cannot be greater than the maximum value. But " + value + " > " + maximum);
            }
        }


        renderPassThruAttrsAndEvents(facesContext, uiComponent);
    }

    // to make this extendible
    protected void renderPassThruAttrsAndEvents(FacesContext facesContext, UIComponent uiComponent) throws IOException
    {
        Map<String, List<ClientBehavior>> clientBehaviors = ((ClientBehaviorHolder) uiComponent).getClientBehaviors();

        Html5RendererUtils.renderPassThroughClientBehaviorEventHandlers(facesContext, uiComponent,
                PassThroughClientBehaviorEvents.PROGRESS, clientBehaviors);

        Html5RendererUtils.renderPassThroughAttributes(facesContext.getResponseWriter(), uiComponent,
                PassThroughAttributes.PROGRESS);
    }

    @Override
    public void encodeEnd(FacesContext facesContext, UIComponent component) throws IOException
    {
        if (log.isLoggable(Level.FINE))
            log.fine("encodeEnd");
        // just close the element
        super.encodeEnd(facesContext, component);

        ResponseWriter writer = facesContext.getResponseWriter();

        writer.endElement("progress");
    }
}
