import { useEffect, useState } from "react";
import Layout from "../components/Layout";
import api from "../api/api";

function Cards() {
    const userId = localStorage.getItem("userId");
    const [cards, setCards] = useState([]);

    useEffect(() => {
        loadCards();
    }, []);

    const loadCards = async () => {
        try {
            const response = await api.get(`/cards/user/${userId}`);
            setCards(response.data);
        } catch (error) {
            alert("Could not load cards");
        }
    };

    const createCard = async () => {
        try {
            await api.post("/cards/create", {
                userId: Number(userId)
            });

            alert("Card created successfully");
            loadCards();
        } catch (error) {
            console.log(error.response?.data);
            alert(error.response?.data?.message || "Could not create card");
        }
    };

    const blockCard = async (cardId) => {
        try {
            await api.patch(`/cards/${cardId}/block`);

            alert("Card blocked successfully");
            loadCards();
        } catch (error) {
            alert(error.response?.data?.message || "Could not block card");
        }
    };

    return (
        <Layout>
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