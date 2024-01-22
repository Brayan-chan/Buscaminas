import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

/**
 *
 * @author Brayan Chan
 *  21/01/2024
 *  14:54 p.m.
 */
public class TableroBuscaminas {
    Casilla[][] casillas;
    
    int numFilas;
    int numColumnas;
    int numMinas;
    
    int numCasillasAbiertas;
    boolean juegoTerminado;
    
    private Consumer<List<Casilla>> eventoPartidaPerdida;
    private Consumer<List<Casilla>> eventoPartidaGanada;
    
    private Consumer<Casilla> eventoCasillaAbierta;
    

    public TableroBuscaminas(int numFilas, int numColumnas, int numMinas) {
        this.numFilas = numFilas;
        this.numColumnas = numColumnas;
        this.numMinas=numMinas;
        this.inicializarCasillas();
    }
    
    public void inicializarCasillas(){
        casillas=new Casilla[this.numFilas][this.numColumnas];
        for (int i = 0; i < casillas.length; i++) {
            for (int j = 0; j < casillas[i].length; j++) {
                casillas[i][j]=new Casilla(i, j);
            }
        }
        generarMinas();
    }

    private void generarMinas(){
        int minasGeneradas=0;
        while(minasGeneradas!=numMinas){
            int posTmpFila=(int)(Math.random()*casillas.length);
            int posTmpColumna=(int)(Math.random()*casillas[0].length);
            if (!casillas[posTmpFila][posTmpColumna].isMina()){
                casillas[posTmpFila][posTmpColumna].setMina(true);
                minasGeneradas++;
            }
        }
        actualizarNumeroMinasAlrededor();
    }
    
    public void imprimirTablero() {
        for (int i = 0; i < casillas.length; i++) {
            for (int j = 0; j < casillas[i].length; j++) {
                System.out.print(casillas[i][j].isMina()?"*":"0");
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
    
    private void actualizarNumeroMinasAlrededor(){
        for (int i = 0; i < casillas.length; i++) {
            for (int j = 0; j < casillas[i].length; j++) {
                if (casillas[i][j].isMina()){
                    List<Casilla> casillasAlrededor=obtenerCasillasAlrededor(i, j);
                    casillasAlrededor.forEach((c)->c.incrementarNumeroMinasAlrededor());
                }
            }
        }
    }
    
    private List<Casilla> obtenerCasillasAlrededor(int posFila, int posColumna){
        List<Casilla> listaCasillas=new LinkedList<>();
        for (int i = 0; i < 8; i++) {
            int tmpPosFila=posFila;
            int tmpPosColumna=posColumna;
            switch(i){
                case 0: tmpPosFila--;break; //Arriba
                case 1: tmpPosFila--;tmpPosColumna++;break; //Arriba Derecha
                case 2: tmpPosColumna++;break; //Derecha
                case 3: tmpPosColumna++;tmpPosFila++;break; //Derecha Abajo
                case 4: tmpPosFila++;break; //Abajo
                case 5: tmpPosFila++;tmpPosColumna--;break; //Abajo Izquierda
                case 6: tmpPosColumna--;break; //Izquierda
                case 7: tmpPosFila--; tmpPosColumna--;break; //Izquierda Arriba
            }
            
            if (tmpPosFila>=0 && tmpPosFila<this.casillas.length
                    && tmpPosColumna>=0 && tmpPosColumna<this.casillas[0].length){
                listaCasillas.add(this.casillas[tmpPosFila][tmpPosColumna]);
            }
            
        }
        return listaCasillas;
    }
    
    List<Casilla> obtenerCasillasConMinas() {
        List<Casilla> casillasConMinas = new LinkedList<>();
        for (int i = 0; i < casillas.length; i++) {
            for (int j = 0; j < casillas[i].length; j++) {
                if (casillas[i][j].isMina()) {
                    casillasConMinas.add(casillas[i][j]);
                }
            }
        }
        return casillasConMinas;
    }
    
    public void seleccionarCasilla(int posFila, int posColumna) {
        eventoCasillaAbierta.accept(this.casillas[posFila][posColumna]);
        if (this.casillas[posFila][posColumna].isMina()) {
            eventoPartidaPerdida.accept(obtenerCasillasConMinas());
        }else if (this.casillas[posFila][posColumna].getNumMinasAlrededor()==0){
            marcarCasillaAbierta(posFila, posColumna);
            List<Casilla> casillasAlrededor=obtenerCasillasAlrededor(posFila, posColumna);
            for(Casilla casilla: casillasAlrededor){
                if (!casilla.isAbierta()){
                    seleccionarCasilla(casilla.getPosFila(), casilla.getPosColumna());
                }
            }
        }else{
            marcarCasillaAbierta(posFila, posColumna);
        }
        if (partidaGanada()){
           eventoPartidaGanada.accept(obtenerCasillasConMinas());
        }
    }
    
    void marcarCasillaAbierta(int posFila, int posColumna){
        if (!this.casillas[posFila][posColumna].isAbierta()){
            numCasillasAbiertas++;
            this.casillas[posFila][posColumna].setAbierta(true);
        }
    }
    
    boolean partidaGanada(){
        return numCasillasAbiertas>=(numFilas*numColumnas)-numMinas;
    }
    
    
    
    public static void main(String[] args) {
        TableroBuscaminas tablero=new TableroBuscaminas(5, 5, 5);
        tablero.imprimirTablero();
        System.out.println("---");
        tablero.imprimirPistas();
    }

    public void setEventoPartidaPerdida(Consumer<List<Casilla>> eventoPartidaPerdida) {
        this.eventoPartidaPerdida = eventoPartidaPerdida;
    }

    public void setEventoCasillaAbierta(Consumer<Casilla> eventoCasillaAbierta) {
        this.eventoCasillaAbierta = eventoCasillaAbierta;
    }

    public void setEventoPartidaGanada(Consumer<List<Casilla>> eventoPartidaGanada) {
        this.eventoPartidaGanada = eventoPartidaGanada;
    }
}