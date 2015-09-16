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
package sc.siemens.base;

import sc.siemens.locator.BaseLocator;

public class KeyedS7Locator<K> {
    private final K key;
    private final BaseLocator<?> locator;

    public KeyedS7Locator(K key, BaseLocator<?> locator) {
        this.key = key;
        this.locator = locator;
    }

    /*public KeyedProfiLocator(K key, int slaveId, int range, int offset, int dataType) {
        this.key = key;
        locator = new ProfiLocator(slaveId, range, offset, dataType);
    }

    public KeyedProfiLocator(K key, int slaveId, int range, int offset, byte bit) {
        this.key = key;
        locator = new ProfiLocator(slaveId, range, offset, bit);
    }

    public KeyedProfiLocator(K key, SlaveAndArea slaveAndArea, int offset, int dataType) {
        this.key = key;
        locator = new ProfiLocator(slaveAndArea, offset, dataType);
    }*/

    public K getKey() {
        return key;
    }

    public BaseLocator<?> getLocator() {
        return locator;
    }

    //
    ///
    /// Delegation.
    ///
    //
    
    public int getSlaveId() {
        return locator.getSlaveId();
    }
    
    public int getArea() {
        return locator.getArea();
    }
    
    public byte getAreaNr() {
        return locator.getAreaNr();
    }

    public SlaveArea getSlaveArea() {
        return new SlaveArea(
        		this.locator.getSlaveId(), 
        		this.locator.getArea(),
        		this.locator.getAreaNr());
    }
    
    public SlaveAreaOffset getSlaveAreaOffset() {
        return new SlaveAreaOffset(
        		this.locator.getSlaveId(), 
        		this.locator.getArea(),
        		this.locator.getAreaNr(),
        		this.locator.getOffset());
    }
    
    public int getDataType() {
        return locator.getDataType();
    }

    public int getOffset() {
        return locator.getOffset();
    }
    
    public int getEndOffset() {
        return locator.getEndOffset();
    }

    public int getByteCount() {
        return locator.getByteCount();
    }

    /*public byte getBit() {
        return locator.getBit();
    }*/

    /*public Object bytesToValue(byte[] data, int requestOffset) {
        try {
            return locator.bytesToValue(data, requestOffset);
        }
        catch (ArrayIndexOutOfBoundsException e) {
            // Some equipment will not return data lengths that we expect, which causes AIOOBEs. Catch them and convert
            // them into illegal data address exceptions.
            return new ExceptionResult(ExceptionCode.ILLEGAL_DATA_ADDRESS);
        }
    }*/
    
}
