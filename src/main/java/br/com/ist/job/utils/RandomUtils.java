package br.com.ist.job.utils;

import java.util.Random;

public class RandomUtils {
    
    /**
     * Generate a random token
     * 
     * @param tamanho
     * @return
     */
    public static String generateRandomToken(int tamanho) {
        String caracteres = "abcdefghijklmnopqrstuvwxyz";
        String digitosNumericos = "0123456789";
        StringBuilder nomeNumeroAleatorio = new StringBuilder();

        Random random = new Random();
        for (int i = 0; i < tamanho; i++) {
            int tipoCaractere = random.nextInt(2); // 0 para caracteres e 1 para dígitos numéricos
            if (tipoCaractere == 0) {
                int indiceAleatorio = random.nextInt(caracteres.length());
                char caractereAleatorio = caracteres.charAt(indiceAleatorio);
                nomeNumeroAleatorio.append(caractereAleatorio);
            } else {
                int indiceAleatorio = random.nextInt(digitosNumericos.length());
                char digitoAleatorio = digitosNumericos.charAt(indiceAleatorio);
                nomeNumeroAleatorio.append(digitoAleatorio);
            }
        }

        return nomeNumeroAleatorio.toString();
    }
}
