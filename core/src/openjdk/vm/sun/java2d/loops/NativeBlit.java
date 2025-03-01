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
 
package sun.java2d.loops;

import sun.java2d.SurfaceData;
import sun.java2d.pipe.Region;
import java.awt.*;

/**
 * @author Levente S\u00e1ntha
 */
class NativeBlit {
    /**
     * @see sun.java2d.loops.Blit#Blit(sun.java2d.SurfaceData, sun.java2d.SurfaceData, java.awt.Composite, sun.java2d.pipe.Region, int, int, int, int, int, int)
     */
    private static void Blit(Blit instance, SurfaceData src, SurfaceData dst,
                Composite comp, Region clip,
                int srcx, int srcy,
                int dstx, int dsty,
                int width, int height){
        //todo implement it
    }
}
