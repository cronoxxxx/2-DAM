import sys
from tablero import Tablero
from PIL import Image
import matplotlib.pyplot as plt

inicio = ['tb_a1', 'cb_b1', 'ab_c1', 'rb_d1', 'db_e1', 'ab_f1', 'cb_g1', 'tb_h1',
'pb_a2', 'pb_b2', 'pb_c2', 'pb_d2', 'pb_e2', 'pb_f2', 'pb_g2', 'pb_h2',
'pn_a7', 'pn_b7', 'pn_c7', 'pn_d7', 'pn_e7', 'pn_f7', 'pn_g7', 'pn_h7',
'tn_a8', 'cn_b8', 'an_c8', 'rn_d8', 'dn_e8', 'an_f8', 'cn_g8', 'tn_h8'
]

JUGADA_DIR = 'jugada.png'
def ayuda(): print(
    'ajedrez.py -help print this help\najedrez.py pinta el tablero inicial\najedrez.py -n X pinta X damas en el tablero sin darse jaque')


def main():
    if len(sys.argv) > 1:
        if sys.argv[1] == '-help':
            ayuda()
        elif sys.argv[1] == '-n':
            tablero = Tablero(inicio)
            tablero.pintar_reinas(int(sys.argv[2]))
            tablero.pintar_tablero()
            img = Image.open(JUGADA_DIR)
            plt.imshow(img)
            plt.axis('off')
            plt.show()
        else:
            print('No se reconoce el comando, usa ajedrez.py -help para ver los comandos')
    else:
        tablero = Tablero(inicio)
        tablero.pintar_tablero()
        img = Image.open(JUGADA_DIR)
        plt.imshow(img)
        plt.axis('off')
        plt.show()

        tablero.cambiar_pos_ficha('pb_d2', 'pb_d4')
        tablero.pintar_tablero()
        img = Image.open(JUGADA_DIR)
        plt.imshow(img)
        plt.axis('off')
        plt.show()

        tablero.cambiar_pos_ficha('cn_b8', 'cn_c6')
        tablero.pintar_tablero()
        img = Image.open(JUGADA_DIR)
        plt.imshow(img)
        plt.axis('off')
        plt.show()

        tablero.cambiar_pos_ficha("ab_c1", "ab_e3")
        tablero.pintar_tablero()
        img = Image.open(JUGADA_DIR)
        plt.imshow(img)
        plt.axis('off')
        plt.show()

        tablero.comer_ficha("cn_c6","cn_d4")
        tablero.pintar_tablero()
        img = Image.open(JUGADA_DIR)
        plt.imshow(img)
        plt.axis('off')
        plt.show()

if __name__ == "__main__":
    main()