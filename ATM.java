import java.util.*;

public abstract class ATMState {
    public void insertCard(ATM atm, Card card) {
        System.out.println("OOPS!! Something went wrong");
    }
    public void authenticatePin(ATM atm, Card card, int pin) {
        System.out.println("OOPS!! Something went wrong");
    }
    public void selectOperation(ATM atm, Card card, TransactionType txnType) {
        System.out.println("OOPS!! Something went wrong");
    }
    public void cashWithdrawl(ATM atm, Card card, int withdrawAmount) {
        System.out.println("OOPS!! Something went wrong");
    }
    public void displayBalance(ATM atm, Card card) {
        System.out.println("OOPS!! Something went wrong");
    }
    public void returnCard() {
        System.out.println("OOPS!! Something went wrong");
    }
    public void exit(ATM atm) {
        System.out.println("OOPS!! Something went wrong");
    }
}

public class IdleState extends ATMState {
    @override
    public void insertCard(ATM atm, Card card) {
        System.out.println("Card inserted");
        atm.setState(new HasCardState());
    }
}

public class HasCardState extends ATMState {
    @override
    public void authenticatePin(ATM atm, Card card, int pin) {
        boolean isPinCorrect = card.checkPin(pin);
        if (isPinCorrect) {
            atm.setState(new SelectOperationState());
        } else {
            System.out.println("Incorrect pin");
            exit(atm);
        }
    }
    @override
    public void exit(ATM atm) {
        atm.setState(new IdleState());
        returnCard();
    }
    @override
    public void returnCard() {
        System.out.println("Collect your card");
    }
}

public class SelectOperationState extends ATMState {
    @override
    public void selectOperation(ATM atm, Card card, TransactionType txnType) {
        switch (txnType) {
            case CASH_WITHDRAWL:
                atm.setState(new CashWithdrawlState());
                break;
            case BALANCE_CHECK:
                atm.setState(new CheckBalanceState());
                break;
            default:
                System.out.println("Invalid operation type");
                exit(atm);
                break;
        }
    }
    @override
    public void exit(ATM atm) {
        atm.setState(new IdleState());
        returnCard();
    }
    @override
    public void returnCard() {
        System.out.println("Collect your card");
    }
}

public class CheckBalanceState extends ATMState {
    @override
    public void displayBalance(ATM atm, Card card) {
        System.out.println("Your balance is: "+card.getBalance());
        exit(atm);
    }
    @override
    public void exit(ATM atm) {
        atm.setState(new IdleState());
        returnCard();
    }
    @override
    public void returnCard() {
        System.out.println("Collect your card");
    }
}

public class CashWithdrawlState extends ATMState {
    public CashWithdrawlState() {
        System.out.println("Enter the amount");
    }
    public void withdrawAmount(ATM atm, Card card, int amount) {
        if (atm.getBalance()<amount) {
            System.out.println("Insufficient fund in machine");
        } else if (card.getBalance()<amount) {
            System.out.println("Insufficient fund in your account");
        } else {
            CashWithdrawProcessor withdrawlProcessor = new TwoThousandProcessor(new FiveHundredProcessor(new HundredProcessor(null)));
            withdrawlProcessor.withdrawAmount(atm, amount);
            card.deductBalance(amount);
            atm.deductBalance(amount);
            exit(atm);
        }
    }
    @override
    public void exit(ATM atm) {
        atm.setState(new IdleState());
        returnCard();
    }
    @override
    public void returnCard() {
        System.out.println("Collect your card");
    }
}

public class ATM {
    ATMState ATMState;
    private int AtmBalance;
    private int noOfTwoThousandNotes;
    private int noOfFiveHundredNotes;
    private int noOfHundredNotes;
    public void setState(ATMState currentATMState) {
        this.currentATMState = currentATMState;
    }
    public ATMState getCurrentATMState() {
        return currentATMState;
    }
    public static ATM getATMObject() {
        atmObject.setCurrentATMState(new IdleState());
        return atmObject;
    }
    public int getAtmBalance() {
        return atmBalance;
    }
    public void setAtmBalance(int atmBalance, int noOfTwoThousandNotes, int noOfFiveHundredNotes, int noOfOneHundredNotes) {
        this.atmBalance = atmBalance;
        this.noOfTwoThousandNotes = noOfTwoThousandNotes;
        this.noOfFiveHundredNotes = noOfFiveHundredNotes;
        this.noOfOneHundredNotes = noOfOneHundredNotes;
    }
    public int getNoOfTwoThousandNotes() {
        return noOfTwoThousandNotes;
    }
    public int getNoOfFiveHundredNotes() {
        return noOfFiveHundredNotes;
    }
    public int getNoOfOneHundredNotes() {
        return noOfOneHundredNotes;
    }
    public void deductATMBalance(int amount) {
        atmBalance = atmBalance - amount;
    }
    public void deductTwoThousandNotes(int number) {
        noOfTwoThousandNotes = noOfTwoThousandNotes - number;
    }
    public void deductFiveHundredNotes(int number) {
        noOfFiveHundredNotes = noOfFiveHundredNotes - number;
    }
    public void deductOneHundredNotes(int number) {
        noOfOneHundredNotes = noOfOneHundredNotes - number;
    }
}

