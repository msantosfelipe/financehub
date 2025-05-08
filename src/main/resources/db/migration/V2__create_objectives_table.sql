CREATE TABLE investments_objective (
    id UUID PRIMARY KEY,
    objective_horizon VARCHAR(50) NOT NULL,
    estimated_date DATE,
    created_date DATE NOT NULL DEFAULT CURRENT_DATE,
    updated_date DATE,
    description TEXT NOT NULL,
    status VARCHAR(50) NOT NULL
);
