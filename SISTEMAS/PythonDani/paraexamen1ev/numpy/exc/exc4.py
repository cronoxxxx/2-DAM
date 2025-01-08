"""Crea una función que te devuelva la media y la desviación estándar de una lista ordenada.
Pruébalo con lista = [4, 1, 11, 13, 2, 7]"""
import numpy as np
lista = [4, 1, 11, 13, 2, 7]
media = np.mean(lista)
desv = np.std(lista)
print("Media")
print(media)
print("Desviación estándar")
print(desv)