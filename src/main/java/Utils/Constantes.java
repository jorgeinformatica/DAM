package Utils;

/**
 * @author Jorge Sempere Jimenez
 */
public class Constantes {

    public enum EstadoLinea {
        PRODUCCION("EN PRODUCCION", "btn-produccion"),
        PREPARADO("PREPARADO", "btn-preparado"),
        ENTREGADO("ENTREGADO", "btn-entregado"),
        ANULADO("ANULADO", "btn-anulado");
        private final String nom;
        private final String id;

        private EstadoLinea(String nom, String id) {
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

    public enum EstadoPedido {
        INCOMPLETO("INCOMPLETO"),
        TERMINADO("TERMINADO"),
        ENTREGADO("ENTREGADO"),
        ANULADO("ANULADO");
        private final String nom;

        private EstadoPedido(String nom) {
            this.nom = nom;
        }

        public String getNom() {
            return nom;
        }

    }

    public enum HQLCondicion {
        NEUTRO("1=1"),
        FECHAENTREGAPEDIDO(" WHERE Cod_local= :elem0 AND Fecha_Entrega like :elem1 ");
        private String sentencia;

        private HQLCondicion(String sentencia) {
            this.sentencia = sentencia;
        }

        public String getSentencia() {
            return sentencia;
        }

    }

}//fin de clase
