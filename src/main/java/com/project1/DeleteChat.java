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
		
		Session session = null;
        Transaction tx = null;
        String forward="messageBoard.jsp";
        HttpSession session1 = request.getSession(false);
        String uid1=null;
		try{
			uid1=session1.getAttribute("uid").toString();
			if(uid1==null){
            	forward = "http://192.168.12.16:8080/project1/index.jsp";
            	return;
            }
		}catch (Exception ex)
		{
        	forward = "http://192.168.12.16:8080/project1/Error.jsp";
			return;
		}
        try 
        {
           
        	Connector conn = new Connector();
            session = conn.configureSessionFactory().openSession();
            tx = session.beginTransaction();
    		
    		if(uid.equals(uid1)==false){
            	forward = "messageBoard.jsp";
    			return;
    		}
    		int uid2;
    		int chatId;
    		
    		try{
    		uid2 = Integer.parseInt(uid1);
    		chatId = Integer.parseInt(request.getParameter("chatID"));
    		}catch (Exception ex){
            	forward = "http://192.168.12.16:8080/project1/Error.jsp";
    			return;
    		}
            String hsql = "delete from Chat where id = :chatID and user_id = :uid";
            Query query = session.createQuery(hsql);
            query.setParameter("chatID", chatId);
            query.setParameter("uid", uid2);
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
        	else{
				forward="http://192.168.12.16:8080/project1/index.jsp";
			}
        	response.sendRedirect(forward);
        	
        }
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
