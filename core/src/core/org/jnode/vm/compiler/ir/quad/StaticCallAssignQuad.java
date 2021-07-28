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
 
package org.jnode.vm.compiler.ir.quad;

import org.jnode.vm.classmgr.VmConstMethodRef;
import org.jnode.vm.compiler.ir.CodeGenerator;
import org.jnode.vm.compiler.ir.IRBasicBlock;

/**
 * @author Levente S\u00e1ntha
 */
public class StaticCallAssignQuad<T> extends CallAssignQuad<T> {

    public StaticCallAssignQuad(int address, IRBasicBlock<T> block, int lhsIndex, VmConstMethodRef methodRef,
                                int[] offs) {
        super(address, block, lhsIndex, methodRef, offs);
        getLHS().setTypeFromJvmType(methodRef.getResolvedVmMethod().getReturnType().getJvmType());
    }

    @Override
    public void generateCode(CodeGenerator<T> cg) {
        cg.generateCodeFor(this);
    }

    @Override
    public String toString() {
        String s = getAddress() + ": " + getLHS() + " = " + methodRef.getClassName() + "." + methodRef.getName() + '(';
        for (int i = 0; i < refs.length; i++) {
            if (i > 0) {
                s += ", ";
            }
            s += refs[i];
        }
        s += ')';
        return s;
    }
}
