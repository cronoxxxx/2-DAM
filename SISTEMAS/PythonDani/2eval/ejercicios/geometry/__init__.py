from .dinamic import Dynamic
din = Dynamic()
din.loadxyz("C60.xyz")
din.get('-d', [5,42])
din.print_out()