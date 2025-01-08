import numpy as np
import matplotlib.pyplot as plt
#Crea un histograma en que se vea la distribución que saldría al sumar dos dados
cuantas_posibilidade=100000
bins = [2,3,4,5,6,7,8,9,10,11,12,13]
dado1 = np.random.randint(1, 7, cuantas_posibilidade)
dado2 = np.random.randint(1, 7, cuantas_posibilidade)
suma = dado1 + dado2
plt.figure(figsize = (8,8)) #amplia
plt.hist(suma, bins,density = True) #la densidad pone porcentajes
plt.xticks(bins) #acomoda los grids
plt.grid(True) #los pone
plt.show()
