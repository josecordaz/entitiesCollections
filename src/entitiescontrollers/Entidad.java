package entitiescontrollers;

import java.util.ArrayList;
import java.util.List;

public class Entidad {
    private static final String statics = "\tprivate static final long serialVersionUID = 1L;\n";
    private static final String implementations = "implements Serializable ";
    
    private Libreria libs[];
    private Notaciones notaciones;
    private String nombre;
    private Campo llavePrimaria;
    private List<Campo> campos = new ArrayList<Campo>();
    private List<Campo> camposObligatorios = new ArrayList<Campo>();
    private List<Campo> camposForaneos = new ArrayList<Campo>();
    private boolean tieneForaneas = false;
    
    public Entidad(Object tabla){
        this.nombre = (String)tabla;
        notaciones = new Notaciones(nombre, campos);
    }
    
    public String getNombre(){
        return this.nombre;
    }
    
    public String toJavaFile(){
        StringBuilder strJavaFile = new StringBuilder();
        strJavaFile.append("package ");
        strJavaFile.append(Paquetes.entitie);
        strJavaFile.append(";\n");
        strJavaFile.append(Libreria.getLibs(tieneForaneas));
        strJavaFile.append(notaciones.getNotacionesdeClase());
        strJavaFile.append(getClassName());
        strJavaFile.append(implementations);
        strJavaFile.append(" {\n");
        strJavaFile.append(statics);
        strJavaFile.append(getClassFields());
        strJavaFile.append("\n");
        strJavaFile.append(constructor());
        strJavaFile.append("\n");
        strJavaFile.append(constructorConLlavePrimaria());
        strJavaFile.append("\n");
        strJavaFile.append(constructorConCamposObligatorios());
        strJavaFile.append("\n");
        strJavaFile.append(metodos());
        strJavaFile.append(getHashCodeMethod());
        strJavaFile.append("\n");
        strJavaFile.append(getEqualsMethod());
        strJavaFile.append("\n");
        strJavaFile.append(getToStringMethod());
        strJavaFile.append("\n");
        strJavaFile.append("}");
        return strJavaFile.toString();
    }
    
    public void addCampo(Campo campo){
        if(campo.isPrimary()){
            llavePrimaria = campo;
        }
        if(!campo.permiteNulos()){
            camposObligatorios.add(campo);
        }
        if(campo.esForanea()){
            tieneForaneas = true;
            camposForaneos.add(campo);
        }
        campos.add(campo);
    }
    
    public String getClassName(){
        return "public class "+tablaCamelCase()+" ";
    }
    
    public String tablaCamelCase(){
        StringBuilder tablaCamelCase = new StringBuilder();
        String palabras[] = nombre.split("_");
        for(String palabra:palabras){
            tablaCamelCase.append(palabra.length()>0?palabra.substring(0,1)+palabra.substring(1).toLowerCase():"");
        }
        return tablaCamelCase.toString();
    }
    
    public String getClassFields(){
        StringBuilder classFields = new StringBuilder();
        for(Campo campo:campos){
            classFields.append(campo.getNotaciones());
        }
        return classFields.toString();
    }
    
    public String constructor(){
        return "\tpublic "+tablaCamelCase()+"() {\n\t}\n";
    }
    
    public String constructorConLlavePrimaria(){
        String strTipoDatoPrimaria = llavePrimaria.getJavaDataType();
        String strLlavePrimaria = llavePrimaria.toCamelCase();
        return "\tpublic "+
                tablaCamelCase()+
                "("+strTipoDatoPrimaria+
                " "+strLlavePrimaria+
                ") { \n\t\tthis."+strLlavePrimaria+
                " = "+strLlavePrimaria+"; \n\t}\n";
    }
    
