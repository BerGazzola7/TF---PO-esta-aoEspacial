import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

// Formato de cada linha:
// PLANETA;nome;massa;raio;distancia;numeroDeLuas;temAtmosfera
// ESTRELA;nome;massa;raio;distancia;temperaturaK;tipoEspectral
// LUA;nome;massa;raio;distancia;planetaOrbitado;periodoOrbitalDias
public class ArquivoCatalogo {

    public static void salvar(Catalogo catalogo, String caminho) {
        BufferedWriter escritor = null;
        try {
            escritor = new BufferedWriter(new FileWriter(caminho));
            for (CorpoCeleste c : catalogo.listar()) {
                escritor.write(paraLinha(c));
                escritor.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar o arquivo: " + e.getMessage());
        } finally {
            if (escritor != null) {
                try {
                    escritor.close();
                } catch (IOException e) {
                    System.out.println("Erro ao fechar o arquivo: " + e.getMessage());
                }
            }
        }
    }

    public static Catalogo carregar(String caminho) {
        Catalogo catalogo = new Catalogo();
        BufferedReader leitor = null;
        try {
            leitor = new BufferedReader(new FileReader(caminho));
            String linha = leitor.readLine();
            while (linha != null) {
                if (!linha.trim().isEmpty()) {
                    CorpoCeleste corpo = deLinha(linha);
                    if (corpo != null) {
                        catalogo.adicionar(corpo);
                    }
                }
                linha = leitor.readLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        } finally {
            if (leitor != null) {
                try {
                    leitor.close();
                } catch (IOException e) {
                    System.out.println("Erro ao fechar o arquivo: " + e.getMessage());
                }
            }
        }
        return catalogo;
    }

    // Se o arquivo nao existir, cria com os dados de exemplo e o salva
    public static Catalogo carregarOuCriar(String caminho) {
        File arquivo = new File(caminho);
        if (!arquivo.exists()) {
            Catalogo inicial = new Catalogo();
            DadosIniciais.popular(inicial);
            salvar(inicial, caminho);
            return inicial;
        }
        return carregar(caminho);
    }

    private static String paraLinha(CorpoCeleste c) {
        String base = c.getNome() + ";" + c.getMassaKg() + ";" + c.getRaioKm() + ";"
                + c.getDistanciaTerraAnosLuz() + ";";
        if (c instanceof Planeta) {
            Planeta p = (Planeta) c;
            return "PLANETA;" + base + p.getNumeroDeLuas() + ";" + p.isTemAtmosfera();
        } else if (c instanceof Estrela) {
            Estrela e = (Estrela) c;
            return "ESTRELA;" + base + e.getTemperaturaK() + ";" + e.getTipoEspectral();
        } else {
            Lua l = (Lua) c;
            return "LUA;" + base + l.getPlanetaOrbitado() + ";" + l.getPeriodoOrbitalDias();
        }
    }

    private static CorpoCeleste deLinha(String linha) {
        String[] p = linha.split(";");
        try {
            String tipo = p[0];
            String nome = p[1];
            double massa = Double.parseDouble(p[2]);
            double raio = Double.parseDouble(p[3]);
            double distancia = Double.parseDouble(p[4]);
            if (tipo.equals("PLANETA")) {
                return new Planeta(nome, massa, raio, distancia, Integer.parseInt(p[5]), Boolean.parseBoolean(p[6]));
            } else if (tipo.equals("ESTRELA")) {
                return new Estrela(nome, massa, raio, distancia, Double.parseDouble(p[5]), p[6]);
            } else if (tipo.equals("LUA")) {
                return new Lua(nome, massa, raio, distancia, p[5], Double.parseDouble(p[6]));
            }
        } catch (NumberFormatException e) {
            System.out.println("Linha ignorada (numero invalido): " + linha);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Linha ignorada (campos faltando): " + linha);
        }
        return null;
    }
}
