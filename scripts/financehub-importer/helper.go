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
	"strings"
)

const (
	Incomes  = "Incomes"
	Expenses = "Expenses"
)

var sheetNames = []string{Incomes, Expenses}

func getHeadersIndexMap(headers []string) map[string]int {
	colMap := make(map[string]int)
	for i, h := range headers {
		colMap[h] = i
	}
	return colMap
}

func isRowAlreadyProcessed(colMap map[string]int, row []string) bool {
	statusCol := colMap["Status"]
	return getString(row, statusCol) == "OK"
}

func processRow(sheet string, colMap map[string]int, row []string) error {
	var payload map[string]any
	var host string
	switch sheet {
	case Incomes:
		payload = buildIncomePayload(row, colMap)
		host = fmt.Sprintf("%s/financehub/api/v1/incomes", apiUrl)
	case Expenses:
		payload = buildExpensesPayload(row, colMap)
		host = fmt.Sprintf("%s/financehub/api/v1/expenses", apiUrl)
	default:
		os.Exit(1)
	}

	return sendData(payload, host)
}

func buildIncomePayload(row []string, colMap map[string]int) map[string]any {
	return map[string]any{
		"referenceDate":  getString(row, colMap["referenceDate"]),
		"grossAmount":    getFloat(row, colMap["grossAmount"]),
		"discountAmount": getFloat(row, colMap["discountAmount"]),
		"netAmount":      getFloat(row, colMap["netAmount"]),
		"type":           getString(row, colMap["type"]),
		"description":    getString(row, colMap["description"]),
	}
}

func buildExpensesPayload(row []string, colMap map[string]int) map[string]any {
	return map[string]any{
		"referenceDate":  getString(row, colMap["referenceDate"]),
		"category":       getString(row, colMap["category"]),
		"amount":         getFloat(row, colMap["amount"]),
		"description":    getString(row, colMap["description"]),
		"isFixedExpense": getBool(row, colMap["isFixedExpense"]),
	}
}

func sendData(payload map[string]any, host string) error {
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

func validateSheet(sheet string) {
	if sheet == "" {
		fmt.Println("❌ Please provide a sheet name using --sheet (e.g., --sheet Incomes)")
		os.Exit(1)
	}

	if !slices.Contains(sheetNames, sheet) {
		log.Fatalf("❌ Invalid sheet name: %s. Allowed values: %v", sheet, sheetNames)
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
		strings.ReplaceAll(row[index], ",", ".")
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

func convertToStringRows(data [][]any) [][]string {
	result := make([][]string, len(data))
	for i, row := range data {
		strRow := make([]string, len(row))
		for j, cell := range row {
			strRow[j] = fmt.Sprintf("%v", cell)
		}
		result[i] = strRow
	}
	return result
}
