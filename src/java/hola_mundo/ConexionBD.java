package hola_mundo;
import java.sql.*;
import java.util.*;
import java.text.SimpleDateFormat; 
import java.util.Date;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
/**
 *
 * @author lenin
 */
public class ConexionBD 
{
    
    //Objeto para conexión con la base de datos
    private Connection conexion = null;
    //Objeto para ejecución de consultas sql, y retorno de resultados que estas produzcan
    private PreparedStatement query=null;
    //Objeto para obtener las filas que retorne la ejecución de la consulta sql
    private ResultSet resultado=null;
    
    //URL de la localización de la base de datos (jdbc:postgresql://host/basededatos)
		//private final String url = "jdbc:postgresql://localhost/catalogar_app";
	private final String url = "jdbc:postgresql://192.168.1.9/catalogar_app";
    //Usuario que se conectará con la base de datos
		//private final String usuario = "postgres";
	//private final String usuario = "encoding_admin";
        private final String usuario = "postgres";
    //Contraseña del usuario
		private final String contrasena = "openxava";
	//private final String contrasena = "admin2015";
  
    private Connection ConexionBD() {
        try {
            Class.forName("org.postgresql.Driver");
   
            //obteniendo una instancia del JDBC y conectando a la base de datos
            return DriverManager.getConnection(url, usuario, contrasena);
            
        }
        catch (SQLException | ClassNotFoundException ex) {
            System.out.println(ex.toString());
        } 
        return null;
       
    }
        
    public ArrayList<String[]> getMessage(int idCliente) {
     try {
          //Abriendo conexion
          conexion = ConexionBD();
          
          query  = conexion.prepareStatement("SELECT * FROM mensaje WHERE cli_id = ? ORDER BY men_fch DESC"); // set the select
          query.setInt(1 , idCliente);// set the parameter avoiding sql injection          
          
          resultado = query.executeQuery(); //return in a resultset scrollable
          
          if (resultado!=null)
          {  
            ArrayList<String[]> lista = new ArrayList<>();
            //Si la consulta no produjo resultados, no se entrara al while
            while(resultado.next())
            {
                 //System.out.println("codigo:"+rs.getString(as)+"nombre:"+rs.getString(as));
                 lista.add(new String[]{
                        resultado.getObject("men_cod").toString(),
                        resultado.getObject("men_asu").toString(),
                        resultado.getObject("men_crp").toString(),
                        resultado.getObject("men_fch").toString(),
                        resultado.getObject("men_est").toString(),
                        resultado.getObject("tme_cod").toString(),
                        resultado.getObject("usu_cod").toString(),
                        resultado.getObject("cli_id").toString()
                 });
            }
            
            return lista;
          }
          
       }catch(SQLException ex){
           System.out.println("ERROR: "+ex.toString());
           
       }finally {
         //Cerrando resultados, sentencias y la conexión a la base de datos
            try {
                if (resultado != null) {
                 resultado.close();
                }
                if (query != null) {
                 query.close();
                }
                if (conexion != null) {
                 conexion.close();
                }

            } catch (SQLException ex) {
             System.out.println("ERROR: "+ex.toString());
            }
        }
        return null;  
    }
    
