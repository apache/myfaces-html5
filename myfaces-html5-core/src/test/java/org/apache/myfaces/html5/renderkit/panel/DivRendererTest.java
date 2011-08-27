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
package org.apache.myfaces.html5.renderkit.panel;

import junit.framework.Test;
import junit.framework.TestSuite;
import org.apache.myfaces.html5.component.panel.Div;
import org.apache.myfaces.html5.test.AbstractHtml5ViewControllerTestCase;
import org.apache.myfaces.html5.test.HtmlCheckAttributesUtil;
import org.apache.myfaces.html5.test.HtmlRenderedAttr;


public class DivRendererTest extends AbstractHtml5ViewControllerTestCase {

    private Div div;

    public DivRendererTest(String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(DivRendererTest.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        div = new Div();
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
        div = null;
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
            //Html5 Global Props
            new HtmlRenderedAttr("draggable", true, "draggable=\"true\""),
            new HtmlRenderedAttr("hidden", true, "hidden=\"true\"")
        };

        HtmlCheckAttributesUtil.checkRenderedAttributes(div, facesContext, writer, attrs);
        if(HtmlCheckAttributesUtil.hasFailedAttrRender(attrs))
        {
            fail(HtmlCheckAttributesUtil.constructErrorMessage(attrs, writer.getWriter().toString()));
        }
    }

}
