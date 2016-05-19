 package ec.com.doc.ele.locator;
 
 import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import ec.com.doc.ele.excepcion.ComEleExcepcion;
import ec.com.doc.ele.servicio.EjbService;
import ec.com.doc.ele.util.log.LogGenerico;
 
 public abstract class ServiceLocator
 {
  private TipoEjb ejbViewType;
   private Properties properties;
   protected InitialContext context;
 
   protected ServiceLocator()
   {
     this.ejbViewType = TipoEjb.valueOf("REMOTE");
     this.properties = new Properties();
     this.properties.put("java.naming.factory.initial", "org.jnp.interfaces.NamingContextFactory");
     this.properties.put("java.naming.factory.url.pkgs", "org.jboss.naming:org.jnp.interfaces");
     this.properties.put("java.naming.provider.url", getProviderURL());
     try {
       newContext(this.properties);
     } catch (NamingException e) {
       System.out.println("Error al obtener el contexto JEE con la URL del proveedor:" + getProviderURL()+ e);
     }
   }
 
   protected InitialContext newContext(Properties env)
     throws NamingException
   {
	   return this.context = new InitialContext(env);
   }
 
   public void shutdown()
   {
     if (this.context == null) return;
     try {
       this.context.close();
     } catch (NamingException ne) {
       LogGenerico.getLogger().error("Error al obtener el initial context para los ejbs", ne);
     } finally {
       this.context = null;
     }
   }
 
    public <T> T getBean(String requiredBean, Class<T> clazz) throws ComEleExcepcion {
     return getBean(requiredBean, clazz, this.ejbViewType);
   }
 
   public <T> T getSimpleBean(String applicationName, String requiredBean, Class<T> clazz) throws ComEleExcepcion {
     return getSimpleBean(applicationName, requiredBean, clazz, this.ejbViewType);
   }
 
   @SuppressWarnings("unchecked")
public <T> T getSimpleBean(String applicationName, String requiredBean, Class<T> clazz, TipoEjb type) throws ComEleExcepcion
   {
     StringBuilder bean = new StringBuilder();
     if (applicationName != null) {
       bean.append(applicationName).append("/");
     }
     bean.append(requiredBean);
     try {
       if (type.equals(TipoEjb.REMOTE)) {
         bean.append("/remote");
       }
       return (T) this.context.lookup(bean.toString());
     }
     catch (NamingException e) {
       throw new ComEleExcepcion("No se puede obtener el ejb:" + requiredBean, e);
     }
   }
 
   @SuppressWarnings("unchecked")
public <T> T getBean(String requiredBean, Class<T> clazz, TipoEjb type)
     throws ComEleExcepcion
   {
     StringBuilder jndi = new StringBuilder();
     Properties props;
     if (type.equals(TipoEjb.REMOTE)) {
       props = new Properties();
       props.put("java.naming.factory.initial","org.jnp.interfaces.NamingContextFactory");
       props.put("java.naming.factory.url.pkgs", "org.jboss.naming:org.jnp.interfaces");
       props.put("java.naming.provider.url", getProviderURL());
       jndi.append(requiredBean).append("/remote");
     }
     else {
       props = null;
       jndi.append(requiredBean);
     }
     try
     {
       Context context;
       if (props == null){
         context = new InitialContext();
       }
       else {
         context = new InitialContext(props);
       }
       return (T)context.lookup(jndi.toString());
     } catch (NamingException e) {
       throw new ComEleExcepcion("No se puede obtener el ejb:" + requiredBean, e);
     }
   }
 
   protected abstract String getApplicationName();
 
   protected abstract String getServiceBeanName();
 
   protected abstract String getProviderURL();
 
   public EjbService getServiceBean() throws NamingException
   {
     StringBuilder beanName = new StringBuilder();
     if (getApplicationName() != null) {
       beanName.append(getApplicationName()).append("/");
     }
     beanName.append(getServiceBeanName());
     
     beanName.append("/remote");
     
     System.out.println(beanName);
     return ((EjbService)this.context.lookup(beanName.toString()));
   }
 }