    public ArrayList<String[]> getEvents(int idCliente, int idEmpresa) {
     try {
          conexion = ConexionBD();          
          
          Date fecha=new Date(); 
          SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
          //query  = conexion.prepareStatement("SELECT * FROM evento_view WHERE eve_fch <= '"+sdf.format(fecha)+"'"); 
          //query  = conexion.prepareStatement("SELECT * FROM get_events('"+idCliente+"') WHERE eve_fch_fin >= '"+sdf.format(fecha)+"'"); 
		query  = conexion.prepareStatement("SELECT * FROM get_events(?,?) WHERE eve_fch_fin >= '"+sdf.format(fecha)+"'"); 
        query.setInt(1 , idCliente);
		query.setInt(2 , idEmpresa);
		resultado = query.executeQuery();    
          
          if (resultado!=null)
          {
            ArrayList<String[]> lista = new ArrayList<>();
            while(resultado.next())
            {
                resultado.getObject("est_ass");
                String tipo_asistencia=(resultado.wasNull())?"0":resultado.getObject("est_ass").toString();
                lista.add(new String[]{
                        resultado.getObject("eve_cod").toString(),
                        resultado.getObject("eve_nom").toString(),
                        resultado.getObject("eve_fch").toString(),
                        resultado.getObject("eve_inf").toString(),
                        resultado.getObject("eve_url_img").toString(),
                        resultado.getObject("suc_nom").toString(),
                        resultado.getObject("suc_dir").toString(),
                        resultado.getObject("suc_tel").toString(),
                        resultado.getObject("ciu_nom").toString(),
                        resultado.getObject("id_detalle_evento_sucursal").toString(),
                        tipo_asistencia,
						resultado.getObject("eve_fch_fin").toString()
                 });
                            System.out.println("fecha inicio::::"+resultado.getObject("eve_fch").toString());
                            System.out.println("fecha fin::::"+resultado.getObject("eve_fch_fin").toString());
                            System.out.println("tipo asistencia::::"+tipo_asistencia);
                            System.out.println("codigo::::"+idCliente);

            }
            return lista;
          }
          
       }catch(SQLException ex){
           System.out.println("ERROR: "+ex.toString());
       }finally {
            try {
                if (resultado != null) {
                 resultado.close();
                }
                if (query != null) {
                 query.close();
                }
                if (conexion != null) {
                 conexion.close();
                }

            } catch (SQLException ex) {
                System.out.println("ERROR: "+ex.toString());
            }
        }
        return null;  
    }
    
    
    //////////// 
    public ArrayList<String[]> getCategory(int idEmpresa) 
    {
     try {
          conexion = ConexionBD();          
   
          query  = conexion.prepareStatement("SELECT * FROM categoria WHERE cat_est=true AND emp_id=?");
		  query.setInt(1 , idEmpresa);
          resultado = query.executeQuery();    
          
          if (resultado!=null)
          {
            ArrayList<String[]> lista = new ArrayList<>();
            while(resultado.next())
            {
                 lista.add(new String[]{
                        resultado.getObject("cat_cod").toString(),
                        resultado.getObject("cat_nom").toString(),
                        resultado.getObject("cat_des").toString(),
                        resultado.getObject("cat_url").toString()
                 });
                 System.out.println(resultado.getObject("cat_url").toString());
            }
            
            return lista;
          }
          
       }catch(SQLException ex){
           System.out.println("ERROR: "+ex.toString());
       }finally {
            try {
                if (resultado != null) {
                 resultado.close();
                }
                if (query != null) {
                 query.close();
                }
                if (conexion != null) {
                 conexion.close();
                }

            } catch (SQLException ex) {
                System.out.println("ERROR: "+ex.toString());
            }
        }
        return null;  
    }
    
    public ArrayList<String []> getOfferts(int idEmpresa)
    {
        try {
          conexion = ConexionBD();          
   
          query  = conexion.prepareStatement("SELECT * FROM product_view WHERE prd_ofr>0 AND emp_id=?");
		  query.setInt(1, idEmpresa);
          resultado = query.executeQuery();    
          
          if (resultado!=null)
          {
            ArrayList<String[]> lista = new ArrayList<>();
            while(resultado.next())
            {
                 lista.add(new String[]{
                        resultado.getObject("prd_cod").toString(),
                        resultado.getObject("prd_nom").toString(),
                        resultado.getObject("prd_des").toString(),
                        resultado.getObject("prd_pre").toString(),
                        resultado.getObject("prd_ofr").toString(),
                        resultado.getObject("prd_est").toString(),
                        resultado.getObject("cat_cod").toString(),
                        resultado.getObject("prd_url").toString(),
                        resultado.getObject("prd_nro_piezas").toString(),
                        resultado.getObject("formas_pago").toString()
                        
                 });
                 System.out.println(resultado.getObject("prd_des").toString());
            }
            
            return lista;
          }
          
       }catch(SQLException ex){
           System.out.println("ERROR: "+ex.toString());
       }finally {
            try {
                if (resultado != null) {
                 resultado.close();
                }
                if (query != null) {
                 query.close();
                }
                if (conexion != null) {
                 conexion.close();
                }

            } catch (SQLException ex) {
                System.out.println("ERROR: "+ex.toString());
            }
        }
        return null;
    }
    
