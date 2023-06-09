package model;

import exception.InsuficientFundsException;
import repository.AccountRepository;
import util.CompareAmounts;

import java.time.LocalDate;
import java.util.Collections;

public class DebitCard extends Card{

    public DebitCard(String cardId, int customerId, String cardNumber, String cvv, LocalDate expirationDate, Account account) {
        super(cardId, customerId, cardNumber, cvv, expirationDate, account);
    }
    @Override
    public void makePayment(double amount) {
        try{
            CompareAmounts.validateAmount(amount, this.getAccount().getBalance());
            this.getAccount().setBalance(this.getAccount().getBalance() - amount);
            AccountRepository.updateAccountInDatabase(this.getAccount());
            Payment payment = new Payment(amount, LocalDate.now(), "Payment from account " + this.getAccount() + " using Debit Card " + this + " of amount " + amount, this.getAccount().getAccountId());
            int pos = Collections.binarySearch(this.getAccount().getTransactionList(), payment);
            if(pos < 0){
               pos = -pos - 1;
            }
            this.getAccount().getTransactionList().add(pos, payment);

            System.out.println("Payment of " + amount + " was made successfully");
        } catch (InsuficientFundsException e) {
            System.out.println(e.getMessage());
        }
        }

    @Override
    public void makeWithdrawal(double amount) {
        try{
            CompareAmounts.validateAmount(amount, this.getAccount().getBalance());
            this.getAccount().setBalance(this.getAccount().getBalance() - amount);
            AccountRepository.updateAccountInDatabase(this.getAccount());
            Withdrawal withdrawal = new Withdrawal(amount, LocalDate.now(), "Withdrawal from account " + this.getAccount() +" using Debit Card " + this + " of amount " + amount, this.getAccount().getAccountId());
            int pos = Collections.binarySearch(this.getAccount().getTransactionList(), withdrawal);
            if(pos < 0){
               pos = -pos - 1;
            }
            this.getAccount().getTransactionList().add(pos, withdrawal);

            System.out.println("Withdrawal of " + amount + " was made successfully");
        } catch (InsuficientFundsException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void makeTransfer(double amount, Account account) {
        try{
            CompareAmounts.validateAmount(amount, this.getAccount().getBalance());
            this.getAccount().setBalance(this.getAccount().getBalance() - amount);
            AccountRepository.updateAccountInDatabase(this.getAccount());
            account.setBalance(account.getBalance() + amount);
            AccountRepository.updateAccountInDatabase(account);
            Transfer transfer = new Transfer(amount, LocalDate.now(), "Transfer from account " + this.getAccount() + " to account " + account +"using Debit Card " + this + " of amount " + amount, this.getAccount().getAccountId(), account.getAccountId());
            int pos = Collections.binarySearch(this.getAccount().getTransactionList(), transfer);
            if(pos < 0){
               pos = -pos - 1;
            }
            this.getAccount().getTransactionList().add(pos, transfer);

            pos = Collections.binarySearch(account.getTransactionList(), transfer);
            if(pos < 0){
               pos = -pos - 1;
            }
            account.getTransactionList().add(pos, transfer);
            System.out.println("Transfer of " + amount + " was made successfully");
        } catch (InsuficientFundsException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void makeDeposit(double amount) {
        this.getAccount().setBalance(this.getAccount().getBalance() + amount);
        AccountRepository.updateAccountInDatabase(this.getAccount());
        Deposit deposit = new Deposit(amount, LocalDate.now(), "Deposit to account " + this.getAccount() +"using Debit Card " + this + " of amount " + amount, this.getAccount().getAccountId());
        int pos = Collections.binarySearch(this.getAccount().getTransactionList(), deposit);
        if(pos < 0){
           pos = -pos - 1;
        }
        this.getAccount().getTransactionList().add(pos, deposit);
        System.out.println("Deposit of " + amount + " was made successfully");
    }

    @Override
    public String toString() {
        return "DebitCard{" +
                "cardId='" + cardId + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                ", cvv='" + cvv + '\'' +
                ", expirationDate=" + expirationDate +
                ", account=" + account +
                '}';
    }

    @Override
    public String toCSV(){
        return "DebitCard," + super.toCSV();
    }
}
