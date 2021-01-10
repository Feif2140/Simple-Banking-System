import java.text.DecimalFormat;

class CADAccount{
    private String password;
    private String accountName;
    private double balance;
    public static final double OVERDRAWN_PENALTY = 20.00;
    private String fName, lName;
    private double chequing, saving, invest;
    private static DecimalFormat df2 = new DecimalFormat("#.00");

    public CADAccount(String name){
        password = "1234";
        balance = 0.00;
        accountName = name;
    }

    public CADAccount(String acctPassword, double acctBalance, String name){
        password = acctPassword;
        balance = acctBalance;
        accountName = name;
    }

    public CADAccount(String FirstName, String LastName, String chequee, String savingg, String investmentt){
        fName = FirstName;
        lName = LastName;
        chequing = Double.valueOf(chequee);
        saving = Double.valueOf(savingg);
        invest = Double.valueOf(investmentt);
    }

    public String getFirstName(){
        return fName;
    }
    public String getLastName(){
        return lName;
    }
    public double getChequing(){
        return chequing;
    }
    public double getSaving(){
        return saving;
    }
    public double getInvest(){
        return invest;
    }
    public String fChequing(){
        return df2.format(chequing);
    }
    public String fSaving(){
        return df2.format(saving);
    }
    public String fInvest(){
        return df2.format(invest);
    }
    public void changeChequing(double val){
        chequing = chequing + val;
    }
    public void changeSaving(double val){
        saving = saving + val;
    }
    public void changeInvest(double val){
        invest = invest + val;
    }
    public void plusMonth(){
        saving = saving*1.02;
        invest = invest*1.06;
    }










    

    public double getBalance(){
        return balance;
    }

    public void deposit(double amount){
        balance = balance + amount;
    }

    public void withdraw(String acctPassword,double amount){
        if (password.equals(acctPassword)){
            if (amount<=balance)
                balance -= amount;
            else System.out.println("insufficient funds");
        }
        else System.out.println("Invalid password, transaction cannot be completed");
    }

    public void setPass(String oldP, String newP){
        if (password.equals(oldP))
            password = newP;
        else System.out.println("Incorrect password");
    }

    public void transferMoney(String acctPassword, double amount, CADAccount destination){
        if (password.equals(acctPassword)){
            if (amount<=balance){
                balance = balance - amount;
                destination.deposit(amount);
            }
            else System.out.println("Insufficient funds");
        }
        else System.out.println("Invalid password, transfer failed");
    }

    public String toString(){
        return ("Account name: " + accountName + "    balance: " + balance);
    }
}
