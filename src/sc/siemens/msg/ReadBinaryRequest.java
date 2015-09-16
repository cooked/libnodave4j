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

import sc.siemens.exception.S7TransportException;

abstract public class ReadBinaryRequest extends S7Request {
	
    private int startOffset;
    private int bit;

    public ReadBinaryRequest(int slaveId, int startOffset, int bit) throws S7TransportException {
        super(slaveId);

        // TODO // Do some validation of the data provided.
        //ProfiUtils.validateOffset(startOffset);
        //ProfiUtils.validateNumberOfBits(numberOfBits);
        //ProfiUtils.validateEndOffset(startOffset + numberOfBits);

        this.startOffset = startOffset;
        this.bit = bit;
    }

    ReadBinaryRequest(int slaveId) throws S7TransportException {
        super(slaveId);
    }

    public int getBit() {
		return bit;
    }
    
    @Override
	public int getStartOffset() {
		return startOffset;
	}
    
    /*@Override
    protected void writeRequest(ByteQueue queue) {
    	S7Utils.pushShort(queue, startOffset);
    	S7Utils.pushShort(queue, bitCount);
    }

    @Override
    protected void readRequest(ByteQueue queue) {
        startOffset = S7Utils.popUnsignedShort(queue);
        bitCount 	= S7Utils.popUnsignedShort(queue);
    }*/

    /*protected byte[] getData(ProcessImage processImage) throws S7TransportException {
        boolean[] data = new boolean[bitCount];

        // Get the data from the process image.
        for (int i = 0; i < bitCount; i++)
            data[i] = getBinary(processImage, i + startOffset);

        // Convert the boolean array into an array of bytes.
        return convertToBytes(data);
    }

    abstract protected boolean getBinary(ProcessImage processImage, int index) throws S7TransportException;
	*/
}
