package jamm.backend;

import jamm.util.ChainedException;

public class MailNotFoundException
    extends MailManagerException
{
    public MailNotFoundException()
    {
        super();
    }

    public MailNotFoundException(String message)
    {
        super(message);
    }

    public MailNotFoundException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public MailNotFoundException(Throwable cause)
    {
        super(cause);
    }
}
