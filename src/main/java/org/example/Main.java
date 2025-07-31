package org.example;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        Scanner leia = new Scanner(System.in);

        // Conectando URL
        String url = "https://v6.exchangerate-api.com/v6/68d58edffa9553a1549cab35/latest/USD";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = client
                .send(request, HttpResponse.BodyHandlers.ofString());

        Gson gson = new Gson();
        ExchangeResponse exchange = gson.fromJson(response.body(), ExchangeResponse.class);

        while (true) {
            System.out.println(" *********************************************** ");
            System.out.println(" Seja bem vindo ao conversor de moedas! ");
            System.out.print("\n");
            System.out.println(" Escolha uma opção: ");
            System.out.println(" 1 - Dólar para Peso Argentino ");
            System.out.println(" 2 - Peso Argentino para Dolar ");
            System.out.println(" 3 - Dólar para Real ");
            System.out.println(" 4 - Real para Dólar ");
            System.out.println(" 5 - Dólar para Peso Colombiano ");
            System.out.println(" 6 - Peso Colombiano para Dólar ");
            System.out.println(" 7 - Sair ");
            System.out.println(" *********************************************** ");

            // Lendo a opção do usuário
            int opcao = leia.nextInt();
            if (opcao == 7) {
                System.out.println("Saindo do programa...");
                return;
            }

            System.out.print("Digite o valor que deseja converter: ");
            double valor = leia.nextDouble();

            double resultado = 0;

            switch (opcao) {
                case 1: // USD → ARS
                    resultado = valor * exchange.conversion_rates.get("ARS");
                    System.out.printf("%.2f dólares equivalem a %.2f pesos argentinos.%n", valor, resultado);
                    break;
                case 2: // ARS → USD
                    resultado = valor / exchange.conversion_rates.get("ARS");
                    System.out.printf("%.2f pesos argentinos equivalem a %.2f dólares.%n", valor, resultado);
                    break;
                case 3: // USD → BRL
                    resultado = valor * exchange.conversion_rates.get("BRL");
                    System.out.printf("%.2f dólares equivalem a %.2f reais.%n", valor, resultado);
                    break;
                case 4: // BRL → USD
                    resultado = valor / exchange.conversion_rates.get("BRL");
                    System.out.printf("%.2f reais equivalem a %.2f dólares.%n", valor, resultado);
                    break;
                case 5: // USD → COP
                    resultado = valor * exchange.conversion_rates.get("COP");
                    System.out.printf("%.2f dólares equivalem a %.2f pesos colombianos.%n", valor, resultado);
                    break;
                case 6: // COP → USD
                    resultado = valor / exchange.conversion_rates.get("COP");
                    System.out.printf("%.2f pesos colombianos equivalem a %.2f dólares.%n", valor, resultado);
                    break;
                default:
                    System.out.println("Opção inválida!");
            }

            System.out.println(); // quebra de linha entre repetições
        }

    }
}