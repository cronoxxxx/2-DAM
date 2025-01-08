"""estudiante1 = Estudiante("Ana", "Matemáticas")

estudiante1.completar_tarea()
estudiante1.completar_tarea()
estudiante1.completar_tarea()

estudiante1.informe_tareas()
Deberías obtener:

Ana ha completado una tarea en el curso Matemáticas.
Ana ha completado una tarea en el curso Matemáticas.
Ana ha completado una tarea en el curso Matemáticas.
Ana ha completado 3 tareas en total."""""



class Estudiante:
    def __init__(self, nombre, asignatura,tareas_completadas=0):
        self.nombre = nombre
        self.tareas_completadas=tareas_completadas
        self.asignatura = asignatura

    def completar_tarea(self):
        self.tareas_completadas+=1

    def informe_tareas(self):
        print(f"{self.nombre} ha completado {self.tareas_completadas} tareas en total.")

estudiante1 = Estudiante("Ana", "Matemáticas")
estudiante1.completar_tarea()
estudiante1.completar_tarea()
estudiante1.completar_tarea()

estudiante1.informe_tareas()