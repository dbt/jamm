package jamm.backend;

import jamm.util.ChainedException;

public class MailManagerException
    extends ChainedException
{
    public MailManagerException()
    {
        super();
    }

    public MailManagerException(String message)
    {
        super(message);
    }

    public MailManagerException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public MailManagerException(Throwable cause)
    {
        super(cause);
    }
}
