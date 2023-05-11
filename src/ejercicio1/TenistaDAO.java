package ejercicio1;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TenistaDAO {
	//CREAMOS EN LA BASE DE DATOS LA DB y creamos la tabla tenista con todos sus atributos
	
	private Connection conexion=null;

	
	public TenistaDAO() {
		conexion=crearConexion(); // crear conexion y devueLVEe el objeto
	}
	
	
	
	private Connection crearConexion() { //PRIVATE
		Connection conexion=null;
		String url="jdbc:mysql://localhost:3306/ATP";
		String usuario="root";
		String password="root";
		try {
			conexion=DriverManager.getConnection(url, usuario, password);
		} catch (SQLException e) {
			System.out.println(e);
		}
		return conexion;
	}
	
	
	
	
	//CRUD // con el prepareStatement_APUNTES CON STATEMENT
	public boolean crear(Tenista t) {
		
		int valores = 0; // es el numero de valores q se han modificado	
		
		try {
			
			String sql="INSERT INTO tenista(idTenista, nombre, fechaNacimiento, puntos, titulosGS) VALUES (?,?,?,?,?)";
			PreparedStatement statement = conexion.prepareStatement(sql);
			statement.setInt(1, t.getIdTenista());
			statement.setString(2, t.getNombre());
			statement.setDate(3, t.getFechaNacimiento());
			statement.setInt(4, t.getPuntos());
			statement.setInt(5, t.getTitulosGS());
			
			int resultado = statement.executeUpdate(); // para hacer modificaciones en DB, exexuteUpdate y para recuperar datos es executeQuery()

		} catch (SQLException e) {
			System.out.println(e);
		}
		
		
		return valores==1?true:false; //si valor es == 1 devuelve true y sino devuelve false
	}
	
	
	//LEER
	public Tenista leer(int idTenista) {
		Tenista tenista=null;
		try {
			
			String sql="SELECT * FROM tenista WHERE idTenista = " + idTenista;
			Statement statement = conexion.createStatement();
			ResultSet resultados = statement.executeQuery(sql);
			
			//desde ResultSet leemos los objetos
			if(resultados.next()) {
				String nombre = resultados.getString(2); //IDCOLUMNA de nombre es la columna nº2
				Date fechaNacimiento = resultados.getDate(3);
				int puntos = resultados.getInt(4);
				int titulosGS = resultados.getInt(5);
				
				tenista= new Tenista(idTenista,nombre,fechaNacimiento,puntos,titulosGS);
			}
		
		
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
		return tenista;
	}
	
	//ACTUALIZAR
	public boolean actualizar(Tenista t) { //con PrepareStatement
			
		int valores = 0;
		
		try {
			String sql = "UPDATE tenista SET nombre=?, fechaNacimiento=?, puntos=?, titulosGS=? WHERE idTenista=?";
			PreparedStatement statement = conexion.prepareStatement(sql);
			
			statement.setString(1, t.getNombre());
			statement.setDate(2, t.getFechaNacimiento());
			statement.setInt(3, t.getPuntos());
			statement.setInt(4, t.getTitulosGS());
			statement.setInt(5, t.getIdTenista());
			 
			valores = statement.executeUpdate();
			
			
			
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
		return valores==1 ? true:false;
	}
	
	//BORRAR
	public boolean borrar(int idTenista) { //Statement normal
		
		int valores = 0; // 1 o 0
		
		try {
			String sql = "DELETE FROM tenista WHERE idTenista ="+idTenista;
			Statement statement = conexion.createStatement();
			valores=statement.executeUpdate(sql);

		} catch (SQLException e) {
			System.out.println(e);
		}
		return valores==1 ? true:false;
	}
	
	
	
	
	//DEVUELVE TODOS LOS TENISTAS
	public List<Tenista> todosTenistas(boolean ordenPuntos) {
		
		List<Tenista> tenistas=new ArrayList<Tenista>();
		int idTenista=0,puntos=0,titulosGS=0;
		String nombre="";
		Date fechaNacimiento=null;
		Tenista t = null;
		try {
			
			String sql="SELECT * from tenista";
			//si ordenPuntos es true, deben devolver todos los tenistas ordenados por puntos de mayor a menor
			if(ordenPuntos) {
				//concatenamos a la sentencia sql
				sql+=" ORDER BY puntos desc";
			}
			Statement statement=conexion.createStatement();
			ResultSet resultados=statement.executeQuery(sql);
			
			//leemos el resultSet
			while(resultados.next()) {
				idTenista = resultados.getInt(1);
				nombre = resultados.getString(2);
				fechaNacimiento = resultados.getDate(3);
				puntos = resultados.getInt(4);
				titulosGS = resultados.getInt(5);
				
				t=new Tenista(idTenista,nombre,fechaNacimiento,puntos,titulosGS);
				tenistas.add(t); // añadimos este tenista al ArrayList de Tenistas
				
			}

		} catch (SQLException e) {
			System.out.println(e);
		}
		return tenistas;
	}
	
	//devuelve todos los tenistas que tengas un NUMERO de titulos q pasamos x parametros: ej: 22, pues nadal y djokovic
	public List<Tenista> todosTenistas(int minTitulosGS, boolean ordenFechaNacimiento){
	
		List<Tenista> tenistas=new ArrayList<Tenista>();
		int idTenista=0,puntos=0,titulosGS=0;
		String nombre="";
		Date fechaNacimiento=null;
		Tenista t = null;
		try {
			
			String sql="SELECT * from tenista WHERE titulosGS>="+minTitulosGS+" ORDER BY fechaNacimiento ";
			if(ordenFechaNacimiento) { //si es true es descending(de mas joven a mas viejo) 2002 es mayor q 1998
				sql+="desc";
			}else { //se puede omitir el ELSE porque por defecto es ASCENDING
				sql+="asc";
			}
			
			Statement statement=conexion.createStatement();
			ResultSet resultados=statement.executeQuery(sql);
			
			//leemos el resultSet
			while(resultados.next()) {
				idTenista = resultados.getInt(1);
				nombre = resultados.getString(2);
				fechaNacimiento = resultados.getDate(3);
				puntos = resultados.getInt(4);
				titulosGS = resultados.getInt(5);
				
				t=new Tenista(idTenista,nombre,fechaNacimiento,puntos,titulosGS);
				tenistas.add(t); // añadimos este tenista al ArrayList de Tenistas
				
			}

		} catch (SQLException e) {
			System.out.println(e);
		}
		return tenistas;
	}
}
