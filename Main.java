public class Main {

    public static final String ARQUIVO = "corpos.txt";

    public static void main(String[] args) {
        Catalogo catalogo = ArquivoCatalogo.carregarOuCriar(ARQUIVO);
        JanelaPrincipal janela = new JanelaPrincipal(catalogo, ARQUIVO);
        janela.setVisible(true);
    }
}
