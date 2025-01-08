"""Lee el archivo.txt """
lineas = []
with open ('archivo.txt', 'r') as file:
    lineas = [linea.split() for linea in file]

for linea in lineas:
    print(linea)

"""Haz lo mismo con el archvo archivo_error.txt,con ciddado de que a este archivo le falta algún dato, 
haz que en ese caso cuando lo lea, si no hay dos columnas no lo escriba."""
print("-"*30)
lineas = []

with open ('archivo_error.txt', 'r') as file:
    lineas = [linea.split() for linea in file]

for linea in lineas:
    if len(linea) == 2:
        print(linea)
print("-"*30)
"""Lee el archivo.txt, haz que imprima las columnas en las filas y las filas en las columnas"""  
lineas = []
with open ('archivo.txt','r') as file:
    lineas = [linea.split() for linea in file]

max_columnas = max(len(fila) for fila in lineas)
traspuesta = []

for i in range(max_columnas):
    fila = []
    for linea in lineas:
        fila.append(linea[i] if i < len(linea) else ' ')
    traspuesta.append(fila)

for fila in traspuesta:
    print(fila)
