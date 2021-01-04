package com.khelacademy.www.payments;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.khelacademy.www.pojos.PaymentOrder;
import com.khelacademy.www.pojos.RazorPayOrder;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

@Component
public class RazorPayPayment implements PaymentStrategy {
	private static final Logger LOGGER = LoggerFactory.getLogger(RazorPayPayment.class);

	@Autowired
	RazorpayClient razorpayClient;
	@Override
	public void pay(int amount) {
		// TODO Auto-generated method stub

	}

	@Override
	public void order(PaymentOrder order) {
		//RazorPayOrder rOrder = (RazorPayOrder) order;
		//rOrder.getAmount();
		JSONObject orderRequest = new JSONObject();
		orderRequest.put("amount", 50000); // amount in the smallest currency unit
		orderRequest.put("currency", "INR");
		orderRequest.put("receipt", "order_rcptid_11");
		try {
			Order orderResponse = razorpayClient.Orders.create(orderRequest);
			//razorpayClient.Payments.capture(id, request)
			LOGGER.info(orderResponse.get("id"));
			
		} catch (RazorpayException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

}
