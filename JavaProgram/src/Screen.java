import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.*;


public class Screen extends JFrame{

    //GUI elements:
    private Users user = new Users();
    private Account activeAccount = new Account("default",10);
    private JLabel status;
    private JTextField text1;
    private JTextField text2;
    private JTextField balance;
    private JButton deleteButton;
    private JTextField newAccount;
    private JTextField switchAccount;
    private JLabel currentAccount;
    private PreparedStatement createSQLuser = null;
    private PreparedStatement deleteSQLuser = null;
    private PreparedStatement withdrawSQL = null;
    private PreparedStatement depositSQL = null;


    public Screen(){


        super("BANK");
        setLayout(new FlowLayout());
        final int inputBarSize = 10;

        status = new JLabel("Account: choose action");
        status.setToolTipText("status message");
        //input bar for withdraw
        text1 = new JTextField(inputBarSize);

        JLabel info1 = new JLabel("Withdraw amount");
        info1.setToolTipText("type amount and press enter");
        //input bar for deposit
        text2 = new JTextField(inputBarSize);

        JLabel info2 = new JLabel("Deposit amount    ");
        info2.setToolTipText("type amount and press enter");

        JLabel infoCreate = new JLabel("create account:  ");
        JLabel infoSwitch = new JLabel("switch account:  ");
        JLabel infoBalance = new JLabel("Account balance:    ");
        JLabel emptyLine = new JLabel("                                                                     ");
        JLabel emptyLine2 = new JLabel("                                                                     ");
        JLabel emptyLine3 = new JLabel("                                                                     ");
        JLabel emptyLine4 = new JLabel("                                                                     ");

        deleteButton = new JButton("Delete Account");

        balance = new JTextField("0", 7);
        balance.setEditable(false);
        String balanceToText = "" + activeAccount.getBalance();
        balance.setText(balanceToText);

        newAccount = new JTextField(inputBarSize);
        switchAccount = new JTextField(inputBarSize);
        currentAccount = new JLabel("CURRENT ACCOUNT:        "+ activeAccount.getName());


        // all adds here (for gui):
        add(emptyLine3);
        add(currentAccount);
        add(emptyLine4);
        add(text1);
        add(info1);
        add(text2);
        add(info2);
        add(infoBalance);
        add(balance);
        add(emptyLine);
        add(infoCreate);
        add(newAccount);
        add(infoSwitch);
        add(switchAccount);
        add(deleteButton);
        add(emptyLine2);
        //add status message last
        add(status);
        //(status message gives user action feedback)

        //handler object creation:
        thehandler handler = new thehandler();
        //listeners (do something when clicked):
        text1.addActionListener(handler);
        text2.addActionListener(handler);
        deleteButton.addActionListener(handler);
        newAccount.addActionListener(handler);
        switchAccount.addActionListener(handler);
        
      //SQL connection test:
    	final String URL =  "jdbc:mysql:";
	    final String USERNAME = "";
	    final String PASSWORD = "";
		
		try {
			// get a connection to database
			Connection myConn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			// create a statement
			Statement myStmt = myConn.createStatement();
			createSQLuser = myConn.prepareStatement("INSERT INTO userlist (name, balance) VALUES (?, '0')");
			deleteSQLuser= myConn.prepareStatement("delete from userlist where name = ?");
			withdrawSQL = myConn.prepareStatement("update userlist set balance = balance - ? where name = ?");
			depositSQL = myConn.prepareStatement("update userlist set balance = balance + ? where name = ?");
			// execute SQL query
			ResultSet myRs = myStmt.executeQuery("select * from userlist");
			// process the result set
			System.out.println("connected to database. users found:");
			while (myRs.next()) {
				this.user.addRichUser(myRs.getString("name"), myRs.getDouble("balance"));
				System.out.println(myRs.getString("name") + " " + myRs.getDouble("balance"));
			}
		}
		catch(SQLException e){
			System.out.println(e);
		}
        //test area ends
		
		

    }

    public boolean createAccount(String input){
        if(input.equals("")){
            System.out.println("EMPTY FIELD");
            System.out.println(this.user);
            return false;
        }
        else if(user.contains(input)){
            System.out.println("ALREADY EXISTS");
            System.out.println(this.user);
            return false;
        }
        else{
            this.user.addUser(input);
            try {
            	createSQLuser.setString(1, input);
            	createSQLuser.executeUpdate();
            }
            catch(Exception e) {
            	System.out.println(e);
            }
            System.out.println("account created: "+ input + " - total users created today: "+ Users.newCreatedUsers);
            System.out.println(this.user);
            return true;
        }
    }

