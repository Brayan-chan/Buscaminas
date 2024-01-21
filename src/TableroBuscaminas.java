/**
 *
 * @author Brayan Chan
 *  21/01/2024
 *  14:54 p.m.
 */
public class TableroBuscaminas {
    Casilla[][] casillas;
    
    int numFilas;
    int numColums;
    int numMinas;

    public TableroBuscaminas(int numFilas, int numColums, int numMinas) {
        this.numFilas = numFilas;
        this.numColums = numColums;
        this.numMinas = numMinas;
        inicializarCasillas();
    }
    
    public void inicializarCasillas() {
        casillas = new Casilla[this.numFilas][this.numColums];
        for (int i = 0; i < casillas.length; i++) {
            for (int j = 0; j < casillas[i].length; j++) {
                casillas[i][j] = new Casilla(i, j);
            }
        }
        generarMinas();
    }
    
    private void generarMinas() {
        int minasGeneradas = 0;
        while (minasGeneradas != numMinas) {
            int posTmpFila = (int)(Math.random() * casillas.length);
            int posTmpColum = (int)(Math.random() * casillas[0].length);
            if (!casillas[posTmpFila][posTmpColum].isMina()) {
                casillas[posTmpFila][posTmpColum].setMina(true);
                minasGeneradas++;
            }
        }
    }
    
    private void imprimirTablero() {
        for (int i = 0; i < casillas.length; i++) {
            for (int j = 0; j < casillas[i].length; j++) {
                System.out.print(casillas[i][j].isMina() ?"*":"0");
            }
            System.out.println("");
        }
    }
    
    public static void main(String[] args) {
        // 6 = numero de columnas y filas, 5 = numero de minas 
        TableroBuscaminas tablero = new TableroBuscaminas(6, 10, 15);
        tablero.imprimirTablero();
    }
}
