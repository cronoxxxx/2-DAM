import random

def imparesRandom(cantidad):
    impares = set()
    for _ in range(cantidad):
        # Genera un número entero impar aleatorio
        numero = random.randrange(1,100,2)
        impares.add(numero)
    return list(impares)


lista = imparesRandom(10)
print (lista)