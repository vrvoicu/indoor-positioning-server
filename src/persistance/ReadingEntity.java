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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author victor
 */
@Entity
@Table(name = "reading")
public class ReadingEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gsm_reading_id")
    private GSMReadingEntity gsmReadingEntity;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orientation_reading_id")
    private OrientationReadingEntity orientationReadingEntity;
    
    @OneToMany(mappedBy = "readingEntity")
    private List<WifiReadingEntity> wifiReadings;
    
    @OneToMany(mappedBy = "readingEntity")
    private List<ARMarkerReadingEntity> arMarkerReadingEntitys;
    
//    new add
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "readings_id")
    private ReadingsEntity readingsEntity;

    public ReadingEntity() {
        wifiReadings = new ArrayList<>();
        arMarkerReadingEntitys = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public PhoneDetailsEntity getPhoneDetailsEntity() {
//        return phoneDetailsEntity;
//    }
//
//    public void setPhoneDetailsEntity(PhoneDetailsEntity phoneDetailsEntity) {
//        this.phoneDetailsEntity = phoneDetailsEntity;
//        if(phoneDetailsEntity.getReadingEntity() != this)
//            phoneDetailsEntity.setReadingEntity(this);
//    }

    public GSMReadingEntity getGsmReadingEntity() {
        return gsmReadingEntity;
    }

    public void setGsmReadingEntity(GSMReadingEntity gsmReadingEntity) {
        if(gsmReadingEntity == null)
            return;
        
        this.gsmReadingEntity = gsmReadingEntity;
        if(gsmReadingEntity.getReadingEntity() != this)
            gsmReadingEntity.setReadingEntity(this);
    }

    public OrientationReadingEntity getOrientationReadingEntity() {
        return orientationReadingEntity;
    }

    public void setOrientationReadingEntity(OrientationReadingEntity orientationReadingEntity) {
        if(orientationReadingEntity == null)
            return;
        
        this.orientationReadingEntity = orientationReadingEntity;
        
        if(orientationReadingEntity.getReadingEntity() == null)
            orientationReadingEntity.setReadingEntity(this);
        
        if(orientationReadingEntity.getReadingEntity() != this)
            orientationReadingEntity.setReadingEntity(this);
    }
    
    public void addWifiReadingEntity(WifiReadingEntity wifiReadingEntity){
        if(wifiReadingEntity == null)
            return;
        
        wifiReadings.add(wifiReadingEntity);
        
        if(wifiReadingEntity.getReadingEntity() == null)
            wifiReadingEntity.setReadingEntity(this);
        
        if(wifiReadingEntity.getReadingEntity() != this)
            wifiReadingEntity.setReadingEntity(this);
    }
    
    public void addArMarkerReading(ARMarkerReadingEntity arMarkerReadingEntity){
        if(arMarkerReadingEntity == null)
            return;
        
        arMarkerReadingEntitys.add(arMarkerReadingEntity);
        
        if(arMarkerReadingEntity.getReadingEntity() == null)
            arMarkerReadingEntity.setReadingEntity(this);
        
        if(arMarkerReadingEntity.getReadingEntity() != this)
            arMarkerReadingEntity.setReadingEntity(this);
    }

    public List<WifiReadingEntity> getWifiReadings() {
        return wifiReadings;
    }

    public void setWifiReadings(List<WifiReadingEntity> wifiReadings) {
        this.wifiReadings = wifiReadings;
        for(WifiReadingEntity wifiReadingEntity : wifiReadings)
            wifiReadingEntity.setReadingEntity(this);
    }

    public List<ARMarkerReadingEntity> getArMarkerReadingEntitys() {
        return arMarkerReadingEntitys;
    }

    public void setArMarkerReadingEntitys(List<ARMarkerReadingEntity> arMarkerReadingEntitis) {
        this.arMarkerReadingEntitys = arMarkerReadingEntitys;
        for(ARMarkerReadingEntity arMarkerEntity : arMarkerReadingEntitis)
            arMarkerEntity.setReadingEntity(this);
    }

    public ReadingsEntity getReadingsEntity() {
        return readingsEntity;
    }

    public void setReadingsEntity(ReadingsEntity readingsEntity) {
        this.readingsEntity = readingsEntity;
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
        if (!(object instanceof ReadingEntity)) {
            return false;
        }
        ReadingEntity other = (ReadingEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "persistance.Reading[ id=" + id + " ]";
    }
    
}
