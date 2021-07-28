/*
 * $Id$
 *
 * Copyright (C) 2003-2015 QTech Community
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
 
package org.jnode.vm.compiler;

import com.qtech.os.assembler.ObjectResolver;
import org.jnode.vm.classmgr.VmClassLoader;
import org.jnode.vm.objects.VmSystemObject;

/**
 * Class used to compile an IMT into a jump table suitable for a specific
 * architecture.
 *
 * @author Ewout Prangsma (epr@users.sourceforge.net)
 */
public abstract class IMTCompiler extends VmSystemObject {

    /**
     * Initialize this compiler
     *
     * @param loader
     */
    public abstract void initialize(VmClassLoader loader);

    /**
     * Compile the given IMT.
     *
     * @param resolver
     * @param imt
     * @param imtCollisions
     * @return the CompiledIMT instance
     */
    public abstract CompiledIMT compile(ObjectResolver resolver, Object[] imt, boolean[] imtCollisions);
}
