CREATE TABLE assets (
    id UUID PRIMARY KEY,
    ticker VARCHAR(50) UNIQUE NOT NULL,
    name VARCHAR(255),
    type VARCHAR(255) NOT NULL,
    country VARCHAR(255)
);
