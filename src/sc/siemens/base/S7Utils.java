package sc.siemens.base;

import org.libnodave.Nodave;
import org.libnodave.S7Connection;

import sc.siemens.code.DataType;
import sc.siemens.exception.IllegalSlaveIdException;
import sc.siemens.exception.S7IdException;
import sc.siemens.exception.S7TransportException;
import sc.siemens.locator.BinaryLocator;

import com.serotonin.util.queue.ByteQueue;

public class S7Utils {
	
	public static final int TCP_PORT = 102;
	
	public static boolean isDebug() {
		return Nodave.Debug==0?false:true;
	}
	
	public static void pushShort(ByteQueue queue, int value) {
        queue.push((byte) (0xff & (value >> 8)));
        queue.push((byte) (0xff & value));
    }
	
	public static void pushByte(ByteQueue queue, int value) {
        queue.push((byte) value);
    }
	
	public static short toShort(byte b1, byte b2) {
        return (short) ((b1 << 8) | (b2 & 0xff));
    }
	
	public static int popUnsignedShort(ByteQueue queue) {
        return ((queue.pop() & 0xff) << 8) | (queue.pop() & 0xff);
    }
	
	public static int popUnsignedByte(ByteQueue queue) {
        return queue.pop() & 0xff;
    }
	
	public static void validateBit(int bit) {
        if (bit < 0 || bit > 7)
            throw new S7IdException("Invalid bit: " + bit);
    }
	
	public static void validateOffset(int offset) throws S7TransportException {
        if (offset < 0 || offset > 255)
            throw new S7TransportException("Invalid offset: " + offset);
    }

    public static void validateEndOffset(int offset) throws S7TransportException {
        if (offset > 255)
            throw new S7TransportException("Invalid end offset: " + offset);
    }
    
    public static void validateSlaveId(int slaveId, boolean includeBroadcast) {
        if (slaveId < (includeBroadcast ? 0 : 1) || slaveId > 255)
            throw new IllegalSlaveIdException("Invalid slave id: " + slaveId);
    }
    
    public static Object rsDataTypetoValue(S7Connection dc, KeyedS7Locator<?> rvl) {
    	Object value;
    	switch(rvl.getDataType()) {
    		case DataType.BOOL: // TODO
    			value = dc.getBYTE();
    			value = ( (((Integer)value)>>((BinaryLocator)rvl.getLocator()).getBit())&0x01 )==1?true:false; 	break;
    		case DataType.BYTE_HEX:
    			value = dc.getBYTE(); 	break;
    		case DataType.REAL:
    			value = dc.getFloat(); 	break;
    		case DataType.INT_INT_S:
    			value = dc.getINT();	break;
    		case DataType.DINT_INT_S:
    			value = dc.getDINT();	break;
    		case DataType.WORD_INT_U:
    			value = dc.getWORD();	break;
    		case DataType.CHAR:	// TODO 
    			value = String.valueOf((char)dc.getCHAR());
    		default:
    			value = dc.getWORD();
    	}
    	return value;
    }
}
