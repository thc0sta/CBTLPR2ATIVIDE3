import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormPessoa extends JFrame {
    private JTextField txtNumero, txtNome, txtIdade;
    private JRadioButton rbMasculino, rbFeminino;
    private ButtonGroup grupoSexo;
    private Pessoa umaPessoa = new Pessoa();

    public FormPessoa() {
        setTitle("Semana 06 - Exercício 01");
        setSize(500, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Painel de campos
        JPanel painelCampos = new JPanel(new GridLayout(4, 2, 5, 5));

        // Numero (Inacessível)
        painelCampos.add(new JLabel("Numero:"));
        txtNumero = new JTextField();
        txtNumero.setEditable(false); // Não acessível à digitação
        painelCampos.add(txtNumero);

        // Nome
        painelCampos.add(new JLabel("Nome:"));
        txtNome = new JTextField();
        painelCampos.add(txtNome);

        // Sexo (JRadioButton - Versão 03)
        painelCampos.add(new JLabel("Sexo:"));
        JPanel painelSexo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        rbMasculino = new JRadioButton("M");
        rbFeminino = new JRadioButton("F");
        grupoSexo = new ButtonGroup();
        grupoSexo.add(rbMasculino);
        grupoSexo.add(rbFeminino);
        painelSexo.add(rbMasculino);
        painelSexo.add(rbFeminino);
        painelCampos.add(painelSexo);

        // Idade
        painelCampos.add(new JLabel("Idade:"));
        txtIdade = new JTextField();
        painelCampos.add(txtIdade);

        add(painelCampos, BorderLayout.CENTER);

        // Painel de botões
        JPanel painelBotoes = new JPanel(new FlowLayout());
        JButton btnOK = new JButton("OK");
        JButton btnLimpar = new JButton("Limpar");
        JButton btnMostrar = new JButton("Mostrar");
        JButton btnSair = new JButton("Sair");

        painelBotoes.add(btnOK);
        painelBotoes.add(btnLimpar);
        painelBotoes.add(btnMostrar);
        painelBotoes.add(btnSair);
        add(painelBotoes, BorderLayout.SOUTH);

        // Ações dos Botões
        btnOK.addActionListener(e -> {
            try {
                // Validação de campos obrigatórios
                if (txtNome.getText().trim().isEmpty() || txtIdade.getText().trim().isEmpty() ||
                        (!rbMasculino.isSelected() && !rbFeminino.isSelected())) {
                    throw new IllegalArgumentException("Todos os campos (Nome, Sexo e Idade) são obrigatórios!");
                }

                // Transferindo dados para o objeto
                umaPessoa.setNome(txtNome.getText());
                char sexo = rbMasculino.isSelected() ? 'M' : 'F';
                umaPessoa.setSexo(sexo);
                umaPessoa.setIdade(Integer.parseInt(txtIdade.getText()));

                // Incrementa o contador estático kp
                Pessoa.setKp();

                // Atualiza o campo Numero com o kp atual
                txtNumero.setText(String.valueOf(Pessoa.getKp()));

                JOptionPane.showMessageDialog(this, "Pessoa registrada com sucesso!");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "A idade deve ser um número inteiro válido.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });

        btnLimpar.addActionListener(e -> {
            txtNome.setText("");
            txtIdade.setText("");
            grupoSexo.clearSelection();
            txtNumero.setText("");
        });

        btnMostrar.addActionListener(e -> {
            String dados = "--- Dados da Pessoa ---\n" +
                    "Kp (Total de pessoas): " + Pessoa.getKp() + "\n" +
                    "Nome: " + umaPessoa.getNome() + "\n" +
                    "Sexo: " + umaPessoa.getSexo() + "\n" +
                    "Idade: " + umaPessoa.getIdade();
            JOptionPane.showMessageDialog(this, dados, "Mostrar Dados", JOptionPane.INFORMATION_MESSAGE);
        });

        btnSair.addActionListener(e -> System.exit(0));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FormPessoa().setVisible(true));
    }
}