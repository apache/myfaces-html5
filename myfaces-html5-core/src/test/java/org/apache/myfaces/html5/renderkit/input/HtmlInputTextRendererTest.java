package org.apache.myfaces.html5.renderkit.input;

import org.apache.myfaces.html5.component.input.HtmlInputText;
import org.apache.myfaces.html5.test.AbstractHtml5ViewControllerTestCase;
import org.apache.myfaces.html5.test.HtmlCheckAttributesUtil;
import org.apache.myfaces.html5.test.HtmlRenderedAttr;

/**
 * @author Ali Ok (aliok@apache.org)
 */
public class HtmlInputTextRendererTest extends AbstractHtml5ViewControllerTestCase {

    private HtmlInputText htmlInputText;

    public HtmlInputTextRendererTest(String name) {
        super(name);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        this.htmlInputText = new HtmlInputText();
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }

    public void testPassThruHtmlPropertiesForInputTextForTypeText() throws Exception
    {
        this.htmlInputText.setType("text");

        doTestForInputText();
    }

    public void testPassThruHtmlPropertiesForInputTextForTypeSearch() throws Exception
    {
        this.htmlInputText.setType("search");

        doTestForInputText();
    }

    public void testPassThruHtmlPropertiesForInputTextForTypeURL() throws Exception
    {
        this.htmlInputText.setType("url");

        doTestForInputText();
    }

    public void testPassThruHtmlPropertiesForInputTextForTypeTel() throws Exception
    {
        this.htmlInputText.setType("tel");

        doTestForInputText();
    }



    private void doTestForInputText() throws Exception {
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
            //text input props
            new HtmlRenderedAttr("placeholder"),
            new HtmlRenderedAttr("required", true, "required=\"true\""),
        };

        HtmlCheckAttributesUtil.checkRenderedAttributes(htmlInputText, facesContext, writer, attrs);
        if(HtmlCheckAttributesUtil.hasFailedAttrRender(attrs))
        {
            fail(HtmlCheckAttributesUtil.constructErrorMessage(attrs, writer.getWriter().toString()));
        }
    }

    public void testPassThruHtmlPropertiesForInputTextForTypeTextarea() throws Exception
    {
        this.htmlInputText.setType("textarea");

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
            //Textarea props
            new HtmlRenderedAttr("autofocus", true, "autofocus=\"true\""),
            new HtmlRenderedAttr("required", true, "required=\"true\""),
            new HtmlRenderedAttr("cols", 1, "cols=\"1\""),
            new HtmlRenderedAttr("rows", 1, "rows=\"1\""),
            new HtmlRenderedAttr("wrap"),

        };

        HtmlCheckAttributesUtil.checkRenderedAttributes(htmlInputText, facesContext, writer, attrs);
        if(HtmlCheckAttributesUtil.hasFailedAttrRender(attrs))
        {
            fail(HtmlCheckAttributesUtil.constructErrorMessage(attrs, writer.getWriter().toString()));
        }
    }

}
