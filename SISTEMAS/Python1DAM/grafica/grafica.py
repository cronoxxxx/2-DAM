import tkinter as tk
import webbrowser

ventana = tk.Tk()
def search_google():
    query = textbox.get()
    url = webbrowser.open(f'https://google.com/search?q={query}')
    webbrowser.open_new_tab(url)
    
textbox = tk.Entry(ventana)
textbox.pack()

boton = tk.Button(ventana, text="Buscar", command=search_google)
boton.pack()


ventana.mainloop()