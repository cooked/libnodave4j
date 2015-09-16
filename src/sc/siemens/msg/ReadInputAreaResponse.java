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
package sc.siemens.msg;

import sc.siemens.code.AreaCode;
import sc.siemens.exception.S7TransportException;

public class ReadInputAreaResponse extends ReadResponse {
	
    /*ReadInputAreaResponse(int slaveId, byte[] data) throws S7TransportException {
        super(slaveId, data);
    }*/

    ReadInputAreaResponse(int slaveId) throws S7TransportException {
        super(slaveId);
    }

    @Override
    public int getArea() {
        return AreaCode.INPUT;
    }

	@Override
	public byte getAreaNr() {
		return 0;
	}

	@Override
	public int getStartOffset() {
		// TODO Auto-generated method stub
		return 0;
	}
}
