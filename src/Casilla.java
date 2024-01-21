/**
 *
 * @author Brayan Chan
 *  21/01/2024
 *  14:54 p.m.
 */
public class Casilla {
    private int posFila;
    private int posColum;
    private boolean mina;

    public Casilla(int posFila, int posColum) {
        this.posFila = posFila;
        this.posColum = posColum;
    }
    
    public int getPosFila() {
        return posFila;
    }

    public void setPosFila(int posFila) {
        this.posFila = posFila;
    }

    public int getPosColum() {
        return posColum;
    }

    public void setPosColum(int posColum) {
        this.posColum = posColum;
    }

    public boolean isMina() {
        return mina;
    }

    public void setMina(boolean mina) {
        this.mina = mina;
    }
    
    
}
