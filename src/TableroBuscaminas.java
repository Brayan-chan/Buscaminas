
import java.util.LinkedList;
import java.util.List;

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
        actualizarNumeroMinasAlrededor();
    }
    
    private void imprimirTablero() {
        for (int i = 0; i < casillas.length; i++) {
            for (int j = 0; j < casillas[i].length; j++) {
                System.out.print(casillas[i][j].isMina() ?"*":"0");
            }
            System.out.println("");
        }
    }
    
    private void imprimirPistas() {
        for (int i = 0; i < casillas.length; i++) {
            for (int j = 0; j < casillas[i].length; j++) {
                System.out.print(casillas[i][j].getNumMinasAlrededor());
            }
            System.out.println("");
        }
    }
    
    private void actualizarNumeroMinasAlrededor() {
        for (int i = 0; i < casillas.length; i++) {
            for (int j = 0; j < casillas[i].length; j++) {
                if (casillas[i][j].isMina()) {
                    List<Casilla> casillasAlrededor = obtenerCasillasAlrededor(i, j);
                    casillasAlrededor.forEach((c)->c.incrementarNumeroMinasAlrededor());
                }
            }
        }
    }
    
    private List<Casilla> obtenerCasillasAlrededor(int posFila, int posColum) {
        List<Casilla> listaCasillas = new LinkedList<>();
        for (int i = 0; i < 8; i++) {
            int tmpPosFila = posFila;
            int tmpPosColum = posColum;
            switch (i) {
                case 0: tmpPosFila--;break; //Arriba
                case 1: tmpPosFila--;tmpPosColum++;break; //Arriba Derecha
                case 2: tmpPosColum++;break; //Derecha
                case 3: tmpPosColum++;tmpPosFila++;break; //Derecha Abajo
                case 4: tmpPosFila++;break; //Abajo
                case 5: tmpPosFila++;tmpPosColum--;break; //Abajo Izquierda
                case 6: tmpPosColum--;break; //Izquierda
                case 7: tmpPosColum--;tmpPosFila--;break; //Izquierda Arriba
            }
            if(tmpPosFila >= 0 && tmpPosFila<this.casillas.length
                    && tmpPosColum >= 0 && tmpPosColum<this.casillas[0].length) {
                listaCasillas.add(this.casillas[tmpPosFila][tmpPosColum]);
            }
        }
        return listaCasillas;
    }
    
    public static void main(String[] args) {
        // 6 = numero de columnas y filas, 5 = numero de minas 
        TableroBuscaminas tablero = new TableroBuscaminas(5, 5, 5);
        tablero.imprimirTablero();
        System.out.println("---");
        tablero.imprimirPistas();
    }
}
