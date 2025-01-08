#Utiliza 10 jugadores y haz el promedio de veces en lo que tardarían en arruinarse

import random
import numpy as np

def roulette():
    return random.randint(0,36)

def apuesta_par():
    numero = roulette()
    return numero%2==0 and numero != 0

def simulacion():
    dinero = 1000
    apuesta = 50
    rondas = 0
    while dinero >0:
        rondas += 1
        if apuesta_par():
            dinero += apuesta
        else:
            dinero -= apuesta
    return rondas

rondas = []
for _ in range(10):
    rondas.append(simulacion())
promedio_rondas = np.mean(rondas)
print(f"Los jugadores lo perdieron todo a las {promedio_rondas} rondas")