    public ArrayList<String []> getProduct(int idCategoria)
    {
        try {
          conexion = ConexionBD();          
   
          query  = conexion.prepareStatement("SELECT * FROM product_view WHERE cat_cod  = ?"); 
          query.setInt(1, idCategoria);
          resultado = query.executeQuery();    
          
          if (resultado!=null)
          {
            ArrayList<String[]> lista = new ArrayList<>();
            while(resultado.next())
            {
                 lista.add(new String[]{
                        resultado.getObject("prd_cod").toString(),
                        resultado.getObject("prd_nom").toString(),
                        resultado.getObject("prd_des").toString(),
                        resultado.getObject("prd_pre").toString(),
                        resultado.getObject("prd_ofr").toString(),
                        resultado.getObject("prd_est").toString(),
                        resultado.getObject("cat_cod").toString(),
                        resultado.getObject("prd_url").toString(),
                        resultado.getObject("prd_nro_piezas").toString(),
                        resultado.getObject("formas_pago").toString(),
                        
                 });
                 System.out.println(resultado.getObject("prd_des").toString());
            }
            
            return lista;
          }
          
       }catch(SQLException ex){
           System.out.println("ERROR: "+ex.toString());
       }finally {
            try {
                if (resultado != null) {
                 resultado.close();
                }
                if (query != null) {
                 query.close();
                }
                if (conexion != null) {
                 conexion.close();
                }

            } catch (SQLException ex) {
                System.out.println("ERROR: "+ex.toString());
            }
        }
        return null;
    }
    
    public void setUpdateMessageState(int idMessage)
    {
        try
        {
            conexion = ConexionBD();        
            query = conexion.prepareStatement("UPDATE mensaje SET men_est = TRUE WHERE men_cod = ?");
            query.setInt(1, idMessage);
            query.executeUpdate();
            
        }catch(SQLException ex){
           System.out.println("ERROR: "+ex.toString());
       }finally {
            try {
                if (resultado != null) {
                 resultado.close();
                }
                if (query != null) {
                 query.close();
                }
                if (conexion != null) {
                 conexion.close();
                }

            } catch (SQLException ex) {
                System.out.println("ERROR: "+ex.toString());
            }
        }
    }
    
    
    public ArrayList<String[]> getModels(int idProduct) 
    {
     try {
          conexion = ConexionBD();          
   
          query  = conexion.prepareStatement("SELECT * FROM pieza WHERE prd_cod = ?"); 
          query.setInt(1, idProduct);
          resultado = query.executeQuery();    
          
          if (resultado!=null)
          {
            ArrayList<String[]> lista = new ArrayList<>();
            while(resultado.next())
            {
                 lista.add(new String[]{
                        resultado.getObject("pie_cod").toString(),
                        resultado.getObject("pie_nom").toString(),
                        resultado.getObject("pie_num").toString(),
                        resultado.getObject("prd_cod").toString()
                 });
                 System.out.println(resultado.getObject("pie_nom").toString());
            }
            
            return lista;
          }
          
       }catch(SQLException ex){
           System.out.println("ERROR: "+ex.toString());
       }finally {
            try {
                if (resultado != null) {
                 resultado.close();
                }
                if (query != null) {
                 query.close();
                }
                if (conexion != null) {
                 conexion.close();
                }

            } catch (SQLException ex) {
                System.out.println("ERROR: "+ex.toString());
            }
        }
        return null;  
    }
    
    public ArrayList<String[]> setAssist(int idCliente, int codEvent, int imagine) 
    {
	 ArrayList<String[]> retorno = new ArrayList<String[]>();
     try {
          conexion = ConexionBD();          
          query  = conexion.prepareStatement("select insert_or_update_event_asist(?, ?, ?)"); 
          query.setInt(1, idCliente);
          query.setInt(2, codEvent);
          query.setInt(3, imagine);
          resultado = query.executeQuery();
          retorno.add(new String[]{"true"});
       }catch(SQLException ex){
           System.out.println("ERROR: "+ex.toString());
		   retorno.add(new String[]{"false::"+ex.toString()});
       }finally {
            try {
                if (resultado != null) {
                 resultado.close();
                }
                if (query != null) {
                 query.close();
                }
                if (conexion != null) {
                 conexion.close();
                }

            } catch (SQLException ex) {
                System.out.println("ERROR: "+ex.toString());
            }
        }
		return retorno;
    }
        
        
    public ArrayList<String[]> getCities(int idEmpresa) 
    {
     try {
          conexion = ConexionBD();          
   System.out.println(idEmpresa+"");
          query  = conexion.prepareStatement("SELECT * FROM ciudad WHERE emp_id=?"); 
		  query.setInt(1, idEmpresa);
          resultado = query.executeQuery();    
          
          if (resultado!=null)
          {
            ArrayList<String[]> lista = new ArrayList<>();
            while(resultado.next())
            {
                 lista.add(new String[]{
                        resultado.getObject("ciu_cod").toString(),
                        resultado.getObject("ciu_nom").toString()
                 });
                 System.out.println(resultado.getObject("ciu_nom").toString());
            }
            
            return lista;
          }
          
       }catch(SQLException ex){
           System.out.println("ERROR: "+ex.toString());
       }finally {
            try {
                if (resultado != null) {
                 resultado.close();
                }
                if (query != null) {
                 query.close();
                }
                if (conexion != null) {
                 conexion.close();
                }

            } catch (SQLException ex) {
                System.out.println("ERROR: "+ex.toString());
            }
        }
        return null;  
    }
    
