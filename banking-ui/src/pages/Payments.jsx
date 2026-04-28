import { useState } from "react";
import Layout from "../components/Layout";
import api from "../api/Api.js";
import { useAccount } from "../context/AccountContext";

function Payments() {
    const userId = localStorage.getItem("userId");

    const { refreshAccountData } = useAccount();

    const [form, setForm] = useState({
        amount: "",
        beneficiary: "",
        description: ""
    });

    const handleChange = (e) => {
        setForm({
            ...form,
            [e.target.name]: e.target.value
        });
    };

    const makePayment = async (type) => {
        try {
            await api.post(`/payments/${type}`, {
                userId: Number(userId),
                amount: Number(form.amount),
                beneficiary: form.beneficiary,
                description: form.description
            });

            await refreshAccountData();

            alert(type === "bill" ? "Bill paid successfully" : "Fine paid successfully");

            setForm({
                amount: "",
                beneficiary: "",
                description: ""
            });
        } catch (error) {
            alert(error.response?.data?.message || "Payment failed");
        }
    };

    return (
        <Layout>
            <section className="dashboard-header">
                <div>
                    <h1>Payments</h1>
                    <p>Pay bills and fines quickly from your active balance.</p>
                </div>
            </section>

            <div className="forms-grid">
                <div className="panel">
                    <h2>Payment Details</h2>

                    <form className="bank-form">
                        <label>Beneficiary</label>
                        <input
                            name="beneficiary"
                            value={form.beneficiary}
                            onChange={handleChange}
                            placeholder="ex: Electrica, Digi, Politia Rutiera"
                        />

                        <label>Amount</label>
                        <input
                            type="number"
                            name="amount"
                            value={form.amount}
                            onChange={handleChange}
                            placeholder="120"
                        />

                        <label>Description</label>
                        <textarea
                            name="description"
                            value={form.description}
                            onChange={handleChange}
                            placeholder="Payment description"
                            rows="4"
                        />

                        <button type="button" onClick={() => makePayment("bill")}>
                            Pay Bill
                        </button>

                        <button
                            type="button"
                            className="secondary-btn"
                            onClick={() => makePayment("fine")}
                        >
                            Pay Fine
                        </button>
                    </form>
                </div>

                <div className="panel info-panel">
                    <h2>Payment Tips</h2>

                    <div className="tip-box">
                        <strong>Bills</strong>
                        <p>Use this option for utilities, internet, phone or services.</p>
                    </div>

                    <div className="tip-box">
                        <strong>Fines</strong>
                        <p>Use this option for traffic fines or official penalties.</p>
                    </div>

                    <div className="tip-box">
                        <strong>Security</strong>
                        <p>Every payment is saved automatically in your transaction history.</p>
                    </div>
                </div>
            </div>
        </Layout>
    );
}

export default Payments;