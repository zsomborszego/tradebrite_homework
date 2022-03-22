import {useState} from "react";
import axios from "axios";


function MyaccountTransfer(props) {

    const initialFormDataValue = Object.freeze({
        value: "",
    });

    const [formDataValue, updateFormDataValue] = useState(initialFormDataValue);

    function handleChange(e) {
        updateFormDataValue({
            ...formDataValue,
            [e.target.name]: e.target.value.trim()
        });
    }

    async function sendTransfer(){
        const data = {account: {accountNumber:props.currentAccount.accountNumber}, value: formDataValue.value}
        const apiUrlEndpoint = props.apiUrl
        await axios.post(apiUrlEndpoint, data)
            .then(response => {
                console.log(response.data)

                //TODO handle errors
            });
        props.function()
    }

    return(<div className="account-transfer-container">
        <label className="label-text">Amount
            <input type="number" name="value" className="input-field amount-input" onChange={handleChange} required/>
        </label>
        <button className="button-submit" onClick={sendTransfer}>{props.name}</button></div>)
}

export default MyaccountTransfer;
