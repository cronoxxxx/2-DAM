#Obtener una función que calcule el coseno del ángulo que forman dos vectores
#leer ejercicio entero en jupyter
import numpy as np
def cos(v, u):
    return np.dot(v, u) / (np.linalg.norm(v) * np.linalg.norm(u))

v = np.array([0,-1])
u = np.array([0,1])

print(cos(v, u))