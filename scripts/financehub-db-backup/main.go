package main

import (
	"context"
	"fmt"
	"io"
	"log"
	"os"
	"os/exec"
	"time"

	firebase "firebase.google.com/go/v4"
	"github.com/joho/godotenv"
	"google.golang.org/api/option"
)

func main() {
	err := godotenv.Load()
	if err != nil {
		log.Fatal("Erro ao carregar .env")
	}

	containerName := os.Getenv("CONAINER_NAME")
	dbName := os.Getenv("DB_NAME")
	dbUsername := os.Getenv("DB_USERNAME")

	date := time.Now().Format("2006-01-02")
	fileName := fmt.Sprintf("backup-financehub-%s.sql", date)

	cmd := exec.Command("docker", "exec", "-t", containerName, "pg_dump", "-U", dbUsername, "-d", dbName, "-F", "p")

	outFile, err := os.Create(fmt.Sprintf("%s", fileName))
	if err != nil {
		panic(err)
	}
	defer outFile.Close()

	cmd.Stdout = outFile

	fmt.Println("Executing pg_dump via docker...")
	err = cmd.Run()
	if err != nil {
		panic(err)
	}

	fmt.Printf("Backup generated: %s\n", fileName)

	err = uploadToFirebase(fileName)
	if err != nil {
		panic(err)
	}

	fmt.Println("Upload finished.")
}

func uploadToFirebase(fileName string) error {
	ctx := context.Background()

	app, err := firebase.NewApp(ctx, nil, option.WithCredentialsFile("creds/serviceAccountKey.json"))
	if err != nil {
		return err
	}

	client, err := app.Storage(ctx)
	if err != nil {
		return err
	}

	bucket, err := client.Bucket("credit-card-bills-6fe28.appspot.com")
	if err != nil {
		return err
	}

	f, err := os.Open(fileName)
	if err != nil {
		return err
	}
	defer f.Close()

	writer := bucket.Object("backups/financehub-postgres/" + fileName).NewWriter(ctx)
	if _, err := io.Copy(writer, f); err != nil {
		return err
	}

	err = os.Remove(fmt.Sprintf("%s", fileName))
	if err != nil {
		fmt.Printf("Error deleting local file: %v\n", err)
	} else {
		fmt.Printf("Local file deleted: %s\n", fileName)
	}
	return writer.Close()
}
