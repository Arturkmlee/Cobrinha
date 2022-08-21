// JOGO DA COBRA PARA DOIS JOGADRES
// Artur Kyung Min Lee - RA: 191025781
// Pedro Henrique Alves de Castro - RA: 191027693


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

class FrameDoJogo extends JFrame{
    public FrameDoJogo(){
        this.add(new PanelDoJogo());
        this.setTitle("Jogo da Cobrinha");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }
}

class PanelDoJogo extends JPanel implements ActionListener {
    final int LARGURA = 600;
    final int ALTURA = 600;
    final int TAMANHO_QUADRADO = 25;
    final int JOGO_TAMANHO = (LARGURA * ALTURA)/TAMANHO_QUADRADO; // DIVIDE A ÁREA DO JOGO EM VÁRIOS "QUADRADINHOS"
    final int DELAY = 75;
    final int[] jg1x = new int[JOGO_TAMANHO];
    final int[] jg1y = new int[JOGO_TAMANHO];
    final int[] jg2x = new int[JOGO_TAMANHO];
    final int[] jg2y = new int[JOGO_TAMANHO];
    int tamanhoCobra = 3;
    int macasComidas;
    int posMacaX, posMacaY;
    char direcao = 'D';
    char direcao2 = 'b';
    boolean rodando = true;
    Timer timer;
    Random random;


    public PanelDoJogo(){
        random = new Random();
        this.setPreferredSize(new Dimension(LARGURA, ALTURA));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new Comandos());
        iniciaJogo();
    }

    public void iniciaJogo(){
        criaMaca();
        rodando = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        desenha(g);
    }
    public void desenha(Graphics g){
//        g.drawImage(maca,posMacaX, posMacaY, TAMANHO_QUADRADO, TAMANHO_QUADRADO, this);
        g.setColor(Color.red);
        g.fillOval(posMacaX, posMacaY, TAMANHO_QUADRADO, TAMANHO_QUADRADO);

        if(rodando){
            for (int i = 0; i < tamanhoCobra; i++) {
                if (i == 0) {
                    g.setColor(Color.green);
                    g.fillRect(jg1x[i], jg1y[i], TAMANHO_QUADRADO, TAMANHO_QUADRADO);
                } else {
                    g.setColor(new Color(45, 180, 0));
                    g.fillRect(jg1x[i], jg1y[i], TAMANHO_QUADRADO, TAMANHO_QUADRADO);
                }
            }

            for (int i = 0; i < tamanhoCobra; i++) {
                if (i == 0) {
                    g.setColor(Color.blue);
                    g.fillRect(jg2x[i], jg2y[i], TAMANHO_QUADRADO, TAMANHO_QUADRADO);
                } else {
                    g.setColor(new Color(96, 138, 210));
                    g.fillRect(jg2x[i], jg2y[i], TAMANHO_QUADRADO, TAMANHO_QUADRADO);
                }
            }

            g.setColor(Color.red);
            g.setFont(new Font("Ink free", Font.BOLD, 40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Pontuacao: " + macasComidas, (LARGURA - metrics.stringWidth("Pontuacao: " + macasComidas))/2, g.getFont().getSize());
        }
        else {
            g.setColor(Color.black);
            g.fillOval(posMacaX, posMacaY, TAMANHO_QUADRADO, TAMANHO_QUADRADO);
            fimDoJogo(g);
        }
    }
    public void criaMaca(){
        posMacaX = random.nextInt((int)(LARGURA / TAMANHO_QUADRADO)) * TAMANHO_QUADRADO;
        posMacaY = random.nextInt((int)(ALTURA / TAMANHO_QUADRADO)) * TAMANHO_QUADRADO;
    }


    public void movimento(){
        for(int i = tamanhoCobra; i > 0; i--){
            jg1x[i] = jg1x[i - 1];
            jg1y[i] = jg1y[i- 1];
        }
        switch (direcao){
            case 'C':
                jg1y[0] = jg1y[0] - TAMANHO_QUADRADO;
                break;
            case 'B':
                jg1y[0] = jg1y[0] + TAMANHO_QUADRADO;
                break;
            case 'E':
                jg1x[0] = jg1x[0] - TAMANHO_QUADRADO;
                break;
            case 'D':
                jg1x[0] = jg1x[0] + TAMANHO_QUADRADO;
                break;

        }

        switch (direcao2){
            case 'c':
                jg2y[0] = jg2y[0] - TAMANHO_QUADRADO;
                break;
            case 'b':
                jg2y[0] = jg2y[0] + TAMANHO_QUADRADO;
                break;
            case 'e':
                jg2x[0] = jg2x[0] - TAMANHO_QUADRADO;
                break;
            case 'd':
                jg2x[0] = jg2x[0] + TAMANHO_QUADRADO;
                break;
        }
    }

    public void verificaComida(){
        if((jg1x[0] == posMacaX) && (jg1y[0] == posMacaY)){
            tamanhoCobra++;
            macasComidas++;
            criaMaca();
        }
    }
    public void verificaHitBox(){
        for(int i = tamanhoCobra; i > 0; i--){
            if((jg1x[0] == jg1x[i]) && (jg1y[0] == jg1y[i])){
                rodando = false;
            }
        }
        if(jg1x[0] < 0)
            rodando = false;
        if(jg1x[0] > LARGURA)
            rodando = false;
        if(jg1y[0] < 0)
            rodando = false;
        if(jg1y[0] > ALTURA)
            rodando = false;
        if(!rodando)
            timer.stop();
    }

    public void fimDoJogo(Graphics g){
        g.setColor(Color.red);
        g.setFont(new Font("Ink free", Font.BOLD, 75));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Fim de Jogo", (LARGURA - metrics.stringWidth("Fim de Jogo"))/2, ALTURA/2);
    }

    public class Comandos extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            switch (e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if(direcao != 'D')
                        direcao = 'E';
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direcao != 'E')
                        direcao = 'D';
                    break;
                case KeyEvent.VK_UP:
                    if(direcao != 'B')
                        direcao = 'C';
                    break;
                case KeyEvent.VK_DOWN:
                    if(direcao != 'C')
                        direcao = 'B';
                    break;
                case KeyEvent.VK_A:
                    if(direcao != 'd')
                        direcao = 'e';
                    break;
                case KeyEvent.VK_D:
                    if(direcao != 'e')
                        direcao = 'd';
                    break;
                case KeyEvent.VK_W:
                    if(direcao != 'b')
                        direcao = 'c';
                    break;
                case KeyEvent.VK_S:
                    if(direcao != 'c')
                        direcao = 'b';
                    break;
            }
        }
    }

    @Override
    public void actionPerformed (ActionEvent e){
        if(rodando){
            movimento();
            verificaComida();
            verificaHitBox();
        }
        repaint();
    }
}

public class Cobrinha {
    public static void main(String[] args) {
        new FrameDoJogo();
    }
}
