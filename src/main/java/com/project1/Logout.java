package com.project1;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.db.Chat;
import com.db.Connector;

/**
 * Servlet implementation class Logout
 */
public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public Logout() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/**
		 * 
		 * 
		 * DO YOUR DEAUTHORIZATION MAGIC HERE
		 * 
		 * 
		 */
		
		
		
		//FOR DELETE ALL DATA
		/*
		Session session = null;
        Transaction tx = null;
        
        try 
        {
        	Connector conn = new Connector();
            session = conn.configureSessionFactory().openSession();
            tx = session.beginTransaction();
             
            session.createQuery("delete from User").executeUpdate();
            session.createQuery("delete from Chat").executeUpdate();
        } 
        catch (Exception ex) 
        {
        	tx.rollback();
            ex.printStackTrace();
        } 
        finally
        {
        	if(session != null && session.isOpen())
			{
        		tx.commit();
				session.flush();
				session.close();
			}
        }*/
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
