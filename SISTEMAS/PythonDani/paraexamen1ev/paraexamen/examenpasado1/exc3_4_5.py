inicio = ['Ta1', 'Cb1', 'Ac1', 'Qd1', 'Re1', 'Af1', 'Cg1', 'Th1',
          'Pa2', 'Pb2', 'Pc2', 'Pd2', 'Pe2', 'Pf2', 'Pg2', 'Ph2',
          'pa7', 'pb7', 'pc7', 'pd7', 'pe7', 'pf7', 'pg7', 'ph7',
          'ta8', 'cb8', 'ac8', 'rd8', 'qe8' ,'af8', 'cg8', 'th8'
         ]
print("Ejercicio 1")
#1. Hacer clase ficha y dar info tablero
class Ficha:
    def __init__(self, x):
        self.name = x
        self.caracter = x[0]
        self.pos = x[1:]
        if x[0].lower() == 't':
            self.tipo = "Torre"
            if x[0].islower():
                self.color = "Negra"
            else:
                self.color = "Blanca"
        elif x[0].lower() == 'c':
            self.tipo = "Caballo"
            if x[0].islower():
                self.color = "Negro"
            else:
                self.color = "Blanco"
        elif x[0].lower() == 'a':
            self.tipo = "Alfil"
            if x[0].islower():
                self.color = "Negro"
            else:
                self.color = "Blanco"
        elif x[0].lower() == 'q':
            self.tipo = "Reina"
            if x[0].islower():
                self.color = "Negra"
            else:
                self.color = "Blanca"
        elif x[0].lower() == 'r':
            self.tipo = "Rey"
            if x[0].islower():
                self.color = "Negro"
            else:
                self.color = "Blanco"
        elif x[0].lower() == 'p':
            self.tipo = "Peon"
            if x[0].islower():
                self.color = "Negro"
            else:
                self.color = "Blanco"

    def info(self):
        print("    " + self.tipo + " " + self.color + " " + self.pos)


posicion_fichas = []

for i in inicio:
    posicion_fichas.append(Ficha(i))
for ficha in posicion_fichas:
    ficha.info()
print("Ejercicio 2")
#2. Pintar tablero
def pintar_tablero(posfichas):
    # Crear un tablero vacío
    tablero = [[' ' for _ in range(8)] for _ in range(8)]

    # Llenar el tablero con las fichas
    for ficha in posfichas:
        # Extraer la columna (letra) y fila (número) de la posición
        columna = ficha.pos[0]  # Ejemplo: 'a'
        fila = int(ficha.pos[1])  # Ejemplo: '1', convertir a entero

        # Convertir columna y fila en índices
        col = ord(columna) - ord('a')  # 'a' -> 0, 'b' -> 1, ..., 'h' -> 7
        row = 8 - fila  # Las filas en el tablero van de 8 a 1 (invertido)

        # Asignar el carácter de la ficha al tablero
        tablero[row][col] = ficha.caracter #el a1 va en la fila 7 columna 0

    # Imprimir el tablero
    for fila in tablero:
        print(' '.join(fila))


# Llamar a la función con las posiciones iniciales
pintar_tablero(posicion_fichas)
#3. Crea una función que cambie la posición de una ficha dada, por ejemplo:
"""""cambiar_posicion_ficha("Cb1","Cc3")
    cambiar_posicion_ficha("pd2","pd3")"""

print("Ejercicio 3")
def cambiar_posicion_ficha(name1, new_position):
    """
    Cambia la posición de una ficha en el tablero.

    name1: Nombre actual de la ficha (por ejemplo, "Cb1").
    new_position: Nueva posición de la ficha (por ejemplo, "Cc3").
    """
    for ficha in posicion_fichas:
        if ficha.name == name1:
            ficha.pos = new_position[1:]  # Actualizar la posición (sin el nombre de ficha)
            ficha.name = new_position
            break
    else:
        print(f"Ficha '{name1}' no encontrada.")


# Cambiar la posición de las fichas
cambiar_posicion_ficha("Cb1", "Cc3")
cambiar_posicion_ficha("pd2", "pd3")

# Pintar el tablero actualizado
pintar_tablero(posicion_fichas)
