package sc.siemens;

import sc.siemens.ip.IpParameters;
import sc.siemens.ip.tcp.TCPMaster;

public class S7Factory {
	
	//
    // Modbus masters
    //
    
	//public ProfiMaster createRtuMaster(SerialParameters params) {
    //    return new RtuMaster(params);
    //}

    //public ProfiMaster createAsciiMaster(SerialParameters params) {
    //    return new AsciiMaster(params);
    //}

    public S7Master createTCPMaster(IpParameters params, boolean keepAlive) {
        return new TCPMaster(params, keepAlive);
    }

    //public ProfiMaster createUdpMaster(IpParameters params) {
    //    return new UdpMaster(params);
    //}
    
}
