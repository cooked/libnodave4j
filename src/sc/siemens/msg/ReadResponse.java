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

import org.libnodave.ResultSet;

import sc.siemens.exception.S7TransportException;

abstract public class ReadResponse extends S7Response {
	
    private byte[] data;
    private ResultSet rs;
    
    ReadResponse(int slaveId) throws S7TransportException {
        super(slaveId);
    }

    /*ReadResponse(int slaveId, byte[] data) throws S7TransportException {
        super(slaveId);
        this.data = data;
        
    }*/
    
    ReadResponse(int slaveId, ResultSet rs) throws S7TransportException {
        super(slaveId);
        this.rs = rs;
        
    }
    
    public ResultSet getResultSet() {
        return rs;
    }
    
    public short[] getShortData() {
        return convertToShorts(data);
    }

    public boolean[] getBooleanData() {
        return convertToBooleans(data);
    }
    
    /*@Override
    protected void readResponse(ByteQueue queue) {
        int numberOfBytes = S7Utils.popUnsignedByte(queue);
        if (queue.size() < numberOfBytes)
            throw new ArrayIndexOutOfBoundsException();

        data = new byte[numberOfBytes];
        queue.pop(data);
    }

    @Override
    protected void writeResponse(ByteQueue queue) {
    	S7Utils.pushByte(queue, data.length);
        queue.push(data);
    }

    /*public byte[] getData() {
        return data;
    }*/
    
    
}
