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
 
package java.util;

import org.jnode.vm.VmSystem;

/**
 * @author Levente S\u00e1ntha
 */
class NativeResourceBundle {
    
    static Class<?>[] getClassContext(){
        //skip the call to VmSystem.getRealClassContext() 
        Class<?>[] context = VmSystem.getRealClassContext();
        Class<?>[] ret = new Class[context.length - 2];
        System.arraycopy(context, 2, ret, 0, ret.length);

        return ret;
    }
}
