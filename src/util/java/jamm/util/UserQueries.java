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

package jamm.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

/**
 * Helper functions to use when communicating with the user.
 */
public class UserQueries
{
    /**
     * Asks a yes/no question
     *
     * @param question the question to ask
     * @return true for "y" or "yes", false for anything else
     */
    public static boolean askYesNo(String question)
    {
        BufferedReader bin = new BufferedReader(
            new InputStreamReader(System.in));

        System.out.print(question);
        String input = "no";

        try
        {
            input = bin.readLine();
        }
        catch (IOException e)
        {
            System.out.println(e);
        }

        if (input.equalsIgnoreCase("y") || input.equalsIgnoreCase("yes"))
        {
            return true;
        }
        return false;
    }
}
