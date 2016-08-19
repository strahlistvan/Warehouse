package entity;

public class DatabaseProperties {
	
	private String type, host, user, password; 
	private int port;
	
	public DatabaseProperties() { }
	
	public DatabaseProperties(String host, String user, String password){
		this.type = "mysql";
		this.port = 3306;
		this.host = host;
		this.user = user; 
		this.password = password;
	}
	
	public DatabaseProperties(String host, String user, String password, String type, int port){
		this.type = type;
		this.port = port;
		this.host = host;
		this.user = user; 
		this.password = password;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	
	

	
}
