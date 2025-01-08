import matplotlib.pyplot as plt
import numpy as np

x = np.arange(0,2.5,2)

y = (3-x)/2
y2 = 2-x
font= {'family':'Arial','color':'darkred','size':16}
plt.title("Sistema de ecuaciones", loc = 'center', fontdict = font)
plt.plot(x,y,linewidth = '1',color='g', marker = 'o', linestyle=':', label=r'$y=\frac{-x+3}{2}$')
plt.plot(x,y2, linewidth = '1',color='r', marker = 'o', linestyle='--', label=r'$y={-x+2}$' )
plt.xlabel("x")
plt.ylabel("y")
plt.axvline(x=1, ymin=0.05, ymax=0.5,color='b', marker = '*', linewidth = '1', label=r'Solución')
plt.legend()
plt.grid()
plt.show()