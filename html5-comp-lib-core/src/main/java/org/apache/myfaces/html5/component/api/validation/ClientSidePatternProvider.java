package org.apache.myfaces.html5.component.api.validation;

/**
 * Provides regex pattern to use it on client-side.
 * <br>
 * Server-side validation against the pattern is responsibility of implementing JSF validator/converter.
 * @author Ali Ok
 *
 */
public interface ClientSidePatternProvider
{
    public String getPattern();

}
