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
import sc.siemens.code.AreaCode;
import sc.siemens.exception.S7TransportException;


public class ReadDBAreaRequest extends ReadNumericRequest {
	
	private byte areaNr;
		
    public ReadDBAreaRequest(int slaveId, byte areaNr, int startOffset, int byteCount)
            throws S7TransportException {
        super(slaveId, startOffset, byteCount);
        this.areaNr = areaNr;
    }

    ReadDBAreaRequest(int slaveId) throws S7TransportException {
        super(slaveId);
    }

    @Override
    public int getArea() {
        return AreaCode.DB;
    }
    
    @Override
    public byte getAreaNr() {
        return areaNr;
    }
        
    /*@Override
    S7Response handleImpl(ProcessImage processImage) throws S7TransportException {
        return new ReadDBAreaResponse(slaveId, getData(processImage));
    }*/

    @Override
    protected short getNumeric(ProcessImage processImage, int index) throws S7TransportException {
        return processImage.getHoldingRegister(index);
    }

    @Override
    S7Response getResponseInstance(int slaveId) throws S7TransportException {
        return new ReadDBAreaResponse(slaveId);
    }

}
