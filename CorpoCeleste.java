public abstract class CorpoCeleste {

    private static final double CONSTANTE_GRAVITACIONAL = 6.674e-11;

    private String nome;
    private double massaKg;
    private double raioKm;
    private double distanciaTerraAnosLuz;

    public CorpoCeleste(String nome, double massaKg, double raioKm, double distanciaTerraAnosLuz) {
        this.nome = nome;
        this.massaKg = massaKg;
        this.raioKm = raioKm;
        this.distanciaTerraAnosLuz = distanciaTerraAnosLuz;
    }

    public String getNome() {
        return nome;
    }

    public double getMassaKg() {
        return massaKg;
    }

    public double getRaioKm() {
        return raioKm;
    }

    public double getDistanciaTerraAnosLuz() {
        return distanciaTerraAnosLuz;
    }

    public abstract String descrever();

    public abstract String getTipo();

    // Gravidade na superficie em m/s2: g = G * M / R^2 (raio convertido para metros)
    public double calcularGravidade() {
        double raioMetros = raioKm * 1000.0;
        return CONSTANTE_GRAVITACIONAL * massaKg / (raioMetros * raioMetros);
    }

    @Override
    public String toString() {
        return getTipo() + " " + nome + " (massa=" + massaKg + " kg, raio=" + raioKm + " km, distancia=" + distanciaTerraAnosLuz + " a.l.)";
    }
}
