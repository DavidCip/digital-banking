import { useEffect, useState } from "react";
import Layout from "../components/Layout";
import api from "../api/Api.js";
import { useAccount } from "../context/AccountContext";

function Cards() {
    const userId = localStorage.getItem("userId");
    const { refreshAccountData } = useAccount();

    const [cards, setCards] = useState([]);

    const [message, setMessage] = useState("");
    const [messageType, setMessageType] = useState("success");

    useEffect(() => {
        loadCards();
    }, []);

    const showMessage = (text, type = "success") => {
        setMessage(text);
        setMessageType(type);

        setTimeout(() => {
            setMessage("");
        }, 3000);
    };

    const loadCards = async () => {
        try {
            const response = await api.get(`/cards/user/${userId}`);
            setCards(response.data);
        } catch (error) {
            showMessage("Could not load cards", "error");
        }
    };

    const createCard = async () => {
        try {
            await api.post("/cards/create", {
                userId: Number(userId)
            });

            await loadCards();
            await refreshAccountData();

            showMessage("Card created successfully");
        } catch (error) {
            showMessage(error.response?.data?.message || "Could not create card", "error");
        }
    };

    const blockCard = async (cardId) => {
        try {
            await api.patch(`/cards/${cardId}/block`);

            await loadCards();
            await refreshAccountData();

            showMessage("Card blocked successfully");
        } catch (error) {
            showMessage(error.response?.data?.message || "Could not block card", "error");
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
                    <h1>Cards</h1>
                    <p>Manage your banking cards safely and easily.</p>
                </div>

                <button className="primary-action" onClick={createCard}>
                    Create New Card
                </button>
            </section>

            <div className="cards-grid">
                {cards.length === 0 ? (
                    <div className="panel">
                        <p className="muted">No cards found.</p>
                    </div>
                ) : (
                    cards.map((card) => (
                        <div className="card-management-panel" key={card.id}>
                            <div className="bank-card-preview large-card">
                                <p>{card.holderName}</p>
                                <h3>**** **** **** {card.cardNumber.slice(-4)}</h3>

                                <div className="card-bottom">
                                    <span>Expires {card.expiryDate}</span>
                                    <strong>{card.status}</strong>
                                </div>
                            </div>

                            <div className="card-actions">
                                <p>
                                    Card ID: <strong>{card.id}</strong>
                                </p>

                                <button
                                    className="danger-btn"
                                    onClick={() => blockCard(card.id)}
                                    disabled={card.status === "BLOCKED"}
                                >
                                    {card.status === "BLOCKED" ? "Already Blocked" : "Block Card"}
                                </button>
                            </div>
                        </div>
                    ))
                )}
            </div>
        </Layout>
    );
}

export default Cards;