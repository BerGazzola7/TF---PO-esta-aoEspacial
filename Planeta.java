public class Planeta extends CorpoCeleste {

    private int numeroDeLuas;
    private boolean temAtmosfera;

    public Planeta(String nome, double massaKg, double raioKm, double distanciaTerraAnosLuz,
                   int numeroDeLuas, boolean temAtmosfera) {
        super(nome, massaKg, raioKm, distanciaTerraAnosLuz);
        this.numeroDeLuas = numeroDeLuas;
        this.temAtmosfera = temAtmosfera;
    }

    public int getNumeroDeLuas() {
        return numeroDeLuas;
    }

    public boolean isTemAtmosfera() {
        return temAtmosfera;
    }

    @Override
    public String getTipo() {
        return "Planeta";
    }

    @Override
    public String descrever() {
        String atmosfera = temAtmosfera ? "com atmosfera" : "sem atmosfera";
        return "Planeta " + getNome() + ", " + numeroDeLuas + " lua(s), " + atmosfera + ".";
    }

    @Override
    public String toString() {
        return super.toString() + " [luas=" + numeroDeLuas + ", atmosfera=" + temAtmosfera + "]";
    }
}
