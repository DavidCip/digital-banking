import { useEffect, useState } from "react";
import Layout from "../components/Layout";
import api from "../api/api";

function Support() {
    const userId = localStorage.getItem("userId");

    const [message, setMessage] = useState("");
    const [cardId, setCardId] = useState("");
    const [tickets, setTickets] = useState([]);
    const [cards, setCards] = useState([]);

    useEffect(() => {
        loadTickets();
        loadCards();
    }, []);

    const loadTickets = async () => {
        try {
            const response = await api.get(`/support/user/${userId}`);
            setTickets(response.data);
        } catch (error) {
            alert("Could not load support tickets");
        }
    };

    const loadCards = async () => {
        try {
            const response = await api.get(`/cards/user/${userId}`);
            setCards(response.data);
        } catch (error) {
            console.log("Could not load cards");
        }
    };

    const createTicket = async (type) => {
        try {
            if (type === "lost-card") {
                await api.post("/support/lost-card", {
                    userId: Number(userId),
                    cardId: Number(cardId)
                });
            } else {
                await api.post(`/support/${type}`, {
                    userId: Number(userId),
                    message
                });
            }

            alert("Support request created");
            setMessage("");
            setCardId("");
            loadTickets();
            loadCards();
        } catch (error) {
            alert(error.response?.data?.message || "Could not create support request");
        }
    };

    return (
        <Layout>
            <section className="dashboard-header">
                <div>
                    <h1>Support</h1>
                    <p>Get help with your account, cards, or banking access.</p>
                </div>
            </section>

            <div className="forms-grid">
                <div className="panel">
                    <h2>Create Support Request</h2>

                    <form className="bank-form">
                        <label>Message</label>
                        <textarea
                            rows="4"
                            value={message}
                            onChange={(e) => setMessage(e.target.value)}
                            placeholder="Tell us how we can help..."
                        />

                        <button type="button" onClick={() => createTicket("ticket")}>
                            General Ticket
                        </button>

                        <button type="button" className="secondary-btn" onClick={() => createTicket("forgot-pin")}>
                            Forgot PIN
                        </button>

                        <button type="button" className="secondary-btn" onClick={() => createTicket("call-assistance")}>
                            Call Assistance
                        </button>
                    </form>

                    <div className="lost-card-box">
                        <h3>Lost Card</h3>

                        <select value={cardId} onChange={(e) => setCardId(e.target.value)}>
                            <option value="">Select card</option>
                            {cards.map((card) => (
                                <option key={card.id} value={card.id}>
                                    Card #{card.id} - **** {card.cardNumber.slice(-4)} - {card.status}
                                </option>
                            ))}
                        </select>

                        <button className="danger-btn full-width" onClick={() => createTicket("lost-card")}>
                            Report Lost Card
                        </button>
                    </div>
                </div>

                <div className="panel">
                    <h2>Your Tickets</h2>

                    {tickets.length === 0 ? (
                        <p className="muted">No support tickets yet.</p>
                    ) : (
                        <div className="ticket-list">
                            {tickets.map((ticket) => (
                                <div className="ticket-item" key={ticket.id}>
                                    <div>
                                        <strong>{ticket.type}</strong>
                                        <p>{ticket.message}</p>
                                        <span>{ticket.createdAt?.replace("T", " ").slice(0, 16)}</span>
                                    </div>

                                    <em>{ticket.status}</em>
                                </div>
                            ))}
                        </div>
                    )}
                </div>
            </div>
        </Layout>
    );
}

export default Support;