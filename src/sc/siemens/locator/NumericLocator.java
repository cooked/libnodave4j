package sc.siemens.locator;

import sc.siemens.code.DataType;
import sc.siemens.code.MemoryArea;
import sc.siemens.exception.IllegalDataTypeException;

public class NumericLocator extends BaseLocator<Number> {

	private final int dataType;

	public NumericLocator(int slaveId, int area, byte areaNr, int offset, int dataType) {
		super(slaveId, area, areaNr, offset);
		this.dataType = dataType;
		validate();
	}

	private void validate() {

		super.validate(getByteCount());
		
		 if ((this.area == MemoryArea.FLAG) || (this.area == MemoryArea.INPUT) || (this.area == MemoryArea.OUTPUT))
			 throw new IllegalDataTypeException("Only binary values can be read from FLAG, INPUT or OUTPUT areas");
		 //if (!(ArrayUtils.contains(DATA_TYPES, this.dataType)))
		 //	 throw new IllegalDataTypeException("Invalid data type");
	}

	public int getDataType() {
		return this.dataType;
	}

	public String toString() {
		return "NumericLocator(slaveId=" + getSlaveId() + 
				", area=" + this.area + 
				", areaNr=" + this.areaNr + 
				", offset=" + this.offset + 
				", dataType=" + this.dataType + ")";
	}

	public int getByteCount() {
		return DataType.getByteCount(dataType);	
	}