    public ArrayList<String[]> getOffices(long idCity) 
    {
     try {
          conexion = ConexionBD();          
   
          query  = conexion.prepareStatement("SELECT * FROM sucursal where  ciu_cod=?"); 
          query.setLong(1, idCity);
          resultado = query.executeQuery();    
          
          if (resultado!=null)
          {
            ArrayList<String[]> lista = new ArrayList<>();
            while(resultado.next())
            {
                 lista.add(new String[]{
                        resultado.getObject("suc_cod").toString(),
                        resultado.getObject("suc_nom").toString(),
                        resultado.getObject("suc_dir").toString(),
                        resultado.getObject("suc_tel").toString(),
                        resultado.getObject("suc_lat").toString(),
                        resultado.getObject("suc_lng").toString()
                 });
                 System.out.println(resultado.getObject("suc_nom").toString());
            }
            
            return lista;
          }
          
       }catch(SQLException ex){
           System.out.println("ERROR: "+ex.toString());
       }finally {
            try {
                if (resultado != null) {
                 resultado.close();
                }
                if (query != null) {
                 query.close();
                }
                if (conexion != null) {
                 conexion.close();
                }

            } catch (SQLException ex) {
                System.out.println("ERROR: "+ex.toString());
            }
        }
        return null;  
    }
	
	public ArrayList<String[]> getProfile(long idCliente) 
    {
     try {
         System.out.println(idCliente+"");
          conexion = ConexionBD();          
          query  = conexion.prepareStatement("SELECT * FROM cliente where  cli_id=?"); 
          query.setLong(1, idCliente);
          resultado = query.executeQuery();    
          if (resultado!=null)
          {
            ArrayList<String[]> lista = new ArrayList<>();
            if(resultado.next())
            {
                System.out.println("entra");
                System.out.println(resultado.getObject("cli_eml").toString());
                resultado.getObject("cli_nom");
                String nombre= resultado.wasNull()?" ":resultado.getObject("cli_nom").toString();
                resultado.getObject("cli_ape");
                String apellido= resultado.wasNull()?" ":resultado.getObject("cli_ape").toString();
                resultado.getObject("cli_tel");
                String telefono= resultado.wasNull()?" ":resultado.getObject("cli_tel").toString();
                resultado.getObject("cli_dir");
                String direccion= resultado.wasNull()?" ":resultado.getObject("cli_dir").toString();
                lista.add(new String[]{nombre, apellido, telefono, direccion});
            }
            return lista;
          }
          
       }catch(SQLException ex){
           System.out.println("ERROR: "+ex.toString());
       }finally {
            try {
                if (resultado != null) {
                 resultado.close();
                }
                if (query != null) {
                 query.close();
                }
                if (conexion != null) {
                 conexion.close();
                }

            } catch (SQLException ex) {
                System.out.println("ERROR: "+ex.toString());
            }
        }
        return null;  
    }
	
    public ArrayList<String[]> setProfile(String nombre, String apellido, String telefono, String direccion, int idCliente) 
    {
     try {
          conexion = ConexionBD();          
   
          query  = conexion.prepareStatement("SELECT * FROM update_profile(?,?,?,?,?)"); 
          query.setString(1, nombre);
		  query.setString(2, apellido);
		  query.setString(3, telefono);
		  query.setString(4, direccion);
		  query.setInt(5, idCliente);
		  System.out.println("1");
          resultado = query.executeQuery();    
		  System.out.println("2");
          ArrayList<String[]> lista = new ArrayList<>();
		  System.out.println("3");
		  lista.add(new String[]{"true"});
		  System.out.println("4");
          return lista;
       }
	   catch(SQLException ex)
	   {
           System.out.println("ERROR: "+ex.toString());
       }
	   finally 
	   {
            try {
                if (resultado != null) {
                 resultado.close();
                }
                if (query != null) {
                 query.close();
                }
                if (conexion != null) {
                 conexion.close();
                }

            } catch (SQLException ex) {
                System.out.println("ERROR: "+ex.toString());
            }
        }
        return null;  
    }
  
