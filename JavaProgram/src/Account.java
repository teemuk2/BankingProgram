public class Account {

    //this class keeps track of the money

    private double balance;
    private String name;

    public Account(String name, double balance){
        this.balance = balance;
        this.name = name;
    }

    public Account(String name){
        this.name = name;
        this.balance = 0;
    }

    public String getName(){
        return this.name;
    }

    public void deposit(double amount){
        this.balance += amount;
    }

    public boolean withdrawal(double amount){
        if(this.balance - amount >= 0){
            this.balance -= amount;
            return true;
        }
        return false;
    }

    public double getBalance(){
        return this.balance;
    }

    public void setBalance(double amount){
        if(amount > 0){
            this.balance = amount;
        }
    }

    public String toString(){
        return "balance: " + this.balance;
    }

}
