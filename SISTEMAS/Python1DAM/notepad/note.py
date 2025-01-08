from tkinter import *

root = Tk()

# Crear un menú principal
menu = Menu(root)
root.config(menu=menu)

# Crear un submenú
submenu = Menu(menu, tearoff=0)
menu.add_cascade(label="Opciones", menu=submenu)
submenu.add_command(label="Abrir")
submenu.add_command(label="Guardar")
submenu.add_separator()
submenu.add_command(label="Salir", command=root.quit)

# Crear un widget de texto
text_widget = Text(root, font=("Arial", 12))
text_widget.grid(row=0, column=0, sticky="nsew")

# Configurar la ventana para que se ajuste al tamaño del widget de texto
root.grid_rowconfigure(0, weight=1)
root.grid_columnconfigure(0, weight=1)

root.mainloop()