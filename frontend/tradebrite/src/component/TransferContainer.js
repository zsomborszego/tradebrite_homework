import {useState} from "react";
import axios from "axios";


function TransferContainer(props) {

    const initialFormDataTransfer = Object.freeze({
        bankAccountNumber: "",
        value: ""
    });

    const [formDataTransfer, updateFormDataTransfer] = useState(initialFormDataTransfer);


    function handleChange(e) {
        updateFormDataTransfer({
            ...formDataTransfer,
            [e.target.name]: e.target.value.trim()
        });
    }


    async function sendTransfer(){
        const jsData = {sender: {accountNumber: props.currentAccount.accountNumber},beneficiary: {accountNumber: formDataTransfer.bankAccountNumber}, value: formDataTransfer.value}
        await axios.post("api/transaction/transfer", jsData)
            .then(response => {
                //TODO handle errors
            });
        props.refreshAccount();
    }


    return (<div className="account-transfer-container">
        <label className="label-text">Account Number
            <input type="text" name="bankAccountNumber" className="input-field account-input" onChange={handleChange} required/>
        </label>
            <label className="label-text">Amount<input type="number" name="value" className="input-field amount-input" onChange={handleChange} required/>
        </label><button className="button-submit" onClick={sendTransfer}>Send</button></div>)

}

export default TransferContainer;
