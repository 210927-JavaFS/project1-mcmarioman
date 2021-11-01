package com.revature.util;

/*
 * Testing 10/10 Service layer methods
 * 100% coverage
 * 
 * */

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.revature.models.ReimbursementStatus;
import com.revature.models.ReimbursementType;
import com.revature.models.User;
import com.revature.services.LoginService;
import com.revature.services.ReimbursementService;
import com.revature.services.ReimbursementStatusService;
import com.revature.services.ReimbursementTypeService;
import com.revature.models.UserDTO;
import com.revature.models.Reimbursement;

public class ProjectOneUnitTest {
	public static LoginService loginService = new LoginService();
	public static ReimbursementService reimbursementService = new ReimbursementService();
	public static ReimbursementStatusService reimbursementStatusService = new ReimbursementStatusService();
	public static ReimbursementTypeService reimbursementTypeService = new ReimbursementTypeService();
	public static Logger log = LoggerFactory.getLogger(ProjectOneUnitTest.class);
	public static User testUser;
	public static UserDTO userDto;
	public static boolean result;
	
	@BeforeAll
	public static void setInitialValues() {
		userDto = new UserDTO();
		userDto.username = "mario.vidal";
		userDto.password = "password";
		testUser = loginService.getUserByUserName(userDto.username);
	}
	
	@Test
	public void testLogin() {
		log.info("In testLogin");
		result = loginService.login(userDto);
		assertTrue(result);
	}
	@Test
	public void testGetUserByUserName() {
		log.info("In testGetUserByUserName");
		User user = loginService.getUserByUserName(userDto.username);
		assertEquals(userDto.username, user.getUserName());
	}
	
	@Test
	public void TestGetUserById() {
		log.info("In TestGetUserById");
		User user2 = loginService.getUserById(testUser.getUserId());
		assertEquals(testUser.getUserName(), user2.getUserName());
	}
	
	@Test
	public void TestGetReimbursementTypeById() {
		log.info("In TestGetReimbursementTypeById");
		ReimbursementType reimbursementType = reimbursementTypeService.getReimbursementById(1);
		assertEquals(reimbursementType.getTypeName(), "LODGING");
	}
	
	@Test
	public void TestGetReimbursementStatusById() {
		log.info("In TestGetReimbursementStatusById");
		ReimbursementStatus reimbursementStatus = reimbursementStatusService.getReimbursementStatusById(1);
		assertEquals(reimbursementStatus.getStatusName(), "Pending");
	}
	
	@Test
	public void TestGetReimbursementsByUserId() {
		log.info("In TestGetReimbursementsByUserId");
		List<Reimbursement> list = reimbursementService.getReimbursementsByUserId(testUser.getUserId());
		assertEquals(testUser.getUserId(), list.get(0).getAuthor().getUserId());
	}
	
	@Test
	public void TestAddReimbursement() {
		log.info("In TestAddReimbursement");
		Reimbursement reimbursement = new Reimbursement();
		reimbursement.setAmount(999);
		reimbursement.setDescription("Testing add reimbursement method");
		reimbursement.setAuthor(testUser);
		reimbursement.setReimbursementType(reimbursementTypeService.getReimbursementById(1));
		reimbursement.setSubmitted(new Date());
		reimbursement.setReimbursementStatus(reimbursementStatusService.getReimbursementStatusById(1));
		assertTrue(reimbursementService.addReimbursement(reimbursement));
	}
	
	@Test void TestGetReimbursementsByStatusId() {
		log.info("In TestGetReimbursementsByStatusId");
		List<Reimbursement> list = reimbursementService.getReimbursementsByStatusId(1);
		assertEquals(list.get(0).getReimbursementStatus().getStatusId(), 1);
	}
	
	@Test void TestGetReimbursementById() {
		log.info("In TestGetReimbursementById");
		List<Reimbursement> list = reimbursementService.getReimbursementsByUserId(testUser.getUserId());
		Reimbursement reimbursement = reimbursementService.getReimbursementById(list.get(0).getReimbursementId());
		assertEquals(list.get(0).getReimbursementId(), reimbursement.getReimbursementId());
	}
	
	@Test void TestupdateReimbursement() {
		log.info("In TestupdateReimbursement");
		List<Reimbursement> list = reimbursementService.getReimbursementsByStatusId(1);
		Reimbursement reimbursement = list.get(0);
		reimbursement.setReimbursementStatus(reimbursementStatusService.getReimbursementStatusById(2));
		assertTrue(reimbursementService.updateReimbursement(reimbursement));
	}
	
	@AfterAll
	public static void clearProjectOneUtil() {
		userDto = null;
		testUser = null;
		log.info("Unit tests completed");
	}
	
}