    public String constructorConCamposObligatorios(){
        String consConCamposObligatorios = "";
        if(camposObligatorios.size()-camposForaneos.size()>1){
            consConCamposObligatorios += "\tpublic "+tablaCamelCase()+"(";
            for(Campo campo:camposObligatorios){
                if(!campo.esForanea()){
                    consConCamposObligatorios += campo.getJavaDataType()+" "+campo.toCamelCase()+", ";
                }
            }
            consConCamposObligatorios = consConCamposObligatorios.substring(0,consConCamposObligatorios.length()-2);
            consConCamposObligatorios += ") {\n";
            for(Campo campo:camposObligatorios){
                if(!campo.esForanea()){
                    consConCamposObligatorios += "\t\tthis."+campo.toCamelCase()+" = "+campo.toCamelCase()+";\n";
                }
            }
            consConCamposObligatorios += "\t}\n";
        }
        return consConCamposObligatorios;
    }
    
    public String metodos(){
        StringBuilder metodos = new StringBuilder();
        for(Campo campo:campos){
            metodos.append(campo.getGetMethod()).append("\n");
            metodos.append(campo.getSetMethod()).append("\n");
        }
        return metodos.toString();
    }
    
    public String getHashCodeMethod(){
        StringBuilder hashCodeMethod = new StringBuilder();
        hashCodeMethod.append(Notaciones.Override);
        hashCodeMethod.append("\tpublic int hashCode() {\n");
        hashCodeMethod.append("\t\tint hash = 0;\n");
        hashCodeMethod.append("\t\thash += (");
        hashCodeMethod.append(llavePrimaria.toCamelCase());
        hashCodeMethod.append(" != null ? ");
        hashCodeMethod.append(llavePrimaria.toCamelCase());
        hashCodeMethod.append(".hashCode() : 0);\n");
        hashCodeMethod.append("\t\treturn hash;\n");
        hashCodeMethod.append("\t}\n");
        return hashCodeMethod.toString();
    }
    
    public String getEqualsMethod(){
        StringBuilder equalsMethod = new StringBuilder();
        equalsMethod.append(Notaciones.Override);
        equalsMethod.append("\tpublic boolean equals(Object object) {\n");
        equalsMethod.append("\t\tif (!(object instanceof ");
        equalsMethod.append(tablaCamelCase());
        equalsMethod.append(")) { \n");
        equalsMethod.append("\t\t\treturn false; \n");
        equalsMethod.append("\t\t} \n\t\t");
        equalsMethod.append(tablaCamelCase());
        equalsMethod.append(" other = (");
        equalsMethod.append(tablaCamelCase());
        equalsMethod.append(") object; \n");
        equalsMethod.append("\t\tif ((this.");
        equalsMethod.append(llavePrimaria.toCamelCase());
        equalsMethod.append(" == null && other.");
        equalsMethod.append(llavePrimaria.toCamelCase());
        equalsMethod.append(" != null) || (this.");
        equalsMethod.append(llavePrimaria.toCamelCase());
        equalsMethod.append(" != null && !this.");
        equalsMethod.append(llavePrimaria.toCamelCase());
        equalsMethod.append(".equals(other.");
        equalsMethod.append(llavePrimaria.toCamelCase());
        equalsMethod.append("))) { \n");
        equalsMethod.append("\t\t\treturn false; \n");
        equalsMethod.append("\t\t}\n");
        equalsMethod.append("\t\treturn true;\n");
        equalsMethod.append("\t}\n");
        return equalsMethod.toString();
    }
    public String getToStringMethod(){
        StringBuilder toStringMethod = new StringBuilder();
        toStringMethod.append(Notaciones.Override);
        toStringMethod.append("\tpublic String toString() {\n");
        toStringMethod.append("\t\treturn \"");
        toStringMethod.append(Paquetes.entitie);
        toStringMethod.append(".");
        toStringMethod.append(tablaCamelCase());
        toStringMethod.append("[ ");
        toStringMethod.append(llavePrimaria.toCamelCase());
        toStringMethod.append("=\" + ");
        toStringMethod.append(llavePrimaria.toCamelCase());
        toStringMethod.append(" + \" ]\";\n");
        toStringMethod.append("\t}");
        return toStringMethod.toString();
    }
}