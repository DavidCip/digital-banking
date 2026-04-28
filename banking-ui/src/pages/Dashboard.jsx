import { useEffect, useState } from "react";
import Layout from "../components/Layout";
import StatCard from "../components/StatCard";
import api from "../api/api";
import { useAccount } from "../context/AccountContext";

function Dashboard() {
    const userId = localStorage.getItem("userId");
    const [message, setMessage] = useState("");
    const [depositAmount, setDepositAmount] = useState("");

    const { account, cards, transactions, refreshAccountData } = useAccount();

    useEffect(() => {
        refreshAccountData();
    }, []);

    const depositMoney = async (e) => {
        e.preventDefault();

        try {
            await api.post("/accounts/deposit", {
                userId: Number(userId),
                amount: Number(depositAmount)
            });

            setDepositAmount("");

            await refreshAccountData();

            setMessage("Money deposited successfully");

            setTimeout(() => {
                setMessage("");
            }, 3000);

        } catch (error) {
            setMessage(error.response?.data?.message || "Deposit failed");

            setTimeout(() => {
                setMessage("");
            }, 3000);
        }
    };

    return (
        <Layout>
            {message && (
                <div className="success-message">
                    {message}
                </div>
            )}

            <section className="dashboard-header">
                <div>
                    <h1>Dashboard</h1>
                    <p>Your money overview in one place.</p>
                </div>
            </section>

            <div className="stats-grid">
                <StatCard
                    title="Active Balance"
                    value={`${account?.activeBalance ?? "0.00"} RON`}
                    subtitle="Available money"
                />

                <StatCard
                    title="Savings"
                    value={`${account?.savingsBalance ?? "0.00"} RON`}
                    subtitle="Saved money"
                />

                <StatCard
                    title="Cards"
                    value={cards.length}
                    subtitle="Active and blocked cards"
                />
            </div>

            <div className="panel deposit-panel">
                <div>
                    <h2>Quick Deposit</h2>
                    <p className="muted">Add money instantly to your active balance.</p>
                </div>

                <form className="deposit-form" onSubmit={depositMoney}>
                    <input
                        type="number"
                        value={depositAmount}
                        onChange={(e) => setDepositAmount(e.target.value)}
                        placeholder="Amount in RON"
                    />

                    <button type="submit">Deposit</button>
                </form>
            </div>

            <div className="dashboard-grid">
                <div className="panel">
                    <h2>Your Cards</h2>

                    {cards.length === 0 ? (
                        <p className="muted">No cards found.</p>
                    ) : (
                        cards.slice(0, 2).map((card) => (
                            <div className="bank-card-preview" key={card.id}>
                                <p>{card.holderName}</p>
                                <h3>**** **** **** {card.cardNumber.slice(-4)}</h3>
                                <span>Expires {card.expiryDate}</span>
                                <strong>{card.status}</strong>
                            </div>
                        ))
                    )}
                </div>

                <div className="panel">
                    <h2>Recent Transactions</h2>

                    {transactions.length === 0 ? (
                        <p className="muted">No transactions yet.</p>
                    ) : (
                        <div className="transaction-list">
                            {transactions.slice(0, 5).map((tx) => (
                                <div className="transaction-item" key={tx.id}>
                                    <div>
                                        <strong>{tx.type}</strong>
                                        <p>{tx.description}</p>
                                    </div>

                                    <span>
                    {tx.amount} {tx.currency}
                  </span>
                                </div>
                            ))}
                        </div>
                    )}
                </div>
            </div>
        </Layout>
    );
}

export default Dashboard;