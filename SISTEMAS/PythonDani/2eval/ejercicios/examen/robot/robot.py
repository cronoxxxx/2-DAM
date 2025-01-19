import matplotlib.pyplot as plt

class Robot:
    def __init__(self):
        self.x = 0
        self.y = 0
        self.movimientos = []

    def mueve(self, instrucciones):
        movimientos = {
            'A': (0, 1),
            'R': (0, -1),
            'I': (-1, 0),
            'D': (1, 0)
        }

        for instruccion in instrucciones:
            if instruccion in movimientos:
                dx, dy = movimientos[instruccion]
                self.x += dx
                self.y += dy
                self.movimientos.append((self.x, self.y))



    def graficar_movimientos(self):
        x_coords, y_coords = zip(*self.movimientos)
        plt.plot(x_coords, y_coords, marker='o')
        plt.xlim(-1, 5)
        plt.ylim(-1, 5)
        plt.grid(True)
        plt.title('Robot adrian')
        plt.xlabel('X')
        plt.ylabel('Y')
        plt.show()