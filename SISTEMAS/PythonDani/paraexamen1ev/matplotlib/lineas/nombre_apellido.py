#Representa también la función con D/2, busca y representa también como poner las barras verticales y horizontales

import matplotlib.pyplot as plt
import numpy as np

D = 20

x = np.linspace(-5, 5, 100)
y = D / (x**2 + 1)
plt.plot(x, y)

plt.vlines(x=-2, color='green', ymin=0, ymax=D, linestyle='--', linewidth=1.5)
plt.vlines(x=2, color='green', ymin=0, ymax=D, linestyle='--', linewidth=1.5)

D2 = D / 2
y2 = D2 / (x ** 2 + 1)
plt.plot(x, y2, linestyle='--')

y2_max = np.max(y2[(x >= -2) & (x <= 2)])

# Dibuja la línea horizontal
plt.hlines(y=y2_max, xmin=-2, xmax=2, colors='red', linestyles='--', linewidth=1.5)

plt.title('Mi cumpleaños', fontname='serif', fontsize=16, color='blue')

plt.xlabel('x')
plt.ylabel('y')

plt.show()