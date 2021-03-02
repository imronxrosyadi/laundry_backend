package com.lawencon.laundry.service;

import org.springframework.transaction.support.TransactionTemplate;

/**
 * @author Imron Rosyadi
 */

public class BaseService {

	private TransactionTemplate transactionTemplate;

	public TransactionTemplate getTransactionTemplate() {
		return transactionTemplate;
	}

	public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
		this.transactionTemplate = transactionTemplate;
	}

}
