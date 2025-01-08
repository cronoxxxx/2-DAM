"""
Representa la función $\frac{D}{(x^2+1)}$ , donde D es el día de tu cumpleaños
Hazque el título sea tenga una fuente: serif de color blue y tamaño 16
"""

import matplotlib.pyplot as plt
import numpy as np


D = 20

x = np.linspace(-5, 5, 100)
y = D / (x**2 + 1)
plt.plot(x, y)

# Configura el título con fuente serif, color azul y tamaño 16
plt.title('Mi cumpleaños', fontname='serif', fontsize=16, color='blue')

plt.xlabel('x')
plt.ylabel('y')
plt.show()
