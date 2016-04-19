/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistance;

import ipsocketmessage.ARMarkerReading;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import utils.MatrixToStringConverter;

/**
 *
 * @author victor
 */
@Entity
@Table(name = "ar_marker_reading")
public class ARMarkerReadingEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private int direction;
    private long markerId;
    private String vertex="";
    private String transMat="";
    //private String cameraTransMat="";
    
    @OneToOne(fetch=FetchType.LAZY)
    private ImageEntity image;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reading_id")
    private ReadingEntity readingEntity;
    
    public ARMarkerReadingEntity(){}
    
    public ARMarkerReadingEntity(ARMarkerReading arMarkerReading){
        direction = arMarkerReading.getDirection();
        markerId = arMarkerReading.getMarkerId();
        
        vertex = MatrixToStringConverter.convertToString(arMarkerReading.getVertex(), ";", "|");
        transMat = MatrixToStringConverter.convertToString(arMarkerReading.getTransMat(), ";", "|");
        //cameraTransMat = MatrixToStringConverter.convertToString(arMarkerReading.getCameraTransMat(), ";", "|");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public long getMarkerId() {
        return markerId;
    }

    public void setMarkerId(long markerId) {
        this.markerId = markerId;
    }

    public String getVertex() {
        return vertex;
    }

    public void setVertex(String vertex) {
        this.vertex = vertex;
    }

    public String getTransMat() {
        return transMat;
    }
    
    public double[][] getTransMatAsVector(){
        return MatrixToStringConverter.convertToMatrix(transMat, ";", "|");
    }

    public void setTransMat(String transMat) {
        this.transMat = transMat;
    }

    public ImageEntity getImage() {
        return image;
    }

    public void setImage(ImageEntity image) {
        if(image == null)
            return;
        
        this.image = image;
        image.setArMarker(ARMarkerReadingEntity.this);
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
        if (!(object instanceof ARMarkerReadingEntity)) {
            return false;
        }
        ARMarkerReadingEntity other = (ARMarkerReadingEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "persistance.ArMarkerReadingEntity[ id=" + id + " ]";
    }

    public ReadingEntity getReadingEntity() {
        return readingEntity;
    }

    public void setReadingEntity(ReadingEntity readingEntity) {
        this.readingEntity = readingEntity;
        if(!readingEntity.getArMarkerReadingEntitys().contains(this))
            readingEntity.getArMarkerReadingEntitys().add(this);
    }
    
}
