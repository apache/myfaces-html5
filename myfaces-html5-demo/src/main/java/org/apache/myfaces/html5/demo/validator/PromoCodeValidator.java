/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.myfaces.html5.demo.validator;

import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.apache.myfaces.html5.component.api.validation.ClientSidePatternProvider;

@FacesValidator(value="promoCodeValidator")
public class PromoCodeValidator implements Validator, ClientSidePatternProvider
{

    private static final String PATTERN = "[0-9]{2}[A-Z]{2}";

    public String getPattern()
    {
        return "[0-9]{2}[A-Z]{2}";
    }

    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException
    {
        if(value == null)
            return;
        if (value instanceof String)
        {
            String partNum = (String) value;
            
            if(! Pattern.matches(PATTERN, partNum)){
                throw new ValidatorException(new FacesMessage("Provided value is not a promo code"));
            }
        }
    }
}
