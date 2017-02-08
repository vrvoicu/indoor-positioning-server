/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistance;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author victor
 */
@Entity
@Table(name = "readings")
public class ReadingsEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "phone_details_id")
    private PhoneDetailsEntity phoneDetailsEntity;

    @OneToMany(mappedBy = "readingsEntity")
    private List<ReadingEntity> readingEntities;

    public enum ReadingType {
//        MARKER_WITH_IMAGE, MARKER_WITHOUT_IMAGE, ORIENTATION, LOCATION
        SINGLE_WIFI_READINGS
    }

    @Enumerated(value = EnumType.STRING)
    private ReadingsEntity.ReadingType readingType;

    public ReadingsEntity() {
        readingEntities = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PhoneDetailsEntity getPhoneDetailsEntity() {
        return phoneDetailsEntity;
    }

    public void setPhoneDetailsEntity(PhoneDetailsEntity phoneDetailsEntity) {
        this.phoneDetailsEntity = phoneDetailsEntity;

        if (phoneDetailsEntity.getReadReadingsEntity() == null) {
            phoneDetailsEntity.setReadReadingsEntity(this);
        }

        if (phoneDetailsEntity.getReadReadingsEntity() != this) {
            phoneDetailsEntity.setReadReadingsEntity(this);
        }
    }

    public List<ReadingEntity> getReadingEntities() {
        return readingEntities;
    }

    public void setReadingEntities(List<ReadingEntity> readingEntities) {
        this.readingEntities = readingEntities;
    }

    public void addReadingsEntity(ReadingEntity readingEntity) {
        if (readingEntity == null) {
            return;
        }

        readingEntities.add(readingEntity);

        if (readingEntity.getReadingsEntity() == null) {
            readingEntity.setReadingsEntity(this);
        }

        if (readingEntity.getReadingsEntity() != this) {
            readingEntity.setReadingsEntity(this);
        }
    }

    public ReadingsEntity.ReadingType getReadingType() {
        return readingType;
    }

    public void setReadingType(ReadingsEntity.ReadingType readingType) {
        this.readingType = readingType;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ReadingsEntity)) {
            return false;
        }
        ReadingsEntity other = (ReadingsEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "persistance.Readings[ id=" + id + " ]";
    }

}
