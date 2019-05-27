package Utils;

/**
 * @author Jorge Sempere Jimenez
 */
public class Constantes {

    public enum EstadosLinea {
        PRODUCCION("EN PRODUCCION", "btn-produccion"),
        PREPARADO("PREPARADO", "btn-preparado"),
        ENTREGADO("ENTREGADO", "btn-entregado"),
        ANULADO("ANULADO", "btn-anulado");
        private final String nom;
        private final String id;

        private EstadosLinea(String nom, String id) {
            this.nom = nom;
            this.id = id;
        }

        public String getNom() {
            return nom;
        }

        public String getId() {
            return id;
        }

    }

    public enum EstadosPedido {
        INCOMPLETO("INCOMPLETO"),
        TERMINADO("TERMINADO"),
        ENTREGADO("ENTREGADO"),
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
