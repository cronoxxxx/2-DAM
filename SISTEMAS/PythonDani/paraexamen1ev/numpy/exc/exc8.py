""""
En el siguiente juego, 3 niños y 3 niñas juegan a pasarse la pelota, si la probabilidad de pasar 
la pelota a la derecha o a su izquierda es la misma (1/2), crea la matriz de transición como hemos visto en clase, 
y obten la probabilidad de que la pelota llegue a 4 en la 3ª ronda si ha empezado en 1.

Mirar imagen en jupyter para entender

sol : 0.25"""""
import numpy as np
T = np.array([[0, 0.5, 0, 0, 0, 0.5],
             [0.5, 0, 0.5, 0, 0, 0],
             [0, 0.5, 0, 0.5, 0, 0],
             [0, 0, 0.5, 0, 0.5, 0],
             [0, 0, 0, 0.5, 0, 0.5],
             [0.5, 0, 0, 0, 0.5, 0]])

paso1 = np.array([1, 0, 0, 0, 0, 0])
paso2 = T.dot(paso1)
paso3 = T.dot(paso2)
paso4 = T.dot(paso3)
print(paso1)
print(paso2)
print(paso3)
print(paso4)
print(paso4[3])
