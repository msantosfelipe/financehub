CREATE TYPE account_type_enum AS ENUM ('NOT_DEFINED', 'CHECKING_ACCOUNT', 'INVESTMENT');

CREATE TABLE accounts (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    bank_code INT,
    agency INT,
    account_number INT,
    pix VARCHAR(255),
    login_user VARCHAR(255),
    account_type account_type_enum DEFAULT 'NOT_DEFINED',
    description VARCHAR(255),
    active BOOLEAN NOT NULL DEFAULT TRUE
);
