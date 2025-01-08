import math


class Atom:
    def __init__(self, Z='H', x=0.0, y=0.0, z=0.0):
        self.Z = Z  # Atomic symbol
        self.r = [x, y, z]  # Position vector

    def distancia(self, atom2):
        """Calculate distance between two atoms"""
        dx = self.r[0] - atom2.r[0]
        dy = self.r[1] - atom2.r[1]
        dz = self.r[2] - atom2.r[2]
        return math.sqrt(dx * dx + dy * dy + dz * dz)