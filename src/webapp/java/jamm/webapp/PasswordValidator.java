package jamm.webapp;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

public class PasswordValidator
{
    public static boolean validatePassword(String password,
                                           String retypedPassword,
                                           ActionErrors errors)
    {
        if ((password != null) && (password.equals(retypedPassword)))
        {
            return validatePassword(password, errors);
        }
        else
        {
            errors.add("password",
                       new ActionError("password.error.do_not_match"));
            return false;
        }
    }

    public static boolean validatePassword(String password,
                                           ActionErrors errors)
    {
        if ((password == null) || (password.length() < MINIMUM_LENGTH))
        {
            errors.add("password",
                       new ActionError("password.error.too_short"));
            return false;
        }
        else
        {
            return true;
        }
    }

    private static final int MINIMUM_LENGTH = 5;
}
