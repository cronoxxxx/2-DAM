""""El método Montecarlo es un método no determinista o estadístico numérico, usado para aproximar expresiones matemáticas complejas y costosas de evaluar con exactitud. El método se llamó así en referencia al Casino de Montecarlo (Mónaco) por ser “la capital del juego de azar”, al ser la ruleta un generador simple de números aleatorios. El nombre y el desarrollo sistemático de los métodos de Montecarlo datan aproximadamente de 1944 testee combinaciones posibles.

Vamos a calcular el valor de 
, para ello, si x e y
 son dos variables aleatorias continuas uniformes en el intervalo (-1,1) y son independientes.
 Vendrá dada por la probabilidad de que x^2 + y^2 <=1
, es decir: 
pi = 4 * numero de puntos que caen dentro / numero total de intentos

"""""
import numpy as np
import matplotlib.pyplot as plt



N = 10000
x = np.random.uniform(-1,1,N)
y = np.random.uniform(-1,1,N)
count = 0
colors = []
for i in range(N):
    distancia_al_cuadrado = np.sqrt(x[i]**2 + y[i]**2)
    if distancia_al_cuadrado <= 1:
        count += 1
        colors.append("pink")
    else:
        colors.append("blue")
print("pi =",count*4/N)
plt.figure(figsize = (4,4))
plt.scatter(x,y,c=colors,s=3)
plt.show()




