package ui;

import jogo.ArquivoUtils;
import jogo.JogoDaForca;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class ForcaUI extends JFrame {
    private JogoDaForca jogo;
    private JTextField entradaLetra;
    private JTextField entradaPalpite; // NOVO
    private JLabel palavraAtual;
    private JLabel letrasUsadas;
    private JPanel painelDesenho;

    public ForcaUI() {
        setTitle("Jogo da Forca");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 450);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        List<String> palavras = ArquivoUtils.carregarPalavras("dados/palavras.txt");
        jogo = new JogoDaForca();
        jogo.iniciar(palavras);

        palavraAtual = new JLabel(jogo.getPalavraAtual());
        palavraAtual.setFont(new Font("Monospaced", Font.BOLD, 24));
        palavraAtual.setHorizontalAlignment(SwingConstants.CENTER);

        letrasUsadas = new JLabel("Letras usadas: ");
        letrasUsadas.setHorizontalAlignment(SwingConstants.CENTER);

        entradaLetra = new JTextField();
        entradaLetra.addActionListener(this::processarLetra);

        entradaPalpite = new JTextField(); // NOVO
        entradaPalpite.addActionListener(this::processarPalpite); // NOVO

        painelDesenho = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                desenharForca(g);
            }
        };

        JPanel painelInferior = new JPanel(new GridLayout(2, 1)); // NOVO
        painelInferior.add(entradaLetra); // campo para letra
        painelInferior.add(entradaPalpite); // campo para palpite da palavra

        add(palavraAtual, BorderLayout.NORTH);
        add(painelDesenho, BorderLayout.CENTER);
        add(painelInferior, BorderLayout.SOUTH); // trocado de entradaLetra para painelInferior
        add(letrasUsadas, BorderLayout.WEST);

        setVisible(true);
    }

    private void processarLetra(ActionEvent e) {
        String texto = entradaLetra.getText().trim();
        if (texto.length() != 1) return;
        char letra = texto.charAt(0);
        jogo.tentarLetra(letra);
        entradaLetra.setText("");
        atualizarTela();
        verificarFimDeJogo();
    }

    // NOVO: método para processar o palpite da palavra
    private void processarPalpite(ActionEvent e) {
        String palpite = entradaPalpite.getText().trim().toUpperCase();
        entradaPalpite.setText("");
        if (palpite.equalsIgnoreCase(jogo.getPalavraSecreta())) {
            JOptionPane.showMessageDialog(this, "Parabéns! Você adivinhou a palavra!");
        } else {
            JOptionPane.showMessageDialog(this, "Palpite incorreto! Você perdeu!");
        }
        JOptionPane.showMessageDialog(this, "A palavra era: " + jogo.getPalavraSecreta());
        System.exit(0);
    }

    private void verificarFimDeJogo() {
        if (jogo.isVitoria()) {
            JOptionPane.showMessageDialog(this, "Você venceu! Palavra: " + jogo.getPalavraSecreta());
            System.exit(0);
        } else if (jogo.isDerrota()) {
            JOptionPane.showMessageDialog(this, "Você perdeu! Palavra: " + jogo.getPalavraSecreta());
            System.exit(0);
        }
    }

    private void atualizarTela() {
        palavraAtual.setText(jogo.getPalavraAtual());
        letrasUsadas.setText("Letras usadas: " + jogo.getLetrasTentadas());
        painelDesenho.repaint();
    }

    private void desenharForca(Graphics g) {
        int erros = jogo.getErros();

        g.drawLine(50, 200, 150, 200); // base
        g.drawLine(100, 200, 100, 50); // poste
        g.drawLine(100, 50, 170, 50); // topo
        g.drawLine(170, 50, 170, 70); // corda

        if (erros < 6) g.drawLine(170, 140, 190, 170); // perna direita
        if (erros < 5) g.drawLine(170, 140, 150, 170); // perna esquerda
        if (erros < 4) g.drawLine(170, 110, 190, 130); // braço direito
        if (erros < 3) g.drawLine(170, 110, 150, 130); // braço esquerdo
        if (erros < 2) g.drawLine(170, 100, 170, 140); // tronco
        if (erros < 1) g.drawOval(155, 70, 30, 30); // cabeça
    }
}
