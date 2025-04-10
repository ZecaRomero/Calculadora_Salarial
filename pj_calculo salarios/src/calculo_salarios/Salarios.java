package calculo_salarios;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class Salarios extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextField txtSalario, txtDesconto;
    private JLabel lblLiquido, lblAnual, lblDecimo, lblFerias;
    private JButton btnCalcular, btnLimpar;
    private JPanel contentPane;

    private Timer fadeInTimer;
    private float opacity = 0f;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Salarios frame = new Salarios();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Salarios() {
        setTitle("Calculadora Salarial - Zeca Romero");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 750, 680);
        setUndecorated(true); // Necessário para usar opacidade
        setOpacity(opacity);

        // Fade-in
        fadeInTimer = new Timer(20, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                opacity += 0.05f;
                if (opacity >= 1f) {
                    opacity = 1f;
                    fadeInTimer.stop();
                }
                setOpacity(opacity);
            }
        });
        fadeInTimer.start();

        // Tema escuro
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            UIManager.put("Panel.background", new Color(45, 45, 45));
            UIManager.put("Label.foreground", Color.WHITE);
            UIManager.put("TextField.background", new Color(60, 60, 60));
            UIManager.put("TextField.foreground", Color.WHITE);
            UIManager.put("TextField.caretForeground", Color.WHITE);
            UIManager.put("Button.background", new Color(70, 70, 70));
            UIManager.put("Button.foreground", Color.BLUE);
        } catch (Exception e) {
            e.printStackTrace();
        }

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentPane.setBackground(new Color(45, 45, 45));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        createHeader();
        createInputPanel();
        createButtonPanel();
        createResultPanel();
        createFooter();
        setupInteractions();
    }

    private void createHeader() {
        JPanel panelHeader = new JPanel();
        panelHeader.setBounds(10, 10, 714, 60);
        panelHeader.setBackground(new Color(0, 70, 120));
        panelHeader.setBorder(new LineBorder(new Color(0, 40, 80), 2));
        panelHeader.setLayout(null);

        JLabel lblTitulo = new JLabel("Zeca - Criação de Software");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBounds(0, 0, 714, 30);
        panelHeader.add(lblTitulo);

        JLabel lblSubtitulo = new JLabel("Calculadora Salarial Completa");
        lblSubtitulo.setForeground(new Color(200, 230, 255));
        lblSubtitulo.setFont(new Font("Arial", Font.ITALIC, 14));
        lblSubtitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblSubtitulo.setBounds(0, 30, 714, 25);
        panelHeader.add(lblSubtitulo);

        contentPane.add(panelHeader);
    }

    private void createInputPanel() {
        JPanel panelEntrada = new JPanel();
        panelEntrada.setBounds(10, 80, 714, 120);
        panelEntrada.setBackground(new Color(60, 60, 60));
        panelEntrada.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(0, 100, 180)),
                "Dados de Entrada",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 12),
                new Color(200, 230, 255)
        ));
        panelEntrada.setLayout(null);

        JLabel lblSalario = new JLabel("Salário Bruto (R$):");
        lblSalario.setForeground(Color.WHITE);
        lblSalario.setFont(new Font("Arial", Font.BOLD, 14));
        lblSalario.setBounds(20, 25, 150, 25);
        panelEntrada.add(lblSalario);

        txtSalario = new JTextField("Ex: 2500.00");
        txtSalario.setFont(new Font("Arial", Font.PLAIN, 14));
        txtSalario.setBounds(170, 25, 150, 25);
        txtSalario.setForeground(Color.GRAY);
        txtSalario.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (txtSalario.getText().equals("Ex: 2500.00")) {
                    txtSalario.setText("");
                    txtSalario.setForeground(Color.WHITE);
                }
            }

            public void focusLost(FocusEvent e) {
                if (txtSalario.getText().isEmpty()) {
                    txtSalario.setForeground(Color.GRAY);
                    txtSalario.setText("Ex: 2500.00");
                }
            }
        });
        panelEntrada.add(txtSalario);

        JLabel lblDesconto = new JLabel("Desconto (%):");
        lblDesconto.setForeground(Color.WHITE);
        lblDesconto.setFont(new Font("Arial", Font.BOLD, 14));
        lblDesconto.setBounds(340, 25, 120, 25);
        panelEntrada.add(lblDesconto);

        txtDesconto = new JTextField("Ex: 15");
        txtDesconto.setFont(new Font("Arial", Font.PLAIN, 14));
        txtDesconto.setBounds(460, 25, 80, 25);
        txtDesconto.setForeground(Color.GRAY);
        txtDesconto.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (txtDesconto.getText().equals("Ex: 15")) {
                    txtDesconto.setText("");
                    txtDesconto.setForeground(Color.WHITE);
                }
            }

            public void focusLost(FocusEvent e) {
                if (txtDesconto.getText().isEmpty()) {
                    txtDesconto.setForeground(Color.GRAY);
                    txtDesconto.setText("Ex: 15");
                }
            }
        });
        panelEntrada.add(txtDesconto);

        contentPane.add(panelEntrada);
    }

    private void createButtonPanel() {
        JPanel panelBotoes = new JPanel();
        panelBotoes.setBounds(10, 210, 714, 60);
        panelBotoes.setBackground(new Color(45, 45, 45));
        panelBotoes.setLayout(new GridLayout(1, 2, 20, 0));

        btnCalcular = new JButton("CALCULAR");
        btnCalcular.setFont(new Font("Arial", Font.BOLD, 14));
        btnCalcular.setBackground(new Color(0, 120, 60));
        btnCalcular.setForeground(Color.BLUE);
        btnCalcular.setFocusPainted(false);
        btnCalcular.addActionListener(this::calcularSalario);
        panelBotoes.add(btnCalcular);

        btnLimpar = new JButton("LIMPAR");
        btnLimpar.setFont(new Font("Arial", Font.BOLD, 14));
        btnLimpar.setBackground(new Color(120, 0, 0));
        btnLimpar.setForeground(Color.RED);
        btnLimpar.setFocusPainted(false);
        btnLimpar.addActionListener(this::limparCampos);
        panelBotoes.add(btnLimpar);

        contentPane.add(panelBotoes);
    }

    private void createResultPanel() {
        JPanel panelResultados = new JPanel();
        panelResultados.setBounds(10, 280, 714, 290);
        panelResultados.setBackground(new Color(60, 60, 60));
        panelResultados.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(0, 100, 180)),
                "Resultados",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 12),
                new Color(200, 230, 255)
        ));
        panelResultados.setLayout(null);

        JPanel panelMensal = new JPanel();
        panelMensal.setBounds(20, 30, 674, 80);
        panelMensal.setBackground(new Color(70, 70, 70));
        panelMensal.setBorder(BorderFactory.createTitledBorder("Mensal"));
        panelResultados.add(panelMensal);
        panelMensal.setLayout(new GridLayout(1, 1, 10, 0));

        lblLiquido = criarLabelResultado("Salário Líquido:");
        panelMensal.add(lblLiquido);

        JPanel panelAnual = new JPanel();
        panelAnual.setBounds(20, 120, 674, 140);
        panelAnual.setBackground(new Color(70, 70, 70));
        panelAnual.setBorder(BorderFactory.createTitledBorder("Anual"));
        panelResultados.add(panelAnual);
        panelAnual.setLayout(new GridLayout(3, 1, 0, 10));

        lblAnual = criarLabelResultado("Salário Anual:");
        panelAnual.add(lblAnual);

        lblDecimo = criarLabelResultado("13º Salário:");
        panelAnual.add(lblDecimo);

        lblFerias = criarLabelResultado("Férias + 1/3:");
        panelAnual.add(lblFerias);

        contentPane.add(panelResultados);
    }

    private JLabel criarLabelResultado(String texto) {
        JLabel label = new JLabel(texto);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100)));
        label.setOpaque(true);
        label.setBackground(new Color(80, 80, 80));
        return label;
    }

    private void createFooter() {
        JLabel lblRodape = new JLabel("Desenvolvido por Zeca Romero - Abril/2025");
        lblRodape.setFont(new Font("Arial", Font.ITALIC, 12));
        lblRodape.setForeground(new Color(150, 200, 255));
        lblRodape.setHorizontalAlignment(SwingConstants.CENTER);
        lblRodape.setBounds(10, 590, 714, 20);
        contentPane.add(lblRodape);
    }

    private void setupInteractions() {
        txtSalario.addActionListener(this::calcularSalario);
        txtDesconto.addActionListener(this::calcularSalario);
    }

    private void calcularSalario(ActionEvent e) {
        try {
            String salarioText = txtSalario.getText().replace(",", ".").replace("Ex:", "").trim();
            String descontoText = txtDesconto.getText().replace(",", ".").replace("Ex:", "").trim();

            double salario = Double.parseDouble(salarioText);
            double desconto = Double.parseDouble(descontoText);

            if (salario <= 0 || desconto < 0 || desconto > 100) throw new NumberFormatException();

            double salarioLiquido = salario - (salario * desconto / 100);
            double salarioAnual = salarioLiquido * 12;
            double decimoTerceiro = salario;
            double ferias = salario + (salario / 3);

            Toolkit.getDefaultToolkit().beep(); // som de sucesso
            lblLiquido.setText(String.format("<html><b>Salário Líquido:</b><br><font size='5' color='#4CAF50'>R$ %.2f</font></html>", salarioLiquido));
            lblAnual.setText(String.format("<html><b>Salário Anual:</b><br><font size='5' color='#2196F3'>R$ %.2f</font></html>", salarioAnual));
            lblDecimo.setText(String.format("<html><b>13º Salário:</b><br><font size='5' color='#2196F3'>R$ %.2f</font></html>", decimoTerceiro));
            lblFerias.setText(String.format("<html><b>Férias + 1/3:</b><br><font size='5' color='#2196F3'>R$ %.2f</font></html>", ferias));

            
            UIManager.put("OptionPane.background", new Color(45, 45, 45));
            UIManager.put("Panel.background", new Color(45, 45, 45));
            UIManager.put("OptionPane.messageForeground", Color.WHITE);

            JLabel msg = new JLabel("<html><div style='color:white;font-size:12pt;'>✅ Cálculo realizado com sucesso!</div></html>");
            JOptionPane.showMessageDialog(this, msg, "Sucesso", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            Toolkit.getDefaultToolkit().beep(); // som de erro
            JOptionPane.showMessageDialog(this,
                    "⚠️ Por favor, digite valores numéricos válidos:\n"
                            + "- Salário deve ser maior que zero\n"
                            + "- Desconto deve estar entre 0 e 100%",
                    "Erro de Entrada",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCampos(ActionEvent e) {
        txtSalario.setText("Ex: 2500.00");
        txtSalario.setForeground(Color.GRAY);
        txtDesconto.setText("Ex: 15");
        txtDesconto.setForeground(Color.RED);
        lblLiquido.setText("Salário Líquido:");
        lblAnual.setText("Salário Anual:");
        lblDecimo.setText("13º Salário:");
        lblFerias.setText("Férias + 1/3:");
        txtSalario.requestFocus();
    }
}
