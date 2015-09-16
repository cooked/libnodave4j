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

import sc.siemens.base.S7Utils;
import sc.siemens.code.AreaCode;
import sc.siemens.exception.S7TransportException;


public class WriteFlagAreaRequest extends S7Request {
	
    private int 	startOffset;
    private int 	bit;
    private boolean value;
    

    public WriteFlagAreaRequest(int slaveId, byte areaNr, int startOffset, int bit, boolean value) throws S7TransportException {
        super(slaveId);

        // Do some validation of the data provided.
        S7Utils.validateOffset(startOffset);

        this.startOffset 	= startOffset;
        this.bit 			= bit;
        this.value			= value;
        
    }

    WriteFlagAreaRequest(int slaveId) throws S7TransportException {
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
		return startOffset;
	}
	
	public int getBit() {
		return bit;
	}
	
    @Override
    S7Response getResponseInstance(int slaveId) throws S7TransportException {
        return new WriteFlagAreaResponse(slaveId);
    }
    
    /*@Override
    protected void readRequest(ByteQueue queue) {
        writeOffset = S7Utils.popUnsignedShort(queue);
        writeValue = S7Utils.popUnsignedShort(queue) == 0xff00;
    }*/
    
    /*@Override
    protected void writeRequest(ByteQueue queue) {
    	S7Utils.pushShort(queue, writeOffset);
    	S7Utils.pushShort(queue, writeValue ? 0xff00 : 0);
    }

    @Override
    S7Response handleImpl(ProcessImage processImage) throws S7TransportException {
        processImage.writeCoil(writeOffset, writeValue);
        return new WriteOutputAreaResponse(slaveId, writeOffset, writeValue);
    }*/
	
}
