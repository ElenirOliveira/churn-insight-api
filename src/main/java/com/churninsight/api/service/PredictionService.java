package com.churninsight.api.service;

import com.churninsight.api.dto.CustomerInputDto;
import com.churninsight.api.dto.PredictionResponseDto;
import com.churninsight.api.mapper.PredictionMapper;
import com.churninsight.api.model.PredictionModel;
import com.churninsight.api.util.PredictionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PredictionService {

    private final PredictionMapper predictionMapper;

    // contador simples em memória para gerar IDs das previsões
    private long counter = 1L;

    public PredictionResponseDto predict(CustomerInputDto input) {

        double score = 0.0;

        // Soma dos "pesos" das features
        score += PredictionUtils.scoreByContractMonths(input.contractMonths());
        score += PredictionUtils.scoreByPaymentDelays(input.paymentDelays());
        score += PredictionUtils.scoreByMonthlyUsage(input.monthlyUsage());
        score += PredictionUtils.scoreByPlanType(input.planType());

        // Normaliza de forma proporcional ao intervalo teórico
        double probability = PredictionUtils.normalizeToProbability(score);

        // Regra de decisão simples
        String prediction = probability >= 0.5 ? "Churn" : "No Churn";

        // Modelo em memória (mock)
        var model = PredictionModel.builder()
                .id(counter++)
                .prediction(prediction)
                .probability(probability)
                .build();

        // Retorna DTO formatado pra API
        return predictionMapper.toDto(model);
    }
}
