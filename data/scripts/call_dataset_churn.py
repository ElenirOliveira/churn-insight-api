import os
import random
from datetime import date

import numpy as np
import pandas as pd
from faker import Faker

# ============================================================
# 1) CONFIGURAÇÃO DE CAMINHOS (BASE_DIR, DATA_DIR, OUTPUT)
# ============================================================

# Caminho absoluto da pasta THIS FILE: .../api/data/scripts/call_dataset_churn.py
CURRENT_FILE_DIR = os.path.dirname(os.path.abspath(__file__))

# Caminho da raiz do projeto (sobe dois níveis: scripts -> data -> api)
BASE_DIR = os.path.dirname(os.path.dirname(CURRENT_FILE_DIR))

# Pasta "data" diretamente dentro de api/
DATA_DIR = os.path.join(BASE_DIR, "data")

# Garante que a pasta api/data exista
os.makedirs(DATA_DIR, exist_ok=True)

# Caminho final do CSV
OUTPUT_PATH = os.path.join(DATA_DIR, "churn_customers_dataset.csv")

# ============================================================
# 2) CONFIGURAÇÃO DO FAKE DATA
# ============================================================

fake = Faker("pt_BR")

N = 10_000  # número de registros

plan_types = ["BASIC", "STANDARD", "PREMIUM"]
device_types = ["MOBILE", "WEB", "BOTH"]

data = []

# ============================================================
# 3) GERAÇÃO DOS REGISTROS
# ============================================================

for i in range(1, N + 1):

    # -----------------------
    # DADOS BÁSICOS DO CLIENTE
    # -----------------------
    name = fake.name()
    age = random.randint(18, 80)
    city = fake.city()
    signup_date = fake.date_between(start_date="-3y", end_date="today")

    try:
        state = fake.estado_sigla()
    except AttributeError:
        state = "SP"  # fallback caso a lib não tenha esse método

    # -----------------------
    # COMPORTAMENTO DE USO
    # -----------------------
    contract_months = random.randint(1, 36)
    tenure_months = random.randint(1, contract_months)

    payment_delays = random.randint(0, 6)
    late_payments_last_6m = random.randint(0, payment_delays)

    on_time_payment_ratio = 1 - (payment_delays * 0.1) + random.uniform(-0.1, 0.1)
    on_time_payment_ratio = max(0.0, min(1.0, on_time_payment_ratio))
    on_time_payment_ratio = round(on_time_payment_ratio, 2)

    plan_type = random.choices(
        plan_types,
        weights=[0.5, 0.3, 0.2],  # mais BASIC, depois STANDARD, depois PREMIUM
        k=1
    )[0]

    device_type = random.choice(device_types)

    # Diferentes perfis de uso dependendo do plano
    if plan_type == "BASIC":
        monthly_usage = round(random.uniform(0, 120), 2)
        logins_per_month = random.randint(2, 20)
    elif plan_type == "STANDARD":
        monthly_usage = round(random.uniform(50, 220), 2)
        logins_per_month = random.randint(5, 30)
    else:  # PREMIUM
        monthly_usage = round(random.uniform(100, 300), 2)
        logins_per_month = random.randint(10, 40)

    # -----------------------
    # MODELO PROBABILÍSTICO DE CHURN (RULE-BASED)
    # -----------------------
    probability = (
        (contract_months < 6) * 0.20 +
        (payment_delays > 2) * 0.30 +
        (monthly_usage < 30) * 0.25 +
        (logins_per_month < 5) * 0.20 +
        (plan_type == "BASIC") * 0.15 +
        (late_payments_last_6m > 1) * 0.10 +
        (on_time_payment_ratio < 0.5) * 0.30
    )

    probability = min(1.0, max(0.0, probability))
    churn = np.random.choice([0, 1], p=[1 - probability, probability])

    # -----------------------
    # MONTAGEM DO REGISTRO
    # -----------------------
    record = {
        "id": i,
        "name": name,
        "age": age,
        "city": city,
        "state": state,
        "contract_months": contract_months,
        "tenure_months": tenure_months,
        "payment_delays": payment_delays,
        "late_payments_last_6m": late_payments_last_6m,
        "on_time_payment_ratio": on_time_payment_ratio,
        "monthly_usage": monthly_usage,
        "logins_per_month": logins_per_month,
        "plan_type": plan_type,
        "device_type": device_type,
        "signup_date": signup_date,
        "churn": churn,
        "churn_probability": round(probability, 2),
    }

    data.append(record)

# ============================================================
# 4) DATAFRAME FINAL + PRÉ-VISUALIZAÇÃO
# ============================================================

df = pd.DataFrame(data)

print("\n===== AMOSTRA DO DATASET =====")
print(df.head())

print("\n===== INFO =====")
print(df.info())

print("\n===== ESTATÍSTICAS =====")
print(df.describe(include="all"))

# ============================================================
# 5) SALVAR CSV EM api/data/churn_customers_dataset.csv
# ============================================================

df.to_csv(OUTPUT_PATH, index=False, encoding="utf-8")

print(f"\n✅ Dataset salvo com sucesso em: {OUTPUT_PATH}")

