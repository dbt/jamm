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

package jamm.tools;

/**
 * Abstract base class for any cleaner class to employ.
 */
public abstract class AbstractCleaner
{
    /**
     * How long should an account be inactive before we nuke it.
     *
     * @param time delta for time in milliseconds
     */
    public void setCutOffTime(long time)
    {
        mCutOffTime = time;
    }

    /**
     * Return the cuttoff time in milliseconds
     *
     * @return a long containing the time
     */
    public long getCutOffTime()
    {
        return mCutOffTime;
    }

    /**
     * Cleaner objects should extend this object and override this method.
     */
    public abstract void cleanUp();

    /** The cutoff time */
    private long mCutOffTime;
}
