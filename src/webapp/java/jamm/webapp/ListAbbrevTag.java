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

import java.io.IOException;

import java.util.List;
import java.util.Iterator;

import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspException;

/**
 * Abbreviates a list and ends it with ellipses.
 *
 * @jsp:tag name="list-abbrev"
 */
public class ListAbbrevTag extends TagSupport
{
    /**
     * Creates a new <code>ListAbbrevTag</code> instance.
     */
    public ListAbbrevTag()
    {
        super();
        setLimit(3);
    }
    
    /**
     * How many of the list should we show before the ellipses?
     * Defaults to 3 in the constructor.
     *
     * @param limit the upper bounds on the number to show
     *
     * @jsp:attribute required="false" rtexprvalue="true"
     *                description="The upper limit of items to show"
     */
    public void setLimit(int limit)
    {
        mLimit = limit;
    }

    /**
     * sets the list of Strings to abbrev
     *
     * @param list the list to abbrev
     *
     * @jsp:attribute required="true" rtexprvalue="true"
     *                description="The list to abbrev"
     */
    public void setList(List list)
    {
        mList = list;
    }

    /**
     * Renders the list as asked.
     *
     * @return what to do after tag is started
     * @exception JspException if an error occurs
     */
    public int doStartTag()
        throws JspException
    {
        JspWriter out = pageContext.getOut();
        Iterator i = mList.iterator();
        boolean firstDone = false;
        try
        {
            for (int count = 0; i.hasNext() && count < mLimit; count++)
            {
                if (firstDone)
                {
                    out.print(", ");
                }
                else
                {
                    firstDone = true;
                }
                String item = (String) i.next();
                out.print(item);
            }

            if (mList.size() > mLimit)
            {
                out.print(", ...");
            }
        }
        catch (IOException e)
        {
            throw new JspException(e);
        }

        return SKIP_BODY;
    }

    /** The upper limit to cutoff at */
    private int mLimit;
    /** The list to cut off */
    private List mList;
}
