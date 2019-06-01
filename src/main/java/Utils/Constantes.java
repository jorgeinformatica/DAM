package Utils;

/**
 * @author Jorge Sempere Jimenez
 */
public class Constantes {

    public enum Estados {
        ENPRODUCCION("INCOMPLETO", "btn-produccion"),
        PREPARADO("PREPARADO", "btn-preparado"),
        ENTREGADO("ENTREGADO", "btn-entregado"),
        ANULADO("ANULADO", "btn-anulado");
        private final String nom;
        private final String id;

        private Estados(String nom, String id) {
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

    public enum HQLCondicion {
        NEUTRO(" 1 = 1 "),
        PEDIDO(" elemento.local.estado = 1 "),
        CENTRALREF(" elemento.ciudad.codCiudad = 265 AND elemento.codigoPostal.codPostal= '03802' "),
        ESTADO(" elemento.estado = 1" );
        private final String condicion;

        private HQLCondicion(String condicion) {
            this.condicion = condicion;
        }

        public String getCondicion() {
            return condicion;
        }
    }

    public enum HQLSentencia{
        PRODUCTOTOTAL("SELECT sum(LP.cantidad) AS total "
                + "FROM LineaPedido LP join LP.producto PRO WHERE PRO.codProd != :id"),
        PRODUCTOSUBTOTAL("SELECT sum(LP.cantidad) AS total "
                + "FROM LineaPedido LP join LP.producto PRO WHERE PRO.codProd = :id"),
        PRODUCTOLOCALES("SELECT sum(LP.cantidad) AS cantidad, L.nombre AS nombre "
                + "FROM LineaPedido LP join LP.pedido P join P.local L join LP.producto PRO "
                + "WHERE PRO.codProd = :id GROUP BY L.nombre"),
        PRODUCTOEVOLUTIVO("SELECT sum(LP.cantidad) AS cantidad, P.fechaEntrega AS fecha "
                + "FROM LineaPedido LP join LP.pedido P join LP.producto PRO "
                + "WHERE PRO.codProd = :id GROUP BY P.fechaEntrega");
        private final String sentencia;

        private HQLSentencia(String sentencia) {
            this.sentencia = sentencia;
        }

        public String getSentencia() {
            return sentencia;
        }
    }
    
}//fin de clase
