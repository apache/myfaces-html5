package org.apache.myfaces.html5.renderkit.media;

import org.apache.myfaces.html5.component.media.Video;
import org.apache.myfaces.html5.test.AbstractHtml5ViewControllerTestCase;
import org.apache.myfaces.html5.test.HtmlCheckAttributesUtil;
import org.apache.myfaces.html5.test.HtmlRenderedAttr;

/**
 * @author Ali Ok (aliok@apache.org)
 */
public class VideoRendererTest extends AbstractHtml5ViewControllerTestCase {

    private Video video;

    public VideoRendererTest(String name) {
        super(name);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        video = new Video();
    }

    public void testPassThruHtmlProperties() throws Exception
    {
        HtmlRenderedAttr[] attrs = {
            //Global Props
            new HtmlRenderedAttr("dir"),
            new HtmlRenderedAttr("lang"),
            new HtmlRenderedAttr("style"),
            new HtmlRenderedAttr("styleClass", "styleClass", "class=\"styleClass\""),
            new HtmlRenderedAttr("title"),
            new HtmlRenderedAttr("accesskey"),
            new HtmlRenderedAttr("tabindex"),
            //audio
            new HtmlRenderedAttr("preload"),
            new HtmlRenderedAttr("showControls", true, "controls=\"true\""),
            new HtmlRenderedAttr("loop", true, "loop=\"true\""),
            new HtmlRenderedAttr("autoplay", true, "autoplay=\"true\""),
            new HtmlRenderedAttr("hidden", true, "hidden=\"true\""),
            //video
            new HtmlRenderedAttr("poster"),
            new HtmlRenderedAttr("width"),
            new HtmlRenderedAttr("height"),
        };

        HtmlCheckAttributesUtil.checkRenderedAttributes(video, facesContext, writer, attrs);
        if(HtmlCheckAttributesUtil.hasFailedAttrRender(attrs))
        {
            fail(HtmlCheckAttributesUtil.constructErrorMessage(attrs, writer.getWriter().toString()));
        }
    }

}
