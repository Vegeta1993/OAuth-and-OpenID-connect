package com.project1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URI;
import java.net.URL;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.db.Connector;
import com.db.User;

import sun.net.www.http.HttpClient;

/**
 * Servlet implementation class ReturnPaypal
 */
public class ReturnPaypal extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public ReturnPaypal() {
        super();

    }

	
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	/**
		 * 
		 * 
		 * DO YOUR OPENID MAGIC HERE
		 * 
		 * 
		 */
		
		
		/* ---- CHECK TO OUR DB BEGIN ----*/
		String name = "Stewart Sentanoe"; //Change this parameter based what you need
		String email = "ss@paypal.com"; //Change this parameter based what you need
		String realm = "PAYPAL"; //Change this parameter based what you need
		
		Session session = null;
        Transaction tx = null;
		try 
		{
			Connector conn = new Connector();
            session = conn.configureSessionFactory().openSession();
            tx = session.beginTransaction();
            
            String hsql = "from User where email = :user_email and realm = :login_realm";
            Query query = session.createQuery(hsql);
            query.setParameter("user_email", email);
            query.setParameter("login_realm", realm);
            List<User> result = query.list();
            
            if(result.size() != 0)
            {
            	//USER IS EXIST, DO THE AUTHORIZATION METHOD
            	for(User u : result)
            	{
            		 System.out.println("Id: " + u.getId() + " | Name:"  + u.getName() + " | Email:" + u.getEmail() + " | Realm:" + u.getRealm());
            		 
        		 	/**
        			 * 
        			 * 
        			 * DO YOUR AUTHORIZATION MAGIC HERE
        			 * 
        			 * 
        			 */
            	}
            }
            else
            {
            	//USER IS NOT EXIST, STORE IN DB
            	try
            	{
            		User newUser = new User(0, name, email,realm);
            		session.save(newUser);
            	}
            	catch(Exception ex)
            	{
            		ex.printStackTrace();
            		tx.rollback();
            	}
            	
            	/**
    			 * 
    			 * 
    			 * DO YOUR AUTHORIZATION MAGIC HERE
    			 * 
    			 * 
    			 */
            }
        } 
		catch (Exception ex) 
		{
			
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
			response.sendRedirect("messageBoard.jsp");
        }
		/* ---- CHECK TO OUR DB END ----*/
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		doGet(request, response);
	}
	
	

}
