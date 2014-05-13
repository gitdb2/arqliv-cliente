/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uy.edu.ort.arqliv.obligatorio.client;

import uy.edu.ort.arqliv.obligatorio.client.menus.MenuFirstLoader;
import uy.edu.ort.arqliv.obligatorio.client.menus.MenuLogin;
import uy.edu.ort.arqliv.obligatorio.client.menus.Renderer;
import uy.edu.ort.arqliv.obligatorio.client.menus.types.MenuRendererEnum;

/**
 * 
 * @author rodrigo
 */
public class DemoConsole {

	public static void main(String[] args) {

		ContextSingleton.getInstance().init();
		
		// Local variable
//		Renderer login = new MenuLogin();

		MenuFirstLoader menuPrincipal = new MenuFirstLoader();
//		menuPrincipal.addOptions(MenuRendererEnum.MAIN_MENU, login);

		menuPrincipal.render();

	}
}
