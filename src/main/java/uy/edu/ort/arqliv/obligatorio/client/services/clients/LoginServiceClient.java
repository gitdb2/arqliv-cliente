package uy.edu.ort.arqliv.obligatorio.client.services.clients;

import uy.edu.ort.arqliv.obligatorio.common.LoginService;
import uy.edu.ort.arqliv.obligatorio.common.exceptions.CustomServiceException;

public class LoginServiceClient {

	private LoginService loginService;

	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}
	
	public boolean login(String user, String password) throws CustomServiceException{
		return loginService.login(user, password);
	}

}
