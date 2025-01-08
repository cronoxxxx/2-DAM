DAM1 = [["0484", "Bases de datos", 6],
        ["0487", "Entornos de desarrollo", 3],
        ["0493", "Formación y orientación laboral", 3],
        ["0373", "Lenguaje de marcas y sistemas de gestión de información", 4],
        ["0485", "Programación", 8],
        ["0483", "Sistemas informáticos", 6]]
DAM2 = [["0486", "Acceso a datos", 6],
        ["0488", "Desarrollo de interfaces", 6],
        ["0494", "Empresa e iniciativa emprendedora", 3],
        ["9009", "Inglés técnico para grado superior", 2],
        ["0490", "Programación de servicios y procesos", 4],
        ["0489", "Programación multimedia y dispositivos móviles", 4],
        ["0491", "Sistemas de gestión empresarial", 5]]


class Curso:
    def __init__(self, curso, seccion):
        self.cursoo = curso
        self.seccion = seccion
        self.modulos = []

    def info(self):
        print(f"Curso {self.cursoo} {self.seccion}")
        for modulo in self.modulos:
            print(f"  {modulo.codigo} {modulo.nombre} ({modulo.nota})")


class Modulo:
    def __init__(self, codigo, nombre, nota):
        self.codigo = codigo
        self.nombre = nombre
        self.nota = nota


# descomenta cuando termines de escribir la clase Modulo y Curso
cursos = [Curso(1, "DAM"), Curso(2, "DAM")]

for i in DAM1:
    cursos[0].modulos.append(Modulo(i[0], i[1], i[2]))

for i in DAM2:
    cursos[1].modulos.append(Modulo(i[0], i[1], i[2]))

for i in cursos:
    i.info()