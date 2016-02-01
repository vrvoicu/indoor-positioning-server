/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistance;

import ipsocketmessage.GSMReading;
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
@Table(name = "gsm_reading")
public class GSMReadingEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private int rssi;
    private String cid;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "gsmReadingEntity")
    private ReadingEntity readingEntity;
    
    public GSMReadingEntity() {}
    
    public GSMReadingEntity(GSMReading gsmReading){
        rssi = gsmReading.getRssi();
        cid = gsmReading.getCid();
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
        if (!(object instanceof GSMReadingEntity)) {
            return false;
        }
        GSMReadingEntity other = (GSMReadingEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "persistance.GSMReadingEntity[ id=" + id + " ]";
    }

    public int getRssi() {
        return rssi;
    }

    public void setRssi(int rssi) {
        this.rssi = rssi;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public ReadingEntity getReadingEntity() {
        return readingEntity;
    }

    public void setReadingEntity(ReadingEntity readingEntity) {
        this.readingEntity = readingEntity;
        if(readingEntity.getGsmReadingEntity() != this)
            readingEntity.setGsmReadingEntity(this);
    }
    
}
