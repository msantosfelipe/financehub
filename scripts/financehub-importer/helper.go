package main

import (
	"bytes"
	"encoding/json"
	"fmt"
	"log"
	"net/http"
	"os"
	"slices"
	"strconv"
)

const (
	ApiUrl   = "http://localhost:8080"
	Incomes  = "Incomes"
	Expenses = "Expenses"
)

var SheetNames = []string{Incomes, Expenses}

func buildIncomePayload(row []string, colMap map[string]int) map[string]interface{} {
	return map[string]interface{}{
		"referenceDate":  getString(row, colMap["referenceDate"]),
		"grossAmount":    getFloat(row, colMap["grossAmount"]),
		"discountAmount": getFloat(row, colMap["discountAmount"]),
		"netAmount":      getFloat(row, colMap["netAmount"]),
		"type":           getString(row, colMap["type"]),
		"description":    getString(row, colMap["description"]),
	}
}

func buildExpensesPayload(row []string, colMap map[string]int) map[string]interface{} {
	return map[string]interface{}{
		"referenceDate":  getString(row, colMap["referenceDate"]),
		"category":       getString(row, colMap["category"]),
		"amount":         getFloat(row, colMap["amount"]),
		"description":    getString(row, colMap["description"]),
		"isFixedExpense": getBool(row, colMap["isFixedExpense"]),
	}
}

func sendData(payload map[string]interface{}, sheet string) error {
	host := getHost(sheet)
	body, err := json.Marshal(payload)
	if err != nil {
		return fmt.Errorf("error serializing JSON: %w", err)
	}

	resp, err := http.Post(host, "application/json", bytes.NewReader(body))
	if err != nil {
		return fmt.Errorf("error in request: %w", err)
	}
	defer resp.Body.Close()

	if resp.StatusCode != http.StatusOK {
		return fmt.Errorf("error in HTTP. Response %d", resp.StatusCode)
	}

	return nil
}

func getHost(sheet string) string {
	switch sheet {
	case Incomes:
		return fmt.Sprintf("%s/financehub/api/v1/incomes", ApiUrl)
	case Expenses:
		return fmt.Sprintf("%s/financehub/api/v1/expenses", ApiUrl)
	default:
		return ""
	}
}

func validateSheet(sheet string) {
	if sheet == "" {
		fmt.Println("❌ Please provide a sheet name using --sheet (e.g., --sheet Incomes)")
		os.Exit(1)
	}

	if !slices.Contains(SheetNames, sheet) {
		log.Fatalf("❌ Invalid sheet name: %s. Allowed values: %v", sheet, SheetNames)
		os.Exit(1)
	}
}

func getString(row []string, index int) string {
	if index < len(row) {
		return row[index]
	}
	return ""
}

func getFloat(row []string, index int) float64 {
	if index < len(row) {
		v, err := strconv.ParseFloat(row[index], 64)
		if err == nil {
			return v
		}
	}
	return 0
}

func getBool(row []string, index int) bool {
	if index < len(row) {
		v, err := strconv.ParseBool(row[index])
		if err == nil {
			return v
		}
	}
	return false
}
