import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

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

    // Devolve os corpos que satisfazem o predicado (expressao lambda)
    public List<CorpoCeleste> filtrar(Predicate<CorpoCeleste> condicao) {
        List<CorpoCeleste> resultado = new ArrayList<CorpoCeleste>();
        for (CorpoCeleste c : corpos) {
            if (condicao.test(c)) {
                resultado.add(c);
            }
        }
        return resultado;
    }

    // Busca por nome (contem), sem diferenciar maiusculas de minusculas
    public List<CorpoCeleste> buscarPorNome(String trecho) {
        String busca = trecho.toLowerCase();
        return filtrar(c -> c.getNome().toLowerCase().contains(busca));
    }

    public int contarTotal() {
        return corpos.size();
    }

    public int contarPlanetas() {
        return filtrar(c -> c instanceof Planeta).size();
    }

    public int contarEstrelas() {
        return filtrar(c -> c instanceof Estrela).size();
    }

    public int contarLuas() {
        return filtrar(c -> c instanceof Lua).size();
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
        Collections.sort(copia, (a, b) -> Double.compare(a.getDistanciaTerraAnosLuz(), b.getDistanciaTerraAnosLuz()));
        List<CorpoCeleste> resultado = new ArrayList<CorpoCeleste>();
        for (int i = 0; i < n && i < copia.size(); i++) {
            resultado.add(copia.get(i));
        }
        return resultado;
    }
}
