package sc.siemens.locator;

import sc.siemens.base.S7Utils;
import sc.siemens.code.DataType;
import sc.siemens.code.MemoryArea;
import sc.siemens.exception.S7IdException;
import sc.siemens.exception.S7TransportException;

public abstract class BaseLocator<T> {
	
        private final int 		slaveId;
        protected final int 	area;
        protected final byte 	areaNr;
        protected final int 	offset;
        
        public static BaseLocator<Boolean> areaFlag(int slaveId, int offset, int bit) {
        	return new BinaryLocator(slaveId, MemoryArea.FLAG, (byte)0, offset, bit);
        }
      
        /*public static BaseLocator<Boolean> inputStatus(int slaveId, int offset) {
        	return new BinaryLocator(slaveId, Nodave.INPUTS, 0, offset);
        }*/
      
        public static BaseLocator<Number> areaDb(int slaveId, byte areaNr, int offset, int dataType) {
        	return new NumericLocator(slaveId, MemoryArea.DB, areaNr, offset, dataType);
        }
      
        /*public static BaseLocator<Boolean> inputRegisterBit(int slaveId, int offset, int bit) {
        	return new BinaryLocator(slaveId, 4, offset, bit);
        }*/
      
        /*      
        public static BaseLocator<Boolean> areaDbBit(int slaveId, byte areaNr, int offset) {
        	return new BinaryLocator(slaveId, MemoryArea.getReadAreaCode(AreaCode.DB), areaNr, offset);
        }*/
        
        
        /*public static BaseLocator<?> createLocator(int slaveId, int area, byte areaNr, int dataType, int bit) {
        	AreaAndOffset rao = new AreaAndOffset(area, areaNr, offset);
        	return createLocator(slaveId, rao.getArea(), rao.getAreaNr(), rao.getOffset(), dataType, bit);
        }*/
      
        /*public static BaseLocator<?> createLocator(int slaveId, int registerId, int dataType, int bit, int registerCount, Charset charset)
        {
        RangeAndOffset rao = new RangeAndOffset(registerId);
        return createLocator(slaveId, rao.getRange(), rao.getOffset(), dataType, bit, registerCount, charset);
        }*/
      
        /*public static BaseLocator<?> createLocator(int slaveId, int range, int offset, int dataType, int bit, int registerCount)
        {
        return createLocator(slaveId, range, offset, dataType, bit, registerCount, StringLocator.ASCII);
        }*/
      
        public static BaseLocator<?> createLocator(int slaveId, int area, byte areaNr, int offset, int dataType, int bit) {
        	if (dataType == DataType.BOOL) {
        		if(BinaryLocator.isBinaryArea(area))
        			return new BinaryLocator(slaveId, area, areaNr, offset, bit);
        		//return new BinaryLocator(slaveId, area, offset, bit);
        	}
        	//if ((dataType == 18) || (dataType == 19))
        		//return new StringLocator(slaveId, range, offset, dataType, registerCount, charset);
        		//return new StringLocator(slaveId, range, offset, dataType, registerCount, charset);
        	return new NumericLocator(slaveId, area, areaNr, offset, dataType);
        }
      
        public BaseLocator(int slaveId, int area, byte areaNr, int offset) {
        	this.slaveId = slaveId;
        	this.area = area;
        	this.areaNr = areaNr;
        	this.offset = offset;
        }
        
        
      
        protected void validate(int byteCount) {
        	try {
        		S7Utils.validateOffset(this.offset);
        		S7Utils.validateEndOffset(this.offset + byteCount - 1);
        	} catch (S7TransportException e) {
        		throw new S7IdException(e);
        	}
        }
      
        public abstract int getDataType();
       
        public abstract int getByteCount();
      
        public int getSlaveId() {
        	return this.slaveId;
        }
      
        public int getArea() {
        	return this.area;
        }
        
        public byte getAreaNr() {
        	return this.areaNr;
        }
      
        public int getOffset() {
        	return this.offset;
        }
      
        public int getEndOffset() {
        	return (this.offset + getByteCount() - 1);
        }
      
        /*public T bytesToValue(byte[] data, int requestOffset) {
        	return bytesToValueRealOffset(data, this.offset - requestOffset);
        }
      
        public abstract T bytesToValueRealOffset(byte[] paramArrayOfByte, int paramInt);
      
        /*public abstract short[] valueToShorts(T paramT);
         */
        
      }