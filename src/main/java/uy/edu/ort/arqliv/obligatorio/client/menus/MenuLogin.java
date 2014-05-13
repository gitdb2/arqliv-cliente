/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.edu.ort.arqliv.obligatorio.client.menus;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uy.edu.ort.arqliv.obligatorio.client.ContextSingleton;
import uy.edu.ort.arqliv.obligatorio.client.Keyin;
import uy.edu.ort.arqliv.obligatorio.client.services.clients.LoginServiceClient;

/**
 * 
 * @author rodrigo
 */
public class MenuLogin implements Renderer {

	private final Logger log = LoggerFactory.getLogger(MenuLogin.class);

	Map<String, Renderer> options = new HashMap<>();

	public void addOptions(String option, Renderer renderer) {
		this.options.put(option, renderer);
	}

	@Override
	public void render() {

		// System.out.print("\u001b[2J");
		// Display menu graphics
		System.out.println("============================");
		System.out.println("|       Identificacion     |");
		System.out.println("============================");
		// System.out.println("| Optiones:                |");
		// System.out.println("|        1. Login          |");
		// System.out.println("|        2. Salir          |");
		// System.out.println("============================");
		// swValue = Keyin.inInt(" Select option: ");

		String usuario = Keyin.inString(" Usuario: ");
		String password = Keyin.inString(" Password: ");

		// if ("1".equals(usuario) && "1".equals(password)) {
		//
		// System.out.println("Llendo al menu Principal");
		// } else {
		// System.out.println("Error: usuario o contrasena incorrecta");
		// }

		try {
			
			LoginServiceClient loginClient = (LoginServiceClient) ContextSingleton.getInstance().getBean("loginClient");
			
			
			
			boolean ok = loginClient.login(usuario, password);		
			if (ok) {
				System.out.println("Logueo Exitso");
				System.out.println("Llendo al menu Principal");
			} else {
				System.out.println("Error: usuario o contrasena incorrecta");
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.error("Problema al contactar al server",e);
			System.out.println("Error: Al contactar al servidor");
		}

		Keyin.inChar("presione enter tecla para continuar...");

	}

}
