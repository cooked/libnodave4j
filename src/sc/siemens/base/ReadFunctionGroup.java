/*
 * ============================================================================
 * GNU General Public License
 * ============================================================================
 *
 * Copyright (C) 2006-2011 Serotonin Software Technologies Inc. http://serotoninsoftware.com
 * @author Matthew Lohbihler
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package sc.siemens.base;

import java.util.ArrayList;
import java.util.List;

public class ReadFunctionGroup<K> {
    //private final SlaveAndArea slaveAndArea;
    private final int slaveId;
	private final int area;
    private final byte areaNr;
    private final List<KeyedS7Locator<K>> locators = new ArrayList<KeyedS7Locator<K>>();
    private int startOffset = 255;
    private int length = 0;

    public ReadFunctionGroup(KeyedS7Locator<K> locator) {
    	slaveId 	= locator.getSlaveId();
    	area 		= locator.getArea();
        areaNr 		= locator.getAreaNr();
        add(locator);
    }

    public void add(KeyedS7Locator<K> locator) {
        if (startOffset > locator.getOffset())
            startOffset = locator.getOffset();
        if (length < locator.getEndOffset() - startOffset + 1)
            length = locator.getEndOffset() - startOffset + 1;
        locators.add(locator);
    }

    public int getSlaveId() {
        return slaveId;
    }
    
    public int getStartOffset() {
        return startOffset;
    }

    public int getEndOffset() {
        return startOffset + length - 1;
    }

    public int getArea() {
        return area;
    }
    public byte getAreaNr() {
        return areaNr;
    }
    
    public int getLength() {
        return length;
    }

    public List<KeyedS7Locator<K>> getLocators() {
        return locators;
    }
}
