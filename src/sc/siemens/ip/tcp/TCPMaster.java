package sc.siemens.ip.tcp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;

import org.libnodave.Nodave;
import org.libnodave.PDU;
import org.libnodave.PLCinterface;
import org.libnodave.ResultSet;
import org.libnodave.TCPConnection;

import sc.siemens.BatchRead;
import sc.siemens.BatchResults;
import sc.siemens.ExceptionResult;
import sc.siemens.S7Master;
import sc.siemens.base.KeyedS7Locator;
import sc.siemens.base.S7Utils;
import sc.siemens.code.AreaCode;
import sc.siemens.code.MemoryArea;
import sc.siemens.exception.ErrorResponseException;
import sc.siemens.exception.S7InitException;
import sc.siemens.exception.S7TransportException;
import sc.siemens.ip.IpParameters;
import sc.siemens.locator.BaseLocator;
import sc.siemens.msg.ReadBinaryRequest;
import sc.siemens.msg.ReadDBAreaRequest;
import sc.siemens.msg.ReadDBAreaResponse;
import sc.siemens.msg.ReadFlagAreaResponse;
import sc.siemens.msg.ReadInputAreaRequest;
import sc.siemens.msg.ReadNumericRequest;
import sc.siemens.msg.S7Request;
import sc.siemens.msg.S7Response;
import sc.siemens.msg.WriteFlagAreaRequest;
import sc.siemens.msg.WriteFlagAreaResponse;

public class TCPMaster extends S7Master {
	
	    // Configuration fields.
	    //private short nextTransactionId = 0;
	    private final IpParameters ipParameters;
	    private final boolean keepAlive;

	    // TODO
	    int rack = 0;
	    int slot = 2;
	    	    	  
	    PDU 			p 		= null;
	    private Socket 	socket	= null;

	    public TCPMaster(IpParameters params, boolean keepAlive) {
	        ipParameters = params;
	        this.keepAlive = keepAlive;
	    }

	    /*protected short getNextTransactionId() {
	        return nextTransactionId++;
	    }*/

	    @Override
	    synchronized public void init() throws S7InitException {
	       try {
	            if(keepAlive)
	            	openConnection();
	        } catch (Exception e) {
	            throw new S7InitException(e);
	        }
	        initialized = true;
	    }

	    @Override
	    synchronized public void destroy() {
	        closeConnection();
	    }

	    /**
	     * Returns a value from the profi network according to the given locator information. Various data types are
	     * allowed to be requested including multi-word types. The determination of the correct request message to send is
	     * handled automatically.
	     * 
	     * @param locator
	     *            the information required to locate the value in the modbus network.
	     * @return an object representing the value found. This will be one of Boolean, Short, Integer, Long, BigInteger,
	     *         Float, or Double. See the DataType enumeration for details on which type to expect.
	     * @throws ModbusTransportException
	     *             if there was an IO error or other technical failure while sending the message
	     * @throws ErrorResponseException
	     *             if the response returned from the slave was an exception.
	     */
	    public Object getValue(BaseLocator<?> locator) throws S7TransportException, ErrorResponseException {
	        
	    	BatchRead<String> batch = new BatchRead<String>();
	        batch.addLocator("", locator);
	        BatchResults<String> result = send(batch);
	        return result.getValue("");
	    	
	    }
	    
	    
	    /**
	     * Useful for sending a number of polling commands at once, or at least in as optimal a batch as possible.
	     */
	    public <K> BatchResults<K> send(BatchRead<K> batch) throws S7TransportException, ErrorResponseException {
	      
	    	if (!initialized)
	            throw new S7TransportException("not initialized");

	    	// DO NOT GROUP/OPTIMIZE STUFF
	    	List<KeyedS7Locator<K>> 	reqValLocators 	= batch.getRequestValues();
	    	BatchResults<K> 			results 		= new BatchResults<K>();
	    	        
	    	try {
	        	
	            // Check if we need to open the connection.
	            if (!keepAlive)
	                openConnection();    
	            
		        initReadRequest();
		    	for(KeyedS7Locator<K> rvl : reqValLocators) {
		    		switch(rvl.getArea()) {
		    			case MemoryArea.FLAG:
		    				//addBitVarToReadRequest(new ReadFlagAreaRequest(rvl.getSlaveId(), rvl.getOffset(), rvl.getLength()));
		    				//addVarToReadRequest(new ReadFlagAreaRequest(rvl.getSlaveId(), rvl.getAreaNr(), rvl.getOffset(), rvl.getByteCount()));
		    				p.addVarToReadRequest(MemoryArea.getReadAreaCode(MemoryArea.FLAG), rvl.getAreaNr(), rvl.getOffset(), rvl.getByteCount());
		    				break;
		    			case MemoryArea.INPUT:
		    				addBitVarToReadRequest(new ReadInputAreaRequest(rvl.getSlaveId(), rvl.getOffset(), rvl.getByteCount()));
		    				break;
		    			case MemoryArea.DB:
		    				addVarToReadRequest(new ReadDBAreaRequest(rvl.getSlaveId(), rvl.getAreaNr(), rvl.getOffset(), rvl.getByteCount()));
		    				break;
		    		}		        
		    	}
	    	
		    	ResultSet rs; int i=0;
		    	rs = execReadRequest();
		        
		    	for(KeyedS7Locator<K> rvl : reqValLocators) {
		    		if(dc.useResult(rs, i)==0)
		    			results.addResult(rvl.getKey(),	S7Utils.rsDataTypetoValue(dc, rvl));
		    		else
		    			results.addResult(rvl.getKey(), new ExceptionResult((byte)0x99));	   
		    		i++;
		    	}
		    	
		        return results;
	    	
	    	} catch (Exception e) {
	        	
	            closeConnection();
	            throw new S7TransportException(e, batch.getRequestValues().get(0).getSlaveId());
	            
	        } finally {
	        	
	            // Check if we should close the connection.
	            if (!keepAlive)
	                closeConnection();
	            
	        }
	    	
	        // GROUP/OPTIMIZE THE STUFF
	        /*List<ReadFunctionGroup<K>> 	functionGroups 	= batch.getReadFunctionGroups();
	        BatchResults<K> 			results 		= new BatchResults<K>();
	        
	        // Execute each read function and process the results.
	        for (ReadFunctionGroup<K> functionGroup : functionGroups)
	        	sendFunctionGroup(functionGroup, results, batch.isErrorsInResults(), batch.isExceptionsInResults());
	         */
	    }
	    
