package mx.com.sonykarl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "TMP_JCOC_REFERENCIA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TmpJcocReferencia.findAll", query = "SELECT t FROM TmpJcocReferencia t"),
    @NamedQuery(name = "TmpJcocReferencia.findByPkReferencia", query = "SELECT t FROM TmpJcocReferencia t WHERE t.pkReferencia = :pkReferencia"),
    @NamedQuery(name = "TmpJcocReferencia.findByCol1", query = "SELECT t FROM TmpJcocReferencia t WHERE t.col1 = :col1"),
    @NamedQuery(name = "TmpJcocReferencia.findByCol2", query = "SELECT t FROM TmpJcocReferencia t WHERE t.col2 = :col2"),
    @NamedQuery(name = "TmpJcocReferencia.findByCol3", query = "SELECT t FROM TmpJcocReferencia t WHERE t.col3 = :col3"),
    @NamedQuery(name = "TmpJcocReferencia.findByCol5", query = "SELECT t FROM TmpJcocReferencia t WHERE t.col5 = :col5"),
    @NamedQuery(name = "TmpJcocReferencia.findByCol6", query = "SELECT t FROM TmpJcocReferencia t WHERE t.col6 = :col6")})
public class TmpJcocReferencia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "PK_REFERENCIA")
    private BigDecimal pkReferencia;
    @Column(name = "COL1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date col1;
    @Basic(optional = false)
    @Column(name = "COL2")
    private BigInteger col2;
    @Column(name = "COL3")
    private BigDecimal col3;
    @Basic(optional = false)
    @Lob
    @Column(name = "COL4")
    private String col4;
    @Column(name = "COL5")
    @Temporal(TemporalType.TIMESTAMP)
    private Date col5;
    @Column(name = "COL6")
    private String col6;
    @Lob
    @Column(name = "COL7")
    private Serializable col7;
    @JoinColumn(name = "PK", referencedColumnName = "PK")
    @ManyToOne(optional = false)
    private TmpJcocPruebas pk;

    public TmpJcocReferencia() {
    }

    public TmpJcocReferencia(BigDecimal pkReferencia) {
        this.pkReferencia = pkReferencia;
    }

    public TmpJcocReferencia(BigDecimal pkReferencia, BigInteger col2, String col4) {
        this.pkReferencia = pkReferencia;
        this.col2 = col2;
        this.col4 = col4;
    }

    public BigDecimal getPkReferencia() {
        return pkReferencia;
    }

    public void setPkReferencia(BigDecimal pkReferencia) {
        this.pkReferencia = pkReferencia;
    }

    public Date getCol1() {
        return col1;
    }

    public void setCol1(Date col1) {
        this.col1 = col1;
    }

    public BigInteger getCol2() {
        return col2;
    }

    public void setCol2(BigInteger col2) {
        this.col2 = col2;
    }

    public BigDecimal getCol3() {
        return col3;
    }

    public void setCol3(BigDecimal col3) {
        this.col3 = col3;
    }

    public String getCol4() {
        return col4;
    }

    public void setCol4(String col4) {
        this.col4 = col4;
    }

    public Date getCol5() {
        return col5;
    }

    public void setCol5(Date col5) {
        this.col5 = col5;
    }

    public String getCol6() {
        return col6;
    }

    public void setCol6(String col6) {
        this.col6 = col6;
    }

    public Serializable getCol7() {
        return col7;
    }

    public void setCol7(Serializable col7) {
        this.col7 = col7;
    }

    public TmpJcocPruebas getPk() {
        return pk;
    }

    public void setPk(TmpJcocPruebas pk) {
        this.pk = pk;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkReferencia != null ? pkReferencia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof TmpJcocReferencia)) {
            return false;
        }
        TmpJcocReferencia other = (TmpJcocReferencia) object;
        if ((this.pkReferencia == null && other.pkReferencia != null) || (this.pkReferencia != null && !this.pkReferencia.equals(other.pkReferencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.com.sonykarl.TmpJcocReferencia[ pkReferencia=" + pkReferencia + " ]";
    }
    
}