    public ArrayList<String[]> createClient(String email, long idEmpresa) 
    {
     try {
         System.out.println("ema::"+email);
          conexion = ConexionBD();          
   
          query  = conexion.prepareStatement("SELECT * FROM crear_cliente(?,?)"); 
          query.setString(1, email);
		  query.setLong(2, idEmpresa);
          resultado = query.executeQuery();    
          
          if (resultado!=null)
          {
            ArrayList<String[]> lista = new ArrayList<>();
            if(resultado.next())
            {
                if(resultado.getInt("crear_cliente")!=0)
                {lista.add(new String[]{resultado.getInt("crear_cliente")+""});
                 
                }System.out.println(resultado.getInt("crear_cliente")+"");
            }
            
            return lista;
          }
          
       }catch(SQLException ex){
           System.out.println("ERROR: "+ex.toString());
       }finally {
            try {
                if (resultado != null) {
                 resultado.close();
                }
                if (query != null) {
                 query.close();
                }
                if (conexion != null) {
                 conexion.close();
                }

            } catch (SQLException ex) {
                System.out.println("ERROR: "+ex.toString());
            }
        }
        return null;  
    }
    
    public ArrayList<String[]> getSchedule(long idOffice) 
    {
     try {
          conexion = ConexionBD();          
   
          query  = conexion.prepareStatement("SELECT * FROM horario where  suc_cod=?"); 
          query.setLong(1, idOffice);
          resultado = query.executeQuery();    
          
          if (resultado!=null)
          {
            ArrayList<String[]> lista = new ArrayList<>();
            while(resultado.next())
            {
                 lista.add(new String[]{
                        resultado.getObject("hor_cod").toString(),
                        resultado.getObject("hor_lun").toString(),
                        resultado.getObject("hor_mar").toString(),
                        resultado.getObject("hor_mie").toString(),
                        resultado.getObject("hor_jue").toString(),
                        resultado.getObject("hor_vie").toString(),
                        resultado.getObject("hor_sab").toString(),
                        resultado.getObject("hor_dom").toString(),
                        resultado.getObject("suc_cod").toString()
                 });
                 System.out.println(resultado.getObject("suc_cod").toString());
            }
            
            return lista;
          }
          
       }catch(SQLException ex){
           System.out.println("ERROR: "+ex.toString());
       }finally {
            try {
                if (resultado != null) {
                 resultado.close();
                }
                if (query != null) {
                 query.close();
                }
                if (conexion != null) {
                 conexion.close();
                }

            } catch (SQLException ex) {
                System.out.println("ERROR: "+ex.toString());
            }
        }
        return null;  
    }

