/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistance;

import ipsocketmessage.WifiReading;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author victor
 */
@Entity
@Table(name = "wifi_reading")
@NamedQueries({
    @NamedQuery(name = "WifiReadingEntity.findAllWhereIn", query = "SELECT w FROM WifiReadingEntity w WHERE w.bssid IN :bssids ")//AND w.readingEntity.readingType = :readingType
})
public class WifiReadingEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String bssid;
    private String ssid;
    private int level;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reading_id")
    private ReadingEntity readingEntity;

    public WifiReadingEntity() {}
    
    public WifiReadingEntity(WifiReading wifiReading){
        bssid = wifiReading.getBSSID();
        ssid = wifiReading.getSSID();
        level = wifiReading.getLevel();
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
        if (!(object instanceof WifiReadingEntity)) {
            return false;
        }
        WifiReadingEntity other = (WifiReadingEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "persistance.WifiReadingEntity[ id=" + id + " ]";
    }

    public String getBssid() {
        return bssid;
    }

    public void setBssid(String bssid) {
        this.bssid = bssid;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public ReadingEntity getReadingEntity() {
        return readingEntity;
    }

    public void setReadingEntity(ReadingEntity readingEntity) {
        this.readingEntity = readingEntity;
        if(!readingEntity.getWifiReadings().contains(this))
            readingEntity.getWifiReadings().add(this);
    }
    
}
