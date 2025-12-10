package com.churninsight.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record CustomerInputDto(

        @NotNull(message = "contractMonths cannot be null")
        @Min(value = 0, message = "contractMonths cannot be negative")
        Integer contractMonths,

        @NotNull(message = "paymentDelays cannot be null")
        @Min(value = 0, message = "paymentDelays cannot be negative")
        Integer paymentDelays,

        @NotNull(message = "monthlyUsage cannot be null")
        @PositiveOrZero(message = "monthlyUsage must be zero or positive")
        Double monthlyUsage,

        @NotBlank(message = "planType cannot be blank")
        String planType

) {}
