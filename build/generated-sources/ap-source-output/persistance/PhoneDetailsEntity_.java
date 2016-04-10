package persistance;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import persistance.ReadingEntity;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-04-09T18:16:37")
@StaticMetamodel(PhoneDetailsEntity.class)
public class PhoneDetailsEntity_ { 

    public static volatile SingularAttribute<PhoneDetailsEntity, ReadingEntity> readingEntity;
    public static volatile SingularAttribute<PhoneDetailsEntity, String> name;
    public static volatile SingularAttribute<PhoneDetailsEntity, String> imei;
    public static volatile SingularAttribute<PhoneDetailsEntity, Long> id;

}