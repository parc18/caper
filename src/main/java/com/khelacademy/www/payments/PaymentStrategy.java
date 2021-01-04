package com.khelacademy.www.payments;

import com.khelacademy.www.pojos.PaymentOrder;

public interface PaymentStrategy {
	
	public void order(PaymentOrder order);
	public void pay(int amount);

}
