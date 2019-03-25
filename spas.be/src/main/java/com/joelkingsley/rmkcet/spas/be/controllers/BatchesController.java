package com.joelkingsley.rmkcet.spas.be.controllers;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.joelkingsley.rmkcet.spas.be.beans.Batch;
import com.joelkingsley.rmkcet.spas.be.constants.ErrorConstants;
import com.joelkingsley.rmkcet.spas.be.delegates.BatchesDelegate;
import com.joelkingsley.rmkcet.spas.be.utils.AppError;

@RestController
public class BatchesController {
BatchesDelegate batchesDelegate;
	
	public BatchesController() {
		super();
		this.batchesDelegate = new BatchesDelegate();
	}
	
	@GetMapping("/batches")
	ResponseEntity<?> getAllBatches() {
		try {
			ArrayList<Batch> batches = batchesDelegate.getAllBatches();
			if(batches == null) {
				ResponseEntity<String> responseEntity = new ResponseEntity<String>(ErrorConstants.BATCHES_NOT_FOUND, HttpStatus.NOT_FOUND);
				return responseEntity;
			} else {
				ResponseEntity<ArrayList<Batch>> responseEntity = new ResponseEntity<ArrayList<Batch>>(batches, HttpStatus.FOUND);
				return responseEntity;
			}
		} catch (AppError appError) {
			appError.getException().printStackTrace();
			ResponseEntity<String> responseEntity = new ResponseEntity<String>(appError.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			return responseEntity;
		}
	}
	
	@PostMapping("batches")
	ResponseEntity<String> addBatch(@RequestBody Batch batch) {
		 try { 
			 boolean added = batchesDelegate.addBatch(batch); 
			 if (added) {		
				 ResponseEntity<String> responseEntity = new ResponseEntity<String>("Added", HttpStatus.OK); 
				 return responseEntity; 
			 } else { 
				 ResponseEntity<String>
				 responseEntity = new ResponseEntity<String>(ErrorConstants.BATCH_NOT_ADDED, HttpStatus.INTERNAL_SERVER_ERROR); 
				 return responseEntity; 
			} 
		} catch (AppError appError) { 
			appError.getException().printStackTrace(); 
			ResponseEntity<String> responseEntity = new ResponseEntity<String>(appError.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); 
			return responseEntity; 
		}
	}
}


