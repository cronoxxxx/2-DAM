import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
from fontTools.subset import prune_hints

naranjas = pd.Series([2, 4, 9, 6, 10, 200])
manzanas = pd.Series([60, 22, 1, 79, 2, 8])
print(naranjas)
print("Multiplicar\n", naranjas * 2)
print(manzanas)

print("*" * 50)
print("SERIES")
# Series
colores = pd.Series(["rojo", "azul", "verde", "amarillo", "morado"])
print(colores)
print("Index", colores.index)
print("Size", colores.size)
print("Dtype", colores.dtype)
materias = pd.Series({"Mates": 6, "Fisica": 9, "Quimica": 7})
print(materias)
print("Materias con nota mayor a 6")
print(materias[materias > 6])
print("Ordenar valores")
print(materias.sort_values())

print(materias[["Mates", "Fisica"]])
print("Ordenar por su nombre")
print(materias.sort_index(ascending=True))  # False si se quiere descendente

# Funciones
numeros = pd.Series([1, 2, 3, 4, 5, 6, 7, 8, 9])
print("Suma", numeros.sum())
print("Valor max", numeros.max())
print("Valor min", numeros.min())
print("Desviacion estandar", numeros.std())
print("Propiedades generales")
print(numeros.describe())

data = 5
serie = pd.Series(data, index=[0, 1, 2, 3, 4, 5])
print(serie)

futbolistas = ["Messi", "Cristiano", "Benzema"]
indices = ["Inter Miami", "Al Nassr", ""]

futbol = pd.Series(index=indices, data=futbolistas)
print(futbol)

# Dataframe
print("*" * 50)
print("DATAFRAME")
dictionary = {'NOMBRE': ["Maria", "Jose", "David", "Ivan"],
              'CARRERA': ["Auditoria", "Informatica", "Derecha", "Idiomas"],
              'CORREO':['a@','b@','c@','d@']}


estudiantes = pd.DataFrame(dictionary)
print(estudiantes)

df = pd.DataFrame([['Maria',28],['Jose',12],['Ana',19],['Jose',18]],columns=['NOMBRE','EDAD'])
print(df)

print("Con NUMPY-----")
frame = pd.DataFrame(np.random.randn(4,3),columns=['a','b','c'])
print(frame)