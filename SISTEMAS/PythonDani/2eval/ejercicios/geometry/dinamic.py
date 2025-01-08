from .step import Step
from .atom import Atom


class Dynamic:
    def __init__(self):
        self.steps = []

    def loadxyz(self, filename):
        """Load XYZ file"""
        current_step = Step()
        with open(filename, 'r') as f:
            while True:
                try:
                    # Read number of atoms
                    natoms = int(f.readline())
                    # Skip comment line
                    f.readline()

                    # Read atomic coordinates
                    for _ in range(natoms):
                        line = f.readline().split()
                        if not line:
                            break
                        Z = line[0]
                        x, y, z = map(float, line[1:4])
                        atom = Atom(Z, x, y, z)
                        current_step.add_atom(atom)

                    self.steps.append(current_step)
                    current_step = Step()
                except:
                    break

    def get(self, flag, atoms):
        """Process command line arguments"""
        if flag == '-d':
            # Calculate distance between specified atoms
            return [step.get_distance(atoms[0], atoms[1]) for step in self.steps]

    def print_out(self):
        """Print results"""
        results = self.get('-d', [5, 42])  # Example atoms 5 and 42
        for r in results:
            print(f"{r:10.6f}")