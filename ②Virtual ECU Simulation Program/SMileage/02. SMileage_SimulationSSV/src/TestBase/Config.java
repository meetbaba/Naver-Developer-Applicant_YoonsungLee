package TestBase;

import java.io.IOException;

import com.etas.vrta.comms.IVRTAServerLink;
import com.etas.vrta.comms.ServerConnection;
import com.etas.vrta.comms.ServerSocketClient;
import com.etas.vrta.comms.VECUConnection;
import com.etas.vrta.comms.VECUSocketClient;

public class Config {
	static ServerConnection connection;
	static VECUConnection vecuConnection;
	static VECUSocketClient ivecuLink;
	static IVRTAServerLink ivrtaServerLink;
	
	public static String getHost() {
		return "192.168.40.14";
	}
	
	public static VECUConnection getVECUPath(String name) {
		ivrtaServerLink = new ServerSocketClient(getHost());
		connection = new ServerConnection(ivrtaServerLink);
		final String[] runningVecus = connection.aliases();
		for(final String vecu : runningVecus) {
			if(name.equals(vecu)) {
				int port = 0;
				port = connection.attach(vecu.trim());
				if(port == 0) {
					continue;
				} else {
					ivecuLink = new VECUSocketClient(connection.host(), port);
					try {
						vecuConnection = new VECUConnection(ivecuLink, vecu);
						return vecuConnection;
					} catch(final Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return null;
	}
	
	public static void closeConnection() throws IOException{
		connection.releaseAlias(vecuConnection.alias());
		ivecuLink.close();
		ivrtaServerLink.close();
	}
}
