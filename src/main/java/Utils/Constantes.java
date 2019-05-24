package Utils;

/**
 * @author Jorge Sempere Jimenez
 */
public class Constantes {

    public enum EstadosLinea {
        PRODUCCION("EN PRODUCCION"),
        PREPARADO("PREPARADO"),
        ENTREGADO("ENTREGADO"),
        ANULADO("ANULADO");
        private final String nom;

        private EstadosLinea(String nom) {
            this.nom = nom;
        }

        public String getNom() {
            return nom;
        }

    }

    public enum EstadosPedido {
        INCOMPLETO("INCOMPLETO"),
        TERMINADO("TERMINADO"),
        ANULADO("ANULADO");
        private final String nom;

        private EstadosPedido(String nom) {
            this.nom = nom;
        }

        public String getNom() {
            return nom;
        }

    }
}//fin de clase
