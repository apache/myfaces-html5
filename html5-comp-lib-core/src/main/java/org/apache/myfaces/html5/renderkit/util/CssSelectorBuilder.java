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

package org.apache.myfaces.html5.renderkit.util;

import org.apache.commons.lang.StringUtils;

public class CssSelectorBuilder {
    private CssSelectorBuilderElement cssSelectorBuilderElement;
    private CssSelectorBuilderElement child;

    public static CssSelectorBuilderElement selector(String element){
        return new CssSelectorBuilder().createElement(element);
    }

    private CssSelectorBuilderElement createElement(String element){
        this.cssSelectorBuilderElement = new CssSelectorBuilderElement(this,element);
        return cssSelectorBuilderElement;
    }

    private void setChild(CssSelectorBuilderElement cssSelectorBuilderElement) {
        if(this.child != null)
            throw new IllegalStateException("Child is already set");

        this.child = cssSelectorBuilderElement;
    }

    public String build() {
        return this.cssSelectorBuilderElement.build() + " " + this.child.build();
    }

    public class CssSelectorBuilderElement{
        private String element;
        private String id;
        private String clazz;

        private CssSelectorBuilder parent;

        public CssSelectorBuilderElement(CssSelectorBuilder parent ,String element) {
            this.parent = parent;
            this.element = element;
        }

        public CssSelectorBuilderElement id(String id){
            this.id = id;
            return this;
        }

        public CssSelectorBuilderElement clazz(String clazz){
            this.clazz = clazz;
            return this;
        }

        public CssSelectorBuilder child(CssSelectorBuilderElement cssSelectorBuilderElement) {
            this.parent.setChild(cssSelectorBuilderElement);
            return this.parent;
        }

        private String build() {
            StringBuilder builder = new StringBuilder();

            builder.append(element);

            if(StringUtils.isNotBlank(id))
                builder.append('#').append(id);

            if(StringUtils.isNotBlank(clazz))
                builder.append('.').append(clazz);

            return builder.toString();
        }
    }
}
