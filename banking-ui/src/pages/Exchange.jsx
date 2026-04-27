import { useEffect, useState } from "react";
import Layout from "../components/Layout";
import api from "../api/api";

function Exchange() {
    const userId = localStorage.getItem("userId");

    const [rates, setRates] = useState({});
    const [result, setResult] = useState(null);

    const [form, setForm] = useState({
        fromCurrency: "RON",
        toCurrency: "EUR",
        amount: ""
    });

    useEffect(() => {
        loadRates();
    }, []);

    const loadRates = async () => {
        try {
            const response = await api.get("/exchange/rates");
            setRates(response.data);
        } catch (error) {
            alert("Could not load exchange rates");
        }
    };

    const handleChange = (e) => {
        setForm({
            ...form,
            [e.target.name]: e.target.value
        });
    };

    const convert = async (e) => {
        e.preventDefault();

        try {
            const response = await api.post("/exchange/convert", {
                userId: Number(userId),
                fromCurrency: form.fromCurrency,
                toCurrency: form.toCurrency,
                amount: Number(form.amount)
            });

            setResult(response.data);
            setForm({ ...form, amount: "" });
        } catch (error) {
            alert(error.response?.data?.message || "Exchange failed");
        }
    };

    return (
        <Layout>
            <section className="dashboard-header">
                <div>
                    <h1>Currency Exchange</h1>
                    <p>Convert your RON balance into other currencies.</p>
                </div>
            </section>

            <div className="forms-grid">
                <div className="panel">
                    <h2>Convert Money</h2>

                    <form className="bank-form" onSubmit={convert}>
                        <label>From currency</label>
                        <select
                            name="fromCurrency"
                            value={form.fromCurrency}
                            onChange={handleChange}
                        >
                            <option value="RON">RON</option>
                            <option value="EUR">EUR</option>
                            <option value="USD">USD</option>
                            <option value="RUB">RUB</option>
                            <option value="JPY">JPY</option>
                        </select>

                        <label>To currency</label>
                        <select
                            name="toCurrency"
                            value={form.toCurrency}
                            onChange={handleChange}
                        >
                            <option value="EUR">EUR</option>
                            <option value="USD">USD</option>
                            <option value="RON">RON</option>
                            <option value="RUB">RUB</option>
                            <option value="JPY">JPY</option>
                        </select>

                        <label>Amount</label>
                        <input
                            type="number"
                            name="amount"
                            value={form.amount}
                            onChange={handleChange}
                            placeholder="100"
                        />

                        <button type="submit">Convert</button>
                    </form>

                    {result && (
                        <div className="exchange-result">
                            <h3>Conversion Result</h3>
                            <p>
                                {result.amount} {result.fromCurrency} =
                            </p>
                            <strong>
                                {result.convertedAmount} {result.toCurrency}
                            </strong>
                            <span>
                Remaining RON balance: {result.remainingRonBalance}
              </span>
                        </div>
                    )}
                </div>

                <div className="panel">
                    <h2>Exchange Rates</h2>
                    <p className="muted">Rates are calculated relative to RON.</p>

                    <div className="rates-list">
                        {Object.entries(rates).map(([currency, value]) => (
                            <div className="rate-item" key={currency}>
                                <span>{currency}</span>
                                <strong>{value} RON</strong>
                            </div>
                        ))}
                    </div>
                </div>
            </div>
        </Layout>
    );
}

export default Exchange;