# 📊 FinanceHub — Domínios e Casos de Uso

## 🧱 Domínio: Contas
Entidade: `Conta`

### Casos de uso:
- Cadastrar nova conta
- Atualizar/remover conta
- Listar contas cadastradas

---

## 🎯 Domínio: Objetivos Financeiros
Entidade: `ObjetivoFinanceiro`

### Casos de uso:
- Criar objetivo
- Atualizar/remover objetivo
- Consultar objetivos ativos e atingidos

---

## 📈 Domínio: Estratégia de Investimento
Entidade: `EstrategiaInvestimento`

### Casos de uso:
- Cadastrar estratégia
- Listar estratégias
- Ativar/desativar uma estratégia
- Consultar estratégia ativa
- [Futuro] Calcular valor ideal a investir com base na estratégia

---

## 💸 Domínio: Proventos
Entidade: `Provento`

### Casos de uso:
- Buscar proventos automaticamente (via API/scraping)
- Inserir provento manual
- Listar proventos por ativo, data, tipo ou conta

---

## 🗕️ Domínio: Balanço Mensal
Entidade: `BalancoMensal`

### Casos de uso:
- Cadastrar entradas do mês
- Cadastrar despesas (fixas e variáveis)
- Inserir fotografia mensal do patrimônio (snapshot)
- Consultar balanço mensal consolidado

---

## 📋 Domínio: Patrimônio Mensal
Entidade: `PatrimonioMensal`

> Pode ser parte do `BalancoMensal` ou separado, se a modelagem exigir

### Casos de uso:
- Inserir valor atual de cada recurso
- Acompanhar evolução patrimonial ao longo dos meses

---

## 📊 Domínio: Balanço Geral
Agregador de `BalancoMensal`

### Casos de uso:
- Gerar relatório consolidado mês a mês
- Visualizar evolução de entradas, gastos, patrimônio
- Filtrar e exportar dados

---

## 🚀 Funcionalidade Transversal: Investimento Ideal do Mês
Dependências: Estratégia + Balanço + Contas

### Casos de uso:
- Calcular valor ideal a ser investido no mês com base na estratégia ativa
- Levar em conta entradas, despesas e saldo disponível

> A ser implementado futuramente como serviço de aplicação ou domínio separado




# 🧱 Próximos passos recomendados
- Modelar as entidades principais: Conta, Objetivo, Estrategia, Provento, BalancoMensal, PatrimonioMensal.

- Listar os ports de entrada (casos de uso): exemplo: CadastrarContaUseCase, BuscarProventosUseCase, etc.

- Decidir se os domínios compartilham dados ou se são isolados (dependência entre domínios).

- Começar pelos domínios mais simples e isolados (Contas, Objetivos) e ir avançando.

