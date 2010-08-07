package org.apache.myfaces.html5.examples.showcase.validator;

import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.apache.myfaces.html5.component.api.validation.ClientSidePatternProvider;

@FacesValidator(value="partNumberValidator")
public class PartNumberValidator implements Validator, ClientSidePatternProvider
{
    private static final String PATTERN = "[0-9][A-Z]{3}";

    public String getPattern()
    {
        return PATTERN;
    }

    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException
    {
        if(value == null)
            return;
        if (value instanceof String)
        {
            String partNum = (String) value;
            
            if(! Pattern.matches(PATTERN, partNum)){
                throw new ValidatorException(new FacesMessage("Provided value is not a part number"));
            }
        }
    }
}
