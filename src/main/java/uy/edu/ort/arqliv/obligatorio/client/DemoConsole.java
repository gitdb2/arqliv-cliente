/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.edu.ort.arqliv.obligatorio.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uy.edu.ort.arqliv.obligatorio.client.menus.MenuFirstLoader;

/**
 * 
 * @author rodrigo
 */
public class DemoConsole {
	private static final Logger log = LoggerFactory.getLogger(DemoConsole.class);

	public static void main(String[] args)throws Exception {

		try {
			ContextSingleton.getInstance().init();
			MenuFirstLoader menuPrincipal = new MenuFirstLoader();
			menuPrincipal.render();

		} catch (java.lang.ExceptionInInitializerError e) {
			log.error(e.getCause().getMessage(), e);
			System.out.println("Se produjo un error Al inicializar los beans remotos:\n "+e.getCause().getMessage());
//			try {
//				throw e.getCause();
//			} catch (java.net.ConnectException e2) {
//				// TODO: handle exception
//			}
	

		}catch (Exception e) {
			log.error(e.getMessage(), e);
			System.out.println("Se produjo un error: "+e.getMessage());
		}

	
	}
}
