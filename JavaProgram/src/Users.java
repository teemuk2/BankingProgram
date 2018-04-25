import java.util.ArrayList;

public class Users {

    //this class keeps track of all users

    private ArrayList<Account> list;
    public static int newCreatedUsers = 0;

    public Users(){
        this.list = new ArrayList<Account>();
    }

    public void addUser(String name){
        this.list.add(new Account(name, 0));
        newCreatedUsers++;
    }
    
    public void addRichUser(String name, double balance) {
    	this.list.add(new Account(name, balance));
    }

    public int countUsers(){
        return list.size();
    }

    public boolean removeUser(Account user){

        if(list.contains(user)){
            this.list.remove(user);
            return true;
        }
        return false;
    }

    public Account getAccount(String name){
        for(Account y : list){
            if(y.getName().equals(name)){
                return y;
            }
        }
        return new Account("default", 0);
    }

    public boolean contains(String name){
        for(Account a : list){
            if(a.getName().equals(name)){
                return true;
            }
        }
        return false;
    }

    public String toString(){
        String all = "";
        for(Account y : list){
            all+= " ";
            all+= y.getName();
        }
        return "ALL CURRENT ACCOUNTS: "+all;
    }

}
