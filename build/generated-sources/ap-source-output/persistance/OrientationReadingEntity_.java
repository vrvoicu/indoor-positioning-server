package persistance;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import persistance.ReadingEntity;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-04-05T11:57:38")
@StaticMetamodel(OrientationReadingEntity.class)
public class OrientationReadingEntity_ { 

    public static volatile SingularAttribute<OrientationReadingEntity, ReadingEntity> readingEntity;
    public static volatile SingularAttribute<OrientationReadingEntity, Long> session;
    public static volatile SingularAttribute<OrientationReadingEntity, Float> roll;
    public static volatile SingularAttribute<OrientationReadingEntity, Float> azimuth;
    public static volatile SingularAttribute<OrientationReadingEntity, Long> id;
    public static volatile SingularAttribute<OrientationReadingEntity, Float> pitch;

}