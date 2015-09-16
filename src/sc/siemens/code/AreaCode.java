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
public class AreaCode {
	
	public static final int SYSINFO 		= Nodave.SYSINFO;			// System info of 200 family
	public static final int SYSTEMFLAGS 	= Nodave.SYSTEMFLAGS;		// System flags of 200 family
	public static final int ANALOGINPUTS200 = Nodave.ANALOGINPUTS200; 	// analog inputs of 200 family
	public static final int ANALOGOUTPUTS200= Nodave.ANALOGOUTPUTS200;	// analog outputs of 200 family
	public static final int P 				= Nodave.P;					//Peripheral I/O
    public static final int INPUT 			= Nodave.INPUTS;
    public static final int OUTPUT 			= Nodave.OUTPUTS;
    public static final int DB 				= Nodave.DB;
    public static final int DI 				= Nodave.DI;
    public static final int LOCAL 			= Nodave.LOCAL; 			//not tested
	public static final int V 				= Nodave.V; 				// local of caller
    public static final int FLAG 			= Nodave.FLAGS;
    public static final int COUNTER 		= Nodave.COUNTER;
    public static final int TIMER 			= Nodave.TIMER;
    public static final int COUNTER200 		= Nodave.COUNTER200;
    public static final int TIMER200 		= Nodave.TIMER200;
    
    public static String toString(byte code) {
        return Integer.toString(code & 0xff);
    }
}
