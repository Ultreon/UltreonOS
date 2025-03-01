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
 
package org.jnode.jnasm.assembler;

import java.util.List;

/**
 * @author Levente S\u00e1ntha (lsantha@users.sourceforge.net)
 */
public class Instruction {
    public static final int LOCK_PREFIX = 1;
    public static final int REP_PREFIX = 2;
    public static final int FS_PREFIX = 4;
    private int times;
    private int prefix;
    private int lineNumber;
    private String sizeInfo;
    private String label;
    private String mnemonic;
    private String jumpType;
    private List<Object> operands;

    public Instruction(String label) {
        this(label, null, null);
    }

    public Instruction(String mnemonic, List<Object> operands) {
        this(null, mnemonic, operands);
    }

    public Instruction(String label, String mnemonic, List<Object> operands) {
        this.label = label;
        this.mnemonic = mnemonic;
        this.operands = operands;
    }

    public String getMnemonic() {
        return mnemonic;
    }

    public List<Object> getOperands() {
        return operands;
    }

    public void setOperands(List<Object> operands) {
        this.operands = operands;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getSizeInfo() {
        return sizeInfo;
    }

    public void setSizeInfo(String sizeInfo) {
        this.sizeInfo = sizeInfo;
    }

    public int getPrefix() {
        return prefix;
    }

    @SuppressWarnings("unused")
    public void addPrefix(int prefix) {
        if (prefix == LOCK_PREFIX || prefix == REP_PREFIX || prefix == FS_PREFIX) {
            this.prefix |= prefix;
        } else {
            throw new RuntimeException("Invalid prefix: " + prefix);
        }
    }

    public void setPrefix(int prefix) {
        this.prefix = prefix;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public String getJumpType() {
        return jumpType;
    }

    public void setJumpType(String jumpType) {
        if (jumpType != null && jumpType.trim().length() > 0) {
            this.jumpType = jumpType.trim().toLowerCase();
        }
    }
}
