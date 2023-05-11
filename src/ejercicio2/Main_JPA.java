package ejercicio2;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

public class Main_JPA {
	/*El código de esta clase resuelve el apartado c del segundo ejercicio del primer examen del tercer trimestre de Programación. Curso 21/22*/
	public static void main(String[] args) {
		EntityManagerFactory eFactory=Persistence.createEntityManagerFactory("gestorEmpleados");
		EntityManager eManager=eFactory.createEntityManager();
		//Creamos cinco objetos empleado
		Empleado e1=new Empleado("Luis", "Marketing", "Valladolid", 30, 2000);
		Empleado e2=new Empleado("Carlos", "Informatica", "Valladolid", 33, 3100);
		Empleado e3=new Empleado("Laura", "Soporte", "Palencia", 54, 2500);
		Empleado e4=new Empleado("Mario", "Informatica", "Segovia", 41, 1900);
		Empleado e5=new Empleado("Jose", "Direccion", "Valladolid", 35, 2000);
		//Insertamos los cinco objetos en la base de datos
		insertar(eManager, e1);
		insertar(eManager, e2);
		insertar(eManager, e3);
		insertar(eManager, e4);
		insertar(eManager, e5);
		//Subimos un 15% el sueldo de todos los empleados del departamento de informática de Valladolid
		actualizarSueldo(eManager, "Informatica", "Valladolid", 15);
		//Obtenemos todos los empleados ordenados por sueldo descenciente. El empleado que ocupe la posición 0 de la lista será el que más gane.
		List<Empleado> lista=todosEmpleados(eManager, true);
		
		int idEmpleadoMayorSueldo=lista.get(0).getIdEmpleado();		
		borrarEmpleado(eManager, idEmpleadoMayorSueldo);
	}

	public static void insertar(EntityManager em, Empleado e) {
		EntityTransaction eTransaction=em.getTransaction();
		eTransaction.begin();
		em.persist(e);
		eTransaction.commit();
	}
	
	public static boolean borrarEmpleado(EntityManager em, int idEmpleado) {
		boolean puede=false;
		EntityTransaction eTransaction=em.getTransaction();
		String jpql="DELETE FROM Empleado e WHERE e.idEmpleado= ?1";
		Query query=em.createQuery(jpql);
		query.setParameter(1, idEmpleado);
		eTransaction.begin();
		int lineas=query.executeUpdate();
		eTransaction.commit();
		if (lineas>0) {
			puede=true;
		}
		return puede;
	}
	
	public static List<Empleado> buscarNombre(EntityManager em, String nombre) {
		String jpql="SELECT e FROM Empleado e WHERE e.nombre LIKE :nombre";
		Query query=em.createQuery(jpql);
		query.setParameter("nombre", "%"+nombre+"%");
		List<Empleado> resultado=query.getResultList();
		return resultado;
	}
	
	public static List<Empleado> buscarDepartamento(EntityManager em, String departamento) {
		String jpql="SELECT e FROM Empleado e WHERE e.departamento='"+departamento+"'";
		Query query=em.createQuery(jpql);
		List<Empleado> resultado=query.getResultList();
		return resultado;
	}
	
	public static List<Empleado> todosEmpleados(EntityManager em, boolean ordenSueldo) {
		String jpqlD="SELECT e FROM Empleado e ORDER BY e.sueldo DESC", jpqlA="SELECT e FROM Empleado e ORDER BY e.sueldo ASC";
		Query query=null;
		if (ordenSueldo) {
			query=em.createQuery(jpqlD);
		} else {
			query=em.createQuery(jpqlA);
		}
		List<Empleado> resultado=query.getResultList();
		return resultado;
	}
	
	public static boolean actualizarSede(EntityManager em, int idEmpleado, String nuevaSede) {
		boolean exito=false;
		EntityTransaction eTransaction=em.getTransaction();
		Query query=em.createNamedQuery("actualizarSede");
		query.setParameter(1, nuevaSede);
		query.setParameter(2, idEmpleado);
		eTransaction.begin();
		int actualizados=query.executeUpdate();
		eTransaction.commit();
		if (actualizados>0) {
			exito=true;
		}
		return exito;
	}
	
	public static int actualizarSueldo(EntityManager em, String departamento, String sede, double porcentaje) {
		int empleadosActualizados=0;
		EntityTransaction eTransaction=em.getTransaction();
		Query query=em.createNamedQuery("actualizarSueldo");
		query.setParameter("porcentaje", porcentaje);
		query.setParameter("departamento", departamento);
		query.setParameter("sede", sede);
		eTransaction.begin();
		empleadosActualizados=query.executeUpdate();
		eTransaction.commit();
		return empleadosActualizados;
	}
}
