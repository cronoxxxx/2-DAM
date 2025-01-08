"""""La ruleta europea tiene 37 números, de los cuales 18 son rojos, 18 negros 
y el 0, que es verde, entre las diversas apuestas existe la de de apostar todo 
a rojos o negros, en este caso si se gana, se ganaría lo mismo que se ha apostado.

Haz la siguiente simulación, empezando con una cantidad de 1000 euros, 
apostando de forma constante 50 Euros, haz que la maquina apueste siempre 
a los pares o a los impares, en ambos casos si sale 0 se perderia lo apostado."""""
import random
import  matplotlib.pyplot as plt
def roulette():
    return random.randint(0,36)

def apuesta_par():
    numero = roulette()
    return numero%2==0 and numero != 0

def simulacion():
    dinero = 1000
    apuesta = 50
    rondas = 0
    historial_dinero = []
    while dinero >0:
        rondas += 1
        if apuesta_par():
            dinero += apuesta
        else:
            dinero -= apuesta
        historial_dinero.append(dinero)
    return historial_dinero, rondas

historial_dinero, rondas = simulacion()
plt.figure(figsize=(10,6))
plt.plot(historial_dinero)
plt.title(f'Histograma de dinero despues de {rondas} rondas')
plt.xlabel('Numero de apuestas')
plt.ylabel('Saldo en euros')
plt.grid(True)
plt.show()


