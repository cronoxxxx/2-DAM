colores_list_1 = ["rojo", "azul", "verde", "amarillo", "marron", "lila", "negro", "rosa", "blanco", "naranja"]
colores_list_2 = ["celeste", "turquesa", "morado", "ocre", "gris", "marron", "cafe", "plata", "dorado", "violeta"]

colores_list_1 = set(colores_list_1)
colores_list_2 = set(colores_list_2)

diferencia = colores_list_1.difference(colores_list_2)

print(diferencia)

interseccion = colores_list_1.intersection(colores_list_2)

print(interseccion)

union = colores_list_1.union(colores_list_2)

print(union)
