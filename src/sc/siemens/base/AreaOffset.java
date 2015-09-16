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

public class AreaOffset {
	
    private int area;
    private byte areaNr;
    private int offset;

    public AreaOffset(int area, byte areaNr, int offset) {
        this.area 	= area;
        this.areaNr = areaNr;
        this.offset = offset;
    }

    /**
     * This constructor provides a best guess at the function and offset the user wants, with the assumption that the
     * offset will never go over 9999.
     */
    /*public RangeAndOffset(int registerId) {
        /*if (registerId < 10000) {
            this.range = MemoryArea.COIL_STATUS;
            this.offset = registerId - 1;
        }
        else if (registerId < 20000) {
            this.range = MemoryArea.INPUT_STATUS;
            this.offset = registerId - 10001;
        }
        else if (registerId < 40000) {
            this.range = MemoryArea.INPUT_REGISTER;
            this.offset = registerId - 30001;
        }
        else {
            this.range = MemoryArea.DB;
            this.offset = registerId; // - 40001;
        }
    }*/

    public int getArea() {
        return area;
    }
    
    public byte getAreaNr() {
        return areaNr;
    }

    public int getOffset() {
        return offset;
    }
}
