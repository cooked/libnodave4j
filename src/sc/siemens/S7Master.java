package sc.siemens;

import org.libnodave.PLCinterface;
import org.libnodave.S7Connection;

import com.serotonin.messaging.MessageControl;

import sc.siemens.exception.ErrorResponseException;
import sc.siemens.exception.S7InitException;
import sc.siemens.exception.S7TransportException;
import sc.siemens.locator.BaseLocator;
import sc.siemens.msg.S7Request;
import sc.siemens.msg.S7Response;

abstract public class S7Master extends S7 {

	public PLCinterface di;
	public S7Connection dc;
	
	private int timeout = 500;
    private int retries = 2;
    protected boolean initialized;
    //private final Map<Integer, SlaveProfile> slaveProfiles = new HashMap<Integer, SlaveProfile>();
	
    abstract public void init() throws S7InitException;
    abstract public void destroy();
    abstract public S7Response send(S7Request request) throws S7TransportException;
    abstract public Object getValue(BaseLocator<?> locator) throws S7TransportException, ErrorResponseException; 
        
    public boolean isInitialized() {
        return initialized;
    }
    
	public int getRetries() {
        return retries;
    }

    public void setRetries(int retries) {
        if (retries < 0)
            this.retries = 0;
        else
            this.retries = retries;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        if (timeout < 1)
            this.timeout = 1;
        else
            this.timeout = timeout;
    }

    
    
    /**
     * Useful for sending a number of polling commands at once, or at least in as optimal a batch as possible.
     */
    public <K> BatchResults<K> send(BatchRead<K> batch) throws S7TransportException, ErrorResponseException {
        if (!initialized)
            throw new S7TransportException("not initialized");

        BatchResults<K> results = new BatchResults<K>();
        //List<ReadFunctionGroup<K>> functionGroups = batch.getReadFunctionGroups();

        // Execute each read function and process the results.
        //for (ReadFunctionGroup<K> functionGroup : functionGroups)
        //    sendFunctionGroup(functionGroup, results, batch.isErrorsInResults(), batch.isExceptionsInResults());

        return results;
    }
	
    /*
    private <K> void sendFunctionGroup(
			ReadFunctionGroup<K> functionGroup, 
			BatchResults<K> results,
            boolean errorsInResults, 
            boolean exceptionsInResults) throws ProfiTransportException, ErrorResponseException {
        
		int slaveId = functionGroup.getSlaveAndRange().getSlaveId();
        int startOffset = functionGroup.getStartOffset();
        int length = functionGroup.getLength();

        // Inspect the function group for data required to create the request.
        ProfiRequest request;
        //if (functionGroup.getFunctionCode() == FunctionCode.READ_COILS)
        //    request = new ReadCoilsRequest(slaveId, startOffset, length);
        //else if (functionGroup.getFunctionCode() == FunctionCode.READ_DISCRETE_INPUTS)
        //    request = new ReadDiscreteInputsRequest(slaveId, startOffset, length);
        //else 
        if (functionGroup.getFunctionCode() == FunctionCode.DB)
            request = new ReadDBAreaRequest(slaveId, functionGroup.getStartOffset(),
                    functionGroup.getLength());
        //else if (functionGroup.getFunctionCode() == FunctionCode.READ_INPUT_REGISTERS)
        //    request = new ReadInputRegistersRequest(slaveId, functionGroup.getStartOffset(), functionGroup.getLength());
        else
            throw new RuntimeException("Unsupported function");

        ReadResponse response;
        try {
            response = (ReadResponse) send(request);
        }
        catch (ProfiTransportException e) {
            if (!exceptionsInResults)
                throw e;

            for (KeyedProfiLocator<K> locator : functionGroup.getLocators())
                results.addResult(locator.getKey(), e);

            return;
        }

        byte[] data = null;
        if (!errorsInResults && response.isException())
            throw new ErrorResponseException(request, response);
        else if (!response.isException())
            data = response.getData();

        for (KeyedProfiLocator<K> locator : functionGroup.getLocators()) {
            if (errorsInResults && response.isException())
                results.addResult(locator.getKey(), new ExceptionResult(response.getExceptionCode()));
            else {
                try {
                    results.addResult(locator.getKey(), locator.bytesToValue(data, startOffset));
                }
                catch (RuntimeException e) {
                    throw new RuntimeException("Result conversion exception. data=" + ArrayUtils.toHexString(data)
                            + ", startOffset=" + startOffset + ", dataType=" + locator.getDataType()
                            + ", locator.endOffset=" + locator.getEndOffset() + ", locator.length="
                            + locator.getLength() + ", locator.offset=" + locator.getOffset() + ", locator.bit="
                            + locator.getBit() + ", locator.key=" + locator.getKey() + ", locator.bit.class="
                            + locator.getKey().getClass() + ", functionGroup.functionCode="
                            + functionGroup.getFunctionCode() + ", functionGroup.startOffset="
                            + functionGroup.getStartOffset() + ", functionGroup.length=" + functionGroup.getLength(), e);
                }
            }
        }
    }*/
    
	//
    // /
    // / Protected methods
    // /
    //
    protected MessageControl getMessageControl() {
        MessageControl conn = new MessageControl();
        conn.setRetries(getRetries());
        conn.setTimeout(getTimeout());
        conn.setExceptionHandler(getExceptionHandler());
        return conn;
    }

    protected void closeMessageControl(MessageControl conn) {
        if (conn != null)
            conn.close();
    }
    
}
