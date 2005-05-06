/*
 * $Id$
 *
 * JNode.org
 * Copyright (C) 2005 JNode.org
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
 * along with this library; if not, write to the Free Software Foundation, 
 * Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA 
 */
 
package org.jnode.fs.service.def;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.jnode.driver.Device;
import org.jnode.fs.FSAccessRights;
import org.jnode.fs.FSDirectory;
import org.jnode.fs.FSEntry;
import org.jnode.fs.FSFile;
import org.jnode.fs.FileSystem;

/**
 * Virtual directory entry.
 * 
 * @author Ewout Prangsma (epr@users.sourceforge.net)
 */
final class VirtualDirEntry implements FSEntry, FSDirectory {
   
    /** The filesystem */
    private final VirtualFS fs;

    /** The last modification time of this entry */
    private long lastModified;

    /** The name of this entry */
    private final String name;

    /** My parent */
    private final FSDirectory parent;
    
    /** My entries */
    private final Map<String, FSEntry> entries;

    /**
     * Initialize this instance.
     * 
     * @param fs
     * @param name
     * @param parent
     * @throws IOException
     */
    VirtualDirEntry(VirtualFS fs, String name, VirtualDirEntry parent)
            throws IOException {
        this.fs = fs;
        this.lastModified = System.currentTimeMillis();
        this.name = name;
        this.parent = (parent != null) ? parent.getDirectory() : null;
        this.entries = new TreeMap<String, FSEntry>();
    }

    /**
     * @see org.jnode.fs.FSEntry#getAccessRights()
     */
    public FSAccessRights getAccessRights() throws IOException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see org.jnode.fs.FSEntry#getDirectory()
     */
    public FSDirectory getDirectory() throws IOException {
        return this;
    }

    /**
     * @see org.jnode.fs.FSEntry#getFile()
     */
    public FSFile getFile() throws IOException {
        throw new IOException("Not a file");
    }

    /**
     * @see org.jnode.fs.FSEntry#getLastModified()
     */
    public long getLastModified() throws IOException {
        return lastModified;
    }

    /**
     * @see org.jnode.fs.FSEntry#getName()
     */
    public String getName() {
        return name;
    }

    /**
     * @see org.jnode.fs.FSEntry#getParent()
     */
    public FSDirectory getParent() {
        return parent;
    }

    /**
     * @see org.jnode.fs.FSEntry#isDirectory()
     */
    public boolean isDirectory() {
        return true;
    }

    /**
     * @see org.jnode.fs.FSEntry#isDirty()
     */
    public boolean isDirty() throws IOException {
        return false;
    }

    /**
     * @see org.jnode.fs.FSEntry#isFile()
     */
    public boolean isFile() {
        return false;
    }

    /**
     * @see org.jnode.fs.FSEntry#setLastModified(long)
     */
    public void setLastModified(long lastModified) throws IOException {
        this.lastModified = lastModified;
    }

    /**
     * @see org.jnode.fs.FSEntry#setName(java.lang.String)
     */
    public void setName(String newName) throws IOException {
        throw new IOException("Cannot rename");
    }

    /**
     * @see org.jnode.fs.FSObject#getFileSystem()
     */
    public FileSystem getFileSystem() {
        return fs;
    }

    /**
     * @see org.jnode.fs.FSObject#isValid()
     */
    public boolean isValid() {
        return true;
    }

    /**
     * @see org.jnode.fs.FSDirectory#addDirectory(java.lang.String)
     */
    public synchronized FSEntry addDirectory(String name) throws IOException {
        VirtualFS.log.debug("addDirectory(" + name + ")");
        if (entries.containsKey(name)) {
            throw new IOException(name + " already exists");
        }
        final VirtualDirEntry entry = new VirtualDirEntry(fs, name, this);
        entries.put(name, entry);
        modified();
        return entry;
    }

    /**
     * Mount the path within given filesystem to an entry with the given name.
     * 
     * @param name The name of this mounted entry.
     * @param fs The filesystem to mount
     * @param path The path in the filesystem to use as root. 
     * @see org.jnode.fs.FSDirectory#addDirectory(java.lang.String)
     */
    public synchronized FSEntry addMount(String name, FileSystem fs, String path) throws IOException {
        VirtualFS.log.debug("addMount(" + name + "," + fs + "," + path + ")");
        if (entries.containsKey(name)) {
            throw new IOException(name + " already exists");
        }
        final VirtualMountEntry entry = new VirtualMountEntry(fs, path, name, this);
        entries.put(name, entry);
        modified();
        return entry;
    }

    /**
     * @see org.jnode.fs.FSDirectory#addFile(java.lang.String)
     */
    public FSEntry addFile(String name) throws IOException {
        throw new IOException("Not allowed");
    }

    /**
     * @see org.jnode.fs.FSDirectory#flush()
     */
    public void flush() throws IOException {
        // Nothing here
    }

    /**
     * @see org.jnode.fs.FSDirectory#getEntry(java.lang.String)
     */
    public synchronized FSEntry getEntry(String name) throws IOException {
        return entries.get(name);
    }

    /**
     * @see org.jnode.fs.FSDirectory#iterator()
     */
    public synchronized Iterator<FSEntry> iterator() throws IOException {
        return new EntryIterator(new ArrayList<FSEntry>(entries.values()));
    }

    /**
     * @see org.jnode.fs.FSDirectory#remove(java.lang.String)
     */
    public synchronized void remove(String name) {
        entries.remove(name);
        modified();
    }
    
    /**
     * Update the lastmodified flag.
     */
    private void modified() {
        this.lastModified = System.currentTimeMillis();
    }
        
    /**
     * The filesystem on the given device will be removed.
     * @param dev
     */
    final synchronized void unregisterFileSystem(Device dev) {
        // Make a clone of the entries.values, so we can remove entries
        // without a problem.
        final Collection<FSEntry> entries = new ArrayList<FSEntry>(this.entries.values());
        for (FSEntry entry : entries) {
            if (entry instanceof VirtualMountEntry) {
                final VirtualMountEntry vme = (VirtualMountEntry)entry;
                if (vme.getMountedFS().getDevice() == dev) {
                    remove(vme.getName());
                }
            } else if (entry instanceof VirtualDirEntry) {
                ((VirtualDirEntry)entry).unregisterFileSystem(dev);
            }
        }
    }
    
    /**
     * Iterator for entries.
     * 
     * @author Ewout Prangsma (epr@users.sourceforge.net)
     */
    private static final class EntryIterator implements Iterator<FSEntry> {
        private final Iterator<FSEntry> i;
        
        public EntryIterator(Collection<FSEntry> entries) {
            this.i = entries.iterator();
        }

        /**
         * @see org.jnode.fs.FSEntryIterator#hasNext()
         */
        public boolean hasNext() {
            return i.hasNext();
        }

        /**
         * @see org.jnode.fs.FSEntryIterator#next()
         */
        public FSEntry next() {
            return i.next();
        }
        
        /**
         * @see java.util.Iterator#remove()
         */
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
