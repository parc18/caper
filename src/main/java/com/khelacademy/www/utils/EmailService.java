package com.khelacademy.www.utils;
import com.sendgrid.*;
public class EmailService {
	public static Integer sendEmailOTP(String content1, String toEmail) {
		Integer x = UserUtils.getRandomNumber(1000000);
	    Email from = new Email("manishpremi20@gmail.com");
	    String subject = "Please verify your account";
	    Email to = new Email(toEmail);
	    Content content = new Content("text/plain", "and easy to do anywhere, even with Java \n this is your OTP "+ x);
	    Mail mail = new Mail(from, subject, to, content);

	    SendGrid sg = new SendGrid("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");
	    Request request = new Request();
	    try {
	      request.setMethod(Method.POST);
	      request.setEndpoint("mail/send");
	      request.setBody(mail.build());
	      Response response = sg.api(request);
	      System.out.println(response.getStatusCode());
	      System.out.println(response.getBody());
	      System.out.println(response.getHeaders());
	      return x;
	    } catch (Exception ex) {
	      ex.printStackTrace();
	    }
	    return null;
	}
}
