import numpy as np
import matplotlib.pyplot as plt

#subplots
fig, (aux1, aux2) = plt.subplots(1, 2, figsize=(10, 5))  # Desempaquetar correctamente los ejes
font = {'family': 'serif','color':  'blue','size': 16}
plt.title('Adrian Saavedra', loc='left', fontdict=font)

def lines_ui(aux,stats,color):
    aux.axvspan(np.mean(stats) - 10, np.mean(stats) + 10, color=color, alpha=0.2)
    aux.vlines(x=np.mean(stats), linestyles='solid', ymin=0, ymax=600)

data = np.load('medida_H18.npz')
M_hombres=data['x']
aux1.hist(M_hombres, bins=20,label='Hombres > 18')
lines_ui(aux1,M_hombres,'blue')

data2= np.load('medida_M18.npz')
M_MUJERES=data2['x']
aux2.hist(M_MUJERES, bins=20,color='orange',label='Mujeres > 18')
lines_ui(aux2,M_MUJERES,'orange')

plt.xlabel('Medida')
plt.ylabel('Frecuencia')
plt.show()