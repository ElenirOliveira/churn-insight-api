# 🧠 Churn Insight API

API em Java/Spring Boot desenvolvida para o **Hackathon ONE**, responsável por calcular a probabilidade de churn (cancelamento) com base nas informações do cliente.

Este repositório contém a primeira versão funcional da API, incluindo:

* Endpoint de previsão (`/api/predict`)
* Estrutura de DTOs
* Modelo de predição mockado
* Regras iniciais de cálculo no utilitário `PredictionUtils`
* Swagger UI para testes rápidos
* Estrutura para geração de dataset sintético

---

## 🚀 Tecnologias Utilizadas

* Java 17
* Spring Boot 3
* Maven
* H2 Database (in-memory)
* MapStruct
* Lombok
* Swagger (Springdoc OpenAPI)
* Python (para geração do dataset)

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

data/
├── .gitkeep
└── scripts/
    ├── call_dataset_churn.py
    └── .gitkeep
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

A lógica inicial está centralizada em `PredictionUtils` e serve como **baseline para o time ajustar e evoluir**.

Cada atributo contribui com pesos positivos ou negativos para um *score*, normalizado entre **0 e 1**.

Essa camada poderá futuramente ser substituída por:

* Modelo real de Machine Learning
* Integração com Python
* Microserviço dedicado à predição
* Ajustes manuais da equipe de Data Science

---

## 🧁 Dataset Sintético (para Data Science)

O projeto inclui uma estrutura destinada à geração de dataset fictício com **10.000 clientes**, usado para análises exploratórias (EDA), engenharia de atributos e modelagem supervisionada.

### 📁 Estrutura

```
data/
├── .gitkeep
└── scripts/
    ├── call_dataset_churn.py
    └── .gitkeep
```

O CSV gerado **não é versionado**, para evitar arquivos grandes no repositório.

---

### 📄 Sobre o Script `call_dataset_churn.py`

O script gera dados sintéticos contendo:

**Dados do cliente**

* `name`, `age`, `city`, `state`
* `signup_date`

**Comportamento**

* `monthly_usage`
* `logins_per_month`
* `device_type`
* `plan_type`

**Pagamentos**

* `payment_delays`
* `late_payments_last_6m`
* `on_time_payment_ratio`

**Atributos para ML**

* `churn` (0 ou 1)
* `churn_probability` (score calculado)

---

### ▶️ Como gerar o dataset

No terminal:

```bash
cd data/scripts
python call_dataset_churn.py
```

O arquivo será gerado automaticamente em:

```
data/churn_customers_dataset.csv
```

---

## 🔧 Como Executar a API

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

3. Envie o JSON de exemplo.

---

## 🤝 Contribuição do Time

Este projeto foi iniciado para o Hackathon ONE e será evoluído em equipe.

Contribuições bem-vindas:

* Ajustes nas regras de churn
* Evolução do modelo de predição
* Refatorações de arquitetura
* Criação de testes automatizados
* Expansão do dataset
* Implementação de ML real

---


README TÉCNICO — ENGENHARIA DE DADOS


📊 Churn Insight — Pipeline de Engenharia de Dados
📌 Visão Geral

Este projeto implementa um pipeline completo de engenharia de dados para tratamento, padronização e preparação de dados de churn de clientes de telecomunicações, com foco em qualidade, governança e preparo para machine learning e consumo analítico.

🗂️ Fonte dos Dados

Dataset público: Telco Customer Churn

Origem: CSV hospedado em repositório GitHub

Ingestão direta via pandas.read_csv()

🏗️ Arquitetura do Pipeline
Ingestão (CSV)
   ↓
Padronização de colunas
   ↓
Tradução e normalização de valores
   ↓
Tratamento de tipos e nulos
   ↓
Feature Engineering
   ↓
Preparação para ML
   ↓
Exportação do dataset tratado

🔧 Tecnologias Utilizadas

Python 3

Pandas

NumPy

Scikit-learn

Matplotlib / Seaborn (exploração)

🧹 Etapas de Tratamento dos Dados
✔️ Padronização

Normalização de nomes de colunas (snake_case)

Remoção de acentos e caracteres especiais

Padronização de strings (lowercase)

✔️ Qualidade

Conversão explícita de colunas numéricas

Tratamento de valores nulos

Remoção de registros duplicados

Validação de tipos

✔️ Feature Engineering

Criação de ticket_medio

Criação de cliente_novo

Mapeamento da variável target (evasao)

🧠 Preparação para Machine Learning

Separação de features e target

Identificação automática de colunas categóricas e numéricas

Pipeline com:

StandardScaler para variáveis numéricas

OneHotEncoder para categóricas

LogisticRegression como modelo base

📁 Saída do Pipeline

Arquivo final gerado:

churn_telco_dados_tratados.csv


Dataset limpo, modelado e pronto para:

Machine Learning

Dashboards analíticos

APIs de inferência

Camadas Silver / Gold

🔐 Governança de Dados

Dados anonimizados para fins analíticos

Padronização consistente de schema

Pipeline reprodutível e versionável

Pronto para integração com ambientes corporativos

🚀 Próximos Passos

Integração com Data Lake / Lakehouse

Versionamento de dados

Monitoramento de qualidade

Deploy em ambiente cloud (Azure / Databricks)

