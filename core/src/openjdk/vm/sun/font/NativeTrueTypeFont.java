/*
 * $Id$
 *
 * Copyright (C) 2020-2022 Ultreon Team
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation; either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public 
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; If not, write to the Free Software Foundation, Inc., 
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */
 
package sun.font;

import java.awt.geom.Point2D;

/**
 * @see sun.font.TrueTypeFont
 * @author Levente S\u00e1ntha
 */
class NativeTrueTypeFont {
    /**
     * @see sun.font.TrueTypeFont#createScaler(int, int, boolean, int[])
     */
    private static long createScaler(TrueTypeFont instance, int arg1, int arg2, boolean arg3, int[] arg4) {
        //todo implement it
        return 0;
    }
    /**
     * @see sun.font.TrueTypeFont#getGlyphPoint(long, int, int)
     */
    private static Point2D.Float getGlyphPoint(TrueTypeFont instance, long arg1, int arg2, int arg3) {
        //todo implement it
        return null;
    }
}
