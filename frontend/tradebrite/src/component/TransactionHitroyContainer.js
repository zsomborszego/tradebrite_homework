import React from "react";


function TransactionHitroyContainer(props){
    if(props.historyValueFow.isLoaded) {
        return (<div className="history-box">
            <h5 className={"history-box-name"}>{props.text}</h5>
            <ul>
                {props.historyValueFow.data[props.contIndex].map((row, index) => (
                    <li key={index}>{row.type}<span>{row.value > 0 ? <span className="green-font">{row.value} huf</span> : <span className="red-font">{row.value} huf</span>}</span></li>
                ))}
            </ul>
        </div>)
    }
    else {
        return (<div className="history-box"/>)
    }


}

export default TransactionHitroyContainer
