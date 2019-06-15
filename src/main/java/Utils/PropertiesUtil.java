package Utils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

/**
 * @author Jorge Sempere Jimenez
 */
public class PropertiesUtil {

    private final String FICHERO = "config.properties";
    private static PropertiesUtil propUtil;
    private final Properties prop;

    public static PropertiesUtil getPropUtil() {
        if (propUtil == null) {
            propUtil = new PropertiesUtil();
        }
        return propUtil;
    }

    private PropertiesUtil() {
        prop = new Properties();
    }
/**
 * @return HashMap devuelve un map con las configuraciones de la aplicación.
 */
    public HashMap getStatus() {
        HashMap map = new HashMap();
        Enumeration<Object> keys;
        try {
            prop.load(getClass().getClassLoader().getResourceAsStream(FICHERO));
            if (!prop.isEmpty()) {
                keys = prop.keys();
                while (keys.hasMoreElements()) {
                    Object key = keys.nextElement();
                    map.put(key, prop.get(key));
                }
            }
        } catch (IOException ex) {
            System.err.println("FICHERO " + FICHERO + " NO ENCONTRADO.");
            return null;
        }
        return map;
    }
/**
 * @param map El map con los parametros de configuracion de la aplicación
 */
    public void setStatus(HashMap map) {
        try {
            map.forEach((k, v) -> prop.setProperty((String) k, (String) v));
            prop.store(new FileWriter(FICHERO), null);
        } catch (IOException ex) {
            System.err.println("FICHERO " + FICHERO + " NO ENCONTRADO.");
        }
    }

}//fin de clase 
