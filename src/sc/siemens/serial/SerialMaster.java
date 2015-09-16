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
package sc.siemens.serial;

import gnu.io.SerialPort;

import sc.siemens.S7Master;
import sc.siemens.exception.S7InitException;

import com.serotonin.io.serial.SerialParameters;
import com.serotonin.io.serial.SerialUtils;
import com.serotonin.messaging.StreamTransport;

abstract public class SerialMaster extends S7Master {
    
    // Configuration fields.
    private final SerialParameters serialParameters;

    // Runtime fields.
    protected SerialPort serialPort;
    protected StreamTransport transport;

    public SerialMaster(SerialParameters params) {
        serialParameters = params;
    }

    @Override
    public void init() throws S7InitException {
        try {
            serialPort = SerialUtils.openSerialPort(serialParameters);
            transport = new StreamTransport(serialPort.getInputStream(), serialPort.getOutputStream());
        }
        catch (Exception e) {
            throw new S7InitException(e);
        }
    }

    public void close() {
        SerialUtils.close(serialPort);
    }
}
