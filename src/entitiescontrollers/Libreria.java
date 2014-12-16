package entitiescontrollers;

public class Libreria {
    private static String libs;
    public static String getLibs(boolean conForaneas){
        libs = "\nimport java.io.Serializable;\n"+
        "import java.math.BigDecimal;\n"+
        "import java.math.BigInteger;\n"+
        "import java.util.Date;\n"+
        "import javax.persistence.Basic;\n"+
        "import javax.persistence.Column;\n"+
        "import javax.persistence.Entity;\n"+
        "import javax.persistence.Id;\n"+
        (conForaneas?"import javax.persistence.JoinColumn;\n":"")+
        "import javax.persistence.Lob;\n"+
        (conForaneas?"import javax.persistence.ManyToOne;\n":"")+
        "import javax.persistence.NamedQueries;\n"+
        "import javax.persistence.NamedQuery;\n"+
        "import javax.persistence.Table;\n"+
        "import javax.persistence.Temporal;\n"+
        "import javax.persistence.TemporalType;\n"+
        "import javax.xml.bind.annotation.XmlRootElement;\n\n";
        return libs;
    }
}