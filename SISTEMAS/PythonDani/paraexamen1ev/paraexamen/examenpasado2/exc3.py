"""Una tragaperras con 3 rodillos y 6 símbolos diferentes en cada rodillo tiene los siguiente premios:

* 3 símbolos iguales: 5 monedas.
* 2 símbolos iguales: 1 monedas.
* ningún símbolo igual: -1 moneda.

Escribe un programa que simule el juego y calcule las ganancias o perdidas totales después de un número 1000 
jugadas si empezamos con 100 monedas. """""

import random

# Configuración
N = 1000  # Número de jugadas
saldo = 100  # Saldo inicial
simbolos = [1, 2, 3, 4, 5, 6]  # Símbolos posibles en los rodillos

for _ in range(N):
    # Simular los 3 rodillos
    rodillo1 = random.choice(simbolos)
    rodillo2 = random.choice(simbolos)
    rodillo3 = random.choice(simbolos)

    # Verificar los premios
    if rodillo1 == rodillo2 == rodillo3:  # Tres símbolos iguales
        saldo += 5
    elif rodillo1 == rodillo2 or rodillo1 == rodillo3 or rodillo2 == rodillo3:  # Dos símbolos iguales
        saldo += 1
    else:  # Ningún símbolo igual
        saldo -= 1

# Mostrar resultado final
print("Saldo final después de", N, "jugadas:", saldo)

#Que sucederia si solo se ganasen las 5 monedas cuando saliese repetido uno de los seis simbolos
import random
def simulacion():
    dinero = 100
    rondas = 1000

    for _ in range(rondas):
        rodillo1 = random.randint(1, 6)
        rodillo2 = random.randint(1, 6)
        rodillo3 = random.randint(1, 6)
        if rodillo1==rodillo2==rodillo3:
            dinero = dinero + 5
        else:
            dinero = dinero - 1
    return dinero, rondas

ganancias, num_tiradas = simulacion()

print(f"Después de {num_tiradas} tiradas, las ganancias totales son: {ganancias} monedas.")