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
package sc.siemens.exception;

public class S7TransportException extends Exception {
    private static final long serialVersionUID = -1;

    private final int slaveId;

    public S7TransportException() {
        this.slaveId = -1;
    }

    public S7TransportException(int slaveId) {
        this.slaveId = slaveId;
    }

    public S7TransportException(String message, Throwable cause, int slaveId) {
        super(message, cause);
        this.slaveId = slaveId;
    }

    public S7TransportException(String message, int slaveId) {
        super(message);
        this.slaveId = slaveId;
    }

    public S7TransportException(String message) {
        super(message);
        this.slaveId = -1;
    }

    public S7TransportException(Throwable cause) {
        super(cause);
        this.slaveId = -1;
    }

    public S7TransportException(Throwable cause, int slaveId) {
        super(cause);
        this.slaveId = slaveId;
    }

    public int getSlaveId() {
        return slaveId;
    }
}
