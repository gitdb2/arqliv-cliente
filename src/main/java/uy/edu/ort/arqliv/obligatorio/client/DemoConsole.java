/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.edu.ort.arqliv.obligatorio.client;

import uy.edu.ort.arqliv.obligatorio.client.menus.MenuFirstLoader;

/**
 * 
 * @author rodrigo
 */
public class DemoConsole {

	public static void main(String[] args) {

		ContextSingleton.getInstance().init();

		MenuFirstLoader menuPrincipal = new MenuFirstLoader();
		menuPrincipal.render();

	}
}
