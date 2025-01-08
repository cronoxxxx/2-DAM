import matplotlib.pyplot as plt
import numpy as np

archivo = "el_quijote.txt"
text = open (archivo, encoding="utf-8").readlines()
ABC = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZ"
f = np.zeros(len(ABC))



num = 0
total = 0

for line in text:
    for char in line:
        char = char.upper()
        for a in ABC:
            if a == char:
                num = f [ABC.index(a)]
                f[ABC.index(a)] = num + 1
                total+=1


x = []
y = []
array_final = []
for i in f :
    c = i / total
    array_final.append(c)
    y.append(c)

for i in ABC:
    x.append(i)

plt.bar(x, y)
plt.show()
