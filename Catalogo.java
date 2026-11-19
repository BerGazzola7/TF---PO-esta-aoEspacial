import java.util.ArrayList;
import java.util.List;

public class Catalogo {

    private ArrayList<CorpoCeleste> corpos;

    public Catalogo() {
        corpos = new ArrayList<CorpoCeleste>();
    }

    public void adicionar(CorpoCeleste corpo) {
        corpos.add(corpo);
    }

    public void remover(CorpoCeleste corpo) {
        corpos.remove(corpo);
    }

    public List<CorpoCeleste> listar() {
        return new ArrayList<CorpoCeleste>(corpos);
    }

    // Busca por nome (contem), sem diferenciar maiusculas de minusculas
    public List<CorpoCeleste> buscarPorNome(String trecho) {
        String busca = trecho.toLowerCase();
        List<CorpoCeleste> resultado = new ArrayList<CorpoCeleste>();
        for (CorpoCeleste c : corpos) {
            if (c.getNome().toLowerCase().contains(busca)) {
                resultado.add(c);
            }
        }
        return resultado;
    }

    public int contarTotal() {
        return corpos.size();
    }

    public int contarPlanetas() {
        int cont = 0;
        for (CorpoCeleste c : corpos) {
            if (c instanceof Planeta) {
                cont++;
            }
        }
        return cont;
    }

    public int contarEstrelas() {
        int cont = 0;
        for (CorpoCeleste c : corpos) {
            if (c instanceof Estrela) {
                cont++;
            }
        }
        return cont;
    }

    public int contarLuas() {
        int cont = 0;
        for (CorpoCeleste c : corpos) {
            if (c instanceof Lua) {
                cont++;
            }
        }
        return cont;
    }

    public double somarMassas() {
        double soma = 0;
        for (CorpoCeleste c : corpos) {
            soma += c.getMassaKg();
        }
        return soma;
    }

    public double mediaDistancias() {
        if (corpos.isEmpty()) {
            return 0.0;
        }
        double soma = 0;
        for (CorpoCeleste c : corpos) {
            soma += c.getDistanciaTerraAnosLuz();
        }
        return soma / corpos.size();
    }

    // Os n corpos mais proximos da Terra, do mais perto para o mais longe
    public List<CorpoCeleste> maisProximos(int n) {
        ArrayList<CorpoCeleste> copia = new ArrayList<CorpoCeleste>(corpos);

        // Ordena a copia pela distancia usando Bubble Sort
        for (int i = 0; i < copia.size() - 1; i++) {
            for (int j = 0; j < copia.size() - 1 - i; j++) {
                CorpoCeleste atual = copia.get(j);
                CorpoCeleste proximo = copia.get(j + 1);
                if (atual.getDistanciaTerraAnosLuz() > proximo.getDistanciaTerraAnosLuz()) {
                    copia.set(j, proximo);
                    copia.set(j + 1, atual);
                }
            }
        }

        List<CorpoCeleste> resultado = new ArrayList<CorpoCeleste>();
        for (int i = 0; i < n && i < copia.size(); i++) {
            resultado.add(copia.get(i));
        }
        return resultado;
    }
}
