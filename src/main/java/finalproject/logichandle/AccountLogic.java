package finalproject.logichandle;


import finalproject.entity.Account;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Pattern;

import static finalproject.main.Main.accounts;
import static finalproject.main.Main.view;


public class AccountLogic {
    public void register(Scanner sc) throws IOException {
        Account account = new Account();
        System.out.println("Enter your email: ");
        emailRegister(sc, account);
        usernameRegister(sc, account);
        passwordRegister(sc, account);
        accounts.add(account);
        System.out.println("Registration successful, please login.");
    }

    private void emailRegister(Scanner sc, Account account) {
        String email;
        String regexPattern = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        boolean flag = true;
        do {
            email = sc.nextLine();
            if (patternMatches(email, regexPattern)) {
                System.out.println("You need to enter the correct email format.");
                continue;
            }
            if (accounts.size() == 0) {
                break;
            } else {
                flag = checkEmailExisted(email);
            }
        } while (flag);
        account.setEmail(email);
    }

    private void usernameRegister(Scanner sc, Account account) {
        System.out.println("Enter username: ");
        String user;
        do {
            user = sc.nextLine();
            if (accounts.size() == 0) {
                break;
            }
        } while (checkUserExisted(user));
        account.setUsername(user);
    }

    private boolean checkUserExisted(String user) {
        boolean flag =  false;
        for (Account i : accounts) {
            if (user.equals(i.getUsername())) {
                System.out.println("This username is already in use.");
                flag = true;
            }
        }
        return flag;
    }

    private void passwordRegister(Scanner sc, Account account) {
        System.out.println("Enter password: ");
        String password;
        String regexPattern = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[.,-_;]).{8,15})";
        boolean flag = true;
        do {
            password = sc.nextLine();
            if (patternMatches(password, regexPattern)) {
                System.out.println("Password must contain from 8-15 characters, at least 1 uppercase character, 1 special character (. , - _ ;)");
            } else {
                flag = false;
            }

        } while (flag);
        account.setPassword(password);
    }

    public boolean patternMatches(String string, String regexPattern) {
        return !Pattern.compile(regexPattern)
                .matcher(string)
                .find();
    }


    public void menuLogin(Scanner sc, Account account) throws IOException {
        int choice = view.checkNumberException(sc, 1, 4);
        switch (choice) {
            case 1: changeUsername(sc, account);
                break;
            case 2: changeEmail(sc, account);
                break;
            case 3: changePassword(sc, account);
                break;
            case 4:
                view.mainMenu();
                break;
        }
    }

    private void changeUsername(Scanner sc, Account account) {
        System.out.println("Enter new username: ");
        String newUsername;
        boolean flag = true;
        do {
            int count = 0;
            newUsername = sc.nextLine();
            for (Account i : accounts) {
                if (newUsername.equals(i.getUsername())) {
                    if (newUsername.equals(account.getUsername())) {
                        System.out.println("The new username cannot be the same as the old username.");
                        count++;
                        continue;
                    }
                    System.out.println("This username is already in use.");
                    count++;
                    break;
                }
            }
            if (count == 0) {
                flag = false;
            }
        } while (flag);
        account.setUsername(newUsername);
        System.out.println("Change username successfully");
    }

    private void changeEmail(Scanner sc, Account account) {
        System.out.println("Enter new email: ");
        String newEmail;
        String regexPattern = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        boolean flag = true;
        do {
            int count = 0;
            newEmail = sc.nextLine();
            for (Account i : accounts) {
                if (newEmail.equals(i.getEmail())) {
                    if (newEmail.equals(account.getEmail())) {
                        System.out.println("The new email cannot be the same as the old email.");
                        count++;
                        continue;
                    }
                    System.out.println("E-mail is being used.");
                    count++;
                    break;
                }
            }
            if (patternMatches(newEmail, regexPattern)) {
                System.out.println("You need to enter the correct email format.");
                count++;
            }
            if (count == 0) {
                flag = false;
            }
        } while (flag);
        account.setEmail(newEmail);
        System.out.println("Change email successfully");
    }

    public void changePassword(Scanner sc, Account account) {
        System.out.println("Enter new password: ");
        String newPassword;
        String regexPattern = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[.,-_;]).{8,15})";
        boolean flag = true;
        do {
            int count = 0;
            newPassword = sc.nextLine();
            if (newPassword.equals(account.getPassword())) {
                System.out.println("The new password cannot be the same as the old password.");
                count++;
            }
            if (patternMatches(newPassword, regexPattern)) {
                System.out.println("Password must contain from 8-15 characters, at least 1 uppercase character, 1 special character (. , - _ ;)");
                count++;
            }
            if (count == 0) {
                flag = false;
            }
        } while (flag);
        account.setPassword(newPassword);
        System.out.println("Change password successfully");
    }

    private boolean checkEmailExisted(String email) {
        boolean flag =  false;
        for (Account i : accounts) {
            if (email.equals(i.getEmail())) {
                System.out.println("E-mail is being used.");
                flag = true;
            }
        }
        return flag;
    }


    public void logIn(Scanner sc) throws IOException {
        Account account = userLogIn(sc);
        passwordLogIn(sc, account);
    }

    public Account userLogIn(Scanner sc) {
        System.out.println("Enter username: ");
        String user;
        Account account = null;
        int count = 0;
        do {
            user = sc.nextLine();
            for (Account i : accounts) {
                if (user.equals(i.getUsername())) {
                    account = i;
                    count++;
                }
            }
            if (count == 0) {
                System.out.println("Check the username again.");
            }
        } while (count == 0);
        return account;
    }

    public void passwordLogIn(Scanner sc, Account account) throws IOException {
        System.out.println("Enter password: ");
        String password = sc.nextLine();
        if (password.equals(account.getPassword())) {
            QuesAnsLogic quesAnsLogic = new QuesAnsLogic();
            while (true) {
                view.showMenu();
                view.chooseMenu(sc, quesAnsLogic, account);
            }
        } else {
            System.out.println("Password is not correct");
            System.out.println("1. Re-login");
            System.out.println("2. Forgot password");
            int choice = view.checkNumberException(sc, 1, 2);
            if (choice == 1) {
                logIn(sc);
            }
            if (choice == 2) {
                checkEmailForgetMode(sc, account);
            }
        }

    }

    private void checkEmailForgetMode(Scanner sc, Account account) {
        boolean flag = true ;
        do {
            System.out.println("Re-enter your registered email: ");
            String email = sc.nextLine();
            if (email.equals(account.getEmail())) {
                AccountLogic accountLogic = new AccountLogic();
                accountLogic.changePassword(sc,account);
                flag = false;
            }
            if (flag) {
                System.out.println("The email you just entered does not match the registered email");
            }
        } while (flag);
    }

    /*public void inputDefaultAccount() {
        Account account1 = new Account("trananhquan335@gmail.com", "xomchua1234", "quan123");
        Account account2 = new Account("trananhquan@gmail.com", "xomchua123456", "quan12313");
        accounts.add(account1);
        accounts.add(account2);
    }*/
}