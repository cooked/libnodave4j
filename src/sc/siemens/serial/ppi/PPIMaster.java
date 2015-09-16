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
package sc.siemens.serial.ppi;

import sc.siemens.exception.S7InitException;
import sc.siemens.serial.SerialMaster;

import com.serotonin.io.serial.SerialParameters;
import com.serotonin.messaging.MessageControl;

abstract public class PPIMaster extends SerialMaster {
    // Runtime fields.
    private MessageControl conn;

    public PPIMaster(SerialParameters params) {
        super(params);
    }

    @Override
    public void init() throws S7InitException {
        super.init();

        //RtuMessageParser rtuMessageParser = new RtuMessageParser(true);
        //conn = getMessageControl();
        //try {
            //conn.start(transport, rtuMessageParser, null);
            //transport.start("Modbus RTU master");
        //} catch (IOException e) {
        //    throw new S7InitException(e);
        //}
        initialized = true;
    }

    @Override
    public void destroy() {
        closeMessageControl(conn);
        super.close();
    }

    // TODO !!!! MAKE THIS CLASS NOT ABSTRACT !!!!
    
    /*@Override
    public S7Response send(S7Request request) throws S7TransportException {
        // Wrap the modbus request in an rtu request.
        //RtuMessageRequest rtuRequest = new RtuMessageRequest(request);

        // Send the request to get the response.
        //RtuMessageResponse rtuResponse;
        try {
            //rtuResponse = (RtuMessageResponse) conn.send(rtuRequest);
            //if (rtuResponse == null)
            //    return null;
            //return rtuResponse.getModbusResponse();
        } catch (Exception e) {
           
            throw new S7TransportException(e, request.getSlaveId());
        }
    }*/
    
}
