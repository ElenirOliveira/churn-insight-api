# 🧠 Churn Insight — Engenharia de Dados

Pipeline de **Engenharia de Dados em Python** desenvolvido no contexto do **Hackathon ONE**, responsável por realizar o tratamento, padronização, modelagem e preparação dos dados de churn de clientes de telecomunicações.

Este repositório contém a versão inicial do pipeline de dados, incluindo:

- Ingestão de dados a partir de CSV público
- Padronização de schema e valores categóricos
- Tratamento de dados inconsistentes e valores nulos
- Feature engineering orientado a churn
- Preparação dos dados para Machine Learning
- Exportação de dataset final limpo e modelado

---

## 🚀 Tecnologias Utilizadas

- Python 3
- Pandas
- NumPy
- Scikit-learn
- Matplotlib
- Seaborn

---

## 🗂️ Fonte dos Dados

- Dataset público: **Telco Customer Churn**
- Origem: arquivo CSV hospedado em repositório GitHub
- Ingestão realizada via `pandas.read_csv()`

Os dados utilizados não contêm informações sensíveis diretas e são tratados para uso analítico e preditivo.

---

## 🏗️ Arquitetura do Projeto

    flowchart LR
    A[📂 Dataset Público<br/>Telco Customer Churn CSV]
    B[⚙️ Pipeline de Engenharia de Dados<br/>Python / Pandas]
    B1[Padronização de Schema]
    B2[Tratamento de Nulos e Tipos]
    B3[Feature Engineering]
    B4[Dataset Final Tratado<br/>churn_telco_dados_tratados.csv]

    C[📊 Power BI<br/>Dashboard Analítico]
    C1[Página 1<br/>Visão Executiva de Churn]
    C2[Página 2<br/>Análise de Perfil e Comportamento]

    D[🧠 Churn Insight API<br/>Java / Spring Boot]
    D1[Endpoint /api/predict]
    D2[Regras de Negócio / Modelo Base]
    D3[Swagger UI]

    E[🤖 Evolução Futura<br/>Modelo de Machine Learning]

    A --> B
    B --> B1 --> B2 --> B3 --> B4
    B4 --> C
    B4 --> D
    D --> E

Este diagrama representa o fluxo completo do projeto, desde a ingestão dos dados até o consumo analítico e preditivo, seguindo boas práticas de engenharia de dados, governança e separação de responsabilidades.

---

🧹 Etapas de Tratamento dos Dados
✔️ Padronização

* Normalização de nomes de colunas (snake_case)

* Remoção de acentos e caracteres especiais

* Padronização de valores textuais (lowercase)

✔️ Qualidade dos Dados

* Conversão explícita de colunas numéricas

* Tratamento de valores nulos

* Remoção de registros duplicados por chave natural

* Validação de tipos e consistência do schema

✔️ Feature Engineering

* Criação da variável ticket_medio

* Criação da variável cliente_novo

* Mapeamento da variável target evasao (0 / 1)

---

🧠 Preparação para Machine Learning

O pipeline realiza a preparação completa dos dados para modelagem, incluindo:

* Separação entre features e variável target

* Identificação automática de colunas numéricas e categóricas

* Pipeline de pré-processamento com:

 * StandardScaler para variáveis numéricas

 * OneHotEncoder para variáveis categóricas

* Modelo baseline com LogisticRegression

Essa estrutura permite fácil substituição ou evolução do modelo.

---

📁 Saída do Pipeline

O pipeline gera um dataset final tratado e modelado:

  churn_telco_dados_tratados.csv

Este arquivo está pronto para uso em:

* Modelos de Machine Learning

* Dashboards analíticos (Power BI)

* APIs de inferência

* Camadas Silver / Gold em arquiteturas de dados

---

🔐 Governança de Dados

O pipeline segue princípios fundamentais de governança:

* Padronização consistente de schema

* Dados anonimizados para fins analíticos

* Pipeline reprodutível e versionável

* Separação clara entre ingestão, transformação e saída

* Preparado para integração com ambientes corporativos

---

🚀 Próximos Passos

* Integração com Data Lake / Lakehouse

* Versionamento de dados

* Monitoramento de qualidade e consistência

* Deploy em ambiente cloud (Oci / Databricks)

* Evolução para arquitetura Bronze / Silver / Gold


---

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
