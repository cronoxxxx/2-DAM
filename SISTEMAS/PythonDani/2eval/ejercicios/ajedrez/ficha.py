class Ficha:
    def __init__(self, x, y, imagen, visible=True):
        self.X = x
        self.Y = y
        self.imagen = imagen
        self.visible = visible

    def image_location(self):
        return f'images/{self.imagen}'