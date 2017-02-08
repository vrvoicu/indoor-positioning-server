package persistance;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import persistance.ReadingsEntity;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-02-08T11:18:16")
@StaticMetamodel(PhoneDetailsEntity.class)
public class PhoneDetailsEntity_ { 

    public static volatile SingularAttribute<PhoneDetailsEntity, ReadingsEntity> readReadingsEntity;
    public static volatile SingularAttribute<PhoneDetailsEntity, String> name;
    public static volatile SingularAttribute<PhoneDetailsEntity, String> imei;
    public static volatile SingularAttribute<PhoneDetailsEntity, Long> id;

}