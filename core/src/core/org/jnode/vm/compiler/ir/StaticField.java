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
 
package org.jnode.vm.compiler.ir;

import org.jnode.vm.JvmType;
import org.jnode.vm.classmgr.VmConstFieldRef;

/**
 * @author Levente S\u00e1ntha
 */
public class StaticField extends Operand {
    private VmConstFieldRef filedRef;

    public StaticField(VmConstFieldRef filedRef) {
        super(JvmType.SignatureToType(filedRef.getSignature()));
        this.filedRef = filedRef;
    }

    public VmConstFieldRef getFiledRef() {
        return filedRef;
    }

    @Override
    public Operand simplify() {
        return null;
    }

    @Override
    public AddressingMode getAddressingMode() {
        return null;
    }

    @Override
    public String toString() {

        return filedRef.getClassName() + "." + filedRef.getName();
    }
}
