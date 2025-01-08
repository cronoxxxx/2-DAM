from .atom import Atom


class Step:
    def __init__(self):
        self.atoms = []

    def add_atom(self, atom):
        self.atoms.append(atom)

    def get_distance(self, i, j):
        """Get distance between atoms i and j"""
        return self.atoms[i].distancia(self.atoms[j])