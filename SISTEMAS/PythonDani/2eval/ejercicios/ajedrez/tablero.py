from PIL import Image
from ficha import Ficha

columnas = {'a': 0, 'b': 45, 'c': 90, 'd': 135, 'e': 180, 'f': 225, 'g': 270, 'h': 315}
filas = {'1': 315, '2': 270, '3': 225, '4': 180, '5': 135, '6': 90, '7': 45, '8': 0}


def declarar_nueva_actual_ficha(posicion_actual, posicion_nueva):
    ficha_actual, pos_tablero_actual = posicion_actual.split('_')
    columna_actual, fila_actual = pos_tablero_actual
    _, pos_tablero_nueva = posicion_nueva.split('_')
    columna_nueva, fila_nueva = pos_tablero_nueva
    x_nueva = columnas[columna_nueva]
    y_nueva = filas[fila_nueva]
    y_actual = filas[fila_actual]
    x_actual = columnas[columna_actual]
    return ficha_actual, x_actual, x_nueva, y_actual, y_nueva


class Tablero:
    def __init__(self, inicio):
        self.posfichas = []
        for posicion in inicio:
            pieza, posicion_tablero = posicion.split('_')
            columna, fila = posicion_tablero
            x = columnas[columna]
            y = filas[fila]
            imagen = f'{pieza}.png'
            self.posfichas.append(Ficha(x, y, imagen))

    def pintar_tablero(self):
        tablero_img = Image.open('images/tablero.png').convert('RGBA')
        for ficha in self.posfichas:
            if ficha.visible:
                aux_img = Image.open(ficha.image_location()).convert('RGBA')
                tablero_img.alpha_composite(aux_img, (ficha.X, ficha.Y))

        tablero_img.save('jugada.png')

    def cambiar_pos_ficha(self, posicion_actual, posicion_nueva):
        ficha_actual, x_actual, x_nueva, y_actual, y_nueva = declarar_nueva_actual_ficha(posicion_actual,posicion_nueva)

        for ficha in self.posfichas:
            if ficha.X == x_actual and ficha.Y == y_actual and ficha.imagen.startswith(ficha_actual):
                ficha.X = x_nueva
                ficha.Y = y_nueva
                break
            #else:print(f'No se encuentra la ficha {ficha_actual} en la posicion {posicion_actual}')

    def comer_ficha(self, posicion_actual, posicion_nueva):
        ficha_actual, x_actual, x_nueva, y_actual, y_nueva = declarar_nueva_actual_ficha(posicion_actual,posicion_nueva)

        ficha_mover = None
        for ficha in self.posfichas:
            if ficha.X == x_actual and ficha.Y == y_actual and ficha.imagen.startswith(ficha_actual):
                ficha_mover = ficha
                break
        if ficha_mover:
            new_posfichas = []
            for ficha in self.posfichas:
                if not (ficha.X == x_nueva and ficha.Y == y_nueva):
                    new_posfichas.append(ficha)
            self.posfichas = new_posfichas
            ficha_mover.X = x_nueva
            ficha_mover.Y = y_nueva
        #else:print(f'No se encuentra la ficha {ficha_actual} en la posicion {posicion_actual}')

    def pintar_reinas(self,x):
        x= min(x,8)
        pos_reinas = [
            ('d','1'),('b','2'),('e','3'),('h','4'),('f','5'),('a','6'),('c','7'),('g','8')
        ]
        self.posfichas.clear()
        for i in range (x):
            columna,fila = pos_reinas[i]
            self.posfichas.append(Ficha(columnas[columna],filas[fila],'db.png'))