    public ArrayList<String[]> enviarCorreo(String para)
    {
                System.out.println("email: "+para);
		String codigo=getCodeCount(para);
		ArrayList<String[]> lista = new ArrayList<>();
		//String user="info@encodingideas.heliohost.org", pass="encid2015";
         String user="alex.nb.92@gmail.com", pass="21792Alexander";
        // La dirección de envío (to)
        //String para = "alex_nb_92@hotmail.com";

        // La dirección de la cuenta de envío (from)
        String de = "alex.nb.92@gmail.com";

        // El servidor (host). En este caso usamos localhost
        String host = "smtp.gmail.com";

        // Obtenemos las propiedades del sistema
        Properties propiedades = System.getProperties();

        // Configuramos el servidor de correo
        propiedades.setProperty("mail.smtp.auth", "true");
        //propiedades.setProperty("mail.smtp.starttls.enable", "true");
        propiedades.setProperty("mail.smtp.socketFactory.port", "465");
        propiedades.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        //propiedades.setProperty("mail.smtp.socketFactory.fallback", "false");
        propiedades.setProperty("mail.smtp.host", host);
        propiedades.setProperty("mail.smtp.port", "465");
        //propiedades.setProperty("mail.user", "info@encodingideas.heliohost.org"); propiedades.setProperty("mail.password", "encid2015");

        // Obtenemos la sesión por defecto
        //Session sesion = Session.getDefaultInstance(propiedades);}
        Session sesion = Session.getInstance(propiedades, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(user, pass);
            }
        });
        sesion.setDebug(true);

        try{
          // Creamos un objeto mensaje tipo MimeMessage por defecto.
          MimeMessage mensaje = new MimeMessage(sesion);

          // Asignamos el “de o from” al header del correo.
          mensaje.setFrom(new InternetAddress(de));

          // Asignamos el “para o to” al header del correo.
          mensaje.addRecipient(Message.RecipientType.TO, new InternetAddress(para));

          // Asignamos el asunto
          mensaje.setSubject(".::HogAR::.");

          // Asignamos el mensaje como tal
          //mensaje.setText("Codigo::"+codigo);
		  mensaje.setContent("<div style=\"margin:100px;\">"
                          + "<div align=\"center\">"
                          + "<img src=\"http://encodingideas.heliohost.org/logo_catalogar.png\" height=\"200\"/>"
                          + "</div>"
                          + "<h1 align=\"center\"><b>CatalogAR</b></h1>"
                          + "<br/><br/>"
                          + "<h3 style=\"color:#333;\">Te damos la bienvenida a HogAR, tu revista de muebles y decoraciones interactiva.</h3>"
                          + "<h3 style=\"color:#333;\">El codigo para la activación de tu cuenta es:<b>"+codigo+"</b></h3>"
                          + "<div align=\"right\">"
                          + "<img src=\"http://encodingideas.heliohost.org/logoEI.png\" height=\"150\"/>"
                          + "</div></div>","text/html" );

          // Enviamos el correo
          //Transport transport = sesion.getTransport("smtp");
          //transport.connect(host, 465, user, pass);
          Transport.send(mensaje);
          //transport.sendMessage(mensaje, mensaje.getAllRecipients());
          //transport.close();
          System.out.println("Mensaje enviado");
		  lista.add(new String[]{"true"});
        } catch (MessagingException e) {
          e.printStackTrace();
		  lista.add(new String[]{"false"});
        }
        //
        return lista;
    }
    
	private String getCodeCount(String email) 
	{
            try{
		MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(email.getBytes());
 
        byte byteData[] = md.digest();
 
        //convert the byte to hex format method 1
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
		int contador=1;
		String codigo="";
		for(int x=0; x<sb.length(); x++)
		{
			if(Character.isDigit(sb.charAt(x)) && contador<=4)
			{
				codigo+=sb.charAt(x);
				contador++;
			}
		}
		System.out.println("codigo: "+codigo);
		return codigo;
            }
            catch(Exception ex)
            {
				System.out.println("error::"+ex.toString());
                return "";
            }
	}
	
	public ArrayList<String[]> setOrder(long idCliente, long idEmpresa, String product, String precios, String cantidad) 
    {
         System.out.println("prd: "+product);
          System.out.println("prc "+precios);
           System.out.println("cnt "+cantidad);
         ArrayList<String[]> retorno = new ArrayList<String[]>();
     try {
	  Date date = new Date();  // wherever you get this
	  DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	  String fecha = df.format(date);
          System.out.println("fch "+fecha);
          conexion = ConexionBD();          
          //query  = conexion.prepareStatement("select insert_order(?, ?, '"+product+"' :: bigint[], '"+precios+"' :: numeric[], '"+cantidad+"' :: integer[], '"+fecha+"' :: date)"); 
		  query  = conexion.prepareStatement("select insert_order( ? :: bigint[], ?, ?, ? :: numeric[], ? :: integer[], ? :: date)"); 
          query.setString(1, product);
          query.setLong(2, idCliente);
		  query.setLong(3, idEmpresa);
		  query.setString(4, precios);
		  query.setString(5, cantidad);
		  query.setString(6, fecha);
          resultado = query.executeQuery();
          retorno.add(new String[]{"true"});
       }catch(SQLException ex){
           System.out.println("ERROR: "+ex.toString());
		   retorno.add(new String[]{"false::"+ex.toString()});
       }finally {
            try {
                if (resultado != null) {
                 resultado.close();
                }
                if (query != null) {
                 query.close();
                }
                if (conexion != null) {
                 conexion.close();
                }

            } catch (SQLException ex) {
                System.out.println("ERROR: "+ex.toString());
            }
        }
		return retorno;
    }
}
