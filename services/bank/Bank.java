/*
	The bank manage the client's bank account
*/

public class Bank {

	private int credit = 25;

	// Transfer money to the account
	public int CreditAccount(int amount){
		this.credit += amount;
		return this.credit;
	}

	// Consult the current credit
	public int GetCredit(){
		return this.credit;
	}

	// Make an online payment 
	public boolean MakeOnlinePayment(int amount){
		int remainingCredit = this.credit - amount;

		if(remainingCredit >= 0){
			this.credit = remainingCredit;
			return true;
		}

		return false;
	}
}