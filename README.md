# Homework

In this project I created a REST api backend with java spring and PSQL database.\
All endpoints I used JSON data structure for data transfer.\
I used my own exceptions for handle invalid/wrong request.\
I used MVC design pattern, and JPA for database access.\
For all transaction I validate the account with the account number which is unique.

####My backend can hanlde:
- invalid/wrong account number
- invalid/wrong transaction value
- invlaid transfer for same account number
- invalid withdraw


###Bakckend transaction endpoints
- /api/transaction/deposit
- /api/transaction/withdraw
- /api/transaction/transfer
- /api/transaction/history/account

###Bakckend account endpoints
- /api/account/register
- /api/account/search
- /api/account/list

###Basic frontend design
####Empty account
![](frontend/tradebrite/src/pictures/1.png)
####Deposit money
![](frontend/tradebrite/src/pictures/2.png)
####Withdraw money
![](frontend/tradebrite/src/pictures/3.png)
####Transef money
![](frontend/tradebrite/src/pictures/4.png)
####Account get the transfer
![](frontend/tradebrite/src/pictures/5.png)
####Register new account
![](frontend/tradebrite/src/pictures/6.png)
####We can switch between account
![](frontend/tradebrite/src/pictures/7.png)
