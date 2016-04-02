/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistance;

import ipsocketmessage.OrientationReading;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author victor
 */
@Entity
@Table(name = "orientation_reading")
public class OrientationReadingEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private float azimuth;
    private float pitch;
    private float roll;
    private long session;
    
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "orientationReadingEntity")
    private ReadingEntity readingEntity;
    
    public OrientationReadingEntity(){}
    
    public OrientationReadingEntity(OrientationReading orientationReading){
        azimuth = orientationReading.getAzimuth();
        pitch = orientationReading.getPitch();
        roll = orientationReading.getRoll();
        session = orientationReading.getSession();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (!(object instanceof OrientationReadingEntity)) {
            return false;
        }
        OrientationReadingEntity other = (OrientationReadingEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "persistance.OrientationReadingEntity[ id=" + id + " ]";
    }

    public ReadingEntity getReadingEntity() {
        return readingEntity;
    }

    public void setReadingEntity(ReadingEntity readingEntity) {
        this.readingEntity = readingEntity;
        readingEntity.setOrientationReadingEntity(this);
    }
    
}
