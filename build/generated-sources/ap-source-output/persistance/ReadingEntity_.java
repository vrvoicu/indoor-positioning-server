package persistance;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import persistance.ARMarkerReadingEntity;
import persistance.GSMReadingEntity;
import persistance.OrientationReadingEntity;
import persistance.ReadingsEntity;
import persistance.WifiReadingEntity;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-02-08T11:18:16")
@StaticMetamodel(ReadingEntity.class)
public class ReadingEntity_ { 

    public static volatile SingularAttribute<ReadingEntity, ReadingsEntity> readingsEntity;
    public static volatile SingularAttribute<ReadingEntity, GSMReadingEntity> gsmReadingEntity;
    public static volatile ListAttribute<ReadingEntity, WifiReadingEntity> wifiReadings;
    public static volatile ListAttribute<ReadingEntity, ARMarkerReadingEntity> arMarkerReadingEntitys;
    public static volatile SingularAttribute<ReadingEntity, Long> id;
    public static volatile SingularAttribute<ReadingEntity, OrientationReadingEntity> orientationReadingEntity;

}