package org.alma.services.client;

import org.apache.axis2.AxisFault;
import org.apache.ws.axis2.BankStub;
import org.apache.ws.axis2.BankStub.GetCredit;
import org.apache.ws.axis2.BankStub.GetCreditResponse;

import java.rmi.RemoteException;

public class Client {
	public static void main(String[] args) {
		try {
			//create service stub with the endpoint of the sercer
			BankStub stub=new BankStub("http://192.168.2.2:9763/services/Bank/");
			//calling for addition operation in the service
			GetCredit add=new GetCredit();
			
			//consume the service using stub and get the response
			GetCreditResponse response=stub.getCredit(add);

			System.out.println("Reponse from Axis2 Service: "+response.get_return());
		} catch (AxisFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}