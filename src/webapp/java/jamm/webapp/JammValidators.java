/*
 * Jamm
 * Copyright (C) 2002 Dave Dribin and Keith Garner
 *  
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package jamm.webapp;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.validator.Field;
import org.apache.commons.validator.GenericValidator;
import org.apache.commons.validator.ValidatorAction;
import org.apache.commons.validator.ValidatorUtil;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.validator.Resources;

/**
 * @author kgarner
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class JammValidators
{
    /**
     * Checks if a field is identical to a second field.  Useful for
     * cases where you are comparing passwords.  Based on work in the
     * <i>Struts in Action</i> published by Manning.
     *
     * @param bean The bean validation is being performed on.
     * @param va The ValidatorAction that is currently being performed.
     * @param field The Field object associated with the current field
     * being validated.
     * @param errors The ActionErrors object to add errors to if any
     * validation errors occur.
     * @param request Current request object.
     * @return True if valid or field blank, false otherwise.
     */
    public static boolean validateIdentical(Object bean, ValidatorAction va,
                                            Field field, ActionErrors errors,
                                            HttpServletRequest request)
    {
        String value = ValidatorUtil.getValueAsString(bean,
                                                      field.getProperty());
        String sProperty2 = field.getVarValue("secondProperty");
        String value2 = ValidatorUtil.getValueAsString(bean, sProperty2);

        if (!GenericValidator.isBlankOrNull(value))
        {
            if (!value.equals(value2))
            {
                errors.add(field.getKey(),
                           Resources.getActionError(request, va, field));
                return false;
            }
        }
        return true;
    }
}
