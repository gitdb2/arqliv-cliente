/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.edu.ort.arqliv.obligatorio.client;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import uy.edu.ort.arqliv.obligatorio.client.services.clients.ArrivalServiceClient;
import uy.edu.ort.arqliv.obligatorio.client.services.clients.constants.RemoteClientsConstants;
import uy.edu.ort.arqliv.obligatorio.common.exceptions.CustomServiceException;


/**
 * 
 * @author rodrigo
 */
public class DemoConsole3 {

	public static void main(String[] args) throws CustomServiceException, ParseException {

		new DemoConsole3().run();
	}

	private void run() throws ParseException, CustomServiceException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		ArrivalServiceClient client = (ArrivalServiceClient) ContextSingleton
				.getInstance().getBean(RemoteClientsConstants.ArrivalClient);
		{
			client.delete(999999);
		}
		
		
	}
}
