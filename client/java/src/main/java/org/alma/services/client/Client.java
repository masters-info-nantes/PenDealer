package org.alma.services.client;

import org.apache.axis2.AxisFault;

import org.apache.ws.axis2.BankStub;

import org.apache.ws.axis2.BankStub.*;

import java.rmi.RemoteException;

public class Client {
	public static void main(String[] args) {
		try {
			BankStub stub = new BankStub("http://192.168.2.2:9763/services/Bank/");

			GetCredit credit1 = new GetCredit();
			GetCreditResponse response1 = stub.getCredit(credit1);
			System.out.println("Reponse from Axis2 Service: " + response1.get_return());

			MakeOnlinePayment payment = new MakeOnlinePayment();
			payment.setArgs0(10);
			MakeOnlinePaymentResponse response2 = stub.makeOnlinePayment(payment);
			System.out.println("Reponse from Axis2 Service: " + response2.get_return());			

			GetCredit credit2 = new GetCredit();
			GetCreditResponse response3 = stub.getCredit(credit2);
			System.out.println("Reponse from Axis2 Service: " + response3.get_return());			

		} 
		catch (AxisFault e) { e.printStackTrace(); } 
		catch (RemoteException e) {	e.printStackTrace(); }
	}
}