"""""Usa los siguientes estudiantes y cursos diferentes para simular la realización de cien tareas de forma aleatoria:

cursos = ["Matemáticas", "Historia", "Ciencias", "Arte"]
repartidor = ["Juan", "Celia", "Lucas", "Celeste","Dani","Gema","Ramon","Elvira","Pablo","Clara"]
Simula que cada tarea es asignada de forma aleatoria a un estudiante y a un curso.
Al finalizar las tareas, genera un informe que muestre cuántas tareas completó cada estudiante en total.
La salida tiene que ser:

# Obtener informes de tareas
for estudiante in estudiantes_obj:
    estudiante.informe_tareas()
    
    
Ana ha completado 15 tareas en total.
Carlos ha completado 10 tareas en total.
Lucía ha completado 8 tareas en total.
Javier ha completado 12 tareas en total.
Sofía ha completado 7 tareas en total.
Diego ha completado 10 tareas en total.
Elena ha completado 15 tareas en total.
Marcos ha completado 6 tareas en total.
Claudia ha completado 9 tareas en total.
Miguel ha completado 8 tareas en total."""""
import random
cursos = ["Matemáticas", "Historia", "Ciencias", "Arte"]
estudiante_list = ["Juan", "Celia", "Lucas", "Celeste", "Dani", "Gema", "Ramon", "Elvira", "Pablo", "Clara"]
estudiantes = []
class Estudiante:
    def __init__(self, nombre, asignatura,tareas_completadas=0):
        self.nombre = nombre
        self.tareas_completadas=tareas_completadas
        self.asignatura = asignatura

    def completar_tarea(self):
        self.tareas_completadas+=1

    def informe_tareas(self):
        print(f"{self.nombre} ha completado {self.tareas_completadas} tareas en total.")

for i in range (len(estudiante_list)):
    estudiante = Estudiante(random.choice(estudiante_list),random.choice(cursos),random.randint(1,10))
    estudiantes.append(estudiante)


for estudiante in estudiantes:
    estudiante.informe_tareas()

"""Imagina que tienes el siguiente informe de tareas realizadas por estudiantes:

for estudiante in estudiantes:
    estudiante.informe_tareas()
    
Lucía ha completado 6 tareas en total.
Carlos ha completado 8 tareas en total.
Ana ha completado 11 tareas en total.
Sofía ha completado 15 tareas en total.
Javier ha completado 6 tareas en total.
Elena ha completado 10 tareas en total.
Carlos ha completado 13 tareas en total.
Marcos ha completado 10 tareas en total.
Miguel ha completado 7 tareas en total.
Carlos ha completado 14 tareas en total.
Genera un informe de tareas completadas ordenado de forma descendente. Para este caso en particular, el informe quedaría como:

# Imprimir informe ordenado
for nombre, tareas_completadas in informe_ordenado:
    print(f"{nombre} ha completado {tareas_completadas} tareas en total.")
    
Sofía ha completado 15 tareas en total.
Carlos ha completado 14 tareas en total.
Carlos ha completado 13 tareas en total.
Ana ha completado 11 tareas en total.
Elena ha completado 10 tareas en total.
Marcos ha completado 10 tareas en total.
Carlos ha completado 8 tareas en total.
Miguel ha completado 7 tareas en total.
Lucía ha completado 6 tareas en total.
Javier ha completado 6 tareas en total."""
print("ordenados")
sorted_estudiantes = []

for i in range(len(estudiantes)):
    insert_index = len(sorted_estudiantes)
    for j in range(len(sorted_estudiantes)):
        if estudiantes[i].tareas_completadas > sorted_estudiantes[j].tareas_completadas:
            insert_index = j
            sorted_estudiantes[insert_index] = estudiantes[i]

for estudiante in sorted_estudiantes:
    estudiante.informe_tareas()
