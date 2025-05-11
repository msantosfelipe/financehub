CREATE TABLE assets (
    id UUID PRIMARY KEY,
    ticker VARCHAR(50) UNIQUE NOT NULL,
    name VARCHAR(255),
    type VARCHAR(255) NOT NULL,
    region VARCHAR(255)
);

CREATE TABLE asset_earnings (
    id UUID PRIMARY KEY,
    asset_id UUID REFERENCES assets(id),
    total_amount_received NUMERIC(15, 2) NOT NULL,
    reference_date DATE NOT NULL,
    notes VARCHAR(255),
    UNIQUE (asset_id, reference_date)
);

CREATE INDEX idx_asset_earnings_asset_date
ON asset_earnings (asset_id, reference_date);
