package model;
import java.time.LocalDate;

public class GiftCard extends Card{

    public double amount;

    public GiftCard(String cardId, String cardNumber, String cardHolderName, LocalDate expiryDate, Account account, double amount) {
        super(cardId ,cardNumber, cardHolderName, expiryDate, account);
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }


    public void validateAmount(double amount) throws Exception
    {
        if(amount > this.amount)
        {
            throw new Exception("Insuficient funds");
        }
    }

    @Override
    public void makePayment(double amount) {
        try{
            validateAmount(amount);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        //Only withdraw from the GiftCard amount:
        this.amount -= amount;
        System.out.println("Payment of " + amount + " was made successfully");
    }

    @Override
    public void makeDeposit(double amount)
    {
        //Only deposit to the GiftCard amount:
        this.amount += amount;
        System.out.println("Deposit of " + amount + " was made successfully");
    }

    @Override
    public void makeWithdrawal(double amount)
    {
        try{
            validateAmount(amount);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        //Only withdraw from the GiftCard amount:
        this.amount -= amount;
        System.out.println("Withdrawal of " + amount + " was made successfully");
    }

    @Override
    public void makeTransfer(double amount, Account account)
    {
        System.out.println("Transfer is not allowed for GiftCard");
    }

    @Override
    public String toString()
    {
        return "GiftCard{" +
                "cardId='" + cardId + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                ", cvv='" + cvv + '\'' +
                ", expirationDate=" + expirationDate +
                ", account=" + account +
                ", amount=" + amount +
                '}';
    }
}
