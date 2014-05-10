package uy.edu.ort.arqliv.obligatorio.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ContextSingleton {
	
	private final Logger log = LoggerFactory.getLogger(ContextSingleton.class);

	private static ContextSingleton instance = new ContextSingleton();
	private ApplicationContext ctx;
	private ContextSingleton(){
		ctx = new ClassPathXmlApplicationContext(new String[] { "client-remoting.xml" });
	}
	
	public static ContextSingleton getInstance(){
		return instance;
	}

	public void init() {
		log.info("Inicializado");
	}
	
	public Object getBean(String beanName){
		return ctx.getBean(beanName);
	}
	
}