	    //
	    // private READ STUFF
	    //
	    private void initReadRequest() {
	    	if(dc!=null)
	    		p = dc.prepareReadRequest();
	    }
	    private ResultSet execReadRequest() {
	    	return dc.execReadRequest(p);
	    }
	    private void addVarToReadRequest(ReadNumericRequest request) {
	    	if(p!=null)
	    		p.addVarToReadRequest(
	    				request.getArea(), 
	    				request.getAreaNr(),
	    				request.getStartOffset(),
	    				request.getByteCount());
	    }
	    private void addBitVarToReadRequest(ReadBinaryRequest request) {
	    	if(p!=null)
	    		p.addBitVarToReadRequest(
	    				request.getArea(),
	    				request.getAreaNr(),
	    				request.getStartOffset(),
	    				request.getBit());
	    }
	    
	    @Override
	    synchronized public S7Response send(S7Request request) throws S7TransportException {
	        
	        ResultSet 		rs;
	        S7Response 		rr;
	        
	    	try {
	        	
	            // Check if we need to open the connection.
	            if (!keepAlive)
	                openConnection();        	    
		        
	            // is READ
	            if(request instanceof ReadNumericRequest || request instanceof ReadBinaryRequest) {
			        
	            	p = dc.prepareReadRequest();
			        if(request instanceof ReadNumericRequest)
			        	addVarToReadRequest((ReadNumericRequest)request);
			        else if(request instanceof ReadBinaryRequest)
			        	addBitVarToReadRequest((ReadBinaryRequest)request);
			        rs = dc.execReadRequest(p);
			        
			        switch(request.getArea()) {
			        	case AreaCode.DB:
			        		rr = new ReadDBAreaResponse(request.getSlaveId(),rs);
			        		break;
			        	case AreaCode.FLAG:
			        		rr = new ReadFlagAreaResponse(request.getSlaveId(),rs);
			        		break;
			        	default:
			        		throw new RuntimeException("Unsupported function");
			        }
			      
			    // is WRITE
	            } else if (request instanceof WriteFlagAreaRequest) {
	            	
	            	// TODO
	            	p = dc.prepareWriteRequest();
	            	rs = dc.execReadRequest(p);
	            	switch(request.getArea()) {
			        	case AreaCode.FLAG:
			        		rr = new WriteFlagAreaResponse(request.getSlaveId());
			        		break;
			        	default:
			        		throw new RuntimeException("Unsupported function");
	            	}
	            	
	            } else {
	            	
	        		throw new RuntimeException("Unsupported function");
	        		
	            }
			    	        
		        return rr;
	            
	            
	        } catch (Exception e) {
	        	
	            closeConnection();
	            throw new S7TransportException(e, request.getSlaveId());
	            
	        } finally {
	        	
	            // Check if we should close the connection.
	            if (!keepAlive)
	                closeConnection();
	            
	        }
	        
	    }
		
