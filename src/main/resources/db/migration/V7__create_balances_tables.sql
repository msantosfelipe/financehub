CREATE TABLE balances (
    id UUID PRIMARY KEY,
    reference_date DATE NOT NULL,
    total_gross_incomes NUMERIC(15, 2) NOT NULL,
    total_net_incomes NUMERIC(15, 2) NOT NULL,
    total_fixed_expenses NUMERIC(15, 2) NOT NULL,
    total_other_expenses NUMERIC(15, 2) NOT NULL,
    total_invested NUMERIC(15, 2) NOT NULL
);

CREATE INDEX idx_balances_reference_date ON balances (reference_date);
