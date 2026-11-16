public class Estrela extends CorpoCeleste {

    private double temperaturaK;
    private String tipoEspectral;

    public Estrela(String nome, double massaKg, double raioKm, double distanciaTerraAnosLuz,
                   double temperaturaK, String tipoEspectral) {
        super(nome, massaKg, raioKm, distanciaTerraAnosLuz);
        this.temperaturaK = temperaturaK;
        this.tipoEspectral = tipoEspectral;
    }

    public double getTemperaturaK() {
        return temperaturaK;
    }

    public String getTipoEspectral() {
        return tipoEspectral;
    }

    @Override
    public String getTipo() {
        return "Estrela";
    }

    @Override
    public String descrever() {
        return "Estrela " + getNome() + ", tipo " + tipoEspectral + ", " + temperaturaK + " K.";
    }

    @Override
    public String toString() {
        return super.toString() + " [temperatura=" + temperaturaK + " K, tipo=" + tipoEspectral + "]";
    }
}
