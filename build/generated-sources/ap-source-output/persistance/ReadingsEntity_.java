package persistance;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import persistance.PhoneDetailsEntity;
import persistance.ReadingEntity;
import persistance.ReadingsEntity.ReadingType;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-02-08T11:18:16")
@StaticMetamodel(ReadingsEntity.class)
public class ReadingsEntity_ { 

    public static volatile SingularAttribute<ReadingsEntity, PhoneDetailsEntity> phoneDetailsEntity;
    public static volatile SingularAttribute<ReadingsEntity, Long> id;
    public static volatile SingularAttribute<ReadingsEntity, ReadingType> readingType;
    public static volatile ListAttribute<ReadingsEntity, ReadingEntity> readingEntities;

}