package com.project1;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.SecureRandom;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nimbusds.oauth2.sdk.ResponseType;
import com.nimbusds.oauth2.sdk.Scope;
import com.nimbusds.oauth2.sdk.id.ClientID;
import com.nimbusds.oauth2.sdk.id.State;
import com.nimbusds.openid.connect.sdk.AuthenticationRequest;
import com.nimbusds.openid.connect.sdk.Nonce;


/**
 * Servlet implementation class LoginRedirection
 */
public class LoginRedirection extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public LoginRedirection() 
    {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String realm = request.getParameter("direction");
		if(realm.equalsIgnoreCase("Google"))
		{
			//REDIRECT THEM TO GOOGLE
			URI redirectURI=null;
			
			try {
				redirectURI = new URI("http://localhost:8080/project1/ReturnGoogle");
			}
			
			catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			URI authURI=null;
			
			try {
				authURI = new URI("https://accounts.google.com/o/oauth2/v2/auth");
			}
			
			catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			ResponseType rt = new ResponseType("code");
			Scope scope = new Scope("openid", "email", "profile");
			ClientID clientID = new ClientID("664700022174-tkgm8ehfjl4sieruvsi1chqkassg6n6p.apps.googleusercontent.com");
			String state1 = new BigInteger(130, new SecureRandom()).toString(32);
			request.getSession().setAttribute("state",state1);
			State state=new State(state1);
			Nonce nonce = null; 
			AuthenticationRequest authRequest = new AuthenticationRequest(
			redirectURI, rt, scope, clientID, redirectURI, state, nonce);
			URI parameterizedRedirectURI=null;
			
			try {
				parameterizedRedirectURI = new URI(authURI.toString() + "?" +authRequest.toQueryString());
			}
			catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			String redirectString = parameterizedRedirectURI.toString();
			
			response.sendRedirect(redirectString);
		}
		else if(realm.equalsIgnoreCase("Github"))
		{
			//REDIRECT THEM TO Paypal
			String appId = "3fc8c836208f5da2ffa9";
			String redirectUrl = "http://localhost:8080/project1/ReturnPaypal";
			String returnValue = "https://github.com/login/oauth/authorize?client_id="+ appId + "&redirect_uri=" + redirectUrl+ "&scope=user:email";
	        response.sendRedirect(returnValue);
		}
		else if(realm.equalsIgnoreCase("Facebook"))
		{
			//REDIRECT THEM TO Facebook
		}		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	

}
