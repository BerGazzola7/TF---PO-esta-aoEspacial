import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DialogoEstatisticas extends JDialog {

    public DialogoEstatisticas(JFrame dono, Catalogo catalogo) {
        super(dono, "Estatisticas do catalogo", true);
        setLayout(new BorderLayout());

        JPanel painel = new JPanel(new GridLayout(0, 2, 10, 8));
        painel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        painel.add(new JLabel("Total de corpos:"));
        painel.add(new JLabel(String.valueOf(catalogo.contarTotal())));
        painel.add(new JLabel("Planetas:"));
        painel.add(new JLabel(String.valueOf(catalogo.contarPlanetas())));
        painel.add(new JLabel("Estrelas:"));
        painel.add(new JLabel(String.valueOf(catalogo.contarEstrelas())));
        painel.add(new JLabel("Luas:"));
        painel.add(new JLabel(String.valueOf(catalogo.contarLuas())));
        painel.add(new JLabel("Soma das massas (kg):"));
        painel.add(new JLabel(String.format("%.4e", catalogo.somarMassas())));
        painel.add(new JLabel("Media das distancias (a.l.):"));
        painel.add(new JLabel(String.format("%.4f", catalogo.mediaDistancias())));
        add(painel, BorderLayout.CENTER);

        JPanel painelBotao = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton botaoFechar = new JButton("Fechar");
        botaoFechar.addActionListener(e -> dispose());
        painelBotao.add(botaoFechar);
        add(painelBotao, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(dono);
    }
}
