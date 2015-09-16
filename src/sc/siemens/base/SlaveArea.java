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

public class SlaveArea {
	
    private final int slaveId;
    private final int area;
    private final byte areaNr;
    
    public SlaveArea(int slaveId, int area, byte areaNr) {
        
    	S7Utils.validateSlaveId(slaveId, true);

        this.slaveId 	= slaveId;
        this.area 		= area;
        this.areaNr 	= areaNr;
    }

    public int getSlaveId() {
        return slaveId;
    }
    
    public int getArea() {
        return area;
    }
    
    public byte getAreaNr() {
        return areaNr;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + slaveId;
        result = prime * result + area;
        result = prime * result + areaNr;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final SlaveArea other = (SlaveArea) obj;
        if (slaveId != other.slaveId)
            return false;
        if (area != other.area)
            return false;
        if (areaNr != other.areaNr)
            return false;
        return true;
    }
}
