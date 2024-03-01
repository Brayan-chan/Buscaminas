import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.function.Consumer;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 *
 * @author Brayan Chan
 *  21/01/2024
 *  18:47 p.m.
 */
public class JuegoMinas extends javax.swing.JFrame {

    int numFilas=10;
    int numColumnas=10;
    int numMinas=10;

    
    JButton[][] botonesTablero;
    
    TableroBuscaminas tableroBuscaminas;

    public JuegoMinas() {
        initComponents();
        cargarControles();
        crearTableroBuscaminas();
        juegoNuevo();
    }
    
    void descargarControles(){
        if (botonesTablero!=null){
            for (int i = 0; i < botonesTablero.length; i++) {
                for (int j = 0; j < botonesTablero[i].length; j++) {
                    if (botonesTablero[i][j]!=null){
                        getContentPane().remove(botonesTablero[i][j]);
                    }
                }
            }
        }
    }
    
    private void juegoNuevo(){
        descargarControles();
        cargarControles();
        crearTableroBuscaminas();
        //Refrescar los graficos con repaint
        repaint();
    }
    
    private void crearTableroBuscaminas(){
        tableroBuscaminas=new TableroBuscaminas(numFilas, numColumnas, numMinas);
        tableroBuscaminas.setEventoPartidaPerdida(new Consumer<List<Casilla>>() {
            @Override
            public void accept(List<Casilla> t) {
                for(Casilla casillaConMina: t){
                    botonesTablero[casillaConMina.getPosFila()][casillaConMina.getPosColumna()].setText("*");
                }
            }
        });
        tableroBuscaminas.setEventoPartidaGanada(new Consumer<List<Casilla>>() {
            @Override
            public void accept(List<Casilla> t) {
                for(Casilla casillaConMina: t){
                    botonesTablero[casillaConMina.getPosFila()][casillaConMina.getPosColumna()].setText(":)");
                }
            }
        });
        
        tableroBuscaminas.setEventoCasillaAbierta(new Consumer<Casilla>() {
            @Override
            public void accept(Casilla t) {
                botonesTablero[t.getPosFila()][t.getPosColumna()].setEnabled(false);
                botonesTablero[t.getPosFila()][t.getPosColumna()]
                        .setText(t.getNumMinasAlrededor()==0?"":t.getNumMinasAlrededor()+"");
            }
        });

        tableroBuscaminas.setEventoPartidaPerdida(new Consumer<List<Casilla>>() {
            @Override
            public void accept(List<Casilla> t) {
                for(Casilla casillaConMina: t){
                    botonesTablero[casillaConMina.getPosFila()][casillaConMina.getPosColumna()].setText("*");
                }
                JOptionPane.showMessageDialog(null, menuJugador.getText() + " Has perdido el juego!");
                juegoNuevo();
            }
        });
        
        tableroBuscaminas.setEventoPartidaGanada(new Consumer<List<Casilla>>() {
            @Override
            public void accept(List<Casilla> t) {
                for(Casilla casillaConMina: t){
                    botonesTablero[casillaConMina.getPosFila()][casillaConMina.getPosColumna()].setText(":)");
                }
                JOptionPane.showMessageDialog(null, menuJugador.getText() + " Has ganado el juego!");
                juegoNuevo();
            }
        });
        tableroBuscaminas.imprimirTablero();
    }
    
