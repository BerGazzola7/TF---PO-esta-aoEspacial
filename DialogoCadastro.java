import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DialogoCadastro extends JDialog {

    private static final String PLANETA = "Planeta";
    private static final String ESTRELA = "Estrela";
    private static final String LUA = "Lua";

    private Catalogo catalogo;

    private JComboBox<String> comboTipo;
    private JTextField campoNome;
    private JTextField campoMassa;
    private JTextField campoRaio;
    private JTextField campoDistancia;

    // Campos especificos
    private JTextField campoNumeroLuas;
    private JCheckBox checkAtmosfera;
    private JTextField campoTemperatura;
    private JTextField campoTipoEspectral;
    private JTextField campoPlanetaOrbitado;
    private JTextField campoPeriodo;

    private JPanel painelPlaneta;
    private JPanel painelEstrela;
    private JPanel painelLua;

    public DialogoCadastro(JFrame dono, Catalogo catalogo) {
        super(dono, "Cadastrar corpo celeste", true);
        this.catalogo = catalogo;
        montarJanela();
        pack();
        setLocationRelativeTo(dono);
    }

    private void montarJanela() {
        setLayout(new BorderLayout());

        // Campos comuns
        JPanel painelComum = new JPanel(new GridLayout(0, 2, 5, 5));
        comboTipo = new JComboBox<String>(new String[]{PLANETA, ESTRELA, LUA});
        campoNome = new JTextField(15);
        campoMassa = new JTextField();
        campoRaio = new JTextField();
        campoDistancia = new JTextField();
        painelComum.add(new JLabel("Tipo:"));
        painelComum.add(comboTipo);
        painelComum.add(new JLabel("Nome:"));
        painelComum.add(campoNome);
        painelComum.add(new JLabel("Massa (kg):"));
        painelComum.add(campoMassa);
        painelComum.add(new JLabel("Raio (km):"));
        painelComum.add(campoRaio);
        painelComum.add(new JLabel("Distancia da Terra (a.l.):"));
        painelComum.add(campoDistancia);
        add(painelComum, BorderLayout.NORTH);

        // Campos especificos de cada tipo: tres paineis, so um fica visivel
        painelPlaneta = new JPanel(new GridLayout(0, 2, 5, 5));
        campoNumeroLuas = new JTextField(10);
        checkAtmosfera = new JCheckBox("Tem atmosfera");
        painelPlaneta.add(new JLabel("Numero de luas:"));
        painelPlaneta.add(campoNumeroLuas);
        painelPlaneta.add(new JLabel(""));
        painelPlaneta.add(checkAtmosfera);

        painelEstrela = new JPanel(new GridLayout(0, 2, 5, 5));
        campoTemperatura = new JTextField(10);
        campoTipoEspectral = new JTextField(10);
        painelEstrela.add(new JLabel("Temperatura (K):"));
        painelEstrela.add(campoTemperatura);
        painelEstrela.add(new JLabel("Tipo espectral:"));
        painelEstrela.add(campoTipoEspectral);

        painelLua = new JPanel(new GridLayout(0, 2, 5, 5));
        campoPlanetaOrbitado = new JTextField(10);
        campoPeriodo = new JTextField(10);
        painelLua.add(new JLabel("Planeta orbitado:"));
        painelLua.add(campoPlanetaOrbitado);
        painelLua.add(new JLabel("Periodo orbital (dias):"));
        painelLua.add(campoPeriodo);

        JPanel painelEspecifico = new JPanel();
        painelEspecifico.add(painelPlaneta);
        painelEspecifico.add(painelEstrela);
        painelEspecifico.add(painelLua);
        add(painelEspecifico, BorderLayout.CENTER);
        mostrarCamposEspecificos();

        // Botoes
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton botaoSalvar = new JButton("Adicionar");
        JButton botaoCancelar = new JButton("Cancelar");
        painelBotoes.add(botaoSalvar);
        painelBotoes.add(botaoCancelar);
        add(painelBotoes, BorderLayout.SOUTH);

        comboTipo.addActionListener(e -> mostrarCamposEspecificos());
        botaoSalvar.addActionListener(e -> adicionar());
        botaoCancelar.addActionListener(e -> dispose());
    }

    private void mostrarCamposEspecificos() {
        String tipo = (String) comboTipo.getSelectedItem();
        painelPlaneta.setVisible(tipo.equals(PLANETA));
        painelEstrela.setVisible(tipo.equals(ESTRELA));
        painelLua.setVisible(tipo.equals(LUA));
        pack();
    }

    private void adicionar() {
        try {
            String nome = campoNome.getText().trim();
            if (nome.isEmpty()) {
                throw new IllegalArgumentException("Informe o nome.");
            }
            double massa = lerPositivo(campoMassa, "massa");
            double raio = lerPositivo(campoRaio, "raio");
            double distancia = lerNaoNegativo(campoDistancia, "distancia");

            String tipo = (String) comboTipo.getSelectedItem();
            CorpoCeleste corpo;
            if (tipo.equals(PLANETA)) {
                int luas = Integer.parseInt(campoNumeroLuas.getText().trim());
                if (luas < 0) {
                    throw new IllegalArgumentException("O numero de luas nao pode ser negativo.");
                }
                corpo = new Planeta(nome, massa, raio, distancia, luas, checkAtmosfera.isSelected());
            } else if (tipo.equals(ESTRELA)) {
                double temperatura = lerPositivo(campoTemperatura, "temperatura");
                String espectral = campoTipoEspectral.getText().trim();
                if (espectral.isEmpty()) {
                    throw new IllegalArgumentException("Informe o tipo espectral.");
                }
                corpo = new Estrela(nome, massa, raio, distancia, temperatura, espectral);
            } else {
                String planeta = campoPlanetaOrbitado.getText().trim();
                if (planeta.isEmpty()) {
                    throw new IllegalArgumentException("Informe o planeta orbitado.");
                }
                double periodo = lerPositivo(campoPeriodo, "periodo orbital");
                corpo = new Lua(nome, massa, raio, distancia, planeta, periodo);
            }

            catalogo.adicionar(corpo);
            dispose();
        } catch (NumberFormatException ex) {
            erro("Preencha os campos numericos com numeros validos.");
        } catch (IllegalArgumentException ex) {
            erro(ex.getMessage());
        }
    }

    // Aceita "1,5" ou "1.5"; notacao cientifica como 5.97e24 tambem funciona
    private double lerPositivo(JTextField campo, String nomeCampo) {
        double valor = Double.parseDouble(campo.getText().trim().replace(',', '.'));
        if (valor <= 0) {
            throw new IllegalArgumentException("O campo " + nomeCampo + " deve ser maior que zero.");
        }
        return valor;
    }

    private double lerNaoNegativo(JTextField campo, String nomeCampo) {
        double valor = Double.parseDouble(campo.getText().trim().replace(',', '.'));
        if (valor < 0) {
            throw new IllegalArgumentException("O campo " + nomeCampo + " nao pode ser negativo.");
        }
        return valor;
    }

    private void erro(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem, "Dados invalidos", JOptionPane.WARNING_MESSAGE);
    }
}
