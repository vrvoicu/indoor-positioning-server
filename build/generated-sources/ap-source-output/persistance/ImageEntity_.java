package persistance;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import persistance.ARMarkerReadingEntity;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-02-08T11:18:16")
@StaticMetamodel(ImageEntity.class)
public class ImageEntity_ { 

    public static volatile SingularAttribute<ImageEntity, byte[]> image;
    public static volatile SingularAttribute<ImageEntity, ARMarkerReadingEntity> arMarker;
    public static volatile SingularAttribute<ImageEntity, Integer> width;
    public static volatile SingularAttribute<ImageEntity, Long> id;
    public static volatile SingularAttribute<ImageEntity, Integer> height;

}