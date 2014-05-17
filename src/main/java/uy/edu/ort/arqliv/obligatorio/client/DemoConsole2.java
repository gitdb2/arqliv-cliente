/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.edu.ort.arqliv.obligatorio.client;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import uy.edu.ort.arqliv.obligatorio.client.services.clients.ArrivalServiceClient;
import uy.edu.ort.arqliv.obligatorio.client.services.clients.constants.RemoteClientesConstants;
import uy.edu.ort.arqliv.obligatorio.common.exceptions.CustomServiceException;
import uy.edu.ort.arqliv.obligatorio.dominio.Arrival;


/**
 * 
 * @author rodrigo
 */
public class DemoConsole2 {

	public static void main(String[] args) throws CustomServiceException, ParseException {

		new DemoConsole2().run();
	}

	private void run() throws ParseException, CustomServiceException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		ArrivalServiceClient client = (ArrivalServiceClient) ContextSingleton
				.getInstance().getBean(RemoteClientesConstants.ArrivalClient);
		{
			Arrival arrival = new Arrival();
			arrival.setArrivalDate(sdf.parse("20120105"));
			arrival.setShipOrigin("caca");	
			arrival.setContainersDescriptions("desd");
			
			Long ship = 5L;
			List<Long> containers = Arrays.asList(1L, 2L);
		
	
			Long idGenerated = client.create(arrival, ship, containers);
			System.out.println("Arribo creado correctamente con id: "+ idGenerated);
		}
		
		
	}
}
