package dnt.itsnow.model;

import dnt.itsnow.platform.model.Record;

/**
 * The ServiceCatalog Object
 */
public class ServiceCatalog extends Record {

    private String    scName;
    private String    scDesc;

    public String getScName() {
        return scName;
    }

    public void setScName(String scName) {
        this.scName = scName;
    }

    public String getScDesc() {
        return scDesc;
    }

    public void setScDesc(String scDesc) {
        this.scDesc = scDesc;
    }



}
