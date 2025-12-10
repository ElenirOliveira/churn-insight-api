# 🧠 Churn Insight API

API em Java/Spring Boot desenvolvida para o **Hackathon ONE**, responsável por calcular a probabilidade de churn (cancelamento) com base nas informações do cliente.

Este repositório contém a primeira versão funcional da API, incluindo:

* Endpoint de previsão (`/api/predict`)
* Estrutura de DTOs
* Modelo de predição mockado
* Regras iniciais de cálculo no utilitário `PredictionUtils`
* Swagger UI para testes rápidos

---

## 🚀 Tecnologias Utilizadas

* Java 17
* Spring Boot 3
* Maven
* H2 Database (in-memory)
* MapStruct
* Lombok
* Swagger (Springdoc OpenAPI)

---

## 📌 Estrutura do Projeto

```
src/main/java/com/churninsight/api
├── controller        → PredictionController
├── dto               → CustomerInputDto, PredictionResponseDto
├── mapper            → PredictionMapper
├── model             → PredictionModel
├── service           → PredictionService
└── util              → PredictionUtils
```

Arquitetura simples, modular e preparada para evolução pelo time.

---

## 🔮 Endpoint Principal

### **POST /api/predict**

Envia dados de um cliente e recebe uma previsão calculada com base nas regras mockadas.

---

### **Exemplo de JSON enviado**

```json
{
  "contractMonths": 12,
  "paymentDelays": 1,
  "monthlyUsage": 230.5,
  "planType": "PREMIUM"
}
```

### **Exemplo de resposta**

```json
{
  "id": 1,
  "prediction": "No Churn",
  "probability": 0.125
}
```

---

## 📊 Regras de Cálculo (MVP)

A lógica atual está centralizada em `PredictionUtils` e serve apenas como **versão inicial para ajustes do time**.

Cada atributo contribui positiva ou negativamente para um score, que é normalizado entre **0 e 1**.

Esta camada é facilmente substituível por:

* Modelo de Machine Learning real
* Integração com Python
* Microserviço de predição
* Ajustes manuais da equipe de Data Science

---

## 🔧 Como Executar

```bash
mvn spring-boot:run
```

A API estará disponível em:

```
http://localhost:8080
```

### Swagger UI

```
http://localhost:8080/swagger-ui.html
```

---

## 🧪 Testando com Postman / Insomnia

1. Crie uma requisição **POST**
2. Use o endpoint:

```
http://localhost:8080/api/predict
```

3. Envie o JSON demonstrado anteriormente.

---


