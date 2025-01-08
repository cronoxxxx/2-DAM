#Representa la función $\frac{1}{1+e^{-x}}$

import matplotlib.pyplot as plt
import numpy as np
D = 20
x = np.linspace(-5, 5, 100)
y = D / (1 + np.exp(-x))
plt.plot(x, y)
plt.title('Mi cumpleaños', fontname='serif', fontsize=16, color='blue')
plt.vlines(x=-2.5, color='green', linestyle='--', linewidth=1.5, ymin=0, ymax=D)
plt.vlines(x=2.5, color='green', linestyle='--', linewidth=1.5, ymin=0, ymax=D)
plt.hlines(y=D, color='green', linestyle='--', linewidth=1.5, xmin=-2.5, xmax=2.5)
plt.hlines(y=0, color='green', linestyle='--', linewidth=1.5, xmin=-2.5, xmax=2.5)
plt.xlabel('x')
plt.ylabel('y')
plt.show()