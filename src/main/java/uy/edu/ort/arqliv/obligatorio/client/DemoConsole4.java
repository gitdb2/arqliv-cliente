/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.edu.ort.arqliv.obligatorio.client;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import uy.edu.ort.arqliv.obligatorio.client.services.clients.ArrivalServiceClient;
import uy.edu.ort.arqliv.obligatorio.client.services.clients.constants.RemoteClientsConstants;
import uy.edu.ort.arqliv.obligatorio.common.exceptions.CustomServiceException;
import uy.edu.ort.arqliv.obligatorio.dominio.Arrival;
import uy.edu.ort.arqliv.obligatorio.dominio.Container;

/**
 * 
 * @author rodrigo
 */
public class DemoConsole4 {

	public static void main(String[] args) throws CustomServiceException,
			ParseException {

		new DemoConsole4().run();
	}

	private void run() throws ParseException, CustomServiceException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		ArrivalServiceClient client = (ArrivalServiceClient) ContextSingleton
				.getInstance().getBean(RemoteClientsConstants.ArrivalClient);

		{
			System.out.println("test1 OK");
			Arrival arr = client.find(10L);
			Date origDate = arr.getArrivalDate();
			System.out.println(arr.toString());

			List<Long> ids = generateContainerList(arr.getContainers());

			arr.setArrivalDate(sdf.parse("10320404"));

			client.update(arr, arr.getShip().getId(), ids);
			arr = client.find(10L);
			System.out.println(arr.toString());
			
			//back to original
			arr.setArrivalDate(origDate);
			client.update(arr, arr.getShip().getId(), ids);

			System.out.println("-----------------------------------");
		}

		{
			System.out.println("test2 da error DATE");
			Arrival arr = client.find(10L);

			System.out.println(arr.toString());

			List<Long> ids = generateContainerList(arr.getContainers());

			arr.setArrivalDate(sdf.parse("20140517"));

			try {
				client.update(arr, arr.getShip().getId(), ids);
				arr = client.find(10L);
				System.out.println(arr.toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("ERROR: " + e.getMessage());
			}

			System.out.println("-----------------------------------");
		}

		{
			System.out.println("test3 OK ");
			Arrival arr = client.find(10L);

			System.out.println(arr.toString());

			List<Long> ids = generateContainerList(arr.getContainers());

			arr.setContainersDescriptions("OOOOOO" + System.currentTimeMillis());

			try {
				client.update(arr, arr.getShip().getId(), ids);
				arr = client.find(10L);
				System.out.println(arr.toString());
			} catch (Exception e) {
				System.out.println("ERROR: " + e.getMessage());
			}

			System.out.println("-----------------------------------");
		}

		{
			System.out.println("test4 OK ");
			Arrival arr = client.find(10L);

			System.out.println(arr.toString());

			List<Long> ids = generateContainerList(arr.getContainers());

			arr.setShipOrigin("SSSSSOOOOOO" + System.currentTimeMillis());

			try {
				client.update(arr, arr.getShip().getId(), ids);
				arr = client.find(10L);
				System.out.println(arr.toString());
			} catch (Exception e) {
				System.out.println("ERROR: " + e.getMessage());
			}

			System.out.println("-----------------------------------");
		}
		
		{
			System.out.println("test5 OK ");
			Arrival arr = client.find(10L);

			System.out.println(arr.toString());

			
			List<Long> idsorig = generateContainerList(arr.getContainers());
			List<Long> ids = Arrays.asList(6L);//generateContainerList(arr.getContainers());

			try {
				client.update(arr, arr.getShip().getId(), ids);
				arr = client.find(10L);
				System.out.println(arr.toString());
				
				client.update(arr, arr.getShip().getId(), idsorig);
			} catch (Exception e) {
				System.out.println("ERROR: " + e.getMessage());
			}

			System.out.println("-----------------------------------");
		}
		
		
		{
			System.out.println("test6 ERROR CONTAINER y date ");
			Arrival arr = client.find(10L);

			System.out.println(arr.toString());

			
			List<Long> idsorig = generateContainerList(arr.getContainers());
			List<Long> ids = Arrays.asList(6L, 1L);//generateContainerList(arr.getContainers());
			arr.setArrivalDate(sdf.parse("20140517"));
			
			try {
				client.update(arr, arr.getShip().getId(), ids);
				arr = client.find(10L);
				System.out.println(arr.toString());
				
				client.update(arr, arr.getShip().getId(), idsorig);
			} catch (Exception e) {
				System.out.println("ERROR: " + e.getMessage());
			}

			System.out.println("-----------------------------------");
		}
		
	}
	
	
	
	

	private List<Long> generateContainerList(List<Container> containers) {
		List<Long> ret = new ArrayList<>();
		if (containers != null) {
			for (Container container : containers) {
				ret.add(container.getId());
			}
		}
		return ret;
	}
}
