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
package sc.siemens.code;

import org.libnodave.Nodave;

/**
 * @author Matthew Lohbihler
 */
public class MemoryArea {
	
    public static final int INPUT 	= 1;
    public static final int OUTPUT 	= 2;
    public static final int FLAG 	= 3;
    public static final int DB 		= 4;
    public static final int DI 		= 5;
    public static final int COUNTER	= 6;
    public static final int TIMER	= 7;
    
    public static int getFrom(int id) {
        switch (id) {
	        case INPUT:
	            return Nodave.INPUTS;
	        case OUTPUT:
	            return Nodave.OUTPUTS;
	        case FLAG:
	            return Nodave.FLAGS;
	        case DB:
	            return Nodave.DB;
	        case DI:
	            return Nodave.DI;
	        case COUNTER:
	            return Nodave.COUNTER;
	        case TIMER:
	            return Nodave.TIMER;
        }
        return -1;
    }

    /*public static int getTo(int id) {
        switch (id) {
        case COIL_STATUS:
            return 0xffff;
        case INPUT_STATUS:
            return 0x1ffff;
        case HOLDING_REGISTER:
            return 0x4ffff;
        case INPUT_REGISTER:
            return 0x3ffff;
        }
        return -1;
    }*/

    public static int getReadAreaCode(int id) {
        switch (id) {
        case INPUT:
            return AreaCode.INPUT;
        case OUTPUT:
            return AreaCode.OUTPUT;
        case DB:
            return AreaCode.DB;
        case FLAG:
            return AreaCode.FLAG;
        }
        return -1;
    }

    public static int getMaxReadCount(int id) {
        switch (id) {
        	// TODO 
        	case INPUT:
        	case OUTPUT:
        	case DB:
        	case DI:
        	case FLAG:
        	case COUNTER:
        	case TIMER:
        		return Nodave.MAX_RAW_LEN; 	//ModbusUtils.MAX_READ_REGISTER_COUNT;
        }									//ModbusUtils.MAX_READ_BIT_COUNT;
        return -1;
    }
}
