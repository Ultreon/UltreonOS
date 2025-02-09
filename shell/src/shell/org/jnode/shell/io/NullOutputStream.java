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
 
package org.jnode.shell.io;

import java.io.IOException;
import java.io.OutputStream;

/**
 * This class is the equivalent of "/dev/null" for output.  All writes
 * are silently thrown away.
 * 
 * @author crawley@jnode.org
 */
public class NullOutputStream extends OutputStream {

    @Override
    public void write(int b) throws IOException {
        // black-hole all output.
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        // black-hole all output.
    }

    @Override
    public void write(byte[] b) throws IOException {
        // black-hole all output.
    }

    
}
