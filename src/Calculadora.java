// Nome da dupla:  Guilherme Torres Postigo e Théo Vera Aguiar da Costa

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculadora extends JFrame {
    private JTextField txtResultado;
    private double valor1 = 0;
    private double valor2 = 0;
    private String operacao = "";
    private boolean novaOperacao = true;

    public Calculadora() {
        setTitle("Calculadora");
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Campo de texto
        txtResultado = new JTextField("0");
        txtResultado.setHorizontalAlignment(JTextField.RIGHT);
        txtResultado.setEditable(false);
        txtResultado.setFont(new Font("Arial", Font.BOLD, 24));
        add(txtResultado, BorderLayout.NORTH);

        // Painel de botões
        JPanel painelBotoes = new JPanel(new GridLayout(5, 4, 5, 5));
        String[] botoes = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "=", "+",
                "C", "", "", ""
        };

        for (String texto : botoes) {
            if (texto.isEmpty()) {
                painelBotoes.add(new JLabel(""));
            } else {
                JButton btn = new JButton(texto);
                btn.setFont(new Font("Arial", Font.BOLD, 20));
                btn.addActionListener(new AcaoBotao());
                painelBotoes.add(btn);
            }
        }
        add(painelBotoes, BorderLayout.CENTER);
    }

    private class AcaoBotao implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String comando = e.getActionCommand();

            try {
                if (comando.matches("[0-9]") || comando.equals(".")) {
                    if (novaOperacao) {
                        txtResultado.setText(comando);
                        novaOperacao = false;
                    } else {
                        txtResultado.setText(txtResultado.getText() + comando);
                    }
                } else if (comando.equals("C")) {
                    txtResultado.setText("0");
                    valor1 = 0;
                    valor2 = 0;
                    operacao = "";
                    novaOperacao = true;
                } else if (comando.equals("=")) {
                    valor2 = Double.parseDouble(txtResultado.getText());
                    double resultado = 0;

                    switch (operacao) {
                        case "+": resultado = valor1 + valor2; break;
                        case "-": resultado = valor1 - valor2; break;
                        case "*": resultado = valor1 * valor2; break;
                        case "/":
                            if (valor2 == 0) {
                                throw new ArithmeticException("Divisão por zero!");
                            }
                            resultado = valor1 / valor2;
                            break;
                    }
                    txtResultado.setText(String.valueOf(resultado));
                    novaOperacao = true;
                } else { // Operadores +, -, *, /
                    valor1 = Double.parseDouble(txtResultado.getText());
                    operacao = comando;
                    novaOperacao = true;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Erro: Formato de número inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
                txtResultado.setText("0");
                novaOperacao = true;
            } catch (ArithmeticException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro Matemático", JOptionPane.ERROR_MESSAGE);
                txtResultado.setText("0");
                novaOperacao = true;
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro inesperado: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            } finally {
                // O bloco finally é executado sempre, independente de erro ou sucesso.
                System.out.println("Operação processada. Estado atual: " + txtResultado.getText());
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Calculadora().setVisible(true));
    }
}