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
 
package org.jnode.fs.hfsplus.tree;

import org.jnode.util.BigEndian;

public class BTHeaderRecord {

    public static final int KEY_COMPARE_TYPE_CASE_FOLDING = 0xCF;
    /**
     * B-Tree was not closed correctly and need check for consistency.
     */
    public static final int BT_BAD_CLOSE_MASK = 0x00000001;
    public static final int BT_BIG_KEYS_MASK = 0x00000002;
    public static final int BT_VARIABLE_INDEX_KEYS_MASK = 0x00000004;

    public static final int BT_TYPE_HFS = 0;
    public static final int BT_TYPE_USER = 128;
    public static final int BT_TYPE_RESERVED = 256;

    public static final int BT_HEADER_RECORD_LENGTH = 106;
    /**
     * The depth of the current B-Tree.
     */
    private int treeDepth;
    /**
     * The root node number
     */
    private long rootNode;
    /**
     * The number of records contains in all leaf nodes.
     */
    private long leafRecords;
    /**
     * The number of the first leaf node. This may be zero.
     */
    private long firstLeafNode;
    /**
     * The number of the last leaf node. This may be zero.
     */
    private long lastLeafNode;
    /**
     * The size in bytes of a node.
     */
    private int nodeSize;
    /**
     * The maximum length of a key.
     */
    private int maxKeyLength;
    /**
     * The total number of free or used nodes in the B-Tree.
     */
    private long totalNodes;
    /**
     * The number of free node in the B-Tree.
     */
    private long freeNodes;
    /**
     * Ignore for HFS+, clumpSize field from {@code HFSPlusForkData} used
     * instead.
     */
    private long clumpSize;
    /**
     * The type of the B-Tree.
     */
    private int treeType;
    /**
     * Ignore in HFS+, should be threat as reserved.
     */
    private int keyCompareType;
    /**
     * Various attributes of the B-Tree.
     */
    private long attributes;

    public BTHeaderRecord(int treeDepth, int rootNode, int leafRecords, int firstLeafNode,
                          int lastLeafNode, int nodeSize, int maxKeyLength, int totalNodes, int freeNodes,
                          int clumpsize, int treeType, int keyCompareType, int attributes) {
        this.treeDepth = treeDepth;
        this.rootNode = rootNode;
        this.leafRecords = leafRecords;
        this.firstLeafNode = firstLeafNode;
        this.lastLeafNode = lastLeafNode;
        this.nodeSize = nodeSize;
        this.maxKeyLength = maxKeyLength;
        this.totalNodes = totalNodes;
        this.freeNodes = freeNodes;
        this.clumpSize = clumpsize;
        this.treeType = treeType;
        this.keyCompareType = keyCompareType;
        this.attributes = attributes;
    }

    public BTHeaderRecord(final byte[] src, int offset) {
        byte[] data = new byte[BT_HEADER_RECORD_LENGTH];
        System.arraycopy(src, offset, data, 0, BT_HEADER_RECORD_LENGTH);
        treeDepth = BigEndian.getUInt16(data, 0);
        rootNode = BigEndian.getUInt32(data, 2);
        leafRecords = BigEndian.getUInt32(data, 6);
        firstLeafNode = BigEndian.getUInt32(data, 10);
        lastLeafNode = BigEndian.getUInt32(data, 14);
        nodeSize = BigEndian.getUInt16(data, 18);
        maxKeyLength = BigEndian.getUInt16(data, 20);
        totalNodes = BigEndian.getUInt32(data, 22);
        freeNodes = BigEndian.getUInt32(data, 26);
        // UInt16 reserved1 - offset 30
        clumpSize = BigEndian.getUInt32(data, 32);
        treeType = BigEndian.getUInt8(data, 36);
        keyCompareType = BigEndian.getUInt8(data, 37);
        attributes = BigEndian.getUInt32(data, 38);
    }

    public byte[] getBytes() {
        byte[] data = new byte[BT_HEADER_RECORD_LENGTH];
        BigEndian.setInt16(data, 0, treeDepth);
        BigEndian.setInt32(data, 2, (int) rootNode);
        BigEndian.setInt32(data, 6, (int) leafRecords);
        BigEndian.setInt32(data, 10, (int) firstLeafNode);
        BigEndian.setInt32(data, 14, (int) lastLeafNode);
        BigEndian.setInt16(data, 18, nodeSize);
        BigEndian.setInt16(data, 20, maxKeyLength);
        BigEndian.setInt32(data, 22, (int) totalNodes);
        BigEndian.setInt32(data, 26, (int) freeNodes);
        BigEndian.setInt32(data, 32, (int) clumpSize);
        BigEndian.setInt8(data, 36, treeType);
        BigEndian.setInt8(data, 37, keyCompareType);
        BigEndian.setInt32(data, 38, (int) attributes);
        return data;
    }

    public final String toString() {
        return ("Root node:  " + getRootNode() + "\n" + "First leaf: " + getFirstLeafNode() + "\n" +
            "Last leaf:  " + getLastLeafNode() + "\n" + "node size:  " + getNodeSize() + "\n");
    }

    public int getTreeDepth() {
        return treeDepth;
    }

    public long getRootNode() {
        return rootNode;
    }

    public long getLeafRecords() {
        return leafRecords;
    }

    public long getFirstLeafNode() {
        return firstLeafNode;
    }

    public long getLastLeafNode() {
        return lastLeafNode;
    }

    public int getNodeSize() {
        return nodeSize;
    }

    public int getMaxKeyLength() {
        return maxKeyLength;
    }

    public long getTotalNodes() {
        return totalNodes;
    }

    public long getFreeNodes() {
        return freeNodes;
    }

    public long getClumpSize() {
        return clumpSize;
    }

    public int getTreeType() {
        return treeType;
    }

    public int getKeyCompareType() {
        return keyCompareType;
    }

    public long getAttributes() {
        return attributes;
    }
}
