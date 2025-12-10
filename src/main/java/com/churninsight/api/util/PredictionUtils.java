package com.churninsight.api.util;

public class PredictionUtils {

    // Faixa teórica do score:
    // mín = -0.25  (cliente "super fiel")
    // máx =  0.95  (cliente "super arriscado")
    private static final double MIN_SCORE = -0.25;
    private static final double MAX_SCORE = 0.95;

    private PredictionUtils() {
        // impede instância
    }

    /**
     * Tempo de contrato em meses.
     * - Clientes muito novos tendem a ter maior risco de churn.
     */
    public static double scoreByContractMonths(Integer contractMonths) {
        if (contractMonths == null) return 0.0;

        if (contractMonths < 3) {
            return 0.25;
        } else if (contractMonths < 6) {
            return 0.15;
        } else if (contractMonths < 12) {
            return 0.05;
        }
        // Cliente com longo tempo de casa tende a ter menor risco
        return -0.05;
    }

    /**
     * Atrasos de pagamento (quantidade de meses com atraso).
     * - Mais atrasos => maior risco de churn.
     */
    public static double scoreByPaymentDelays(Integer paymentDelays) {
        if (paymentDelays == null) return 0.0;

        if (paymentDelays == 0) return -0.05;
        if (paymentDelays == 1) return 0.10;
        if (paymentDelays <= 3) return 0.20;
        return 0.30;
    }

    /**
     * Uso mensal (horas, unidades, etc.).
     * - Uso muito baixo => maior risco.
     * - Uso muito alto => menor risco.
     */
    public static double scoreByMonthlyUsage(Double monthlyUsage) {
        if (monthlyUsage == null) return 0.0;

        if (monthlyUsage < 5) return 0.25;
        if (monthlyUsage < 10) return 0.10;
        if (monthlyUsage < 30) return 0.00;
        // usuário engajado
        return -0.10;
    }

    /**
     * Tipo de plano:
     * - BASIC: maior risco
     * - STANDARD: neutro/leve risco
     * - PREMIUM: menor risco
     */
    public static double scoreByPlanType(String planType) {
        if (planType == null) return 0.0;

        return switch (planType.trim().toUpperCase()) {
            case "BASIC" -> 0.15;
            case "STANDARD" -> 0.05;
            case "PREMIUM" -> -0.05;
            default -> 0.0; // plano desconhecido: neutro
        };
    }

    /**
     * Normaliza o score bruto para um valor de probabilidade entre 0.0 e 1.0
     * proporcionalmente ao intervalo [MIN_SCORE, MAX_SCORE].
     */
    public static double normalizeToProbability(double rawScore) {
        // garante que o score está dentro da faixa esperada
        double clamped = Math.max(MIN_SCORE, Math.min(MAX_SCORE, rawScore));

        // transforma de [-0.25, 0.95] para [0, 1]
        double probability = (clamped - MIN_SCORE) / (MAX_SCORE - MIN_SCORE);

        // só por segurança: clamp final
        probability = Math.max(0.0, Math.min(1.0, probability));

        // opcional: arredondar para 4 casas decimais
        return Math.round(probability * 10_000.0) / 10_000.0;
    }
}
