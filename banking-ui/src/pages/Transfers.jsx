import { useState } from "react";
import Layout from "../components/Layout";
import api from "../api/Api.js";
import { useAccount } from "../context/AccountContext";

function Transfers() {
    const userId = localStorage.getItem("userId");
    const { refreshAccountData } = useAccount();

    const [message, setMessage] = useState("");
    const [messageType, setMessageType] = useState("success");

    const [receiverFirstName, setReceiverFirstName] = useState("");
    const [receiverLastName, setReceiverLastName] = useState("");
    const [userAmount, setUserAmount] = useState("");
    const [savingsAmount, setSavingsAmount] = useState("");

    const showMessage = (text, type = "success") => {
        setMessage(text);
        setMessageType(type);

        setTimeout(() => {
            setMessage("");
        }, 3000);
    };

    const transferToUser = async (e) => {
        e.preventDefault();

        try {
            await api.post("/transfers/send", {
                senderUserId: Number(userId),
                firstName: receiverFirstName,
                lastName: receiverLastName,
                amount: Number(userAmount)
            });

            await refreshAccountData();

            showMessage("Transfer successful");

            setReceiverFirstName("");
            setReceiverLastName("");
            setUserAmount("");
        } catch (error) {
            showMessage(
                error.response?.data?.message ||
                JSON.stringify(error.response?.data) ||
                "Transfer failed",
                "error"
            );
        }
    };

    const transferToSavings = async (e) => {
        e.preventDefault();

        try {
            await api.post("/accounts/transfer-to-savings", {
                userId: Number(userId),
                amount: Number(savingsAmount)
            });

            await refreshAccountData();

            showMessage("Money moved to savings");
            setSavingsAmount("");
        } catch (error) {
            showMessage(
                error.response?.data?.message ||
                JSON.stringify(error.response?.data) ||
                "Transfer failed",
                "error"
            );
        }
    };

    const transferFromSavings = async () => {
        try {
            await api.post("/accounts/transfer-from-savings", {
                userId: Number(userId),
                amount: Number(savingsAmount)
            });

            await refreshAccountData();

            showMessage("Money moved back to active balance");
            setSavingsAmount("");
        } catch (error) {
            showMessage(
                error.response?.data?.message ||
                JSON.stringify(error.response?.data) ||
                "Transfer failed",
                "error"
            );
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
                    <h1>Transfers</h1>
                    <p>Move money between users or your savings account.</p>
                </div>
            </section>

            <div className="forms-grid">
                <div className="panel">
                    <h2>Send Money</h2>

                    <form className="bank-form" onSubmit={transferToUser}>
                        <label>Receiver first name</label>
                        <input
                            value={receiverFirstName}
                            onChange={(e) => setReceiverFirstName(e.target.value)}
                            placeholder="ex: David"
                        />

                        <label>Receiver last name</label>
                        <input
                            value={receiverLastName}
                            onChange={(e) => setReceiverLastName(e.target.value)}
                            placeholder="ex: Ionescu"
                        />

                        <label>Amount</label>
                        <input
                            type="number"
                            value={userAmount}
                            onChange={(e) => setUserAmount(e.target.value)}
                            placeholder="100"
                        />

                        <button type="submit">Send Money</button>
                    </form>
                </div>

                <div className="panel">
                    <h2>Savings Transfer</h2>

                    <form className="bank-form" onSubmit={transferToSavings}>
                        <label>Amount</label>
                        <input
                            type="number"
                            value={savingsAmount}
                            onChange={(e) => setSavingsAmount(e.target.value)}
                            placeholder="200"
                        />

                        <button type="submit">Move to Savings</button>

                        <button
                            type="button"
                            className="secondary-btn"
                            onClick={transferFromSavings}
                        >
                            Move to Active Balance
                        </button>
                    </form>
                </div>
            </div>
        </Layout>
    );
}

export default Transfers;