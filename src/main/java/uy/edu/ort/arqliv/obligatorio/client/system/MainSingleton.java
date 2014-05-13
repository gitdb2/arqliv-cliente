package uy.edu.ort.arqliv.obligatorio.client.system;


public class MainSingleton {
	private static MainSingleton instance = new MainSingleton();
	
	private MainSingleton(){}
	
	private String user = "dummy";
	
	public static MainSingleton getInstance(){
		return instance;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	
	
}
