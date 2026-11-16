public class DadosIniciais {

    // Valores aproximados. Distancias medias aproximadas a Terra, em anos-luz.
    public static void popular(Catalogo catalogo) {
        catalogo.adicionar(new Estrela("Sol", 1.989e30, 695700, 0.0000158, 5772, "G2V"));
        catalogo.adicionar(new Estrela("Sirius A", 4.02e30, 1190000, 8.6, 9940, "A1V"));
        catalogo.adicionar(new Estrela("Proxima Centauri", 2.43e29, 107300, 4.24, 3042, "M5.5Ve"));

        catalogo.adicionar(new Planeta("Terra", 5.972e24, 6371, 0.0, 1, true));
        catalogo.adicionar(new Planeta("Venus", 4.867e24, 6052, 0.000017, 0, true));
        catalogo.adicionar(new Planeta("Marte", 6.417e23, 3390, 0.000028, 2, true));
        catalogo.adicionar(new Planeta("Jupiter", 1.898e27, 69911, 0.000082, 95, true));

        catalogo.adicionar(new Lua("Lua", 7.342e22, 1737, 0.00000004, "Terra", 27.3));
        catalogo.adicionar(new Lua("Europa", 4.8e22, 1561, 0.000082, "Jupiter", 3.55));
    }
}
