import numpy as np
"""Utilizando la matriz de transición vista en el ejemplo de (Cadenas de Markov), 
pongamos el caso de una población de 1000 habitantes que en un momento inicial compran 1000 procductos de la marca B, 
como sería la distribución de compras en la decima compra."""
markov = np.array([[0.6,0.3],[0.3,0.5]])
distribucion = np.array([1000,0])
for i in range(10):
    distribucion = np.dot(distribucion,markov)
print("Distribucion de compras en la decima compra")
print(distribucion)


"""markov[0, 0] = 0.6: Probabilidad de seguir comprando de la marca A.
markov[0, 1] = 0.3: Probabilidad de cambiar de la marca A a la marca B.
markov[1, 0] = 0.3: Probabilidad de cambiar de la marca B a la marca A.
markov[1, 1] = 0.5: Probabilidad de seguir comprando de la marca B.

Matemáticamente, la multiplicación matriz-vector es:
distribucionSiguiente = distribucionActual * matriz de transición
osea [1000,0] x [[0.6,0.3],[0.3,0.5]] = [600, 300]
[600, 300] x [[0.6,0.3],[0.3,0.5]] = [360, 180]
y asi sucesivamente

"""