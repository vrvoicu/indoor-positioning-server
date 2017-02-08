package persistance;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import persistance.ImageEntity;
import persistance.ReadingEntity;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-02-08T11:18:16")
@StaticMetamodel(ARMarkerReadingEntity.class)
public class ARMarkerReadingEntity_ { 

    public static volatile SingularAttribute<ARMarkerReadingEntity, String> transMat;
    public static volatile SingularAttribute<ARMarkerReadingEntity, ImageEntity> image;
    public static volatile SingularAttribute<ARMarkerReadingEntity, ReadingEntity> readingEntity;
    public static volatile SingularAttribute<ARMarkerReadingEntity, String> vertex;
    public static volatile SingularAttribute<ARMarkerReadingEntity, Long> id;
    public static volatile SingularAttribute<ARMarkerReadingEntity, Long> markerId;
    public static volatile SingularAttribute<ARMarkerReadingEntity, Integer> direction;

}