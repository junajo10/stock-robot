package model.robot;

public class StartModel {
	private boolean simulate = false;
	private int port = 45000;
	private String parserServer = "localhost:45000";
	
	public boolean isSimulate() {
		return simulate;
	}
	public void setSimulate(boolean simulate) {
		this.simulate = simulate;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getParserServer() {
		return parserServer;
	}
	public void setParserServer(String parserServer) {
		this.parserServer = parserServer;
	}
}
