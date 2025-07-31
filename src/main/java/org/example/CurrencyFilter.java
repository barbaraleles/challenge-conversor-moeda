package org.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CurrencyFilter {

    // Moedas permitidas
    private static final Set<String> MOEDAS_PERMITIDAS = Set.of("ARS", "BOB", "BRL", "CLP", "COP", "USD");

    // Mapa de nomes das moedas
    private static final Map<String, String> NOMES_MOEDAS = Map.of(
            "ARS", "Argentine Peso",
            "BOB", "Boliviano",
            "BRL", "Brazilian Real",
            "CLP", "Chilean Peso",
            "COP", "Colombian Peso",
            "USD", "US Dollar"
    );

    public static Map<String, Double> filtrarMoedas(Map<String, Double> todasTaxas) {
        Map<String, Double> taxasFiltradas = new HashMap<>();
        for (String moeda : MOEDAS_PERMITIDAS) {
            if (todasTaxas.containsKey(moeda)) {
                taxasFiltradas.put(moeda, todasTaxas.get(moeda));
            }
        }
        return taxasFiltradas;
    }


    public static String getNomeMoeda(String codigo) {
        return NOMES_MOEDAS.getOrDefault(codigo, "Moeda desconhecida");
    }


    public static Set<String> getMoedasPermitidas() {
        return MOEDAS_PERMITIDAS;
    }
}
