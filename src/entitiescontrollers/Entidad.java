package entitiescontrollers;

import java.util.ArrayList;
import java.util.List;

public class Entidad {
    private static final String statics = "\tprivate static final long serialVersionUID = 1L;\n";
    private static final String implementations = "implements Serializable ";
    
    private Libreria libs[];
    private Notaciones notaciones;
    private String nombre;
//    private Interface interfaces[];
    private List<Campo> campos = new ArrayList<Campo>();
//    private Constructor constructors[];
//    private Metodo metodos[];
    
    public Entidad(Object tabla){
        this.nombre = (String)tabla;
        notaciones = new Notaciones(nombre, campos);
    }
    
    public String getNombre(){
        return this.nombre;
    }
    
    public String toJavaFile(){
        StringBuilder strJavaFile = new StringBuilder();
        strJavaFile.append(Paquetes.entitie);
        strJavaFile.append(Libreria.libs);
        strJavaFile.append(notaciones.getNotacionesdeClase());
        strJavaFile.append(getClassName());
        strJavaFile.append(implementations);
        strJavaFile.append(" {\n");
        strJavaFile.append(statics);
        strJavaFile.append(getClassFields());
        return strJavaFile.toString();
    }
    
    public void addCampo(Campo campo){
        this.campos.add(campo);
    }
    
    public String getClassName(){
        return "public class "+tablaCamelCase()+" ";
    }
    
    public String tablaCamelCase(){
        StringBuilder tablaCamelCase = new StringBuilder();
        String palabras[] = this.nombre.split("_");
        for(String palabra:palabras){
            tablaCamelCase.append(palabra.substring(0,1)).append(palabra.substring(1).toLowerCase());
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
}