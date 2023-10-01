package Estructura.Proxies;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import Estructura.Message.*;

/*El DynamicProxy no va a permitir interceptar las invocaciones a los métodos de la clase InsultService.
 * El InsultService proporciona los mismos métodos que un InsultActor, pero en realidad cuando el DynamicProxy
 * intercepta sus métodos, se comunica con un InsultActor que da los resultados como si fuese una comunicación
 * orientada a objetos con el objeto de la clase InsultService.
 */
public class DynamicProxy implements InvocationHandler {
    private Object target = null;
    private ClientProxy prox;

    public static Object intercept (Object target, ClientProxy prox){
        Class targetClass = target.getClass();
        Class interfaces[] = targetClass.getInterfaces();
        return Proxy. newProxyInstance(targetClass.getClassLoader(),
                interfaces, new DynamicProxy(target, prox));
    }
    private DynamicProxy(Object target, ClientProxy prox){
        this.target = target;
        this.prox=prox;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable{
        Object invocationResult = null;
       
            if(method.getName().equals("getInsult")){
                this.prox.send(new GetInsultMessage(null));
                invocationResult = this.prox.recieve().getMessage();
            }

            else if(method.getName().equals("getAllInsult")){
                this.prox.send(new GetAllInsultsMessage(null));
                invocationResult = this.prox.recieve().getMessage();
            }

            else if(method.getName().equals("addInsult")){
                String mes = args[0].toString();
                this.prox.send(new AddInsultMessage(null, mes));
            }   
            return invocationResult;
        }        
    }


