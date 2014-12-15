package entitiescontrollers;

import java.util.List;

public class Notaciones {
    public static String Entity = "@Entity\n";
    public static String XmlRootElement = "@XmlRootElement\n";
    public static String Id = "\t@Id\n";
    public static String Lob = "\t@Lob\n";
    public static String Temporal = "\t@Temporal(TemporalType.TIMESTAMP)\n";
    
    private String tabla;
    private List<Campo> campos;
    public Notaciones(String tabla,List<Campo> campos){
        this.tabla = tabla;
        this.campos = campos;
    }
    
    public String getNotacionesdeClase(){
        StringBuilder notacionesClase = new StringBuilder();
        notacionesClase.append(Notaciones.Entity);
        notacionesClase.append(notacionTabla());
        notacionesClase.append(Notaciones.XmlRootElement);
        notacionesClase.append(notacionesCampos());
        return notacionesClase.toString();
    }
    
    public String notacionTabla(){
        return "@Table(name = \""+this.tabla+"\")\n";
    }
    
    public String notacionesCampos(){
        String tablaCamelCase = tablaCamelCase();
        StringBuilder campoString = new StringBuilder();
        campoString.append("@NamedQueries({\n");
        campoString.append("\t@NamedQuery(name = \"");
        campoString.append(tablaCamelCase);
        campoString.append(".findAll\", query = \"SELECT t FROM ");
        campoString.append(tablaCamelCase);
        campoString.append(" t\"),\n");
        String strCampo = "";
        for(Campo campo:campos){
            if (!campo.isLob()) {
                strCampo += "\t@NamedQuery(name = \"";
                strCampo += tablaCamelCase;
                strCampo += ".findBy";
                strCampo += campo.getNombre();
                strCampo += "\", query = \"SELECT t FROM ";
                strCampo += tablaCamelCase;
                strCampo += " t WHERE t.";
                strCampo += campo.getNombre().toLowerCase();
                strCampo += " = :";
                strCampo += campo.getNombre().toLowerCase();
                strCampo += "),\n";
            }
        }
        campoString.append(strCampo.substring(0,strCampo.length()-2));
        campoString.append("})\n");
        return campoString.toString();
    }
    
    public String tablaCamelCase(){
        StringBuilder tablaCamelCase = new StringBuilder();
        String palabras[] = this.tabla.split("_");
        for(String palabra:palabras){
            tablaCamelCase.append(palabra.substring(0,1)).append(palabra.substring(1).toLowerCase());
        }
        return tablaCamelCase.toString();
    }
    
    public static String getBasicNotation(boolean optional){
        return "\t@Basic(optional = "+optional+")\n";
    }
    
    public static String getNotationColumna(String campo){
        return "\t@Column(name = \""+campo+"\")\n";
    }
}