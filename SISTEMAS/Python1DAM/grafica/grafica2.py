from tkinter import *
import tkinter.messagebox as MessageBox
import tkinter.filedialog as FileDialog
import tkinter.ttk as ttk
from PIL import ImageTk, Image
from chatterbot import ChatBot
import random

def escribe_fichero(preguntarespuesta):
    fichero = open("preguntasrespuestas.txt", "a")
    fichero.write(preguntarespuesta + "\n")
    fichero.close()

def responde_graba(pr, textbox, preview, cfichero):
    respuestas = ["Si", "No", "Probablemente", "No estoy muy seguro", "Totalmente cierto", "Ten por seguro que si", "Eso nunca", "Daro por hecho"]
    query = textbox.get()
    respuesta = random.choice(respuestas)
    pr = "Usted pregunto: " + query + " Respuesta: " + respuesta
    # Show result in the label
    preview.config(text=pr)
    MessageBox.showinfo("Respuesta", pr)
    escribe_fichero(pr)
    rutaFichero = FileDialog.askopenfilename(title="Abrir")
    nFichero = open(rutaFichero, "r")
    contenido = nFichero.read()
    cfichero.config(text=contenido)
    nFichero.close()

ventana = Tk()
ventana.title("Buscador")

style = ttk.Style()
style.theme_use("default")

style.configure("TEntry", foreground="blue", font=("Arial", 12))
style.configure("TLabel", foreground="blue", font=("Arial", 12))
style.configure("TButton", background="green", foreground="white", font=("Arial", 12))

# Load first image and resize it
img1 = Image.open("negro.jpg")
img1 = img1.resize((400, 350))
img1 = ImageTk.PhotoImage(img1)
panel1 = Label(ventana, image=img1)
panel1.pack(side="bottom")

# Load second image and resize it
img2 = Image.open("donpollo.jpg")
img2 = img2.resize((400, 350))
img2 = ImageTk.PhotoImage(img2)
panel2 = Label(ventana, image=img2)
panel2.pack(side="bottom")

textbox = ttk.Entry(ventana, style="TEntry")
textbox.pack(side="top")

preview = ttk.Label(ventana, style="TLabel")
preview.pack(side="top")

cfichero = ttk.Label(ventana, style="TLabel")
cfichero.pack(side="top")

boton = ttk.Button(ventana, text="Buscar", command=lambda: responde_graba(preview, textbox, preview, cfichero))
boton.pack(side="top")

ventana.mainloop()