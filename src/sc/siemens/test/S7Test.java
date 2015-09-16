package sc.siemens.test;

import org.libnodave.ResultSet;

import sc.siemens.S7Factory;
import sc.siemens.S7Master;
import sc.siemens.exception.S7InitException;
import sc.siemens.exception.S7TransportException;
import sc.siemens.ip.IpParameters;
import sc.siemens.msg.S7Request;
import sc.siemens.msg.ReadDBAreaRequest;
import sc.siemens.msg.ReadResponse;
import sc.siemens.msg.S7Response;

public class S7Test {

	static ResultSet rs;
	//private static PLCinterface di;
	//private static TCPConnection dc;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		IpParameters params = new IpParameters();
		params.setHost("172.16.110.191");
		params.setPort(102);

		S7Master profinetMaster 	= new S7Factory().createTCPMaster(params,false);
		
		S7Request 	request;
		S7Response	response;
				
		try {
			
			profinetMaster.init();
			
			request = new ReadDBAreaRequest(1,(byte) 2,0,4);
			response = profinetMaster.send(request);
			
			profinetMaster.dc.useResult(((ReadResponse)response).getResultSet(), 0);
			
			
			Object value = profinetMaster.dc.getFloat();
					//ProfiUtils.rsDataTypetoValue(profinetMaster.dc, DataType.FOUR_BYTE_FLOAT);
			
			System.out.println(value);
			
			profinetMaster.destroy();
			
		} catch (S7InitException e) {
			e.printStackTrace();
		} catch (S7TransportException e) {
			e.printStackTrace();
		}
		
		/*Socket socket = new Socket();
        try {
			socket.connect(new InetSocketAddress("172.16.110.191", 102), 500);
			
			di = new PLCinterface(
					socket.getOutputStream(),
					socket.getInputStream(),
					"IF1",
					0,
					Nodave.PROTOCOL_ISOTCP);
			
			dc = new TCPConnection(di, 0, 2);
			// insert your PPI address here
			
			dc.connectPLC();
			
			PDU p = dc.prepareReadRequest();
			p.addVarToReadRequest(Nodave.DB, 2, 0, 4);
			ResultSet rs = dc.execReadRequest(p);
			
			dc.useResult(rs, 0);
			float a = dc.getFloat();
			
			System.out.println(a);
			
			dc.disconnectPLC();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
        
		
		
	}

}
