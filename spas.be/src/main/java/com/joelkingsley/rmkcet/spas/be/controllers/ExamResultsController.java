package com.joelkingsley.rmkcet.spas.be.controllers;

import java.math.BigInteger;
import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.joelkingsley.rmkcet.spas.be.beans.ExamResult;
import com.joelkingsley.rmkcet.spas.be.beans.requests.AddExamResultRequest;
import com.joelkingsley.rmkcet.spas.be.constants.ErrorConstants;
import com.joelkingsley.rmkcet.spas.be.delegates.ExamResultsDelegate;
import com.joelkingsley.rmkcet.spas.be.utils.AppError;

@RestController
public class ExamResultsController {

ExamResultsDelegate examResultsDelegate;
	
	public ExamResultsController() {
		super();
		this.examResultsDelegate = new ExamResultsDelegate();
	}

	@GetMapping("/examResults")
	ResponseEntity<?> getAllExamResults() {
		try {
			ArrayList<ExamResult> examResults = examResultsDelegate.getAllExamResults();
			if(examResults.size() == 0) {
				ResponseEntity<String> responseEntity = new ResponseEntity<String>(ErrorConstants.EXAM_RESULTS_NOT_FOUND, HttpStatus.NOT_FOUND);
				return responseEntity;
			} else {
				ResponseEntity<ArrayList<ExamResult>> responseEntity = new ResponseEntity<ArrayList<ExamResult>>(examResults, HttpStatus.OK);
				return responseEntity;
			}
		} catch (AppError appError) {
			appError.getException().printStackTrace();
			ResponseEntity<String> responseEntity = new ResponseEntity<String>(appError.getErrorMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			return responseEntity;
		}
	}
	
	@GetMapping("/examResults/{registerNumber}")
	ResponseEntity<?> getAllExamResultsOfRegisterNumber(@PathVariable BigInteger registerNumber) {
		try {
			ArrayList<ExamResult> examResults = examResultsDelegate.getAllExamResultsOfRegisterNumber(registerNumber);
			if(examResults.size() == 0) {
				ResponseEntity<String> responseEntity = new ResponseEntity<String>(ErrorConstants.EXAM_RESULTS_NOT_FOUND, HttpStatus.NOT_FOUND);
				return responseEntity;
			} else {
				ResponseEntity<ArrayList<ExamResult>> responseEntity = new ResponseEntity<ArrayList<ExamResult>>(examResults, HttpStatus.OK);
				return responseEntity;
			}
		} catch (AppError appError) {
			appError.getException().printStackTrace();
			ResponseEntity<String> responseEntity = new ResponseEntity<String>(appError.getErrorMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			return responseEntity;
		}
	}
	
	@PostMapping("/examResults")
	ResponseEntity<?> addExamResult(@RequestBody AddExamResultRequest addExamResultRequest) {
		try { 
			 AddExamResultRequest addedExamResult = examResultsDelegate.addExamResult(addExamResultRequest); 
			 ResponseEntity<AddExamResultRequest> responseEntity = new ResponseEntity<AddExamResultRequest>(addedExamResult, HttpStatus.OK); 
			 return responseEntity;
	
		} catch (AppError appError) { 
			appError.getException().printStackTrace(); 
			ResponseEntity<String> responseEntity = new ResponseEntity<String>(appError.getErrorMessage(), HttpStatus.INTERNAL_SERVER_ERROR); 
			return responseEntity; 
		}
	}
	
}
