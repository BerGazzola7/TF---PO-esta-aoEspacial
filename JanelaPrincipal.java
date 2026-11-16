import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class JanelaPrincipal extends JFrame {

    private Catalogo catalogo;
    private String caminhoArquivo;

    private DefaultTableModel modelo;
    private JTable tabela;
    private JTextField campoBusca;

    // Corpos que estao sendo mostrados na tabela (todos, resultado de busca ou "mais proximos")
    private List<CorpoCeleste> exibidos;

    public JanelaPrincipal(Catalogo catalogo, String caminhoArquivo) {
        super("Estacao de Pesquisa Espacial");
        this.catalogo = catalogo;
        this.caminhoArquivo = caminhoArquivo;
        montarJanela();
        mostrarTodos();
    }

    private void montarJanela() {
        setSize(950, 480);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Topo: busca
        JPanel painelBusca = new JPanel(new FlowLayout(FlowLayout.LEFT));
        campoBusca = new JTextField(20);
        JButton botaoBuscar = new JButton("Buscar");
        JButton botaoMostrarTodos = new JButton("Mostrar todos");
        painelBusca.add(new JLabel("Nome:"));
        painelBusca.add(campoBusca);
        painelBusca.add(botaoBuscar);
        painelBusca.add(botaoMostrarTodos);
        add(painelBusca, BorderLayout.NORTH);

        // Centro: tabela
        String[] colunas = {"Tipo", "Nome", "Massa (kg)", "Raio (km)", "Distancia (a.l.)", "Gravidade (m/s2)", "Detalhes"};
        modelo = new DefaultTableModel(colunas, 0);
        tabela = new JTable(modelo);
        tabela.setDefaultEditor(Object.class, null); // celulas somente leitura
        tabela.getColumnModel().getColumn(6).setPreferredWidth(380);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        // Base: botoes
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton botaoCadastrar = new JButton("Cadastrar");
        JButton botaoRemover = new JButton("Remover");
        JButton botaoProximos = new JButton("Mais proximos da Terra");
        JButton botaoEstatisticas = new JButton("Estatisticas");
        JButton botaoSalvar = new JButton("Salvar");
        painelBotoes.add(botaoCadastrar);
        painelBotoes.add(botaoRemover);
        painelBotoes.add(botaoProximos);
        painelBotoes.add(botaoEstatisticas);
        painelBotoes.add(botaoSalvar);
        add(painelBotoes, BorderLayout.SOUTH);

        // Eventos
        botaoBuscar.addActionListener(e -> buscar());
        campoBusca.addActionListener(e -> buscar());
        botaoMostrarTodos.addActionListener(e -> {
            campoBusca.setText("");
            mostrarTodos();
        });
        botaoCadastrar.addActionListener(e -> abrirCadastro());
        botaoRemover.addActionListener(e -> removerSelecionado());
        botaoProximos.addActionListener(e -> mostrarMaisProximos());
        botaoEstatisticas.addActionListener(e -> {
            DialogoEstatisticas dialogo = new DialogoEstatisticas(this, catalogo);
            dialogo.setVisible(true);
        });
        botaoSalvar.addActionListener(e -> {
            ArquivoCatalogo.salvar(catalogo, caminhoArquivo);
            JOptionPane.showMessageDialog(this, "Catalogo salvo em " + caminhoArquivo);
        });
    }

    private void mostrarTodos() {
        exibidos = catalogo.listar();
        atualizarTabela();
    }

    private void buscar() {
        String texto = campoBusca.getText().trim();
        if (texto.isEmpty()) {
            mostrarTodos();
            return;
        }
        exibidos = catalogo.buscarPorNome(texto);
        atualizarTabela();
    }

    private void mostrarMaisProximos() {
        String resposta = JOptionPane.showInputDialog(this, "Quantos corpos mostrar?", "5");
        if (resposta == null) {
            return;
        }
        try {
            int n = Integer.parseInt(resposta.trim());
            if (n <= 0) {
                throw new NumberFormatException();
            }
            exibidos = catalogo.maisProximos(n);
            atualizarTabela();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Digite um numero inteiro maior que zero.",
                    "Valor invalido", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void abrirCadastro() {
        DialogoCadastro dialogo = new DialogoCadastro(this, catalogo);
        dialogo.setVisible(true); // modal: so volta aqui depois que o dialogo fecha
        ArquivoCatalogo.salvar(catalogo, caminhoArquivo);
        campoBusca.setText("");
        mostrarTodos();
    }

    private void removerSelecionado() {
        int linha = tabela.getSelectedRow();
        if (linha < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um corpo na tabela.");
            return;
        }
        CorpoCeleste corpo = exibidos.get(linha);
        int opcao = JOptionPane.showConfirmDialog(this, "Remover " + corpo.getNome() + "?",
                "Confirmar", JOptionPane.YES_NO_OPTION);
        if (opcao == JOptionPane.YES_OPTION) {
            catalogo.remover(corpo);
            ArquivoCatalogo.salvar(catalogo, caminhoArquivo);
            buscar();
        }
    }

    private void atualizarTabela() {
        modelo.setRowCount(0);
        for (CorpoCeleste c : exibidos) {
            Object[] linha = {
                c.getTipo(),
                c.getNome(),
                String.format("%.3e", c.getMassaKg()),
                String.format("%.1f", c.getRaioKm()),
                String.format("%.3e", c.getDistanciaTerraAnosLuz()),
                String.format("%.2f", c.calcularGravidade()),
                c.descrever()
            };
            modelo.addRow(linha);
        }
    }
}
