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

package org.apache.myfaces.html5.renderkit.output;

import org.apache.myfaces.html5.component.output.SlideView;
import org.apache.myfaces.html5.test.AbstractHtml5ViewControllerTestCase;
import org.apache.myfaces.html5.test.HtmlCheckAttributesUtil;
import org.apache.myfaces.html5.test.HtmlRenderedAttr;

/**
 * @author Ali Ok (aliok@apache.org)
 */
public class SlideViewRendererTest extends AbstractHtml5ViewControllerTestCase {

    private SlideView slideView;

    public SlideViewRendererTest(String name) {
        super(name);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        this.slideView = new SlideView();
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
            new HtmlRenderedAttr("hidden", true, "hidden=\"true\""),
        };

        HtmlCheckAttributesUtil.checkRenderedAttributes(slideView, facesContext, writer, attrs);
        if(HtmlCheckAttributesUtil.hasFailedAttrRender(attrs))
        {
            fail(HtmlCheckAttributesUtil.constructErrorMessage(attrs, writer.getWriter().toString()));
        }
    }
}
