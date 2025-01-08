#Utiliza aquí tus datos de tu cumpleaños:
from matplotlib import pyplot as plt

dia=20
mes=9
A=float(dia+mes)
#En el caso de que sea 26 el numero sumale 1
print("En este examen A =",A)

import numpy as np
x = np.linspace(0,1.75,100)
font = {'family': 'Arial','color':  'darkred','size': 16}
plt.title('Titulo (A=26.0)', fontdict=font)
y = A*x**2
y2 = -A*x**2+A*x+A
plt.plot(x,y, color='green',label='$y=Ax^2$')
plt.plot(x,y2,color='red',label='$y=-Ax^2+Ax+A$')
plt.scatter(1,29, color='b', marker='*', label='Solución')
plt.axvline(1,0,26,color='blue')

plt.grid(True)
plt.legend()
plt.show()