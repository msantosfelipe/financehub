CREATE TABLE accounts (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    display_name VARCHAR(255),
    bank_code VARCHAR(10),
    agency VARCHAR(20),
    account_number VARCHAR(50),
    pix VARCHAR(255),
    login_user VARCHAR(255),
    account_type VARCHAR(255),
    description VARCHAR(255),
    active BOOLEAN NOT NULL DEFAULT TRUE
);
