import numpy as np
import matplotlib.pyplot as plt

plt.title('Adrian Saavedra')
def lines_ui(stats,color):
    plt.axvspan(np.mean(stats) - 10, np.mean(stats) + 10, color=color, alpha=0.2)
    plt.vlines(x=np.mean(stats), linestyles='solid', ymin=0, ymax=600)

data = np.load('medida_H18.npz')
M_hombres=data['x']
plt.hist(M_hombres, bins=20,label='Hombres > 18')
lines_ui(M_hombres,'blue')

data2= np.load('medida_M18.npz')
M_MUJERES=data2['x']
plt.hist(M_MUJERES, bins=20,color='orange',label='Mujeres > 18')
lines_ui(M_MUJERES,'orange')



plt.xlabel('Medida')
plt.ylabel('Frecuencia')
plt.show()

