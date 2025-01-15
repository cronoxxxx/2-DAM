import matplotlib.pyplot as plt
import random



class Tablero:
    def __init__(self, nombre_robot):
        self.nombre_robot = nombre_robot

        self.x = 0
        self.y = 0
        self.movimientos = [(self.x, self.y)]

    def generar_aleatorios_uno(self, pasos=1000):
        movimientos = ['A', 'R', 'I', 'D']
        desplazamientos = {
            'A': (0, 1),
            'R': (0, -1),
            'I': (-1, 0),
            'D': (1, 0)
        }

        for _ in range(pasos):
            movimiento = random.choice(movimientos)
            dx, dy = desplazamientos[movimiento]
            self.x += dx
            self.y += dy
            self.movimientos.append((self.x, self.y))


    def generar_aleatorios_dos(self, pasos=1000):
        movimientos = ['A', 'R', 'I', 'D']
        desplazamientos = {
            'A': (0, 1),
            'R': (0, -1),
            'I': (-1, 0),
            'D': (1, 0)
        }

        for _ in range(pasos):
            movimiento = random.choice(movimientos)
            dx, dy = desplazamientos[movimiento]
            nuevo_x = self.x + dx
            nuevo_y = self.y + dy
            if -20 <= nuevo_x <= 20 and -20 <= nuevo_y <= 20:
                self.x = nuevo_x
                self.y = nuevo_y
                self.movimientos.append((self.x, self.y))




    def graficar_trayectoria(self):
        x_coords, y_coords = zip(*self.movimientos)
        plt.plot(x_coords, y_coords, marker='o', label=self.nombre_robot)
        plt.xlim(-20, 20)
        plt.ylim(-20,20)
        plt.grid(True)
        plt.title('trayectoria robots')
        plt.xlabel('X')
        plt.ylabel('Y')
        plt.legend()
        plt.show()







