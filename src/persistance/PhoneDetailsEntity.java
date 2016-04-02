/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistance;

import ipsocketmessage.PhoneDetails;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author victor
 */
@Entity
@Table(name = "phone_details")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PhoneDetailsEntity.findAll", query = "SELECT p FROM PhoneDetailsEntity p"),
    @NamedQuery(name = "PhoneDetailsEntity.findById", query = "SELECT p FROM PhoneDetailsEntity p WHERE p.id = :id"),
    @NamedQuery(name = "PhoneDetailsEntity.findByImei", query = "SELECT p FROM PhoneDetailsEntity p WHERE p.imei = :imei"),
    @NamedQuery(name = "PhoneDetailsEntity.findByName", query = "SELECT p FROM PhoneDetailsEntity p WHERE p.name = :name")})
public class PhoneDetailsEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name = "imei")
    private String imei;
    @Column(name = "name")
    private String name;
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "phoneDetailsEntity")
    private ReadingEntity readingEntity;

    public PhoneDetailsEntity() {
    }
    
    public PhoneDetailsEntity(PhoneDetails phoneDetails){
        imei= phoneDetails.getImei();
        name = phoneDetails.getPhoneName();
    }

    public PhoneDetailsEntity(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ReadingEntity getReadingEntity() {
        return readingEntity;
    }

    public void setReadingEntity(ReadingEntity readingEntity) {
        this.readingEntity = readingEntity;
        if(readingEntity.getPhoneDetailsEntity() != this)
            readingEntity.setPhoneDetailsEntity(this);
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
        if (!(object instanceof PhoneDetailsEntity)) {
            return false;
        }
        PhoneDetailsEntity other = (PhoneDetailsEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "persistance.PhoneDetailsEntity[ id=" + id + " ]";
    }
    
}
