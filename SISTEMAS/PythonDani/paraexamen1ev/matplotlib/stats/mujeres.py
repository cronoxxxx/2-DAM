import numpy as np
import matplotlib.pyplot as plt
data = np.load('medida_M18.npz')
M_MUJERES=data['x']

plt.hist(M_MUJERES, bins=20,color='orange')
plt.title('Hombres')
plt.axvspan(np.mean(M_MUJERES) - 10, np.mean(M_MUJERES) + 10, color='orange', alpha=0.2)
plt.vlines(x=np.mean(M_MUJERES), linestyles='solid', ymin=0, ymax=600)
plt.xlabel('Medida')
plt.ylabel('Frecuencia')
plt.show()