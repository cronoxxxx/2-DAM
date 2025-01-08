import numpy as np
import matplotlib.pyplot as plt
data = np.load('medida_H18.npz')
M_hombres=data['x']

plt.hist(M_hombres, bins=20)
plt.title('Hombres')
plt.axvspan(np.mean(M_hombres)-10, np.mean(M_hombres)+10, color='b', alpha=0.2)
plt.vlines(x=np.mean(M_hombres), linestyles='solid', ymin=0, ymax=600)
plt.xlabel('Medida')
plt.ylabel('Frecuencia')
plt.show()