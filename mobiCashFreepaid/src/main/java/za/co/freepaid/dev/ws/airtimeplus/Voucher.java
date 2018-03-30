
package za.co.freepaid.dev.ws.airtimeplus;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for voucher complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="voucher">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="network" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="sellvalue" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="pin" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="serial" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="costprice" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "voucher", propOrder = {

})
public class Voucher {

    @XmlElement(required = true)
    protected String network;
    protected float sellvalue;
    @XmlElement(required = true)
    protected String pin;
    @XmlElement(required = true)
    protected String serial;
    protected float costprice;

    /**
     * Gets the value of the network property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNetwork() {
        return network;
    }

    /**
     * Sets the value of the network property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNetwork(String value) {
        this.network = value;
    }

    /**
     * Gets the value of the sellvalue property.
     * 
     */
    public float getSellvalue() {
        return sellvalue;
    }

    /**
     * Sets the value of the sellvalue property.
     * 
     */
    public void setSellvalue(float value) {
        this.sellvalue = value;
    }

    /**
     * Gets the value of the pin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPin() {
        return pin;
    }

    /**
     * Sets the value of the pin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPin(String value) {
        this.pin = value;
    }

    /**
     * Gets the value of the serial property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSerial() {
        return serial;
    }

    /**
     * Sets the value of the serial property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSerial(String value) {
        this.serial = value;
    }

    /**
     * Gets the value of the costprice property.
     * 
     */
    public float getCostprice() {
        return costprice;
    }

    /**
     * Sets the value of the costprice property.
     * 
     */
    public void setCostprice(float value) {
        this.costprice = value;
    }

}
