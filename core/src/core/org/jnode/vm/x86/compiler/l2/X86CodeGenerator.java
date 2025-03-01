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
 
package org.jnode.vm.x86.compiler.l2;

import org.jnode.assembler.x86.X86Assembler;
import org.jnode.assembler.x86.X86Constants;
import org.jnode.assembler.x86.X86Register;
import org.jnode.vm.classmgr.VmMethod;
import org.jnode.vm.facade.TypeSizeInfo;

/**
 * @author Madhu Siddalingaiah
 * @author Levente S\u00e1ntha
 */
public class X86CodeGenerator extends GenericX86CodeGenerator<X86Register> implements
    X86Constants {

    /**
     * Initialize this instance
     */
    public X86CodeGenerator(VmMethod method, X86Assembler os, int length, TypeSizeInfo typeSizeInfo,
                            X86StackFrame stackFrame) {
        super(os, new X86RegisterPool(), length, typeSizeInfo, stackFrame, method);
    }
}
