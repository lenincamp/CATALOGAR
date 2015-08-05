package hola_mundo;

import java.util.ArrayList;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author lenin
 */
@WebService(serviceName = "ServicioWeb")
public class ServicioWeb 
{
    @WebMethod(operationName = "get_messages")
    public ArrayList<String []> getMessages(@WebParam(name = "idPhone") int idPhone)
    {
        ConexionBD connection = new ConexionBD();
        ArrayList<String []> data;
        data = connection.getMessage(idPhone);
        return data;
    }

    @WebMethod(operationName = "getEvents")
    public ArrayList<String []> getEvents(@WebParam(name = "idCliente")int idCliente, @WebParam(name = "idEmpresa")int idEmpresa) {
        ConexionBD connection = new ConexionBD();
        ArrayList<String []> data;
        data = connection.getEvents(idCliente, idEmpresa);
        return data;
    }
    
    @WebMethod(operationName = "getCategory")
    public ArrayList<String []> getCategory(@WebParam(name = "idEmpresa")int idEmpresa) 
    {
        ConexionBD connection = new ConexionBD();
        ArrayList<String []> data;
        data = connection.getCategory(idEmpresa);
        return data;
    }
    
    @WebMethod(operationName = "getProduct")
    public ArrayList<String []> getProduct(@WebParam(name = "idCategory")int idCategory) 
    {
        ConexionBD connection = new ConexionBD();
        ArrayList<String []> data;       
        data = connection.getProduct(idCategory);        
        return data;
    }
    
    @WebMethod(operationName = "getOfferts")
    public ArrayList<String []> getOfferts(@WebParam(name = "idEmpresa")int idEmpresa) 
    {
        ConexionBD connection = new ConexionBD();
        return connection.getOfferts(idEmpresa);        
    }
    
    @WebMethod(operationName = "updateMessageState")
    public void updateMessageState(@WebParam(name = "idMessage")int idMessage)
    {
        ConexionBD connection = new ConexionBD();
        connection.setUpdateMessageState(idMessage);        
    }
    
    @WebMethod(operationName = "getModelsProduct")
    public ArrayList<String []> getModelsProduct(@WebParam(name = "idProduct") int idProduct)
    {
        ConexionBD connection = new ConexionBD();
        ArrayList<String []> data;
        data = connection.getModels(idProduct);
        return data;
    }
    
    @WebMethod(operationName = "getCities")
    public ArrayList<String []> getCities(@WebParam(name = "idEmpresa") int idEmpresa)
    {
        ConexionBD connection = new ConexionBD();
        return connection.getCities(idEmpresa);
    }
    
    @WebMethod(operationName = "getOffices")
    public ArrayList<String []> getOffices(@WebParam(name = "idCity") long idCity)
    {
        ConexionBD connection = new ConexionBD();
        return connection.getOffices(idCity);
    }
    
    @WebMethod(operationName = "getSchedule")
    public ArrayList<String []> getSchedule(@WebParam(name = "idOffice") long idOffice)
    {
        ConexionBD connection = new ConexionBD();
        return connection.getSchedule(idOffice);
    }
    
    @WebMethod(operationName = "setAssist")
    public ArrayList<String []> setAssist(@WebParam(name = "phone") int idcliente, @WebParam(name = "codEvent")int codEvent, @WebParam(name = "imagine") int imagine)
    {
        ConexionBD connection = new ConexionBD();
        return connection.setAssist(idcliente, codEvent, imagine);
    }
	
    @WebMethod(operationName = "enviarEmail")
    public ArrayList<String []> enviarEmail(@WebParam(name = "email") String email)
    {
        ConexionBD connection = new ConexionBD();
        return connection.enviarCorreo(email);
    }
    
    @WebMethod(operationName = "createClient")
    public ArrayList<String []> createClient(@WebParam(name = "email") String email, @WebParam(name = "idEmpresa") long idEmpresa)
    {
        ConexionBD connection = new ConexionBD();
        return connection.createClient(email, idEmpresa);
    }
	
	@WebMethod(operationName = "setProfile")
    public ArrayList<String []> setProfile(@WebParam(name = "nombre") String nombre, @WebParam(name = "apellido") String apellido, @WebParam(name = "telefono") String telefono, @WebParam(name = "direccion") String direccion, @WebParam(name = "idCliente") int idCliente)
	{
		ConexionBD connection = new ConexionBD();
        return connection.setProfile(nombre, apellido, telefono, direccion, idCliente);
	}
	
	@WebMethod(operationName = "getProfile")
    public ArrayList<String []> getProfile(@WebParam(name = "idCliente") long idCliente)
	{
		ConexionBD connection = new ConexionBD();
        return connection.getProfile(idCliente);
	}
    
    	@WebMethod(operationName = "setOrder")
    public ArrayList<String []> setOrder(@WebParam(name = "idCliente") long idCliente, @WebParam(name = "idEmpresa") long idEmpresa, @WebParam(name = "products") String productos, @WebParam(name = "precios") String precios, @WebParam(name = "cantidad") String cantidad)
	{
		ConexionBD connection = new ConexionBD();
        return connection.setOrder(idCliente, idEmpresa, productos, precios, cantidad);
	}

}
