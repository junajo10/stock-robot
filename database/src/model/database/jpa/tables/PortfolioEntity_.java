/** 
 *  Generated by OpenJPA MetaModel Generator Tool.
**/

package model.database.jpa.tables;

import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;

@javax.persistence.metamodel.StaticMetamodel
(value=model.database.jpa.tables.PortfolioEntity.class)
@javax.annotation.Generated
(value="org.apache.openjpa.persistence.meta.AnnotationProcessor6",date="Thu May 03 20:24:15 CEST 2012")
public class PortfolioEntity_ {
    public static volatile SingularAttribute<PortfolioEntity,AlgorithmSettings> algorithmSettings;
    public static volatile SingularAttribute<PortfolioEntity,Long> balance;
    public static volatile SetAttribute<PortfolioEntity,PortfolioHistory> history;
    public static volatile SetAttribute<PortfolioEntity,PortfolioInvestment> investments;
    public static volatile SingularAttribute<PortfolioEntity,String> name;
    public static volatile SingularAttribute<PortfolioEntity,Integer> portfolioId;
    public static volatile SingularAttribute<PortfolioEntity,Boolean> stopBuying;
    public static volatile SingularAttribute<PortfolioEntity,Boolean> stopSelling;
    public static volatile SingularAttribute<PortfolioEntity,Boolean> watchAllStocks;
}
