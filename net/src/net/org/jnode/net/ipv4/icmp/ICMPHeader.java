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
 
package org.jnode.net.ipv4.icmp;

import org.jnode.net.SocketBuffer;
import org.jnode.net.TransportLayerHeader;
import org.jnode.net.ipv4.IPv4Header;
import org.jnode.net.ipv4.IPv4Utils;

/**
 * @author epr
 */
public abstract class ICMPHeader implements TransportLayerHeader, ICMPConstants {

    private final ICMPType type;
    private final int code;
    private final boolean checksumOk;

    /**
     * Create a new instance
     * 
     * @param type
     * @param code
     */
    public ICMPHeader(ICMPType type, int code) {
        if (code < 0) {
            throw new IllegalArgumentException("Invalid code " + code);
        }
        this.type = type;
        this.code = code;
        this.checksumOk = true;
    }

    /**
     * Read an ICMP header for the given buffer
     * 
     * @param skbuf
     */
    public ICMPHeader(SocketBuffer skbuf) {
        this.type = ICMPType.getType(skbuf.get(0));
        this.code = skbuf.get(1);
        final int dataLength = ((IPv4Header) skbuf.getNetworkLayerHeader()).getDataLength();
        final int ccs = IPv4Utils.calcChecksum(skbuf, 0, dataLength);
        this.checksumOk = (ccs == 0);
    }

    /**
     * @see org.jnode.net.LayerHeader#prefixTo(org.jnode.net.SocketBuffer)
     */
    public void prefixTo(SocketBuffer skbuf) {
        skbuf.insert(getLength());
        skbuf.set(0, type.getId());
        skbuf.set(1, code);
        skbuf.set16(2, 0); // Checksum, overwritten later
        doPrefixTo(skbuf);
        final int dataLength = skbuf.getSize();
        final int ccs = IPv4Utils.calcChecksum(skbuf, 0, dataLength);
        skbuf.set16(2, ccs);
    }

    /**
     * Finalize the header in the given buffer. This method is called when all
     * layers have set their header data and can be used e.g. to update checksum
     * values.
     * 
     * @param skbuf The buffer
     * @param offset The offset to the first byte (in the buffer) of this header
     *            (since low layer headers are already prefixed)
     */
    public void finalizeHeader(SocketBuffer skbuf, int offset) {
        // Do nothing
    }

    /**
     * Do the header specific prefixing.
     */
    protected abstract void doPrefixTo(SocketBuffer skbuf);

    /**
     * Is the checksum valid?
     */
    public boolean isChecksumOk() {
        return checksumOk;
    }

    /**
     * Gets the code field
     */
    public int getCode() {
        return code;
    }

    /**
     * Gets the type field
     */
    public ICMPType getType() {
        return type;
    }

}
