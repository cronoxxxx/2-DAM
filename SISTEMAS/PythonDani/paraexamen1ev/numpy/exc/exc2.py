#Obtener una función que calcule la distancia forman dos vectores
import numpy as np


def distancia(v, u):
    return np.linalg.norm(v - u)

v = np.array([1, -1])
u = np.array([0, 1])

print(distancia(v, u))