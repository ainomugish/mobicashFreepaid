
package za.co.freepaid.dev.ws.airtimeplus;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for placeOrderIn complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="placeOrderIn">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="user" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="pass" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="refno" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="network" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="sellvalue" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="count" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="extra" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "placeOrderIn", propOrder = {

})
public class PlaceOrderIn {

    protected int user;
    @XmlElement(required = true)
    protected String pass;
    @XmlElement(required = true)
    protected String refno;
    @XmlElement(required = true)
    protected String network;
    protected float sellvalue;
    protected int count;
    @XmlElement(required = true)
    protected String extra;

    /**
     * Gets the value of the user property.
     * 
     */
    public int getUser() {
        return user;
    }

    /**
     * Sets the value of the user property.
     * 
     */
    public void setUser(int value) {
        this.user = value;
    }

    /**
     * Gets the value of the pass property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPass() {
        return pass;
    }

    /**
     * Sets the value of the pass property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPass(String value) {
        this.pass = value;
    }

    /**
     * Gets the value of the refno property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRefno() {
        return refno;
    }

    /**
     * Sets the value of the refno property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRefno(String value) {
        this.refno = value;
    }

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
     * Gets the value of the count property.
     * 
     */
    public int getCount() {
        return count;
    }

    /**
     * Sets the value of the count property.
     * 
     */
    public void setCount(int value) {
        this.count = value;
    }

    /**
     * Gets the value of the extra property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExtra() {
        return extra;
    }

    /**
     * Sets the value of the extra property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExtra(String value) {
        this.extra = value;
    }

}
