package Hibernate;

/**
 * @author Jorge Sempere Jimenez
 */
public enum SentenciasSQL {
    REFRESCO_LOCALES("SELECT d.Nombre, d.Numero, cp.Cod_Postal, c.Cod_Ciudad, p.Cod_Prov "
            + "FROM DIRECCION d INNER JOIN CODIGO_POSTAL cp INNER JOIN CIUDAD_CONCP cc "
            + "INNER JOIN CIUDAD c INNER JOIN PROVINCIA p "
            + "ON d.Cod_Postal = cp.Cod_Postal AND cp.Cod_Postal = cc.Cod_Postal "
            + "AND cc.Cod_Ciudad = c.Cod_Ciudad AND c.Cod_Prov = p.Cod_Prov WHERE d.Cod_Direc = ?;");
    private final String sentencia;

    private SentenciasSQL(String sentencia) {
        this.sentencia = sentencia;
    }

    public String getSentencia() {
        return sentencia;
    }
    
}//fin de la clase