    public boolean swapAccount(String input){
        if(input.equals("")){
            System.out.println("EMPTY FIELD");
            return false;
        }
        else if(user.contains(input)){
            activeAccount = user.getAccount(input);
            System.out.println("Account swapping has worked");
            return true;
        }
        System.out.println(this.user);
        return false;
    }

    public boolean swapOrCreateAndThenSwapToDefault(){
        String wantedDefault = "default";
        if(user.contains(wantedDefault)){
            activeAccount = user.getAccount(wantedDefault);
            System.out.println("account swapped to default that existed");
            return true;
        }
        else{
            createAccount(wantedDefault);
            swapAccount(wantedDefault);
            System.out.println("default never extisted so now created and swapped to it");
            return true;
        }
    }

    public void deleteAccount(String input){
        user.removeUser(user.getAccount(input));
        System.out.println(this.user);
        try {
        	deleteSQLuser.setString(1, input);
        	deleteSQLuser.executeUpdate();
        }
        catch(Exception e) {
        	System.out.println(e);
        }
    }

    //obsolete method graveyard
    public boolean moreThanOne(){
        int total = 0;
        total = user.countUsers();
        if(total >1){
            return true;
        }
        return false;
    }

    private class thehandler implements ActionListener{

        public void actionPerformed(ActionEvent event){

            String string = "";
            String input;
            double amount;
            boolean possible;

            //action listener logic:

            if(event.getSource()==text1){
                input = String.format(event.getActionCommand());
                try {
                    amount = Double.parseDouble(input);
                    possible = activeAccount.withdrawal(amount);
                    if (possible) {
                        string = String.format("Account balance -  %s", event.getActionCommand());
                        //SQL update money:
                        try {
                        	withdrawSQL.setDouble(1, amount);
                        	withdrawSQL.setString(2, activeAccount.getName()); //get user name from active account
                        	withdrawSQL.executeUpdate();
                        }
                        catch(Exception e) {
                        	System.out.println(e);
                        }
                        
                    } else {
                        string = String.format("Transaction Failed: Insufficient funds.");
                    }
                }
                catch(Exception e){
                    System.out.println("Error: maybe wrong input type");
                    string = "wrong input type";
                }
                System.out.println(activeAccount.getBalance());

            }
            else if(event.getSource()==text2){
                try {
                    input = String.format(event.getActionCommand());
                    amount = Double.parseDouble(input);
                    activeAccount.deposit(amount);
                    System.out.println(activeAccount.getBalance());

                    string = String.format("Account balance +  %s", event.getActionCommand());
                    //SQL update money:
                    try {
                    	depositSQL.setDouble(1, amount);
                    	depositSQL.setString(2, activeAccount.getName()); //getusername from list
                    	depositSQL.executeUpdate();
                    }
                    catch(Exception e) {
                    	System.out.println(e);
                    }
                }
                catch(NumberFormatException e){
                    System.out.println("Error: wrong input type");
                    string = "wrong input type";
                }
            }
            // important account creator:

            else if(event.getSource()==newAccount){

                input = String.format(event.getActionCommand());
                boolean x = createAccount(input);
                if(!x){
                    string = "failure to create account";
                    System.out.println("FAIL");
                }
                else{
                    string = "Account created        ";
                }
            }
            // important account switcher:

            else if(event.getSource()==switchAccount){
                input = String.format(event.getActionCommand());
                System.out.println(input);
                boolean y = swapAccount(input);
                if(!y){
                    string = "failure to switch account";
                    System.out.println("FAIL");
                }
                else{
                    currentAccount.setText("CURRENT ACCOUNT:        "+ activeAccount.getName());
                    string = "Account switched        ";
                }

            }

            // important account deleter:

            else if(event.getSource()==deleteButton){
                if(activeAccount.getName().equals("default")){
                    string = "cant delete default account!";
                    System.out.println("cant delete default");
                }
                else{
                    String accName = activeAccount.getName();
                    boolean isItWorking = swapOrCreateAndThenSwapToDefault();
                    if(!isItWorking){
                        System.out.println("not working");
                    }
                    currentAccount.setText("CURRENT ACCOUNT:        "+ activeAccount.getName());
                    deleteAccount(accName);
                    string = "account deleted forever!";
                }

            }

            //always happens after any action:
            //making sure text fields get reset for user convenience :^)
            //also displaying status message for user to receive feed back on consequence for action

            text1.setText("");
            text2.setText("");
            newAccount.setText("");
            switchAccount.setText("");
            status.setText(string);
            String balanceToText = "" + activeAccount.getBalance();
            balance.setText(balanceToText);

        }

    }

}
