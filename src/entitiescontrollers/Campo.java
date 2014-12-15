package entitiescontrollers;

import java.math.BigDecimal;

public class Campo {
    private String nombre;
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
        this.nombre = (String) nombre;
        this.tipoDatoBase = (String) tipoDatoBase;
        this.precision = (BigDecimal) precision;
        this.permiteNulos = ((String)permiteNulos).equals("Y");
        this.tablaForanea = (String) tablaForanea;
        this.esPrimario = esPrimario != null;
        this.esFecha = this.tipoDatoBase.contains("DATE") || this.tipoDatoBase.contains("TIMESTAMP");
        this.tipoDatoJava = getJavaDataType();
    }
    
    public String getNombre(){
        return this.nombre.substring(0,1)+this.nombre.substring(1).toLowerCase();
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
    public String getNotaciones(){
        StringBuilder getNotaciones = new StringBuilder();
        if(esPrimario){
            getNotaciones.append(Notaciones.Id);
        }
        if(!permiteNulos){
            getNotaciones.append(Notaciones.getBasicNotation(permiteNulos));
        }
        if(isLob()){
            getNotaciones.append(Notaciones.Lob);
        }
        getNotaciones.append(Notaciones.getNotationColumna(nombre));
        if(esFecha){
            getNotaciones.append(Notaciones.Temporal);
        }
        getNotaciones.append(getFieldClass());
        //getNotaciones.append("\n");
        
        return getNotaciones.toString();
    }
    public String getFieldClass(){
        return "\tprivate "+tipoDatoJava+nombre.toLowerCase()+";\n";
    }
    
    public String getJavaDataType(){
        switch(tipoDatoBase){
            case "NUMBER":
                if(esPrimario||precision!=null){
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
}