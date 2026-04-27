import { useEffect, useState } from "react";
import api from "../api/Api.js";
import Layout from "../components/Layout";
import StatCard from "../components/StatCard";

function Dashboard() {
    const userId = localStorage.getItem("userId");

    const [account, setAccount] = useState(null);
    const [cards, setCards] = useState([]);
    const [transactions, setTransactions] = useState([]);

    useEffect(() => {
        loadDashboard();
    }, []);

    const loadDashboard = async () => {
        try {
            const accountRes = await api.get(`/accounts/user/${userId}`);
            const cardsRes = await api.get(`/cards/user/${userId}`);
            const txRes = await api.get(`/transactions/user/${userId}`);

            setAccount(accountRes.data);
            setCards(cardsRes.data);
            setTransactions(txRes.data.slice(0, 5));
        } catch (error) {
            alert("Could not load dashboard data");
        }
    };

    return (
        <Layout>
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
                            {transactions.map((tx) => (
                                <div className="transaction-item" key={tx.id}>
                                    <div>
                                        <strong>{tx.type}</strong>
                                        <p>{tx.description}</p>
                                    </div>

                                    <span>{tx.amount} {tx.currency}</span>
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