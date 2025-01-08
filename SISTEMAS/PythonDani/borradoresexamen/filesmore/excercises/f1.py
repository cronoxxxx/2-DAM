"""Leer un archivo llamado nombres.txt, que contiene dos columnas: la primera con nombres y la segunda con el año de nacimiento.
Determinar la edad media. Siendo esta la suma de todos los valores dividia entre el número de valores."""


with open('nombres.txt', 'r') as file:
    edades = [(2024 - int(linea.split()[1])) for linea in file]

print("Media de edad:", sum(edades) / len(edades))

"""Determinar y mostrar el nombre que aparece con más frecuencia junto con la cantidad de veces que se repite"""

def contar_ocurrencias(nombre):
    return nombres.count(nombre)

with open('nombres.txt', 'r') as file:
    nombres = [linea.split()[0] for linea in file]

nombre_mas_frecuente = max(nombres, key=contar_ocurrencias)
cantidad = nombres.count(nombre_mas_frecuente)

print(f"Nombre más frecuente: {nombre_mas_frecuente}")
print(f"Cantidad de repeticiones: {cantidad}")

"""Crear una lista con los nombres y la frecuencia con la que aparecen

Haz que la lista este ordeanda de mayor a menor frecuencia"""

with open('nombres.txt', 'r') as file:
    nombres = [linea.split()[0] for linea in file]

frecuencias = {}
for nombre in nombres:
    if nombre in frecuencias:
        frecuencias[nombre] += 1
    else:
        frecuencias[nombre] = 1

for nombre, frecuencia in frecuencias.items():
    print(f"{nombre}: {frecuencia}")
        

"""#Algoritmo burbuja"""
with open('nombres.txt', 'r') as file:
    nombres = [linea.split()[0] for linea in file]

frecuencias = {}
for nombre in nombres:
    if nombre in frecuencias:
        frecuencias[nombre] += 1
    else:
        frecuencias[nombre] = 1

# Algoritmo de ordenamiento de burbuja
lista_frecuencias = list(frecuencias.items())
n = len(lista_frecuencias)
for i in range(n-1):
    for j in range(n-i-1):
        if lista_frecuencias[j][1] < lista_frecuencias[j+1][1]:
            lista_frecuencias[j], lista_frecuencias[j+1] = lista_frecuencias[j+1], lista_frecuencias[j]

for nombre, frecuencia in lista_frecuencias:
    print(f"{nombre}: {frecuencia}")