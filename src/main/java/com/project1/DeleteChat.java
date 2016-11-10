package com.project1;

import java.io.IOException;
import java.util.List;

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

/**
 * Servlet implementation class DeleteChat
 */
public class DeleteChat extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public DeleteChat() {
        super();
        // TODO Auto-generated constructor stub
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uid = request.getParameter("userid");

		int chatId = Integer.parseInt(request.getParameter("chatID"));
		Session session = null;
        Transaction tx = null;
        
        try 
        {
        	Connector conn = new Connector();
            session = conn.configureSessionFactory().openSession();
            tx = session.beginTransaction();
            
            String hsql = "delete from Chat where id = :chatID";
            Query query = session.createQuery(hsql);
            query.setParameter("chatID", chatId);
            int res = query.executeUpdate();
            
            
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
        	
        	response.sendRedirect("messageBoard.jsp?userid="+uid);
        }
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
