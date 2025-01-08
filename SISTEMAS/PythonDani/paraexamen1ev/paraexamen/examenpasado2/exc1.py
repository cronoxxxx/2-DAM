
#Utiliza aquí tus datos de tu cumpleaños:
dia=19
mes=4
A=float(dia+mes)
print("En este examen A =",A)



"""""* 
Dibuja las siguientes funciones
- y = A * x
- y = A * x^2


(0.5 puntos) La fuente de título es : "Arial" o "DejaVu Sans", de color darkred y tamaño 16.
* (0.5 puntos) Los colores de las gráficas son verde, rojo y azul, la linea de 1 y el grid que aparezca por defecto
* (0.5 puntos) Dibuja una linea vertical que vaya hasta la solución y leyenda con ecuaciones.
* (0.5 puntos) Marca la solución con una estrella y pon las etiquetas en cada eje"""""
import numpy as np
import matplotlib.pyplot as plt

# Crear un rango de valores para x usando np.arange
# start (0.0): Es el valor inicial del rango.
# stop (1.4): Es el valor final del rango (sin incluir este valor).
# step (0.014): Es el espacio entre cada par de valores consecutivos en el rango.


# Crear un rango de valores para x usando np.linspace
# start (0.0): Es el valor inicial del rango.
# stop (1.4): Es el valor final del rango (inclusive).
# num (100): Es el número de elementos en el rango.
# x = np.linspace(0.0, 1.4, 100)

# Calcular y1 y y2 basándose en la ecuación lineal y la ecuación cuadrática

x = np.linspace(0.0, 1.4, 100)
font = {'family': 'Arial','color':  'darkred','size': 16}
plt.title('Sistema de ecuaciones', fontdict=font)
y = A * x
y2 = A * x**2
plt.plot(x, y,color='green',label='$y=A*x$')
plt.plot(x, y2,color='red',label='$y=A*x^2$')

plt.vlines(x=1, ymin=0.05, ymax=23, color='blue')
#estrella
plt.scatter(1,23, color='b', marker='*', label='Solución')
plt.grid(True)
plt.xlabel('x')
plt.ylabel('y')
plt.legend()
plt.show()

