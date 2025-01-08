""""Obten la siguiente evolución respecto el número de jugadores:

print("%6d" % njugadores,"%.1f" % a.mean(),"%.0f"%a.std())
    10 636.8 624
    20 603.0 799
    50 799.2 875
   100 558.1 500
   200 921.6 1236
   500 744.2 936
  1000 726.1 1021
  5000 734.3 967
 10000 734.5 1021
"""""
import random
import numpy as np

def roulette():
    return random.randint(0,36)

def apuesta_par():
    numero = roulette()
    return numero%2==0 and numero != 0

def simulacion ():
    dinero = 1000
    apuesta = 50
    rondas = 0
    while dinero > 0:
        rondas += 1
        if apuesta_par():
            dinero += apuesta
        else:
            dinero -= apuesta
    return rondas
rondas_list = []
list_players = [10,20,50,100,200,500,1000,5000,10000]
for number_players in list_players:
    for _ in range(number_players):
        rondas_list.append(simulacion())
    print("%6d" % number_players, "%.1f" % np.mean(rondas_list), "%.0f" % np.std(rondas_list))



