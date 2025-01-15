from ejercicios.examen.tablero.tablero import Tablero
tableros = []
robot_names = ["Adrian robot1", "Adrian robot2", "Adrian robot3", "Adrian robot4"]
tablero = Tablero(robot_names)
for i in range(4):
    if i%2==0:
        tablero.generar_aleatorios_uno()
    else:
        tablero.generar_aleatorios_dos()


tablero.graficar_trayectoria()

