package com.revature.controllers;
import org.apache.commons.io.FileUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.models.Reimbursement;
import com.revature.models.ReimbursementDTO;
import com.revature.models.ReimbursementStatus;
import com.revature.models.ReimbursementType;
import com.revature.models.User;
import com.revature.services.LoginService;
import com.revature.services.ReimbursementService;
import com.revature.services.ReimbursementStatusService;
import com.revature.services.ReimbursementTypeService;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public class ReimbursementController implements Controller {

	private ReimbursementService reimbursementService = new ReimbursementService();
	private static Logger log = LoggerFactory.getLogger(ReimbursementController.class);
	
	private Handler getReimbursementsByUser = (ctx) -> {
		if (ctx.req.getSession(false) != null) {
			
			String suserId =  ctx.pathParam("userid");
			int userId = Integer.parseInt(suserId);
			List<Reimbursement> list = reimbursementService.getReimbursementsByUserId(userId);
			
			ctx.json(list);
			log.info("Returning reimbursements to user.");
			ctx.status(200);
		}else {
			ctx.status(401);
		}
		
	};
	
	private Handler getReimbursementById = (ctx) -> {
		if (ctx.req.getSession(false) != null) {
			
			String sreimbursementId =  ctx.pathParam("reimbursementid");
			int reimbursementId = Integer.parseInt(sreimbursementId);
			Reimbursement reimbursement = reimbursementService.getReimbursementById(reimbursementId);
			
			ctx.json(reimbursement);
			log.info("Returning reimbursement for action id = " + reimbursement.getReimbursementId());
			ctx.status(200);
		}else {
			ctx.status(401);
		}
		
	};
	
	private Handler getReimbursementsByStatus = (ctx) -> {
		if (ctx.req.getSession(false) != null) {
			
			String sstatusid =  ctx.pathParam("statusid");
			int statusid = Integer.parseInt(sstatusid);
			List<Reimbursement> list = reimbursementService.getReimbursementsByStatusId(statusid);
			
			ctx.json(list);
			log.info("Returning pending reimbursements to manager.");
			ctx.status(200);
		}else {
			ctx.status(401);
		}
		
	};
	
	private Handler addReimbursement = (ctx) -> {
		if (ctx.req.getSession(false) != null) {
			ReimbursementDTO reimbursementDTO = ctx.bodyAsClass(ReimbursementDTO.class);
			
			Reimbursement reimbursement = new Reimbursement();
			reimbursement.setAmount(reimbursementDTO.amount);
			reimbursement.setDescription(reimbursementDTO.description);
			if(reimbursementDTO.receipt.length() > 0) {
				reimbursement.setReceipt(reimbursementDTO.receipt);
			}
			
			ReimbursementTypeService rtService = new ReimbursementTypeService();
			ReimbursementType reimbursementType = rtService.getReimbursementById(reimbursementDTO.typeId);
			
			reimbursement.setReimbursementType(reimbursementType);
			
			ReimbursementStatusService rsService = new ReimbursementStatusService();
			ReimbursementStatus reimbursementStatus = rsService.getReimbursementStatusById(reimbursementDTO.statusId);
			reimbursement.setReimbursementStatus(reimbursementStatus);
			
			LoginService ls = new LoginService();
			
			String userName = ctx.cookieStore("userName").toString();
			User user = ls.getUserByUserName(userName);
			
			reimbursement.setAuthor(user);
			reimbursement.setSubmitted(new Date());
			log.info("Added new reimbursement to database.");
			if (reimbursementService.addReimbursement(reimbursement)) {
				ctx.status(201);
			} else {
				log.warn("Adding reimbursement failed.");
				ctx.status(400);
			}
		} else {
			ctx.status(401);
		}
		
	};
	
	private Handler updateReimbursement = (ctx) -> {
		if (ctx.req.getSession(false) != null) {
			ReimbursementDTO reimbursementDTO = ctx.bodyAsClass(ReimbursementDTO.class);
			Reimbursement reimbursement = reimbursementService.getReimbursementById(reimbursementDTO.reimbursementId);
			ReimbursementStatusService rsService = new ReimbursementStatusService();
			ReimbursementStatus reimbursementStatus = rsService.getReimbursementStatusById(reimbursementDTO.statusId);
			reimbursement.setResolved(new Date());
			
			LoginService ls = new LoginService();
			
			String userName = ctx.cookieStore("userName").toString();
			User user = ls.getUserByUserName(userName);
			
			reimbursement.setResolver(user);
			reimbursement.setReimbursementStatus(reimbursementStatus);
			if (reimbursementService.updateReimbursement(reimbursement)) {
				log.info("Updating reimbursement status. Id = " + reimbursementDTO.reimbursementId);
				ctx.status(201);
			} else {
				log.warn("Reimbursement update failed. Id = " + reimbursementDTO.reimbursementId);
				ctx.status(400);
			}
		}else {
			ctx.status(401);
		}
		
	};
	
	public Handler uploadFile = (ctx) -> {
		ctx.uploadedFiles("receipt").forEach(file -> {
	        try {
	        	System.out.println(file.getFilename());
				FileUtils.copyInputStreamToFile(file.getContent(), new File("upload/" + file.getFilename()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    });
	};
	
	public Handler downloadFile = (ctx) -> {
		
		File localFile = new File("upload/" + ctx.pathParam("filename"));
        InputStream inputStream = new BufferedInputStream(new FileInputStream(localFile));
        ctx.header("Content-Disposition", "attachment; filename=\"" + localFile.getName() + "\"");
        ctx.header("Content-Length", String.valueOf(localFile.length()));
        ctx.result(inputStream);
	};
	
	@Override
	public void addRoutes(Javalin app) {
		// TODO Auto-generated method stub
		app.get("/reimbursements/:reimbursementid", this.getReimbursementById);
		app.get("/reimbursements/user/:userid", this.getReimbursementsByUser);
		app.get("/reimbursements/status/:statusid", this.getReimbursementsByStatus);
		app.post("/reimbursements", this.addReimbursement);
		app.post("/upload", this.uploadFile);
		app.get("/download/:filename", this.downloadFile);
		app.patch("/reimbursements", this.updateReimbursement);
	}

}
