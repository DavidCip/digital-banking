import { createContext, useContext, useState } from "react";
import api from "../api/api";

const AccountContext = createContext();

export function AccountProvider({ children }) {
    const [account, setAccount] = useState(null);
    const [transactions, setTransactions] = useState([]);
    const [cards, setCards] = useState([]);

    const refreshAccountData = async () => {
        const userId = localStorage.getItem("userId");

        if (!userId) return;

        const accountRes = await api.get(`/accounts/user/${userId}`);
        const txRes = await api.get(`/transactions/user/${userId}`);
        const cardsRes = await api.get(`/cards/user/${userId}`);

        setAccount(accountRes.data);
        setTransactions(txRes.data);
        setCards(cardsRes.data);
    };

    return (
        <AccountContext.Provider
            value={{
                account,
                transactions,
                cards,
                refreshAccountData
            }}
        >
            {children}
        </AccountContext.Provider>
    );
}

export function useAccount() {
    return useContext(AccountContext);
}