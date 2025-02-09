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
 
package org.jnode.awt.swingpeers;

import java.awt.AWTEvent;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.peer.LightweightPeer;
import javax.swing.JComponent;

/**
 * @author Levente S\u00e1ntha
 */
final class SwingLightweightContainerPeer extends
    SwingContainerPeer<Container, SwingLightweightContainer> implements
    LightweightPeer {
    private Insets containerInsets;

    public SwingLightweightContainerPeer(SwingToolkit toolkit, Container component) {
        super(toolkit, component, new SwingLightweightContainer(component));
    }

    /**
     * @see java.awt.peer.ContainerPeer#getInsets()
     */
    public Insets getInsets() {
        if (containerInsets == null) {
            containerInsets = new Insets(0, 0, 0, 0);
        }
        return containerInsets;
    }

    public Graphics getGraphics() {
        return null;
    }

}

final class SwingLightweightContainer extends JComponent implements ISwingPeer<Container> {
    private static final long serialVersionUID = 1L;

    private final Container awtComponent;

    public SwingLightweightContainer(Container awtComponent) {
        this.awtComponent = awtComponent;
    }

    /**
     * @see org.jnode.awt.swingpeers.ISwingPeer#getAWTComponent()
     */
    public Container getAWTComponent() {
        return awtComponent;
    }

    /**
     * Pass an event onto the AWT component.
     *
     * @see java.awt.Component#processEvent(java.awt.AWTEvent)
     */
    protected final void processEvent(AWTEvent event) {
        awtComponent.dispatchEvent(SwingToolkit.convertEvent(event, awtComponent));
    }

    /**
     * Process an event within this swingpeer
     *
     * @param event
     */
    public final void processAWTEvent(AWTEvent event) {
        super.processEvent(event);
    }

    /**
     * @see org.jnode.awt.swingpeers.ISwingPeer#validatePeerOnly()
     */
    public final void validatePeerOnly() {
        super.validate();
    }
}
