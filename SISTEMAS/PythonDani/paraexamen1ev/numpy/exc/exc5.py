"""Crear un array con 1 en el borde y  0 dentro"""
"""Esribe tu código aquí"""
# la salida tiene que ser:
#array([[1., 1., 1., 1., 1., 1., 1., 1., 1., 1.],
#      [1., 0., 0., 0., 0., 0., 0., 0., 0., 1.],
#      [1., 0., 0., 0., 0., 0., 0., 0., 0., 1.],
#      [1., 0., 0., 0., 0., 0., 0., 0., 0., 1.],
#      [1., 0., 0., 0., 0., 0., 0., 0., 0., 1.],
#      [1., 0., 0., 0., 0., 0., 0., 0., 0., 1.],
#      [1., 0., 0., 0., 0., 0., 0., 0., 0., 1.],
#      [1., 0., 0., 0., 0., 0., 0., 0., 0., 1.],
#      [1., 0., 0., 0., 0., 0., 0., 0., 0., 1.],
#      [1., 1., 1., 1., 1., 1., 1., 1., 1., 1.]])

import numpy as np

arr = np.ones((10, 10))
arr[1:-1, 1:-1] = 0
print(arr)

print("USANDO EYE")
arr = np.ones((10, 10))
arr[1:-1, 1:-1] = 0
arr[np.eye(len(arr),len(arr)) == 1] = 1
print(arr)
print("EYE CON DIAGONAL IZQUIERDA Y DERECHA DE UNOS: solucion 1")
arr = np.ones((10, 10))
arr[1:-1, 1:-1] = 0
arr[np.eye(len(arr), len(arr)) == 1] = 1
pos = -1
plus = 0
for i in arr :
    arr [pos][plus] = 1
    pos-=1
    plus+=1
print(arr)
print("EYE CON DIAGONAL IZQUIERDA Y DERECHA DE UNOS: solucion 2")
arr = np.ones((10, 10))
arr[1:-1, 1:-1] = 0
arr[np.eye(len(arr), len(arr)) == 1] = 1
arr[np.fliplr(np.eye(len(arr), len(arr))) == 1] = 1
print(arr)