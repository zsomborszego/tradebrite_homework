import {useState} from "react";
import axios from "axios";


function CreateUserContainer(){


    const initialFormDataUser = Object.freeze({
        name: "",
        email: "",
        username: ""
    });

    const [userDetails, updateUserDetails] = useState(initialFormDataUser);

    function handleChangeUser(e) {
        updateUserDetails({
            ...userDetails,
            [e.target.name]: e.target.value.trim()
        });
    }

    async function registerUser(){
        const apiUrlEndpoint = "http://localhost:8080/api/account/register"
        await axios.post(apiUrlEndpoint, userDetails)
            .then(response => {
            });
        window.location.reload(false);
    }


    return(<div className="account-transfer-container"><div className="user-register-container">
        <label className="label-text">Name<br/>
            <input type="text" name="name" className="input-field user-details-input" onChange={handleChangeUser} required/>
        </label><br/>
        <label className="label-text">Username<br/>
            <input type="text" name="email" className="input-field user-details-input" onChange={handleChangeUser} required/>
        </label><br/>
        <label className="label-text">Email<br/>
            <input type="text" name="username" className="input-field user-details-input" onChange={handleChangeUser} required/>
        </label><br/>
        <button className="button-submit" onClick={registerUser}>Register</button></div></div>)

}

export default CreateUserContainer;
