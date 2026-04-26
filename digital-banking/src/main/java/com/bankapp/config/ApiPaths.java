package com.bankapp.config;

public final class ApiPaths {

    private ApiPaths() {
    }

    public static final String API = "/api";
    public static final String AUTH = API + "/auth";
    public static final String USERS = API + "/users";
    public static final String USER_BY_ID = "/user/{userId}";
    public static final String ACCOUNTS = API + "/accounts";
    public static final String CARDS = API + "/cards";
    public static final String BLOCK_CARD = "/{cardId}/block";
    public static final String TRANSACTIONS = API + "/transactions";
    public static final String TRANSFERS = API + "/transfers";


    public static final String PAYMENTS = API + "/payments";
    public static final String EXCHANGE = API + "/exchange";
    public static final String SUPPORT = API + "/support";

    public static final String REGISTER = "/register";
    public static final String LOGIN = "/login";

    public static final String ME = "/me";
    public static final String DEPOSIT = "/deposit";
    public static final String TRANSFER_TO_SAVINGS = "/transfer-to-savings";
    public static final String TRANSFER_FROM_SAVINGS = "/transfer-from-savings";

    public static final String SEND = "/send";
    public static final String SEARCH = "/search";

    public static final String CREATE = "/create";
    public static final String BLOCK = "/{cardId}/block";

    public static final String BILL = "/bill";
    public static final String FINE = "/fine";

    public static final String CONVERT = "/convert";
    public static final String RATES = "/rates";

    public static final String LOST_CARD = "/lost-card";
    public static final String FORGOT_PIN = "/forgot-pin";
    public static final String CALL_ASSISTANCE = "/call-assistance";
    public static final String TICKET = "/ticket";

    public static final String BY_ID = "/{userId}";
    public static final String UPDATE_PHONE = "/{userId}/phone";
    public static final String UPDATE_PASSWORD = "/{userId}/password";

}
