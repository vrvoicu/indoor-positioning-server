package persistance;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import persistance.ReadingEntity;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-04-09T18:16:37")
@StaticMetamodel(WifiReadingEntity.class)
public class WifiReadingEntity_ { 

    public static volatile SingularAttribute<WifiReadingEntity, ReadingEntity> readingEntity;
    public static volatile SingularAttribute<WifiReadingEntity, Integer> level;
    public static volatile SingularAttribute<WifiReadingEntity, String> bssid;
    public static volatile SingularAttribute<WifiReadingEntity, Long> id;
    public static volatile SingularAttribute<WifiReadingEntity, String> ssid;

}