	/*public Number bytesToValueRealOffset(byte[] data, int offset) {
		offset *= 2;

		if (this.dataType == 2) {
			return new Integer((data[offset] & 0xFF) << 8 | data[(offset + 1)]
					& 0xFF);
		}
		if (this.dataType == 3) {
			return new Short(
					(short) ((data[offset] & 0xFF) << 8 | data[(offset + 1)] & 0xFF));
		}
		if (this.dataType == 16) {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < 2; ++i) {
				sb.append(bcdNibbleToInt(data[(offset + i)], true));
				sb.append(bcdNibbleToInt(data[(offset + i)], false));
			}
			return Short.valueOf(Short.parseShort(sb.toString()));
		}

		if (this.dataType == 4) {
			return new Long((data[offset] & 0xFF) << 24
					| (data[(offset + 1)] & 0xFF) << 16
					| (data[(offset + 2)] & 0xFF) << 8 | data[(offset + 3)]
					& 0xFF);
		}

		if (this.dataType == 5) {
			return new Integer((data[offset] & 0xFF) << 24
					| (data[(offset + 1)] & 0xFF) << 16
					| (data[(offset + 2)] & 0xFF) << 8 | data[(offset + 3)]
					& 0xFF);
		}

		if (this.dataType == 6) {
			return new Long((data[(offset + 2)] & 0xFF) << 24
					| (data[(offset + 3)] & 0xFF) << 16
					| (data[offset] & 0xFF) << 8 | data[(offset + 1)] & 0xFF);
		}

		if (this.dataType == 7) {
			return new Integer((data[(offset + 2)] & 0xFF) << 24
					| (data[(offset + 3)] & 0xFF) << 16
					| (data[offset] & 0xFF) << 8 | data[(offset + 1)] & 0xFF);
		}

		if (this.dataType == 8) {
			return Float.valueOf(Float
					.intBitsToFloat((data[offset] & 0xFF) << 24
							| (data[(offset + 1)] & 0xFF) << 16
							| (data[(offset + 2)] & 0xFF) << 8
							| data[(offset + 3)] & 0xFF));
		}

		if (this.dataType == 9) {
			return Float.valueOf(Float
					.intBitsToFloat((data[(offset + 2)] & 0xFF) << 24
							| (data[(offset + 3)] & 0xFF) << 16
							| (data[offset] & 0xFF) << 8 | data[(offset + 1)]
							& 0xFF));
		}

		if (this.dataType == 17) {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < 4; ++i) {
				sb.append(bcdNibbleToInt(data[(offset + i)], true));
				sb.append(bcdNibbleToInt(data[(offset + i)], false));
			}
			return Integer.valueOf(Integer.parseInt(sb.toString()));
		}

		if (this.dataType == 10) {
			byte[] b9 = new byte[9];
			System.arraycopy(data, offset, b9, 1, 8);
			return new BigInteger(b9);
		}

		if (this.dataType == 11) {
			return new Long((data[offset] & 0xFF) << 56
					| (data[(offset + 1)] & 0xFF) << 48
					| (data[(offset + 2)] & 0xFF) << 40
					| (data[(offset + 3)] & 0xFF) << 32
					| (data[(offset + 4)] & 0xFF) << 24
					| (data[(offset + 5)] & 0xFF) << 16
					| (data[(offset + 6)] & 0xFF) << 8 | data[(offset + 7)]
					& 0xFF);
		}

		if (this.dataType == 12) {
			byte[] b9 = new byte[9];
			b9[1] = data[(offset + 6)];
			b9[2] = data[(offset + 7)];
			b9[3] = data[(offset + 4)];
			b9[4] = data[(offset + 5)];
			b9[5] = data[(offset + 2)];
			b9[6] = data[(offset + 3)];
			b9[7] = data[offset];
			b9[8] = data[(offset + 1)];
			return new BigInteger(b9);
		}

		if (this.dataType == 13) {
			return new Long((data[(offset + 6)] & 0xFF) << 56
					| (data[(offset + 7)] & 0xFF) << 48
					| (data[(offset + 4)] & 0xFF) << 40
					| (data[(offset + 5)] & 0xFF) << 32
					| (data[(offset + 2)] & 0xFF) << 24
					| (data[(offset + 3)] & 0xFF) << 16
					| (data[offset] & 0xFF) << 8 | data[(offset + 1)] & 0xFF);
		}

		if (this.dataType == 14) {
			return Double.valueOf(Double
					.longBitsToDouble((data[offset] & 0xFF) << 56
							| (data[(offset + 1)] & 0xFF) << 48
							| (data[(offset + 2)] & 0xFF) << 40
							| (data[(offset + 3)] & 0xFF) << 32
							| (data[(offset + 4)] & 0xFF) << 24
							| (data[(offset + 5)] & 0xFF) << 16
							| (data[(offset + 6)] & 0xFF) << 8
							| data[(offset + 7)] & 0xFF));
		}

		if (this.dataType == 15) {
			return Double.valueOf(Double
					.longBitsToDouble((data[(offset + 6)] & 0xFF) << 56
							| (data[(offset + 7)] & 0xFF) << 48
							| (data[(offset + 4)] & 0xFF) << 40
							| (data[(offset + 5)] & 0xFF) << 32
							| (data[(offset + 2)] & 0xFF) << 24
							| (data[(offset + 3)] & 0xFF) << 16
							| (data[offset] & 0xFF) << 8 | data[(offset + 1)]
							& 0xFF));
		}

		throw new RuntimeException("Unsupported data type: " + this.dataType);
	}

	private static int bcdNibbleToInt(byte b, boolean high) {
		int n;
		if (high)
			n = b >> 4 & 0xF;
		else
			n = b & 0xF;
		if (n > 9)
			n = 0;
		return n;
	} 

	public short[] valueToShorts(Number value) {
		if ((this.dataType == 2) || (this.dataType == 3)) {
			return new short[] { value.shortValue() };
		}
		if (this.dataType == 16) {
			short s = value.shortValue();
			return new short[] { (short) (s / 1000 % 10 << 12
					| s / 100 % 10 << 8 | s / 10 % 10 << 4 | s % 10) };
		}

		if ((this.dataType == 4) || (this.dataType == 5)) {
			int i = value.intValue();
			return new short[] { (short) (i >> 16), (short) i };
		}

		if ((this.dataType == 6) || (this.dataType == 7)) {
			int i = value.intValue();
			return new short[] { (short) i, (short) (i >> 16) };
		}

		if (this.dataType == 8) {
			int i = Float.floatToIntBits(value.floatValue());
			return new short[] { (short) (i >> 16), (short) i };
		}

		if (this.dataType == 9) {
			int i = Float.floatToIntBits(value.floatValue());
			return new short[] { (short) i, (short) (i >> 16) };
		}

		if (this.dataType == 17) {
			int i = value.intValue();
			return new short[] {
					(short) (i / 10000000 % 10 << 12 | i / 1000000 % 10 << 8
							| i / 100000 % 10 << 4 | i / 10000 % 10),
					(short) (i / 1000 % 10 << 12 | i / 100 % 10 << 8
							| i / 10 % 10 << 4 | i % 10) };
		}

		if ((this.dataType == 10) || (this.dataType == 11)) {
			long l = value.longValue();
			return new short[] { (short) (int) (l >> 48),
					(short) (int) (l >> 32), (short) (int) (l >> 16),
					(short) (int) l };
		}

		if ((this.dataType == 12) || (this.dataType == 13)) {
			long l = value.longValue();
			return new short[] { (short) (int) l, (short) (int) (l >> 16),
					(short) (int) (l >> 32), (short) (int) (l >> 48) };
		}

		if (this.dataType == 14) {
			long l = Double.doubleToLongBits(value.doubleValue());
			return new short[] { (short) (int) (l >> 48),
					(short) (int) (l >> 32), (short) (int) (l >> 16),
					(short) (int) l };
		}

		if (this.dataType == 15) {
			long l = Double.doubleToLongBits(value.doubleValue());
			return new short[] { (short) (int) l, (short) (int) (l >> 16),
					(short) (int) (l >> 32), (short) (int) (l >> 48) };
		}

		throw new RuntimeException("Unsupported data type: " + this.dataType);
	}*/
	
}
