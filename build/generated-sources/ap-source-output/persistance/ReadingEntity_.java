package persistance;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import persistance.ARMarkerReadingEntity;
import persistance.GSMReadingEntity;
import persistance.OrientationReadingEntity;
import persistance.PhoneDetailsEntity;
import persistance.ReadingEntity.ReadingType;
import persistance.WifiReadingEntity;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-04-09T18:16:37")
@StaticMetamodel(ReadingEntity.class)
public class ReadingEntity_ { 

    public static volatile SingularAttribute<ReadingEntity, PhoneDetailsEntity> phoneDetailsEntity;
    public static volatile SingularAttribute<ReadingEntity, GSMReadingEntity> gsmReadingEntity;
    public static volatile ListAttribute<ReadingEntity, WifiReadingEntity> wifiReadings;
    public static volatile ListAttribute<ReadingEntity, ARMarkerReadingEntity> arMarkerReadingEntitys;
    public static volatile SingularAttribute<ReadingEntity, Long> id;
    public static volatile SingularAttribute<ReadingEntity, OrientationReadingEntity> orientationReadingEntity;
    public static volatile SingularAttribute<ReadingEntity, ReadingType> readingType;

}