public class Card {
    private int cardNumber;
    private int cvv;
    private int expiryDate;
    private int holderName;
    static int PIN_NUMBER = 112211;
    private UserBankAccount bankAccount;

    public boolean isCorrectPINEntered(int pin) {
        if (pin == PIN_NUMBER) {
            return true;
        }
        return false;
    }
    public int getBankBalance(){
        return bankAccount.balance;
    }
    public void deductBankBalance(int amount){
        bankAccount.withdrawalBalance(amount);
    }
    public void setBankAccount(UserBankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }
}

public class User {
    Card card;
    UserBankAccount bankAccount;

    public Card getCard() {
        return card;
    }
    public void setCard(Card card) {
        this.card = card;
    }
}

public class UserBankAccount {
    int balance;

    public void withdrawalBalance(int amount) {
        balance = balance - amount;
    }
}

public enum TransactionType {
    CASH_WITHDRAWAL,
    BALANCE_CHECK;

    public static void showAllTransactionTypes(){
        for(TransactionType type: TransactionType.values()){
            System.out.println(type.name());
        }
    }
}

public abstract class CashWithdrawProcessor {
    CashWithdrawProcessor nextCashWithdrawalProcessor;

    CashWithdrawProcessor(CashWithdrawProcessor cashWithdrawalProcessor) {
        this.nextCashWithdrawalProcessor = cashWithdrawalProcessor;
    }

    public void withdraw(ATM atm, int remainingAmount) {
        if (nextCashWithdrawalProcessor != null) {
            nextCashWithdrawalProcessor.withdraw(atm, remainingAmount);
        }
    }
}

public class TwoThousandWithdrawProcessor extends CashWithdrawProcessor {

    public TwoThousandWithdrawProcessor(CashWithdrawProcessor nextCashWithdrawProcessor) {
        super(nextCashWithdrawProcessor);
    }

    public void withdraw(ATM atm, int remainingAmount) {
       int required =  remainingAmount/2000;
       int balance = remainingAmount%2000;
       if(required <= atm.getNoOfTwoThousandNotes()) {
           atm.deductTwoThousandNotes(required);
       }
       else if(required > atm.getNoOfTwoThousandNotes()) {
           atm.deductTwoThousandNotes(atm.getNoOfTwoThousandNotes());
           balance = balance + (required-atm.getNoOfTwoThousandNotes()) * 2000;
        }
       if(balance != 0){
           super.withdraw(atm, balance);
       }
    }
}

public class FiveHundredWithdrawProcessor extends CashWithdrawProcessor{

    public FiveHundredWithdrawProcessor(CashWithdrawProcessor nextCashWithdrawProcessor){
        super(nextCashWithdrawProcessor);
    }

    public void withdraw(ATM atm, int remainingAmount){
        int required =  remainingAmount/500;
        int balance = remainingAmount%500;
        if(required <= atm.getNoOfFiveHundredNotes()) {
            atm.deductFiveHundredNotes(required);
        }
        else if(required > atm.getNoOfFiveHundredNotes()) {
            atm.deductFiveHundredNotes(atm.getNoOfFiveHundredNotes());
            balance = balance + (required-atm.getNoOfFiveHundredNotes()) * 500;
        }
        if(balance != 0){
            super.withdraw(atm, balance);
        }
    }
}

public class HundredWithdrawProcessor extends CashWithdrawProcessor{

    public HundredWithdrawProcessor(CashWithdrawProcessor nextCashWithdrawProcessor){
        super(nextCashWithdrawProcessor);
    }

    public void withdraw(ATM atm, int remainingAmount){

        int required =  remainingAmount/100;
        int balance = remainingAmount%100;

        if(required <= atm.getNoOfOneHundredNotes()) {
            atm.deductOneHundredNotes(required);
        }
        else if(required > atm.getNoOfOneHundredNotes()) {
            atm.deductOneHundredNotes(atm.getNoOfOneHundredNotes());
            balance = balance + (required-atm.getNoOfOneHundredNotes()) * 100;
        }

        if(balance != 0){
           System.out.println("Something went wrong");
        }
    }
}