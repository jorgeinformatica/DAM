package Utils;

/**
 * @author Jorge Sempere Jimenez
 */
public class Constantes {

    public enum Estados {
        PREPARADOPARCIAL("PARCIAL", "btn-preparado-parcial"),
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
        ESTADO(" elemento.estado = 1");
        private final String condicion;

        private HQLCondicion(String condicion) {
            this.condicion = condicion;
        }

        public String getCondicion() {
            return condicion;
        }
    }

    public enum HQLSentencia {
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

    public enum CSSId {
        TABLEVIEWID("dashboard-table");
        private final String id;

        private CSSId(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }

    }

    public enum Qlik {
        LOCALES("http://localhost:4848/single/?appid=D%3A%5CqlikDocuments%5CSense%5CApps%5CAn%C3%A1lisis.qvf&sheet=e3644f10-c61a-4059-a7a5-d5aeaa0e7c4d&opt=currsel%2Cctxmenu&select=clearall"),
        PRODUCTOS("http://localhost:4848/single/?appid=D%3A%5CqlikDocuments%5CSense%5CApps%5CAn%C3%A1lisis.qvf&sheet=5033ea1c-618f-46cb-8239-bb1e949f8326&opt=currsel%2Cctxmenu&select=clearalle"),
        REPORTING("http://localhost:4848/single/?appid=D%3A%5CqlikDocuments%5CSense%5CApps%5CAn%C3%A1lisis.qvf&sheet=809cc475-21e3-4469-bd21-382efb4f9abd&opt=currsel%2Cctxmenu&select=clearall");
        private final String url;

        private Qlik(String url) {
            this.url = url;
        }

        public String getUrl() {
            return url;
        }

    }
}//fin de clase
