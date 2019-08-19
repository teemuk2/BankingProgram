# BankingProgram

Please check the wiki page for pictures and more info

A banking program that allows you to make withdrawals or deposit money for user accounts. You can create new users, switch between users and delete users. Accounts are tracked with SQL and changes made to accounts or their account balance are saved.

Installation: Open with any Java editor. Run Main.
(to get SQL database working): change variables URL, USERNAME, and PASSWORD to their correct values.

Usage: 
Current account displays account in use. <br/>
withdrawal: type in to text field and press enter. Cant withdraw more than account balance.<br/>
Depositing: type in to text field and press enter.<br/>
Account balance displays balance for current account.<br/>
Create account: type in wanted account name and press enter. Duplicate accounts will not be created if name already exists.<br/>
Switch account: type in wanted account name (CASE SENSITIVE) and press Enter. (will only switch if account exists)<br/>
Delete account: deletes current account and changes to default account. (Default account can't be deleted.<br/>
Text at bottom: gives user feedback on actions (also displays error messages)

Credits: All work by Teemu Korhonen
