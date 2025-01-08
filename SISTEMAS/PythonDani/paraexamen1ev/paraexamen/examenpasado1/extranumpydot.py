"""Una empresa automovilistica abre un nuevo concesionario en una ciudad, pasado un par de años observa el comportamiento 
de sus clientes en cuanto a la compra y venta y e clasifica el comportamiento de los clientes en tres estados:

* **Compra (C)**: Los clientes están comprando un coche.
* **Venta (V)**: Los clientes están vendiendo el coche en el mercado de reventa.
* **Inactivo (I)**: Los clientes no están comprando ni vendiendo.

El equipo de marketing de la empresa ha analizado las transiciones de estado y ha determinado la siguiente matriz de transición,
 que describe las probabilidades de cambio de estado en un periodo determinado:

| Estado Actual   | Compra (C) | Venta (V) | Inactivo (I) |
|-----------------|------------|-----------|--------------|
| **Compra (C)**  | 0.5        | 0.3       | 0.2          |
| **Venta (V)**   | 0.2        | 0.6       | 0.2          |
| **Inactivo (I)**| 0.3        | 0.2       | 0.5          |

 Cada celda representa la probabilidad de transición del estado i al estado j en un periodo. Por ejemplo La probabilidad de pasar 
 del estado de compra al estado de venta es 0.3 y la problabilidad de permanecer en un estado inactivo es 0.5
 
 Es decir que la matriz de transición \( P \) es la siguiente:
 
 P = [[0.5, 0.3, 0.2], [0.2, 0.6, 0.2], [0.3, 0.2, 0.5]]
 
 Al comienzo del análisis, la empresa ha observado que hay 1200 clientes en el estado de Compra, 1000 en el estado de Venta y 
 900 en el estado Inactivo, lo cual puede representarse como el vector de estado inicial:

v_0=[1200,1000,900]
 
 
 """""


import numpy as np

P = np.array([[0.5, 0.3, 0.2], [0.2, 0.6, 0.2], [0.3, 0.2, 0.5]])

prob = np.array([[1200, 1000, 900]]).T
for i in range(5):
    prob = np.dot(P, prob)

print(prob[:,-1])