/*
	The bank manage the client's bank account
*/

public class Bank {

	private int credit = 25;

	// Transfer money to the account
	public void creditAccount(int amount){
		this.credit += amount;
	}

	// Consult the current credit
	public int getCredit(){
		return this.credit;
	}

	// Make an online payment 
	public boolean makeOnlinePayment(int amount){
		int remainingCredit = this.creadit - amount;

		if(remainingCredit >= 0){
			this.credit = remainingCredit;
			return true;
		}

		return false;
	}
}