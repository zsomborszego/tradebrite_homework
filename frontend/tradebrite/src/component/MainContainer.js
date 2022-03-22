import React, {useEffect, useState} from "react";
import TransferContainer from "./TransferContainer";
import axios from "axios";
import MyaccountTransfer from "./DepositWithdrawContainer";
import CreateUserContainer from "./CreateUserContainer";
import TransactionHitroyContainer from "./TransactionHitroyContainer";


function MainContainer() {

    const [compoments, setComponents] = useState(3)
    const [allBankAccount, setAllBankAccount] = useState({isLoaded: false})
    const [currentAccount, setCurrentAccount] = useState({})
    const [userTransacitonHistory, setUserTransacitonHistory] = useState({isLoaded: false})


    function showDeposit() {
        setComponents(1)
    }

    function showWithdraw() {
        setComponents(2)
    }

    function showTransfer() {
        setComponents(3)
    }

    function showUserRegister() {
        setComponents(4)
    }

    const handleChange = async (e) => {
        const userAccountNumber = e.target.value;
        const data = {accountNumber: userAccountNumber}
        axios.post("http://localhost:8080/api/account/search", data)
            .then(response => {
                setCurrentAccount(response.data)
                const dataJson = {accountNumber: response.data.accountNumber}
                axios.post("http://localhost:8080/api/transaction/history/account", dataJson)
                    .then(response => {
                        setUserTransacitonHistory({data:response.data, isLoaded: true})
                    });
            });

    };

    const refreshAccountData = () => {
        const currnetUserJson = {accountNumber: currentAccount.accountNumber}
        axios.post("http://localhost:8080/api/account/search", currnetUserJson)
            .then(response => {
                setCurrentAccount(response.data)
            });
        const dataJson = {accountNumber: currentAccount.accountNumber}
        axios.post("http://localhost:8080/api/transaction/history/account", dataJson)
            .then(response => {
                setUserTransacitonHistory({data:response.data, isLoaded: true})
            });
    };


    useEffect(async () => {
        const responseJson =  await axios.get("api/account/list");
        setCurrentAccount(responseJson.data[0])
        setAllBankAccount({data: responseJson.data, isLoaded: true})
        const dataJson = {accountNumber: responseJson.data[0].accountNumber}
        axios.post("http://localhost:8080/api/transaction/history/account", dataJson)
            .then(response => {
                setUserTransacitonHistory({data:response.data, isLoaded: true})
            });
    }, []);


    if (allBankAccount.isLoaded) {
        return (<div className="main-container">
            <div className="button-container">
                <button className="button" onClick={showDeposit}>Deposit</button>
                <button className="button" onClick={showWithdraw}>Withdraw</button>
                <button className="button" onClick={showTransfer}>Transfer</button>
                <button className="button" onClick={showUserRegister}>User register</button>
                <select className="select-dropdown account-change" name="type" onChange={handleChange}>
                    {allBankAccount.data.map((row, index) => (
                        <option key={index} value={row.accountNumber}>{row.name}</option>
                    ))}
                </select>
                <h5 className={"account-number"}>{currentAccount.accountNumber}</h5>
            </div>
            <div className="current-balance"><p className="current-balance-text">Current
                balance</p>{currentAccount.accountBalance} huf
            </div>
            {compoments === 3 && <TransferContainer currentAccount={currentAccount} refreshAccount={refreshAccountData}/>}
            {compoments === 1 && <MyaccountTransfer name="Deposit" apiUrl="/api/transaction/deposit" currentAccount={currentAccount} function={refreshAccountData}/>}
            {compoments === 2 && <MyaccountTransfer name="Withdraw" apiUrl="/api/transaction/withdraw" currentAccount={currentAccount} function={refreshAccountData}/>}
            {compoments === 4 && <CreateUserContainer/>}
            {compoments < 4 &&
            <div className="history-containers">
                <TransactionHitroyContainer historyValueFow={userTransacitonHistory} contIndex={0} text={"Deposit/Withdraw"}/>
                <TransactionHitroyContainer historyValueFow={userTransacitonHistory} contIndex={1} text={"Transfer"}/>
            </div>}
        </div>)
    } else {
        return (<div className="main-container"/>)
    }
}

export default MainContainer;
