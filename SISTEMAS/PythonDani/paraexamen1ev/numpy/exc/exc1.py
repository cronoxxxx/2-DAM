print("Selecciona las primera columna de la matriz `p` proporcionada, y guárdala en la variable `p1`.")
print("Sustituye los elementos de p1 que sean iguales a 0, por el valor $7$.")
import numpy as np
p = np.array([[1, 2, 4],
              [0, 0, 0],
              [0, 6, 8]])
#columna
print("En caso de columna")
p1 = p[:, 0]
p1[p1==0] = 7
print(p1)
print("Matriz")
print(p)
#fila
print("En caso de fila")
p2 = p[0, :]
print(p2)