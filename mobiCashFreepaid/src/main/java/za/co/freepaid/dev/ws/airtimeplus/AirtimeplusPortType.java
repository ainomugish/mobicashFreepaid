
package za.co.freepaid.dev.ws.airtimeplus;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.8
 * Generated source version: 2.2
 * 
 */
@WebService(name = "airtimeplusPortType", targetNamespace = "https://ws.freepaid.co.za/airtimeplus/")
@SOAPBinding(style = SOAPBinding.Style.RPC)
@XmlSeeAlso({
    org.xmlsoap.schemas.soap.encoding.ObjectFactory.class,
    za.co.freepaid.dev.ws.airtimeplus.ObjectFactory.class
})
public interface AirtimeplusPortType {


    /**
     * Fetch your current balance. 'user' is your user number and 'pass' is your password. 'status' will always contain the result of the request: 1 = success, 0 = failure. 'errorcode' is a secondary code describing e.g. why a request failed. 'message' is an extended status message. 'balance' is your current balance, assuming it got that far in the request. For sample code, please visit https://ws.freepaid.co.za/airtimeplus/sample.html
     * 
     * @param request
     * @return
     *     returns za.co.freepaid.dev.ws.airtimeplus.FetchBalanceOut
     */
    @WebMethod(action = "https://ws.freepaid.co.za/airtimeplus/fetchBalance")
    @WebResult(name = "reply", partName = "reply")
    public FetchBalanceOut fetchBalance(
        @WebParam(name = "request", partName = "request")
        FetchBalanceIn request);

    /**
     * Place order for multiple vouchers. 'user' is your user number and 'pass' is your password. 'refno' should be a unique reference number for the request, but uniqueness is not enforced. 'network' is the voucher network. 'sellvalue' is the voucher sell value. 'count' is the number of vouchers. 'extra' is required in certain cases, eg. pinless recharges. 'status' will always contain the result of the request: 1 = success, 0 = failure. 'errorcode' is a secondary code describing e.g. why a request failed. 'message' is an extended status message. 'balance' is the remaining balance after the voucher request, regardless of whether the request succeeded or not, assuming it got that far in the request. 'orderno' is our unique order number, to be used in the fetchOrder call. Pinless orders must use the queryOrder call for proper status. For sample code, please visit https://ws.freepaid.co.za/airtimeplus/sample.html
     * 
     * @param request
     * @return
     *     returns za.co.freepaid.dev.ws.airtimeplus.PlaceOrderOut
     */
    @WebMethod(action = "https://ws.freepaid.co.za/airtimeplus/placeOrder")
    @WebResult(name = "reply", partName = "reply")
    public PlaceOrderOut placeOrder(
        @WebParam(name = "request", partName = "request")
        PlaceOrderIn request);

    /**
     * Returns order placed with placeOrder call. 'user' is your user number and 'pass' is your password. 'orderno' should be the unique order number returned in the placeOrder call. 'status' will always contain the result of the request: 1 = success, 0 = failure. 'errorcode' is a secondary code describing e.g. why a request failed. 'message' is an extended status message. 'balance' is your current balance, assuming it got that far in the request. 'orderno' contains the number of the order that was fetched. 'vouchers' refers to the issued vouchers. For sample code, please visit https://ws.freepaid.co.za/airtimeplus/sample.html
     * 
     * @param request
     * @return
     *     returns za.co.freepaid.dev.ws.airtimeplus.FetchOrderOut
     */
    @WebMethod(action = "https://ws.freepaid.co.za/airtimeplus/fetchOrder")
    @WebResult(name = "reply", partName = "reply")
    public FetchOrderOut fetchOrder(
        @WebParam(name = "request", partName = "request")
        FetchOrderIn request);

    /**
     * Returns order placed with placeOrder call. 'user' is your user number and 'pass' is your password. 'last' should be the last order number you've fetched from us. 'status' will always contain the result of the request: 1 = success, 0 = failure. 'errorcode' is a secondary code describing e.g. why a request failed. 'message' is an extended status message. 'balance' is your current balance, assuming it got that far in the request. 'orderno' contains the number of the order that was fetched. 'vouchers' refers to the issued vouchers. For sample code, please visit https://ws.freepaid.co.za/airtimeplus/sample.html
     * 
     * @param request
     * @return
     *     returns za.co.freepaid.dev.ws.airtimeplus.FetchOrderOut
     */
    @WebMethod(action = "https://ws.freepaid.co.za/airtimeplus/fetchOrderLatest")
    @WebResult(name = "reply", partName = "reply")
    public FetchOrderOut fetchOrderLatest(
        @WebParam(name = "request", partName = "request")
        FetchOrderLatestIn request);

    /**
     * Query pinless order placed with placeOrder call. 'user' is your user number and 'pass' is your password. 'orderno' should be the unique order number returned in the placeOrder call. 'status' will always contain the result of the request: 1 = success, 0 = failure. 'errorcode' is a secondary code describing e.g. why a request failed. 'message' is an extended status message. 'balance' is your current balance, assuming it got that far in the request. For sample code, please visit https://ws.freepaid.co.za/airtimeplus/sample.html
     * 
     * @param request
     * @return
     *     returns za.co.freepaid.dev.ws.airtimeplus.QueryOrderOut
     */
    @WebMethod(action = "https://ws.freepaid.co.za/airtimeplus/queryOrder")
    @WebResult(name = "reply", partName = "reply")
    public QueryOrderOut queryOrder(
        @WebParam(name = "request", partName = "request")
        QueryOrderIn request);

    /**
     * Fetch list of products. 'user' is your user number and 'pass' is your password. 'status' will always contain the result of the request: 1 = success, 0 = failure. 'errorcode' is a secondary code describing e.g. why a request failed. 'message' is an extended status message. 'balance' is your current balance, assuming it got that far in the request. 'products' is a list of products. Note that the cost price is not always available and will be shown as a 0 when not known. For sample code, please visit https://ws.freepaid.co.za/airtimeplus/sample.html
     * 
     * @param request
     * @return
     *     returns za.co.freepaid.dev.ws.airtimeplus.FetchProductsOut
     */
    @WebMethod(action = "https://ws.freepaid.co.za/airtimeplus/fetchProducts")
    @WebResult(name = "reply", partName = "reply")
    public FetchProductsOut fetchProducts(
        @WebParam(name = "request", partName = "request")
        FetchProductsIn request);

}
