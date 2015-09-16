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

import sc.siemens.ProcessImage;
import sc.siemens.exception.S7TransportException;

abstract public class ReadNumericRequest extends S7Request {

    private int startOffset;
    private int byteCount;

    public ReadNumericRequest(int slaveId, int startOffset, int byteCount) throws S7TransportException {
        super(slaveId);

        // Do some validation of the data provided.
        //S7Utils.validateOffset(startOffset);
        //S7Utils.validateNumberOfRegisters(byteCount);
        //S7Utils.validateEndOffset(startOffset + byteCount);
        
        this.startOffset 	= startOffset;
        this.byteCount 		= byteCount;
    }

    ReadNumericRequest(int slaveId) throws S7TransportException {
        super(slaveId);
    }

    @Override
	public int getStartOffset() {
		return startOffset;
	}
    

	public int getByteCount() {
		return byteCount;
	}
    
    /*@Override
    protected void writeRequest(ByteQueue queue) {
        S7Utils.pushShort(queue, startOffset);
        S7Utils.pushShort(queue, byteCount);
    }

    @Override
    protected void readRequest(ByteQueue queue) {
        startOffset = S7Utils.popUnsignedShort(queue);
        byteCount = S7Utils.popUnsignedShort(queue);
    }*/

    protected byte[] getData(ProcessImage processImage) throws S7TransportException {
        short[] data = new short[byteCount];

        // Get the data from the process image.
        for (int i = 0; i < byteCount; i++)
            data[i] = getNumeric(processImage, i + startOffset);

        return convertToBytes(data);
    }

    abstract protected short getNumeric(ProcessImage processImage, int index) throws S7TransportException;
}