	    /**
	     * This method assumes that all locators have already been pre-sorted and grouped into valid requests, say, by the
	     * createRequestGroups method.
	     */
	    /*private <K> void sendFunctionGroup(ReadFunctionGroup<K> functionGroup, BatchResults<K> results,
	            boolean errorsInResults, boolean exceptionsInResults) throws S7TransportException,
	            ErrorResponseException {
	    	
	    	Object value;
	    	
	        int slaveId 	= functionGroup.getSlaveId();
	        int area 		= functionGroup.getArea();
	        byte areaNr 	= functionGroup.getAreaNr();
	        int startOffset = functionGroup.getStartOffset();
	        int length 		= functionGroup.getLength();
	        
	        
	        // Inspect the function group for data required to create the request.
	        S7Request request;
	        if (area == MemoryArea.FLAG)
	            request = new ReadFlagAreaRequest(slaveId, startOffset, length);
	        else if (area == MemoryArea.DB)
	        	request = new ReadDBAreaRequest(slaveId, areaNr, startOffset, length);
	        else if (area == MemoryArea.INPUT)
	            request = new ReadInputAreaRequest(slaveId, functionGroup.getStartOffset(), length);
	        //else if (functionGroup.getAreaCode() == AreaCode.OUTPUT)
	        //    request = new WriteOutputAreaRequest(slaveId, functionGroup.getStartOffset(), length);
	        else
	            throw new RuntimeException("Unsupported function");
			
	        
	        ReadResponse 	response	= null;
	        ResultSet 		rs 			= null;
	        try {
	            response = (ReadResponse) send(request);
	        } catch (S7TransportException e) {
	            if (!exceptionsInResults)
	                throw e;
	            for (KeyedS7Locator<K> locator : functionGroup.getLocators())
	                results.addResult(locator.getKey(), e);
	            return;
	        }

	        
	        if (!errorsInResults && response.isException())
	            throw new ErrorResponseException(request, response);
	        else if (!response.isException())
	        	rs = response.getResultSet();
	        
	        int rsIndex = 0;
	        for (KeyedS7Locator<K> locator : functionGroup.getLocators()) {
	        	
	            if (errorsInResults && response.isException())
	                results.addResult(locator.getKey(), new ExceptionResult(response.getExceptionCode()));
	            else {
	            	
	                try {
	                	dc.useResult(rs, rsIndex);
	                	value = S7Utils.rsDataTypetoValue(dc, locator.getDataType());
	                	results.addResult(locator.getKey(),value);
	                	
	                } catch (RuntimeException e) {
	                    throw new RuntimeException("Result conversion exception. data=" + " "//+ ArrayUtils.toHexString(data)
	                            + ", startOffset=" + startOffset + ", dataType=" + locator.getDataType()
	                            + ", locator.endOffset=" + locator.getEndOffset() + ", locator.length="
	                            + locator.getLength() + ", locator.offset=" + locator.getOffset() + ", locator.bit="
	                            //+ locator.getBit() + ", locator.key=" + locator.getKey() + ", locator.bit.class="
	                            + locator.getKey().getClass() + ", functionGroup.functionCode="
	                            + functionGroup.getArea() + ", functionGroup.startOffset="
	                            + functionGroup.getStartOffset() + ", functionGroup.length=" + functionGroup.getLength(), e);
	                }
	            }
	            rsIndex++;
	        }
	        
	    }*/
	    
	    //
	    // /
	    // / Private methods
	    // /
	    //
	    private void openConnection() throws IOException {
	    	
	        // Make sure any existing connection is closed.
	        closeConnection();

	        // Try 'retries' times to get the socket open.
	        int retries = getRetries();
	        while (true) {
	            try {
	                socket = new Socket();
	                socket.connect(new InetSocketAddress(ipParameters.getHost(), ipParameters.getPort()), getTimeout());
	                
	                //transport = new StreamTransport(socket.getInputStream(), socket.getOutputStream());
	                break;
	            }
	            catch (IOException e) {
	                closeConnection();

	                if (retries <= 0)
	                    throw e;
	                System.out.println("Profinet: Open connection failed, trying again.");
	                retries--;
	            }
	        }
	        
	        di = new PLCinterface(socket.getOutputStream(), socket.getInputStream(),"IF1",0,Nodave.PROTOCOL_ISOTCP);
            dc = new TCPConnection(di, rack, slot);
            
            dc.connectPLC();
            
	    }

	    private void closeConnection() {

	        try {
	        	if (dc != null)
	        		dc.disconnectPLC();
	        	if(di != null)
	        		di.disconnectAdapter();
	            if (socket != null)
	                socket.close();
	        } catch (IOException e) {
	            getExceptionHandler().receivedException(e);
	        }

	        //dc 		= null; // NON ELIMINARE ALTRIMENTI PERDO I DATI LETTI
	        //di 		= null; 
	        socket 	= null;
	    }
		
	}

