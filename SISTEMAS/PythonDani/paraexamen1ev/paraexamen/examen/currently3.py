import random


def render():
    dinero = 100
    rondas = 1000

    for _ in range(rondas):
        rodillo1 = random.randint(1, 6)
        rodillo2 = random.randint(1, 6)
        rodillo3 = random.randint(1, 6)
        if rodillo1 == rodillo2 == rodillo3:
            dinero = dinero + 5
        else:
            dinero = dinero - 1
    return dinero, rondas


ganancias, num_tiradas = render()
if ganancias > 0:
    print(f"Después de {num_tiradas} tiradas, las ganancias totales son: {ganancias} monedas.")
else:
    print(f"Saldo en negativo - Perdidas: {ganancias}, Tiradas: {num_tiradas}")