/** 
 *  Generated by OpenJPA MetaModel Generator Tool.
**/

package model.database.jpa.tables;

import java.util.Date;
import javax.persistence.metamodel.SingularAttribute;

@javax.persistence.metamodel.StaticMetamodel
(value=model.database.jpa.tables.PortfolioInvestment.class)
@javax.annotation.Generated
(value="org.apache.openjpa.persistence.meta.AnnotationProcessor6",date="Wed May 16 20:21:34 CEST 2012")
public class PortfolioInvestment_ {
    public static volatile SingularAttribute<PortfolioInvestment,Long> amount; //NOPMD
    public static volatile SingularAttribute<PortfolioInvestment,Date> date; //NOPMD
    public static volatile SingularAttribute<PortfolioInvestment,Boolean> invested; //NOPMD
    public static volatile SingularAttribute<PortfolioInvestment,PortfolioEntity> portfolio; //NOPMD
}
