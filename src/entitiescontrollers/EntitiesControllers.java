package entitiescontrollers;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class EntitiesControllers {
    public static void main(String[] args) {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        Query query;
        try {
            emf = Persistence.createEntityManagerFactory("entitiesControllersPU");
            em = emf.createEntityManager();
            
            StringBuilder strConsulta = new StringBuilder();
            strConsulta.append("SELECT atc.table_name, ");
            strConsulta.append("  atc.column_name, ");
            strConsulta.append("  atc.data_type, ");
            strConsulta.append("  atc.data_precision, ");
            strConsulta.append("  atc.nullable, ");
            strConsulta.append("  (SELECT ac2.TABLE_NAME ");
            strConsulta.append("  FROM ALL_CONSTRAINTS ac, ");
            strConsulta.append("    ALL_CONS_COLUMNS acc, ");
            strConsulta.append("    ALL_CONSTRAINTS ac2 ");
            strConsulta.append("  WHERE ac.owner           = acc.owner ");
            strConsulta.append("  AND ac.constraint_name   = acc.constraint_name ");
            strConsulta.append("  AND ac.table_name        = acc.table_name ");
            strConsulta.append("  AND ac.constraint_type   = 'R' ");
            strConsulta.append("  AND ac.owner             = ac2.owner ");
            strConsulta.append("  AND ac.R_CONSTRAINT_NAME = ac2.CONSTRAINT_NAME ");
            strConsulta.append("  AND acc.column_name      = atc.column_name ");
            strConsulta.append("  AND acc.table_name       = atc.table_name ");
            strConsulta.append("  AND ac.owner             = atc.owner ");
            strConsulta.append(") ");
            strConsulta.append("AS ");
            strConsulta.append("  tabla_referencia, ");
            strConsulta.append("  (SELECT NVL(ac.CONSTRAINT_TYPE,'') ");
            strConsulta.append("  FROM ALL_CONSTRAINtS ac, ");
            strConsulta.append("    ALL_CONS_COLUMNS acc ");
            strConsulta.append("  WHERE ac.owner         = acc.owner ");
            strConsulta.append("  AND ac.table_name      = acc.table_name ");
            strConsulta.append("  AND ac.constraint_name = acc.CONSTRAINT_NAME ");
            strConsulta.append("  AND ac.CONSTRAINT_TYPE = 'P' ");
            strConsulta.append("  AND acc.column_name    = atc.column_name ");
            strConsulta.append("  AND acc.table_name     = atc.table_name ");
            strConsulta.append("  AND ac.owner           = atc.owner ");
            strConsulta.append("  ) ");
            strConsulta.append("AS ");
            strConsulta.append("  tipo_constraint ");
            strConsulta.append("FROM ALL_TAB_COLS atc ");
            strConsulta.append("WHERE atc.owner     = 'VENFI_DESA' ");
            strConsulta.append("AND atc.TABLE_NAME IN ('TMP_JCOC_PRUEBAS','TMP_JCOC_REFERENCIA') ");
            strConsulta.append("ORDER BY atc.table_name,atc.column_id");
            
            query = em.createNativeQuery(strConsulta.toString());
            List<Entidad> listaEntidades = new ArrayList<Entidad>();
            Entidad tabla = null;
            Campo campo = null;
            for(Object []reg:(List<Object[]>)query.getResultList()){
                if(tabla!= null && tabla.getNombre().equals((String)reg[0])){
                    campo = new Campo(reg[1],reg[2],reg[3],reg[4],reg[5],reg[6]);
                    tabla.addCampo(campo);
                }else{
                    if(tabla != null){
                        listaEntidades.add(tabla);
                    }
                    tabla = new Entidad(reg[0]);
                    campo = new Campo(reg[1],reg[2],reg[3],reg[4],reg[5],reg[6]);
                    tabla.addCampo(campo);
                }
            }
            listaEntidades.add(tabla);
            System.out.println(listaEntidades.get(0).toJavaFile());
            
        } catch (Exception e) {
            e.printStackTrace(System.out);
            System.out.println(e.getMessage());
        } finally {
            if(em!=null){em.close();}
            if(emf!=null){emf.close();}
        }
    }
}