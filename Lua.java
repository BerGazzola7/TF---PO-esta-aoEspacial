public class Lua extends CorpoCeleste {

    private String planetaOrbitado;
    private double periodoOrbitalDias;

    public Lua(String nome, double massaKg, double raioKm, double distanciaTerraAnosLuz,
               String planetaOrbitado, double periodoOrbitalDias) {
        super(nome, massaKg, raioKm, distanciaTerraAnosLuz);
        this.planetaOrbitado = planetaOrbitado;
        this.periodoOrbitalDias = periodoOrbitalDias;
    }

    public String getPlanetaOrbitado() {
        return planetaOrbitado;
    }

    public double getPeriodoOrbitalDias() {
        return periodoOrbitalDias;
    }

    @Override
    public String getTipo() {
        return "Lua";
    }

    @Override
    public String descrever() {
        return "Lua " + getNome() + ", orbita " + planetaOrbitado + " a cada " + periodoOrbitalDias + " dia(s).";
    }

    @Override
    public String toString() {
        return super.toString() + " [orbita=" + planetaOrbitado + ", periodo=" + periodoOrbitalDias + " d]";
    }
}
