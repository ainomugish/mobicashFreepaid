
package za.co.freepaid.dev.ws.airtimeplus;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for fetchOrderOut complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="fetchOrderOut">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="status" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="errorcode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="message" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="balance" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="orderno" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="vouchers" type="{http://ws.dev.freepaid.co.za/airtimeplus/}vouchers"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "fetchOrderOut", propOrder = {

})
public class FetchOrderOut {

    protected int status;
    @XmlElement(required = true)
    protected String errorcode;
    @XmlElement(required = true)
    protected String message;
    protected float balance;
    @XmlElement(required = true)
    protected String orderno;
    @XmlElement(required = true)
    protected Vouchers vouchers;

    /**
     * Gets the value of the status property.
     * 
     */
    public int getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     */
    public void setStatus(int value) {
        this.status = value;
    }

    /**
     * Gets the value of the errorcode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorcode() {
        return errorcode;
    }

    /**
     * Sets the value of the errorcode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorcode(String value) {
        this.errorcode = value;
    }

    /**
     * Gets the value of the message property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the value of the message property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessage(String value) {
        this.message = value;
    }

    /**
     * Gets the value of the balance property.
     * 
     */
    public float getBalance() {
        return balance;
    }

    /**
     * Sets the value of the balance property.
     * 
     */
    public void setBalance(float value) {
        this.balance = value;
    }

    /**
     * Gets the value of the orderno property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderno() {
        return orderno;
    }

    /**
     * Sets the value of the orderno property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderno(String value) {
        this.orderno = value;
    }

    /**
     * Gets the value of the vouchers property.
     * 
     * @return
     *     possible object is
     *     {@link Vouchers }
     *     
     */
    public Vouchers getVouchers() {
        return vouchers;
    }

    /**
     * Sets the value of the vouchers property.
     * 
     * @param value
     *     allowed object is
     *     {@link Vouchers }
     *     
     */
    public void setVouchers(Vouchers value) {
        this.vouchers = value;
    }

}
