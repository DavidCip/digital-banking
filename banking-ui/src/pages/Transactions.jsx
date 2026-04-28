import { useEffect, useState } from "react";
import Layout from "../components/Layout";
import api from "../api/Api.js";

function Transactions() {
    const userId = localStorage.getItem("userId");
    const [transactions, setTransactions] = useState([]);

    const [message, setMessage] = useState("");
    const [messageType, setMessageType] = useState("success");

    useEffect(() => {
        loadTransactions();
    }, []);

    const showMessage = (text, type = "success") => {
        setMessage(text);
        setMessageType(type);

        setTimeout(() => {
            setMessage("");
        }, 3000);
    };

    const loadTransactions = async () => {
        try {
            const response = await api.get(`/transactions/user/${userId}`);
            setTransactions(response.data);
        } catch (error) {
            showMessage("Could not load transactions", "error");
        }
    };

    return (
        <Layout>
            {message && (
                <div className={messageType === "success" ? "success-message" : "error-message"}>
                    {message}
                </div>
            )}

            <section className="dashboard-header">
                <div>
                    <h1>Transactions</h1>
                    <p>View your full banking activity history.</p>
                </div>
            </section>

            <div className="panel">
                {transactions.length === 0 ? (
                    <p className="muted">No transactions yet.</p>
                ) : (
                    <div className="transactions-table">
                        <div className="transactions-header">
                            <span>Type</span>
                            <span>Description</span>
                            <span>Amount</span>
                            <span>Status</span>
                            <span>Date</span>
                        </div>

                        {transactions.map((tx) => (
                            <div className="transactions-row" key={tx.id}>
                                <span className="tx-type">{tx.type}</span>
                                <span>{tx.description}</span>

                                <strong>
                                    {tx.amount} {tx.currency}
                                </strong>

                                <em>{tx.status}</em>

                                <span>
                  {tx.createdAt?.replace("T", " ").slice(0, 16)}
                </span>
                            </div>
                        ))}
                    </div>
                )}
            </div>
        </Layout>
    );
}

export default Transactions;