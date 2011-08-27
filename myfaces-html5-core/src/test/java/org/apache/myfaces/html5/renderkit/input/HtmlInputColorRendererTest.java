package org.apache.myfaces.html5.renderkit.input;

import org.apache.myfaces.html5.component.input.HtmlInputColor;
import org.apache.myfaces.html5.test.AbstractHtml5ViewControllerTestCase;
import org.apache.myfaces.html5.test.HtmlCheckAttributesUtil;
import org.apache.myfaces.html5.test.HtmlRenderedAttr;

/**
 * @author Ali Ok (aliok@apache.org)
 */
public class HtmlInputColorRendererTest extends AbstractHtml5ViewControllerTestCase {

    private HtmlInputColor htmlInputColor;

    public HtmlInputColorRendererTest(String name) {
        super(name);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        htmlInputColor = new HtmlInputColor();
    }

    public void testPassThruHtmlProperties() throws Exception {
        HtmlRenderedAttr[] attrs = {
            //Html5 Global Props
            new HtmlRenderedAttr("draggable", true, "draggable=\"true\""),
            new HtmlRenderedAttr("hidden", true, "hidden=\"true\""),
            //Common input props
            new HtmlRenderedAttr("align"),
            new HtmlRenderedAttr("alt"),
            new HtmlRenderedAttr("maxlength", 3, "maxlength=\"3\""),
            new HtmlRenderedAttr("readonly", true, "readonly=\"true\""),
            new HtmlRenderedAttr("style"),
            new HtmlRenderedAttr("styleClass", "styleClass", "class=\"styleClass\""),
            new HtmlRenderedAttr("dir"),
            new HtmlRenderedAttr("lang"),
            new HtmlRenderedAttr("title"),
            new HtmlRenderedAttr("accesskey"),
            new HtmlRenderedAttr("tabindex"),
            new HtmlRenderedAttr("disabled", true, "disabled=\"true\""),
            //Input props
            new HtmlRenderedAttr("autofocus", true, "autofocus=\"true\""),
            new HtmlRenderedAttr("datalist", "datalist", "list=\"datalist\""),
        };

        HtmlCheckAttributesUtil.checkRenderedAttributes(htmlInputColor, facesContext, writer, attrs);
        if(HtmlCheckAttributesUtil.hasFailedAttrRender(attrs))
        {
            fail(HtmlCheckAttributesUtil.constructErrorMessage(attrs, writer.getWriter().toString()));
        }
    }


}