    private void cargarControles() { 
        
        int posXReferencia=25;
        int posYReferencia=25;
        int anchoControl=30;
        int altoControl=30;

        
        botonesTablero = new JButton[numFilas][numColumnas];
        for (int i = 0; i < botonesTablero.length; i++) {
            for (int j = 0; j < botonesTablero[i].length; j++) {
                botonesTablero[i][j] = new JButton();
                botonesTablero[i][j].setName(i+","+j);
                botonesTablero[i][j].setBorder(null);
                if (i==0 && j==0){
                  botonesTablero[i][j].setBounds(posXReferencia, 
                          posYReferencia, anchoControl, altoControl);  
                } else if (i==0 && j!=0) {
                    botonesTablero[i][j].setBounds(
                            botonesTablero[i][j-1].getX()+botonesTablero[i][j-1].getWidth(), 
                            posYReferencia, anchoControl, altoControl);
                } else {
                    botonesTablero[i][j].setBounds(
                            botonesTablero[i-1][j].getX(), 
                            botonesTablero[i-1][j].getY()+botonesTablero[i-1][j].getHeight(),
                            anchoControl, altoControl);
                }
                botonesTablero[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        btnClick(e);
                    }
                });
                getContentPane().add(botonesTablero[i][j]);
            }
        }
        this.setSize(botonesTablero[numFilas-1][numColumnas-1].getX()+
                botonesTablero[numFilas-1][numColumnas-1].getWidth()+30,
                botonesTablero[numFilas-1][numColumnas-1].getY()+
                botonesTablero[numFilas-1][numColumnas-1].getHeight()+70
                );
    }
    
    private void btnClick(ActionEvent e) {
        JButton btn=(JButton)e.getSource();
        String[] coordenada=btn.getName().split(",");
        int posFila=Integer.parseInt(coordenada[0]);
        int posColumna=Integer.parseInt(coordenada[1]);
        tableroBuscaminas.seleccionarCasilla(posFila, posColumna);
    }


    @SuppressWarnings("unchecked")
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        menuScore = new javax.swing.JMenuItem();
        menuJugador = new javax.swing.JMenuItem();
        menuNuevoJuego = new javax.swing.JMenuItem();
        menuTamano = new javax.swing.JMenuItem();
        menuNumMinas = new javax.swing.JMenuItem();

        jMenuItem1.setText("jMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jMenu1.setText("Juego");

        menuJugador.setText("Nombre Jugador");
        menuJugador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuJugadorActionPerformed(evt);
            }
        }); 
        jMenu1.add(menuJugador);

        menuScore.setText("Score");
        menuScore.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuScoreActionPerformed(evt);
            }
        });
        jMenu1.add(menuScore);

        menuNuevoJuego.setText("Generar Juego Nuevo");
        menuNuevoJuego.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuNuevoJuegoActionPerformed(evt);
            }
        });
        jMenu1.add(menuNuevoJuego);

        menuTamano.setText("Tamaño");
        menuTamano.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuTamanoActionPerformed(evt);
            }
        });
        jMenu1.add(menuTamano);

        menuNumMinas.setText("Minas");
        menuNumMinas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuNumMinasActionPerformed(evt);
            }
        });
        jMenu1.add(menuNumMinas);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 277, Short.MAX_VALUE)
        );

        pack();
    }

    private void menuJugadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuJugadorActionPerformed
        String nombre = JOptionPane.showInputDialog("Digite el nombre del jugador");
        this.setTitle("Buscaminas - " + nombre);
        this.menuJugador.setText("Jugador: " + nombre);
    }
    
    private void menuScoreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuScoreActionPerformed
        int score = tableroBuscaminas.getScore();
        JOptionPane.showMessageDialog(rootPane, "Score: " + score);
        this.menuScore.setText("Score: " + score);
    }

    private void menuNuevoJuegoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuNuevoJuegoActionPerformed
        JOptionPane.showMessageDialog(rootPane, "Nuevo juego generado");
        juegoNuevo();
    }

    private void menuTamanoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuTamanoActionPerformed
        int num = Integer.parseInt(JOptionPane.showInputDialog("Digite el tamnno de la matriz, n*n"));
        this.numFilas = num;
        this.numColumnas = num;
        juegoNuevo();
    }

    private void menuNumMinasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuNumMinasActionPerformed
        int num = Integer.parseInt(JOptionPane.showInputDialog("Digite el numero de minas"));
        this.numMinas = num;
        juegoNuevo();
    }
 
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JuegoMinas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JuegoMinas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JuegoMinas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JuegoMinas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JuegoMinas().setVisible(true);
            }
        });
    }

    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem menuJugador;
    private javax.swing.JMenuItem menuNuevoJuego;
    private javax.swing.JMenuItem menuNumMinas;
    private javax.swing.JMenuItem menuTamano;
    private javax.swing.JMenuItem menuScore;
}