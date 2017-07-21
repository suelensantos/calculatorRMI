/**
 * @author Suelen Santos
**/

import java.util.Arrays;
import java.util.List;

public class Balancer {

	private static Balancer balancer;
	private List<String> servers;
	private int pos;

	private Balancer() {
		this.servers = Arrays.asList("152.92.236.11", "152.92.236.16");
		this.pos = 0;
	}

	public static Balancer getInstance() {
		if(balancer == null) {
			balancer = new Balancer();
		}
		return balancer;
	}

	public String getNextServerIP() {
		if(this.pos > 1) {
			this.pos = 0;
		}
		String serverIP = this.servers.get(this.pos);
		this.pos++;
		return serverIP;
	}
}
