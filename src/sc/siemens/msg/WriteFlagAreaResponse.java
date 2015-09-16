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

public class WriteFlagAreaResponse extends S7Response {
	
    private int writeOffset;
    private boolean writeValue;

    WriteFlagAreaResponse(int slaveId, int writeOffset, boolean writeValue) throws S7TransportException {
        super(slaveId);
        this.writeOffset = writeOffset;
        this.writeValue = writeValue;
    }
    
    public WriteFlagAreaResponse(int slaveId) throws S7TransportException {
        super(slaveId);
    }

    @Override
    public int getArea() {
        return AreaCode.FLAG;
    }
    
    @Override
	public byte getAreaNr() {
		return 0;
	}

	@Override
	public int getStartOffset() {
		return writeOffset;
	}
	
	public boolean isWriteValue() {
        return writeValue;
    }
	
    /*@Override
    protected void writeResponse(ByteQueue queue) {
        S7Utils.pushShort(queue, writeOffset);
        S7Utils.pushShort(queue, writeValue ? 0xff00 : 0);
    }

    @Override
    protected void readResponse(ByteQueue queue) {
        writeOffset = S7Utils.popUnsignedShort(queue);
        writeValue 	= S7Utils.popUnsignedShort(queue) == 0xff00;
    }*/

}
