//
package entitiescontrollers;

import java.math.BigDecimal;

public class Campo {
    private String nombre;
    private String nombreSinFormato;
    private String tipoDatoBase;
    private String tipoDatoJava;
    private BigDecimal precision;
    private boolean permiteNulos;
    private String tablaForanea;
    private boolean esPrimario;
    private boolean esFecha;

    public Campo(Object nombre,
            Object tipoDatoBase,
            Object precision,
            Object permiteNulos,
            Object tablaForanea,
            Object esPrimario) {
        this.nombreSinFormato = (String) nombre;
        this.nombre = toCasiCamelCase((String) nombre);
        this.tipoDatoBase = (String) tipoDatoBase;
        this.precision = (BigDecimal) precision;
        this.permiteNulos = ((String)permiteNulos).equals("Y");
        this.tablaForanea = (String) tablaForanea;
        this.esPrimario = esPrimario != null;
        this.esFecha = this.tipoDatoBase.contains("DATE") || this.tipoDatoBase.contains("TIMESTAMP");
        this.tipoDatoJava = getJavaDataType();
    }
    
    public String getNombre(){
        return this.nombre;
    }
    public String getTipoDatoBase(){
        return this.tipoDatoBase;
    }
    public BigDecimal getPrecision(){
        return this.precision;
    }
    public boolean getPermiteNulos(){
        return this.permiteNulos;
    }
    public String getTablaForanea(){
        return this.tablaForanea;
    }
    public boolean isLob(){
        return this.tipoDatoBase.contains("LOB");
    }
    public boolean isPrimary(){
        return this.esPrimario;
    }
    public boolean permiteNulos(){
        return this.permiteNulos;
    }
    public boolean esForanea(){
        return this.tablaForanea!=null;
    }
    public String getNotaciones(){
        StringBuilder getNotaciones = new StringBuilder();
        if(esPrimario){
            getNotaciones.append(Notaciones.Id);
        }
        if(!permiteNulos&&!esForanea()){
            getNotaciones.append(Notaciones.getBasicNotation(permiteNulos));
        }
        if(isLob()){
            getNotaciones.append(Notaciones.Lob);
        }
        if(!esForanea()){
            getNotaciones.append(Notaciones.getNotationColumna(nombreSinFormato));
        }else{
            getNotaciones.append(Notaciones.getNotationJoinColumn(nombreSinFormato));
            getNotaciones.append(Notaciones.ManyToOne);
        }
        if(esFecha){
            getNotaciones.append(Notaciones.Temporal);
        }
        getNotaciones.append(getFieldClass());
        
        return getNotaciones.toString();
    }
    public String getFieldClass(){
        return "\tprivate "+tipoDatoJava+toCamelCase()+";\n";
    }
    
    public String getJavaDataType(){
        switch(tipoDatoBase){
            case "NUMBER":
                if(esForanea()){
                    return this.toCasiCamelCase(tablaForanea)+" ";
                }else if(esPrimario||precision!=null){
                    return "BigDecimal ";
                }else {
                    return "BigInteger ";
                }
            case "TIMESTAMP(6)":
                return "Date ";
            case "CLOB":
                return "String ";
            case "DATE":
                return "Date ";
            case "VARCHAR2":
                return "String ";
            case "BLOB":
                return "Serializable ";
            default:
                return "DESCONOCIDO ";
        }
    }
    
    public String getGetMethod(){
        StringBuilder getMethod = new StringBuilder();
        getMethod.append("\tpublic ");
        getMethod.append(tipoDatoJava);
        getMethod.append(" get");
        getMethod.append(nombre);
        getMethod.append("() {\n\t\treturn ");
        getMethod.append(toCamelCase());
        getMethod.append(";\n\t}\n");
        return getMethod.toString();
    }
    
    public String getSetMethod(){
        StringBuilder getMethod = new StringBuilder();
        getMethod.append("\tpublic void set");
        getMethod.append(nombre);
        getMethod.append("(");
        getMethod.append(tipoDatoJava);
        getMethod.append(" ");
        getMethod.append(toCamelCase());
        getMethod.append(") {\n\t\tthis.");
        getMethod.append(toCamelCase());
        getMethod.append(" = ");
        getMethod.append(toCamelCase());
        getMethod.append(";\n\t}\n");
        return getMethod.toString();
    }
    
    public String toCasiCamelCase(String nombre){
        String camelCase;
        String []cads = nombre.split("_");
        camelCase = cads[0].substring(0,1)+cads[0].substring(1).toLowerCase();
        for(int i = 1 ; i < cads.length ; i++){
            camelCase += cads[i].length()>0?cads[i].substring(0,1)+cads[i].substring(1).toLowerCase():"";
        }
        return camelCase;
    }
    public String toCamelCase(){
        String camelCase;
        String []cads = nombreSinFormato.split("_");
        camelCase = cads[0].toLowerCase();
        for(int i = 1 ; i < cads.length ; i++){
            camelCase += cads[i].length()>0?cads[i].substring(0,1)+cads[i].substring(1).toLowerCase():"";
        }
        return camelCase;
    }
}
