package sc.siemens.locator;

import sc.siemens.base.S7Utils;
import sc.siemens.code.MemoryArea;
import sc.siemens.exception.S7IdException;

import com.serotonin.NotImplementedException;

public class BinaryLocator extends BaseLocator<Boolean> {
	
	private int bit 	= -1;
	
	public BinaryLocator(int slaveId, int area, byte areaNr, int offset, int bit) {
		super(slaveId, area, areaNr, offset);
		if (!(isBinaryArea(area)))
			throw new S7IdException("Non-bit requests can only be made from DB ...TODO... areas.");
		this.bit = bit;
		validate();
	}

	/*public BinaryLocator(int slaveId, int area, int offset, int bit) {
		super(slaveId, area, (byte)0x00, offset);
		if (isBinaryArea(area))
			throw new S7IdException("Bit requests can only be made from FLAG, INPUT or OUTPUT areas.");
		this.bit = bit;
		validate();
	}*/

	public static boolean isBinaryArea(int area) {
		return ((area == MemoryArea.INPUT) || (area == MemoryArea.OUTPUT) || (area == MemoryArea.FLAG));
	}

	protected void validate() {
		super.validate(getByteCount());
		if (!(isBinaryArea(this.area)))
			S7Utils.validateBit(this.bit);
	}

	public int getBit() {
		return this.bit;
	}

	public int getDataType() {
		return 1;
	}

	public int getByteCount() {
		return 1;
	}

	public String toString() {
		return "BinaryLocator(slaveId=" + getSlaveId() + 
				", area=" + this.area +
				", areaNr=" + this.areaNr + 
				", offset=" + this.offset + 
				", bit=" + this.bit + ")";
	}

	public Boolean bytesToValueRealOffset(byte[] data, int offset) {
		if ((this.area == 1) || (this.area == 2))
			return new Boolean(((data[(offset / 8)] & 0xFF) >> offset % 8 & 0x1) == 1);
		offset *= 2;
		return new Boolean(((data[(offset + 1 - (this.bit / 8))] & 0xFF) >> this.bit % 8 & 0x1) == 1);
	}

	public short[] valueToShorts(Boolean value) {
		throw new NotImplementedException();
	}
}
