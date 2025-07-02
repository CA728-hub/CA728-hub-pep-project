package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {

    AccountDAO accountDAO = new AccountDAO();

    

    public AccountService() {}; 

    
    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO; 
    }

    
    public Account registerUser(Account account) {

        Account existingAccount = accountDAO.getUserByUsername(account.getUsername());


        if (existingAccount != null) {
            return null; 
        }


        if (account.getUsername() == null || account.getPassword() == null ||
            account.getUsername().isEmpty() || account.getPassword().isEmpty()) {
            return null;
        }


       return accountDAO.registerUser(account);  
    }


    public Account loginUser(String username, String password) {


        AccountDAO dao = new AccountDAO();
        Account account = dao.getUserByUsername(username);



        if (account == null || !account.getPassword().equals(password)) {
        return null;
    }




        return account; 
    }


}
