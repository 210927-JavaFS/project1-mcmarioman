package com.revature.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import com.revature.models.User;
import com.revature.models.UserDTO;
import com.revature.services.LoginService;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public class UserController implements Controller{
	
	private LoginService loginService = new LoginService();
	private static Logger log = LoggerFactory.getLogger(UserController.class);

	private Handler loginAttempt = (ctx) -> {
		UserDTO userDto = ctx.bodyAsClass(UserDTO.class);
		log.info("User attempts to login.");
		if(loginService.login(userDto)) {
			//If someone logs in I will create a session
			ctx.req.getSession(); //This will create a session for us to track the client that logged in. 
			ctx.cookieStore("userName", userDto.username);
			MDC.put("User Name", userDto.username);
			log.info("User logged in.");
			ctx.status(200);
		}else {
			log.warn("Invalid credentials.");
			ctx.req.getSession().invalidate();// invalidates any open session tracking the client.
			ctx.status(401);
		}
	};
	
	public Handler validateSession = (ctx) -> {
		if (ctx.req.getSession(false) != null) {
			String userName = ctx.cookieStore("userName").toString();
			
			User user = loginService.getUserByUserName(userName);
			MDC.put("User Name", userName);
			ctx.json(user);
			log.info("Credentials checked ok.");
			ctx.status(200);
		}else {
			log.warn("Unauthorized user attempt.");
			ctx.status(401);
		}
	};
	
	public Handler logOut = (ctx) -> {
		if (ctx.req.getSession(false) != null) {
			log.info("User logged out.");
			ctx.req.getSession().invalidate();
		}
		ctx.status(410);
	};
	
	
	@Override
	public void addRoutes(Javalin app) {
		app.post("/login", this.loginAttempt);
		app.get("/loggeduser", this.validateSession);
		app.post("/logout", this.logOut);
	}
}
