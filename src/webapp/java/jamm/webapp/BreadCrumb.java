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

import java.io.Serializable;

/**
 * An immutable bean to hold one item in a list of bread crumbs.
 * Bread crumbs are the trail of pages leading to the current page.
 * Each bread crumb is a link to a page higher up in the navigation
 * hierarchy.  This is a primary source of navigation for the user.
 */
public class BreadCrumb implements Serializable
{
    /**
     * Create a new bread crumb item with the specified hyperlink and
     * text.
     *
     * @param link Hyperlink for this bread crumb
     * @param text Test for this bread crumb
     */
    public BreadCrumb(String link, String text)
    {
        mLink = link;
        mText = text;
    }

    /**
     * Returns the text of this bread crumb.
     *
     * @return The text of this bread crumb
     */
    public String getText()
    {
        return mText;
    }

    /**
     * Returns the hyperlink for this bread crumb.
     *
     * @return The hyperlink for this bread crumb
     */
    public String getLink()
    {
        return mLink;
    }

    /** Text of this bread crumb.*/
    private String mText;

    /** Hyperlink of this bread crumb. */
    private String mLink;
}
