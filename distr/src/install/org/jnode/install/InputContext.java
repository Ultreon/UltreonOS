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
 
package org.jnode.install;

import java.util.HashMap;

/**
 * @author Levente S\u00e1ntha
 */
public abstract class InputContext {
    private HashMap<String, Object> values = new HashMap<String, Object>();

    public abstract String getStringInput(String message);

    public void setStringValue(String key, String value) {
        values.put(key, value);
    }

    public String getStringValue(String key) {
        return (String) values.get(key);
    }
}
