package org.example;

import com.google.gson.Gson;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
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

        // Filtra as moedas permitidas da resposta
        Map<String, Double> taxasFiltradas = CurrencyFilter.filtrarMoedas(exchange.conversion_rates);

        // Exibe as cotações filtradas
        System.out.println("Cotações disponíveis com base no Dólar (USD):");
        for (String moeda : CurrencyFilter.getMoedasPermitidas()) {
            if (taxasFiltradas.containsKey(moeda)) {
                System.out.printf("1 USD = %.2f %s (%s)%n", taxasFiltradas.get(moeda), moeda, CurrencyFilter.getNomeMoeda(moeda));
            }
        }
        System.out.println();

        // Opções do menu com pares de moedas (origem, destino)
        Map<Integer, String[]> opcoes = Map.of(
                1, new String[]{"USD", "ARS"},
                2, new String[]{"ARS", "USD"},
                3, new String[]{"USD", "BRL"},
                4, new String[]{"BRL", "USD"},
                5, new String[]{"USD", "COP"},
                6, new String[]{"COP", "USD"}
        );

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

            if (!opcoes.containsKey(opcao)) {
                System.out.println("Opção inválida!");
                System.out.println();
                continue;
            }

            System.out.print("Digite o valor que deseja converter: ");
            double valor = leia.nextDouble();

            String from = opcoes.get(opcao)[0];
            String to = opcoes.get(opcao)[1];

            // Obtém taxas usando o filtro (taxas das moedas permitidas)
            double taxaFrom = taxasFiltradas.get(from);
            double taxaTo = taxasFiltradas.get(to);

            // Converte o valor via USD intermediário para precisão
            double valorEmUSD = valor / taxaFrom;
            double resultado = valorEmUSD * taxaTo;

            System.out.printf("%.2f %s (%s) equivalem a %.2f %s (%s).%n",
                    valor, from, CurrencyFilter.getNomeMoeda(from),
                    resultado, to, CurrencyFilter.getNomeMoeda(to));

            System.out.println();
        }

    }
}