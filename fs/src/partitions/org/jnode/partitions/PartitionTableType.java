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
 
package org.jnode.partitions;

import org.jnode.driver.Device;
import org.jnode.driver.block.BlockDeviceAPI;

/**
 * @author Ewout Prangsma (epr@users.sourceforge.net)
 */
public interface PartitionTableType {

    /**
     * Gets the unique name of this partition table type.
     */
    public String getName();

    /**
     * Can this partition table type be used on the given first sector of a
     * blockdevice?
     * 
     * @param devApi
     * @param firstSectors
     */
    public boolean supports(byte[] firstSectors, BlockDeviceAPI devApi);

    /**
     * Create a partition table for a given device.
     * 
     * @param device
     * @param firstSectors
     */
    public PartitionTable<?> create(byte[] firstSectors, Device device) throws PartitionTableException;

}
