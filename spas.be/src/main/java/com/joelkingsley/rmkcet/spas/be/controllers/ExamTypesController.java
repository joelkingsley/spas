package com.joelkingsley.rmkcet.spas.be.controllers;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.joelkingsley.rmkcet.spas.be.beans.ExamType;
import com.joelkingsley.rmkcet.spas.be.constants.ErrorConstants;
import com.joelkingsley.rmkcet.spas.be.delegates.ExamTypesDelegate;
import com.joelkingsley.rmkcet.spas.be.utils.AppError;

@RestController
public class ExamTypesController {
	

ExamTypesDelegate examTypesDelegate;

public ExamTypesController() {
	super();
	this.examTypesDelegate = new ExamTypesDelegate();
}



@GetMapping("/examTypes")
ResponseEntity<?> getAllExamTypes() {
	try {
		ArrayList<ExamType> examTypes = examTypesDelegate.getAllExamTypes();
		if(examTypes.size() == 0) {
			ResponseEntity<String> responseEntity = new ResponseEntity<String>(ErrorConstants.EXAM_TYPES_NOT_FOUND, HttpStatus.NOT_FOUND);
			return responseEntity;
		} else {
			ResponseEntity<ArrayList<ExamType>> responseEntity = new ResponseEntity<ArrayList<ExamType>>(examTypes, HttpStatus.OK);
			return responseEntity;
		}
	} catch (AppError appError) {
		appError.getException().printStackTrace();
		ResponseEntity<String> responseEntity = new ResponseEntity<String>(appError.getErrorMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		return responseEntity;
	}
}


@PostMapping("/examTypes")
ResponseEntity<?> addexamtype(@RequestBody ExamType examType) {
	 try { 
		 ExamType addedExamType = examTypesDelegate.addExamType(examType); 
		 ResponseEntity<ExamType> responseEntity = new ResponseEntity<ExamType>(addedExamType, HttpStatus.OK); 
		 return responseEntity;

	} catch (AppError appError) { 
		appError.getException().printStackTrace(); 
		ResponseEntity<String> responseEntity = new ResponseEntity<String>(appError.getErrorMessage(), HttpStatus.INTERNAL_SERVER_ERROR); 
		return responseEntity; 
	}
}
}

