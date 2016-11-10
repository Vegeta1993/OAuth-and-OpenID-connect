package com.db;

import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class Connector {
	
	public Connector()
	{
		
	}
	
	public SessionFactory configureSessionFactory() throws HibernateException {  
        Configuration configuration = new Configuration();  
        configuration.configure();  
         
        Properties properties = configuration.getProperties();
         
        ServiceRegistry builder = new ServiceRegistryBuilder().applySettings(properties).buildServiceRegistry(); 
        SessionFactory factory = configuration.buildSessionFactory(builder);
         
        return factory;  
    }
}
