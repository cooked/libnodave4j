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

/**
 * @author Stefano Cottafavi
 */
public class DataType {
	
    /*public static final int BINARY = 1;
    public static final int TWO_BYTE_INT_UNSIGNED = 2;
    public static final int TWO_BYTE_INT_SIGNED = 3;
    public static final int FOUR_BYTE_INT_UNSIGNED = 4;
    public static final int FOUR_BYTE_INT_SIGNED = 5;
    public static final int FOUR_BYTE_INT_UNSIGNED_SWAPPED = 6;
    public static final int FOUR_BYTE_INT_SIGNED_SWAPPED = 7;
    public static final int FOUR_BYTE_FLOAT = 8;
    public static final int FOUR_BYTE_FLOAT_SWAPPED = 9;
    public static final int EIGHT_BYTE_INT_UNSIGNED = 10;
    public static final int EIGHT_BYTE_INT_SIGNED = 11;
    public static final int EIGHT_BYTE_INT_UNSIGNED_SWAPPED = 12;
    public static final int EIGHT_BYTE_INT_SIGNED_SWAPPED = 13;
    public static final int EIGHT_BYTE_FLOAT = 14;
    public static final int EIGHT_BYTE_FLOAT_SWAPPED = 15;
    public static final int TWO_BYTE_BCD = 16;
    public static final int FOUR_BYTE_BCD = 17;
    public static final int CHAR = 18;
    public static final int VARCHAR = 19;*/

	public static final int BOOL		= 1;
	
	public static final int BYTE_HEX 	= 2;	
	
	public static final int WORD_BIN 	= 3;
	public static final int WORD_HEX	= 4;
	public static final int WORD_BCD	= 5;
	public static final int WORD_INT_U	= 6;
	
	public static final int DWORD_BIN	= 7;
    public static final int DWORD_HEX 	= 8;
    public static final int DWORD_INT_U = 9;
    
    public static final int INT_INT_S 	= 10;
    public static final int DINT_INT_S 	= 11;
    
    public static final int REAL		= 12;
    
    public static final int S5TIME		= 13;
    public static final int TIME		= 14;
    public static final int DATE		= 15;
    public static final int TIME_OF_DAY	= 16;
    
    public static final int CHAR		= 17;
    
    
    public static int getByteCount(int id) {
        switch (id) {
	        case BOOL:
	        case BYTE_HEX:
	        case CHAR:
	            return 1;
	        case WORD_BIN:
	        case WORD_HEX:
	        case WORD_BCD:
	        case WORD_INT_U:
	        case INT_INT_S:
	        case S5TIME:
	        case DATE:
	            return 2;
	        case DWORD_BIN:
	        case DWORD_HEX:
	        case DWORD_INT_U:
	        case DINT_INT_S:
	        case REAL:
	        case TIME:
	        case TIME_OF_DAY:
	            return 4;
	    }
        throw new RuntimeException("Unsupported data type: " + id);
    }

	public static Class<?> getJavaType(int id) {
		switch (id) {
			case BOOL:
				return Boolean.class;
			case BYTE_HEX:
			case CHAR:
				return Byte.class;
			case WORD_BIN:
			case WORD_HEX:
			case WORD_BCD:
			case WORD_INT_U:
			case INT_INT_S:
			case S5TIME:
			case DATE:
				return Short.class;
			case DWORD_INT_U:	
			case DINT_INT_S:
			case TIME:
				return Integer.class;
			case REAL:
				return Float.class;
		}
		return null;
	}
}
