package persistance;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import persistance.ReadingEntity;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-02-08T11:18:16")
@StaticMetamodel(GSMReadingEntity.class)
public class GSMReadingEntity_ { 

    public static volatile SingularAttribute<GSMReadingEntity, ReadingEntity> readingEntity;
    public static volatile SingularAttribute<GSMReadingEntity, Integer> rssi;
    public static volatile SingularAttribute<GSMReadingEntity, Long> id;
    public static volatile SingularAttribute<GSMReadingEntity, String> cid;

}